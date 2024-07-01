package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.utils.LoggerAssert;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;

@JsonTest
public class OpeningHoursMapperTest {

    @RegisterExtension
    private final LoggerAssert loggerAssert = new LoggerAssert();

    @Autowired
    private ObjectMapper objectMapper;

    private OpeningHoursMapper openingHoursMapper;

    @BeforeEach
    void setUp() {
        openingHoursMapper = new OpeningHoursMapper(objectMapper);
    }

    @Test
    void toJsonString_givenOpeningHours_shouldMapToJson() {
        String result = openingHoursMapper.toJsonString(getOpeningHours());

        assertThat(result).isEqualTo(getOpeningHoursJson());
    }

    @Test
    void toDomain_givenOpeningHoursJson_shouldMapToOpeningHours() {
        OpeningHours result = openingHoursMapper.toDomain(getOpeningHoursJson());

        assertThat(result).isEqualTo(getOpeningHours());
    }

    @Test
    void toDomain_givenInvalidOpeningHoursJson_shouldLogErrorAndNullify() {
        OpeningHours result = openingHoursMapper.toDomain("{");

        loggerAssert.assertError("Failed to map opening hours - {");
        assertThat(result).isNull();
    }

    @Test
    void toJsonString_givenFailureWritingToString_shouldLogAndReturnNull() throws JsonProcessingException {
        ObjectMapper mockedObjectMapper = mock();
        OpeningHoursMapper mockedOpeningHoursMapper = new OpeningHoursMapper(mockedObjectMapper);

        when(mockedOpeningHoursMapper.toJsonString(any())).thenThrow(JsonProcessingException.class);

        String result = mockedOpeningHoursMapper.toJsonString(getOpeningHours());

        verify(mockedObjectMapper).writeValueAsString(getOpeningHours());

        loggerAssert.assertError("Failed to map opening hours - OpeningHours[monday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=false], tuesday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=false], wednesday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=false], thursday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=false], friday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=false], saturday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=false], sunday=OpenCloseTime[openingTime=07:00, closingTime=19:00, closed=true]]");
        assertThat(result).isNull();
    }

    private String getOpeningHoursJson() {
        return "{\"monday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"tuesday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"wednesday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"thursday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"friday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"saturday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"sunday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":true}}";
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
}
