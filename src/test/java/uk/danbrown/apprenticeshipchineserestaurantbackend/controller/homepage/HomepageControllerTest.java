package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.BiographyResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ImagesResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.HomepageService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage.Builder.aHomepage;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours.Builder.anOpeningHours;

@ExtendWith(MockitoExtension.class)
class HomepageControllerTest {

    @Mock
    private HomepageService homepageService;

    private HomepageController homepageController;

    @BeforeEach
    void setUp() {
        homepageController = new HomepageController(homepageService);
    }

    @Test
    void getHomepage_shouldCallHomepageService() throws EntityNotFoundException {
        when(homepageService.getHomepage()).thenReturn(getHomepage());

        ResponseEntity<Homepage> result = homepageController.getHomepage();

        verify(homepageService).getHomepage();
        assertThat(result).isEqualTo(ResponseEntity.ok(getHomepage()));
    }

    @Test
    void updateAddress_givenNewAddress_shouldCallHomepageService() throws FailureInsertingEntityException {
        when(homepageService.updateAddress(any())).thenReturn(getAddress());

        ResponseEntity<Location> result = homepageController.updateAddress(getAddress());

        verify(homepageService).updateAddress(getAddress());
        assertThat(result).isEqualTo(ResponseEntity.ok(getAddress()));
    }

    @Test
    void updateBiography_givenNewBiography_shouldCallHomepageService() throws FailureInsertingEntityException {
        when(homepageService.updateBiography(any())).thenReturn(getBiography());

        ResponseEntity<BiographyResource> result = homepageController.updateBiography(new BiographyResource(getBiography()));

        verify(homepageService).updateBiography(getBiography());
        assertThat(result).isEqualTo(ResponseEntity.ok(new BiographyResource(getBiography())));
    }

    @Test
    void updateImages_givenListOfImages_shouldCallHomepageService() throws FailureInsertingEntityException {
        when(homepageService.updateImages(any())).thenReturn(getImages());

        ResponseEntity<ImagesResource> result = homepageController.updateImages(new ImagesResource(getImages()));

        verify(homepageService).updateImages(getImages());
        assertThat(result).isEqualTo(ResponseEntity.ok(new ImagesResource(getImages())));
    }

    @Test
    void deleteImage_givenImageUrl_shouldCallHomepageService() throws FailureInsertingEntityException, EntityNotFoundException {
        String imageUrl = "url";

        ResponseEntity<?> result = homepageController.deleteImage(imageUrl);

        verify(homepageService).deleteImage(imageUrl);
        assertThat(result).isEqualTo(ResponseEntity.noContent().build());
    }

    private List<String> getImages() {
        return List.of("url");
    }

    private Location getAddress() {
        return new Location("address1", "address2", "city", "postcode", "country");
    }

    private Homepage getHomepage() {
        return aHomepage()
                .withImages(getImages())
                .withOpeningHours(getOpeningHours())
                .withArticles(singletonList(getArticle()))
                .withLocation(getAddress())
                .withBiography(getBiography())
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

    private String getBiography() {
        return "biography";
    }
}