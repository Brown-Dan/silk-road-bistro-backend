package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import org.springframework.stereotype.Component;
import uk.co.autotrader.generated.tables.pojos.OpenCloseTimeEntity;
import uk.co.autotrader.generated.tables.pojos.OpeningHoursEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;

@Component
public class OpeningHoursMapper {

    public OpeningHours toDomain(OpeningHoursEntity openingHoursEntity) {
        return anOpeningHours()
                .withMonday(mapOpenCloseTimeToDomain(openingHoursEntity.getMonday()))
                .withTuesday(mapOpenCloseTimeToDomain(openingHoursEntity.getTuesday()))
                .withWednesday(mapOpenCloseTimeToDomain(openingHoursEntity.getWednesday()))
                .withThursday(mapOpenCloseTimeToDomain(openingHoursEntity.getThursday()))
                .withFriday(mapOpenCloseTimeToDomain(openingHoursEntity.getFriday()))
                .withSaturday(mapOpenCloseTimeToDomain(openingHoursEntity.getSaturday()))
                .withSunday(mapOpenCloseTimeToDomain(openingHoursEntity.getSunday()))
                .build();
    }

    public OpeningHoursEntity toEntity(OpeningHours openingHours) {
        return new OpeningHoursEntity(
                mapOpenCloseTimeToEntity(openingHours.monday()),
                mapOpenCloseTimeToEntity(openingHours.tuesday()),
                mapOpenCloseTimeToEntity(openingHours.wednesday()),
                mapOpenCloseTimeToEntity(openingHours.thursday()),
                mapOpenCloseTimeToEntity(openingHours.friday()),
                mapOpenCloseTimeToEntity(openingHours.saturday()),
                mapOpenCloseTimeToEntity(openingHours.sunday())
        );
    }

    private OpenCloseTimeEntity mapOpenCloseTimeToEntity(OpenCloseTime openCloseTime) {
        return new OpenCloseTimeEntity(openCloseTime.openingTime(),
                openCloseTime.closingTime(),
                openCloseTime.closed());
    }

    private OpenCloseTime mapOpenCloseTimeToDomain(OpenCloseTimeEntity openCloseTimeEntity) {
        return new OpenCloseTime(openCloseTimeEntity.getOpeningTime(),
                openCloseTimeEntity.getClosingTime(),
                openCloseTimeEntity.getClosed());
    }
}
