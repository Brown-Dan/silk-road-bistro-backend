package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.OpeningHoursService;

import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.utils.MvcResultAssert.assertThat;

@WebMvcTest(OpeningHoursController.class)
public class OpeningHoursControllerTest extends ControllerTestBase {

    @MockBean
    private OpeningHoursService openingHoursService;

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
