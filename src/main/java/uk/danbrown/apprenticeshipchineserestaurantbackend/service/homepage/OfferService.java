package uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.OfferRepository;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getOffers(Integer limit) {
        return offerRepository.getOffers(limit);
    }

    public Offer insertOffer(Offer offer) throws FailureInsertingEntityException {
        return offerRepository.insertOffer(offer);
    }
}
