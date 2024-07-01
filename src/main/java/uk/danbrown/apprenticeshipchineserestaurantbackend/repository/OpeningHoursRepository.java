package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.OpeningHoursMapper;

import java.util.Optional;

import static uk.co.autotrader.generated.tables.OpeningHours.OPENING_HOURS;

@Repository
public class OpeningHoursRepository {

    private final DSLContext db;
    private final OpeningHoursMapper openingHoursMapper;

    public OpeningHoursRepository(DSLContext db, OpeningHoursMapper openingHoursMapper) {
        this.db = db;
        this.openingHoursMapper = openingHoursMapper;
    }

    public Optional<OpeningHours> getOpeningHours() {
        String openingHoursJson = db.selectFrom(OPENING_HOURS).fetchOneInto(String.class);

        return Optional.ofNullable(openingHoursJson).map(openingHoursMapper::toDomain);
    }

    public OpeningHours insertOpeningHours(OpeningHours openingHours) {
        String insertedOpeningHours = db.insertInto(OPENING_HOURS)
                .set(OPENING_HOURS.OPENING_HOURS_, openingHoursMapper.toJsonString(openingHours))
                .returningResult().fetchOneInto(String.class);

        return openingHoursMapper.toDomain(insertedOpeningHours);
    }
}
