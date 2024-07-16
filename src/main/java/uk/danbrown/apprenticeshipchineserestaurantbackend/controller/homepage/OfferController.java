package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OfferService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getOffers(@RequestParam Optional<Integer> limit, @RequestParam boolean includeDisabled) {
        return ResponseEntity.ok(offerService.getOffers(limit.orElse(20), includeDisabled));
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) throws FailureInsertingEntityException {
        return ResponseEntity.status(201).body(offerService.insertOffer(offer));
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleEnabled(@RequestParam String offerTitle) throws EntityNotFoundException {
        offerService.toggleOffer(offerTitle);
        return ResponseEntity.noContent().build();
    }
}
