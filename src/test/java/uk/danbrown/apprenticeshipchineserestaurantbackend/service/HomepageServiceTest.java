package uk.danbrown.apprenticeshipchineserestaurantbackend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Builder.aHomepage;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours.Builder.anOpeningHours;

@ExtendWith(MockitoExtension.class)
public class HomepageServiceTest {

    @Mock
    private ArticlesService articlesService;

    @Mock
    private OpeningHoursService openingHoursService;


    private HomepageService homepageService;

    @BeforeEach
    void setUp() {
        homepageService = new HomepageService(
                openingHoursService,
                articlesService
        );
    }

    @Test
    void getHomepage_shouldReturnHomepage() {
        when(articlesService.getArticles(any())).thenReturn(singletonList(getArticle()));
        when(openingHoursService.getOpeningHours()).thenReturn(Optional.of(getOpeningHours()));

        Homepage result = homepageService.getHomepage();

        verify(articlesService).getArticles(3);

        assertThat(result).isEqualTo(getHomepage());

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
