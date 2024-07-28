package uk.danbrown.apprenticeshipchineserestaurantbackend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.HomepageRepository;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.ArticlesService;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.HomepageService;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OfferService;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OpeningHoursService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage.Builder.aHomepage;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours.Builder.anOpeningHours;

@ExtendWith(MockitoExtension.class)
public class HomepageServiceTest {

    @Mock
    private ArticlesService articlesService;

    @Mock
    private OpeningHoursService openingHoursService;

    @Mock
    private HomepageRepository homepageRepository;

    @Mock
    private OfferService offerService;

    private HomepageService homepageService;

    @BeforeEach
    void setUp() {
        homepageService = new HomepageService(
                openingHoursService,
                articlesService,
                homepageRepository,
                offerService
        );
    }

    @Test
    void getHomepage_shouldReturnHomepage() throws EntityNotFoundException {
        when(homepageRepository.getHomepage()).thenReturn(getEmptyHomepage());
        when(articlesService.getArticles(any())).thenReturn(singletonList(getArticle()));
        when(openingHoursService.getOpeningHours()).thenReturn(getOpeningHours());
        when(offerService.getOffers(any(), anyBoolean())).thenReturn(getOffers());

        Homepage result = homepageService.getHomepage();

        verify(articlesService).getArticles(3);
        verify(offerService).getOffers(3, false);

        assertThat(result).isEqualTo(getHomepage());

    }

    private Homepage getHomepage() {
        return aHomepage()
                .withOpeningHours(getOpeningHours())
                .withArticles(singletonList(getArticle()))
                .withOffers(getOffers())
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

    private Homepage getEmptyHomepage() {
        return aHomepage()
                .build();
    }

    private List<Offer> getOffers() {
        return singletonList(new Offer("title", "content", true, 10.0, 50, "OFFERCODE"));
    }
}
