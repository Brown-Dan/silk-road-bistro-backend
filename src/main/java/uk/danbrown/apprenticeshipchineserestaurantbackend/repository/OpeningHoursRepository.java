package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.OpeningHoursEntity;
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
        OpeningHoursEntity openingHoursEntity = db.selectFrom(OPENING_HOURS).fetchOneInto(OpeningHoursEntity.class);

        return Optional.ofNullable(openingHoursEntity).map(openingHoursMapper::toDomain);
    }

    public OpeningHoursEntity insertOpeningHours(OpeningHours openingHours) {
        OpeningHoursEntity openingHoursEntity = openingHoursMapper.toEntity(openingHours);

        return db.insertInto(OPENING_HOURS)
                .set(OPENING_HOURS.MONDAY, (Record) openingHoursEntity.getMonday())
                .set( OPENING_HOURS.TUESDAY, (Record) openingHoursEntity.getTuesday())
                .set(OPENING_HOURS.WEDNESDAY, (Record) openingHoursEntity.getWednesday())
                .set( OPENING_HOURS.FRIDAY, (Record) openingHoursEntity.getFriday())
                .set( OPENING_HOURS.SATURDAY, (Record) openingHoursEntity.getSaturday())
                .set( OPENING_HOURS.SUNDAY, (Record) openingHoursEntity.getSunday())
                .returningResult().fetchOneInto(OpeningHoursEntity.class);
    }
}
