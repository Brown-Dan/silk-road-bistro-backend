package uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.OfferRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getOffers(Integer limit, boolean includeDisabled) {
        List<Offer> offers = offerRepository.getOffers(limit, includeDisabled);
        offers.sort(new compareOffers());
        return offers;
    }

    public static class compareOffers implements Comparator<Offer>
    {
        @Override
        public int compare(Offer o1, Offer o2) {
            if (o1.discountPercentage() < o2.discountPercentage()) {
                return -1;
            } else if (o2.discountPercentage() < o1.discountPercentage()) {
                return 1;
            }
            return 0;
        }
    }

    public Offer insertOffer(Offer offer) throws FailureInsertingEntityException {
        return offerRepository.insertOffer(offer);
    }

    public void toggleOffer(String offerTitle) throws EntityNotFoundException {
        offerRepository.toggleOffer(offerTitle);
    }
}
