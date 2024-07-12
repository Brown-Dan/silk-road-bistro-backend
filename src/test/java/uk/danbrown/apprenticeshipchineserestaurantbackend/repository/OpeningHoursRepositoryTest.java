package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContext;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.OpeningHoursMapper;
import uk.danbrown.apprenticeshipchineserestaurantbackend.utils.DatabaseHelper;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;

@JsonTest
@ExtendWith(MockitoExtension.class)
public class OpeningHoursRepositoryTest {

    private static final DatabaseHelper dbHelper = new DatabaseHelper();

    @MockBean
    private RequestContextManager requestContextManager;

    @Autowired
    private ObjectMapper objectMapper;
    private OpeningHoursRepository openingHoursRepository;

    @BeforeEach
    void setUp() {
        when(requestContextManager.getRequestContext()).thenReturn(new RequestContext("123"));
        openingHoursRepository = new OpeningHoursRepository(dbHelper.getDslContext(), new OpeningHoursMapper(objectMapper), requestContextManager);
    }

    @AfterEach
    void tearDown() {
        dbHelper.clearTables();
    }

    @Test
    void insertOpeningHours_givenOpeningHours_shouldInsertIntoDatabase() throws FailureInsertingEntityException {
        OpeningHours result = openingHoursRepository.insertOpeningHours(getOpeningHours());

        assertThat(result).isEqualTo(getOpeningHours());
    }

    @Test
    void getOpeningHours_givenOpeningHoursExist_shouldReturnOpeningHours() {
        dbHelper.insertOpeningHoursJson(getOpeningHoursJson(), "123");

        Optional<OpeningHours> result = openingHoursRepository.getOpeningHours();

        assertThat(result).hasValue(getOpeningHours());
    }

    @Test
    void getOpeningHours_givenOpeningHoursDoNotExist_shouldReturnEmptyOptional() {
        Optional<OpeningHours> result = openingHoursRepository.getOpeningHours();

        assertThat(result).isEmpty();
    }

    private OpeningHours getOpeningHours() {
        return anOpeningHours()
                .withMonday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withTuesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withWednesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withThursday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withFriday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withSaturday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withSunday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), true))
                .build();
    }

    private String getOpeningHoursJson() {
        return "{\"monday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"tuesday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"wednesday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"thursday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"friday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"saturday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"sunday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":true}}";
    }
}

