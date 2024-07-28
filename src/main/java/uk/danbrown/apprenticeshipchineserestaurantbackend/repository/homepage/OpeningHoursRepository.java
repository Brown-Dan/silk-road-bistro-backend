package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.OpeningHoursEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.OpeningHoursMapper;

import java.util.Optional;

import static uk.co.autotrader.generated.tables.OpeningHours.OPENING_HOURS;

@Repository
public class OpeningHoursRepository {

    private final DSLContext db;
    private final OpeningHoursMapper openingHoursMapper;
    private final RequestContextManager requestContextManager;

    public OpeningHoursRepository(DSLContext db, OpeningHoursMapper openingHoursMapper, RequestContextManager requestContextManager) {
        this.db = db;
        this.openingHoursMapper = openingHoursMapper;
        this.requestContextManager = requestContextManager;
    }

    public Optional<OpeningHours> getOpeningHours() {
        OpeningHoursEntity openingHoursJson = db.selectFrom(OPENING_HOURS)
                .where(OPENING_HOURS.ID.eq(requestContextManager.getRequestContext().currentId())).fetchOneInto(OpeningHoursEntity.class);

        return Optional.ofNullable(openingHoursJson).map(openingHoursEntity -> openingHoursMapper.toDomain(openingHoursEntity.getOpeningHours()));
    }

    public OpeningHours insertOpeningHours(OpeningHours openingHours) throws FailureInsertingEntityException {
        db.deleteFrom(OPENING_HOURS).execute();
        Optional<OpeningHoursEntity> insertedOpeningHours = db.insertInto(OPENING_HOURS)
                .set(OPENING_HOURS.OPENING_HOURS_, openingHoursMapper.toJsonString(openingHours))
                .set(OPENING_HOURS.ID, requestContextManager.getRequestContext().currentId())
                .returningResult().fetchOptionalInto(OpeningHoursEntity.class);

        return insertedOpeningHours
                .map(oh -> openingHoursMapper.toDomain(oh.getOpeningHours()))
                .orElseThrow(() -> new FailureInsertingEntityException(openingHours));

    }
}
