package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Builder.aHomepage;

@Service
public class HomepageService {

    private final OpeningHoursService openingHoursService;
    private final ArticlesService articlesService;

    public HomepageService(OpeningHoursService openingHoursService, ArticlesService articlesService) {
        this.openingHoursService = openingHoursService;
        this.articlesService = articlesService;
    }

    public Homepage getHomepage() {
        return aHomepage()
                .withArticles(articlesService.getArticles(3))
                .withOpeningHours(openingHoursService.getOpeningHours().orElse(null))
                .build();
    }
}
