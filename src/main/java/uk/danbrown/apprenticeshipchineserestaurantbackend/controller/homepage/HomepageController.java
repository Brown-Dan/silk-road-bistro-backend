package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.BiographyResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ImagesResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.HomepageService;

@RestController
@RequestMapping("/homepage")
public class HomepageController {

    private final HomepageService homepageService;

    public HomepageController(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    @GetMapping
    public ResponseEntity<Homepage> getHomepage() throws EntityNotFoundException {
        return ResponseEntity.ok(homepageService.getHomepage());
    }

    @PostMapping("/address")
    public ResponseEntity<Location> updateAddress(@RequestBody Location address) throws FailureInsertingEntityException {
        return ResponseEntity.ok(homepageService.updateAddress(address));
    }

    @PostMapping("/biography")
    public ResponseEntity<BiographyResource> updateBiography(@RequestBody BiographyResource biography) throws FailureInsertingEntityException {
        return ResponseEntity.ok(new BiographyResource(homepageService.updateBiography(biography.biography())));
    }

    @PostMapping("/images")
    public ResponseEntity<ImagesResource> updateImages(@RequestBody ImagesResource images) throws FailureInsertingEntityException {
        return ResponseEntity.ok(new ImagesResource(homepageService.updateImages(images.images())));
    }

    @DeleteMapping("/images")
    public ResponseEntity<?> deleteImage(@RequestBody String imageUrl) throws FailureInsertingEntityException, EntityNotFoundException {
        homepageService.deleteImage(imageUrl);
        return ResponseEntity.noContent().build();
    }
}
