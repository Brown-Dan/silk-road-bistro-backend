package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
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
}
