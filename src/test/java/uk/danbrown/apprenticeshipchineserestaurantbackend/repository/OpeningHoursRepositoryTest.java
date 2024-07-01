//package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.json.JsonTest;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.OpeningHoursMapper;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.utils.DatabaseHelper;
//
//import java.time.LocalTime;
//
//import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;
//
//@JsonTest
//public class OpeningHoursRepositoryTest {
//
//    private static final DatabaseHelper dbHelper = new DatabaseHelper();
//
//    private OpeningHoursRepository openingHoursRepository;
//
//    @BeforeEach
//    void setUp() {
//        openingHoursRepository = new OpeningHoursRepository(dbHelper.getDslContext(), new OpeningHoursMapper());
//    }
//
//    @Test
//    void insertOpeningHours_givenOpeningHours_shouldInsertAnd() {
//        OpeningHours openingHours = anOpeningHours()
//                .withMonday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false))
//                .withTuesday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false))
//                .withWednesday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false))
//                .withThursday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false))
//                .withFriday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false))
//                .withSaturday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false))
//                .withSunday(new OpenCloseTime(LocalTime.now(), LocalTime.now(), false)).build();
//
//        openingHoursRepository.insertOpeningHours(openingHours);
//    }
//
//
//    private OpeningHours getOpeningHours() {
//        return anOpeningHours()
//                .withMonday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
//                .withTuesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
//                .withWednesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
//                .withThursday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
//                .withFriday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
//                .withSaturday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
//                .withSunday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), true))
//                .build();
//    }
//}
//
