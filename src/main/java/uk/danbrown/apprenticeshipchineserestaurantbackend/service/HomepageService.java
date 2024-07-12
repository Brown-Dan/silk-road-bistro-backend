package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.HomepageRepository;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Builder.aHomepage;

@Service
public class HomepageService {

    private final OpeningHoursService openingHoursService;
    private final ArticlesService articlesService;
    private final HomepageRepository homepageRepository;

    public HomepageService(OpeningHoursService openingHoursService, ArticlesService articlesService, HomepageRepository homepageRepository) {
        this.openingHoursService = openingHoursService;
        this.articlesService = articlesService;
        this.homepageRepository = homepageRepository;
    }

    public Homepage getHomepage() throws EntityNotFoundException {
        Homepage homepage = homepageRepository.getHomepage();

        return homepage.cloneBuilder()
                .withArticles(articlesService.getArticles(3))
                .withOpeningHours(openingHoursService.getOpeningHours().orElse(null))
                .build();
    }
}
