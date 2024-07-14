package uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.HomepageRepository;

@Service
public class HomepageService {

    private final OpeningHoursService openingHoursService;
    private final ArticlesService articlesService;
    private final HomepageRepository homepageRepository;
    private final OfferService offerService;

    public HomepageService(OpeningHoursService openingHoursService, ArticlesService articlesService, HomepageRepository homepageRepository, OfferService offerService) {
        this.openingHoursService = openingHoursService;
        this.articlesService = articlesService;
        this.homepageRepository = homepageRepository;
        this.offerService = offerService;
    }

    public Homepage getHomepage() throws EntityNotFoundException {
        Homepage homepage = homepageRepository.getHomepage();

        return homepage.cloneBuilder()
                .withArticles(articlesService.getArticles(3))
                .withOpeningHours(openingHoursService.getOpeningHours().orElse(null))
                .withOffers(offerService.getOffers(3))
                .build();
    }
}
