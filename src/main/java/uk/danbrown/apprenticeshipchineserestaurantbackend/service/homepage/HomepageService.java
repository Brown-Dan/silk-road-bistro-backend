package uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.HomepageRepository;

import java.util.List;

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
                .withArticles(articlesService.getArticles(3).reversed())
                .withOpeningHours(openingHoursService.getOpeningHours())
                .withOffers(offerService.getOffers(3, false))
                .build();
    }

    public Location updateAddress(Location address) throws FailureInsertingEntityException {
        return homepageRepository.updateAddress(address);
    }

    public String updateBiography(String biography) throws FailureInsertingEntityException {
        return homepageRepository.updateBiography(biography);
    }

    public List<String> updateImages(List<String> images) throws FailureInsertingEntityException {
        return homepageRepository.updateImages(images);
    }

    public void deleteImage(String imageUrl) throws FailureInsertingEntityException, EntityNotFoundException {
        homepageRepository.deleteImage(imageUrl);
    }
}
