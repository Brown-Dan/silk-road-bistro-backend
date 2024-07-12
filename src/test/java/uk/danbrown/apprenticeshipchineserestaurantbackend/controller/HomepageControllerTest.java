package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.HomepageService;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Builder.aHomepage;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.utils.MvcResultAssert.assertThat;

@WebMvcTest(HomepageController.class)
public class HomepageControllerTest extends ControllerTestBase {

    @MockBean
    HomepageService homepageService;

    @Test
    void getHomepage_shouldReturnHomepage() throws EntityNotFoundException {
        when(homepageService.getHomepage()).thenReturn(getHomepage());

        MvcResult mvcResult = get("/homepage");

        assertThat(mvcResult).hasStatus(HttpStatus.OK).hasBody(getHomepage());
    }

    private Homepage getHomepage() {
        return aHomepage()
                .withOpeningHours(getOpeningHours())
                .withArticles(singletonList(getArticle()))
                .build();
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

    private Article getArticle() {
        return anArticle()
                .withTitle("title")
                .withContent("content")
                .withDate(LocalDate.now())
                .build();
    }
}
