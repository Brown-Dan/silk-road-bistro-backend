package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.OpeningHoursService;

import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.utils.MvcResultAssert.assertThat;

@WebMvcTest(OpeningHoursController.class)
public class OpeningHoursControllerTest extends ControllerTestBase {

    @MockBean
    private OpeningHoursService openingHoursService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getOpeningHours_givenOpeningHoursExist_shouldReturnOpeningHours() {
        when(openingHoursService.getOpeningHours()).thenReturn(Optional.of(getOpeningHours()));

        MvcResult mvcResult = get("/opening-hours");

        verify(openingHoursService).getOpeningHours();

        assertThat(mvcResult).hasStatus(HttpStatus.OK).hasBody(getOpeningHoursJson());
    }

    @Test
    void getOpeningHours_givenOpeningHoursDoNotExist_shouldReturn404() {
        when(openingHoursService.getOpeningHours()).thenReturn(Optional.empty());

        MvcResult mvcResult = get("/opening-hours");

        verify(openingHoursService).getOpeningHours();

        assertThat(mvcResult).hasStatus(HttpStatus.NOT_FOUND).hasBody("{\"errors\":[{\"key\":\"ENTITY_NOT_FOUND\",\"message\":\"Opening Hours Not Found\"}]}");
    }

    @Test
    void createOpeningHours_givenValidOpeningHours_shouldReturnCreatedOpeningHours() throws FailureInsertingEntityException {
        when(openingHoursService.insertOpeningHours(any())).thenReturn(getOpeningHours());

        MvcResult mvcResult = post("/opening-hours", getOpeningHoursJson());

        verify(openingHoursService).insertOpeningHours(getOpeningHours());

        assertThat(mvcResult).hasStatus(HttpStatus.CREATED).hasBody(getOpeningHoursJson());
    }

    @Test
    void createOpeningHours_givenOpeningHoursWithValidTimesButClosedStatus_shouldReturnBadRequest() throws JsonProcessingException {
        OpeningHours invalidOpeningHours = getOpeningHours().cloneBuilder()
                .withMonday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(18, 0, 0), true))
                .build();

        MvcResult mvcResult = post("/opening-hours", objectMapper.writeValueAsString(invalidOpeningHours));

        verifyNoInteractions(openingHoursService);

        assertThat(mvcResult).hasStatus(HttpStatus.BAD_REQUEST).hasBody("{\"errors\":[{\"key\":\"INVALID_REQUEST_BODY\",\"message\":\"'closed' must not be true if 'openingTime' and 'closingTime' are provided.\"}]}");
    }

    @Test
    void createOpeningHours_givenOpeningTimeBeforeClosingTime_shouldReturnBadRequest() throws JsonProcessingException {
        OpeningHours invalidOpeningHours = getOpeningHours().cloneBuilder()
                .withMonday(new OpenCloseTime(LocalTime.of(18, 0, 0), LocalTime.of(7, 0, 0), false))
                .build();

        MvcResult mvcResult = post("/opening-hours", objectMapper.writeValueAsString(invalidOpeningHours));

        verifyNoInteractions(openingHoursService);

        assertThat(mvcResult).hasStatus(HttpStatus.BAD_REQUEST).hasBody("{\"errors\":[{\"key\":\"INVALID_REQUEST_BODY\",\"message\":\"'openingTime' must take place before 'closingTime'.\"}]}");
    }

    @Test
    void createOpeningHours_givenOpeningTimeProvidedButClosingTimeNotProvided_shouldReturnBadRequest() throws JsonProcessingException {
        OpeningHours invalidOpeningHours = getOpeningHours().cloneBuilder()
                .withMonday(new OpenCloseTime(LocalTime.of(18, 0, 0), null, false))
                .build();

        MvcResult mvcResult = post("/opening-hours", objectMapper.writeValueAsString(invalidOpeningHours));

        verifyNoInteractions(openingHoursService);

        assertThat(mvcResult).hasStatus(HttpStatus.BAD_REQUEST).hasBody("{\"errors\":[{\"key\":\"INVALID_REQUEST_BODY\",\"message\":\"'openingTime' and 'closingTime' must both be provided.\"}]}");
    }

    @Test
    void createOpeningHours_givenClosingTimeProvidedButOpeningTimeNotProvided_shouldReturnBadRequest() throws JsonProcessingException {
        OpeningHours invalidOpeningHours = getOpeningHours().cloneBuilder()
                .withMonday(new OpenCloseTime(LocalTime.of(18, 0, 0), null, false))
                .build();

        MvcResult mvcResult = post("/opening-hours", objectMapper.writeValueAsString(invalidOpeningHours));

        verifyNoInteractions(openingHoursService);

        assertThat(mvcResult).hasStatus(HttpStatus.BAD_REQUEST).hasBody("{\"errors\":[{\"key\":\"INVALID_REQUEST_BODY\",\"message\":\"'openingTime' and 'closingTime' must both be provided.\"}]}");
    }

    @Test
    void createOpeningHours_givenNeitherOpeningTimeOrClosingTimeProvided_shouldReturnBadRequest() throws JsonProcessingException {
        OpeningHours invalidOpeningHours = getOpeningHours().cloneBuilder()
                .withMonday(new OpenCloseTime(null, null, false))
                .build();

        MvcResult mvcResult = post("/opening-hours", objectMapper.writeValueAsString(invalidOpeningHours));

        verifyNoInteractions(openingHoursService);

        assertThat(mvcResult).hasStatus(HttpStatus.BAD_REQUEST).hasBody("{\"errors\":[{\"key\":\"INVALID_REQUEST_BODY\",\"message\":\"Either 'closed' must be true or 'openingTime' and 'closingTime' must be provided.\"}]}");
    }

    private OpeningHours getOpeningHours() {
        return anOpeningHours()
                .withMonday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withTuesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withWednesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withThursday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withFriday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withSaturday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withSunday(new OpenCloseTime(null, null, true))
                .build();
    }

    private String getOpeningHoursJson() {
        return "{\"monday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"tuesday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"wednesday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"thursday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"friday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"saturday\":{\"openingTime\":\"07:00:00\",\"closingTime\":\"19:00:00\",\"closed\":false},\"sunday\":{\"openingTime\":null,\"closingTime\":null,\"closed\":true}}";
    }
}
