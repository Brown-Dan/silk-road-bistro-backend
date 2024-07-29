package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OfferService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferControllerTest {

    @Mock
    private OfferService offerService;

    private OfferController offerController;


    @BeforeEach
    void setUp() {
        offerController = new OfferController(offerService);
    }

    @Test
    void getOffers_givenLimitAndDisabled_shouldReturnOffers() {
        when(offerService.getOffers(any(), anyBoolean())).thenReturn(getOffers());

        ResponseEntity<List<Offer>> result = offerController.getOffers(Optional.of(3), false);

        verify(offerService).getOffers(3, false);
        assertThat(result).isEqualTo(ResponseEntity.ok(getOffers()));
    }

    @Test
    void getOffers_givenNoLimitAndDisabled_shouldDefaultLimitTo20AndReturnOffers() {
        when(offerService.getOffers(any(), anyBoolean())).thenReturn(getOffers());

        ResponseEntity<List<Offer>> result = offerController.getOffers(Optional.empty(), false);

        verify(offerService).getOffers(20, false);
        assertThat(result).isEqualTo(ResponseEntity.ok(getOffers()));
    }

    @Test
    void createOffer_givenOffer_shouldCallOfferService() throws FailureInsertingEntityException {
        when(offerService.insertOffer(any())).thenReturn(getOffer());

        ResponseEntity<Offer> result = offerController.createOffer(getOffer());

        verify(offerService).insertOffer(getOffer());
        assertThat(result).isEqualTo(ResponseEntity.status(201).body(getOffer()));
    }

    @Test
    void toggleEnabled_givenOfferTitle_shouldCallOfferService() throws EntityNotFoundException {
        ResponseEntity<?> result = offerController.toggleEnabled("Offer 1");

        verify(offerService).toggleOffer("Offer 1");
        assertThat(result).isEqualTo(ResponseEntity.noContent().build());
    }

    private Offer getOffer() {
        return new Offer("Offer 1", "Description 1", true, 10.0, 5, "CODE");
    }

    private List<Offer> getOffers() {
        return List.of(
                new Offer("Offer 1", "Description 1", true, 10.0, 5, "CODE"),
                new Offer("Offer 2", "Description 2", false, 20.0, 10, "CODE2"),
                new Offer("Offer 3", "Description 3", true, 30.0, 15, "CODE3")
        );
    }
}