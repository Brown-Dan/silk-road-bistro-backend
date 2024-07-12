package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.OpeningHoursRepository;

import java.util.Optional;

@Service
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;

    public OpeningHoursService(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }

    public OpeningHours insertOpeningHours(OpeningHours openingHours) throws FailureInsertingEntityException {
        return openingHoursRepository.insertOpeningHours(openingHours);
    }

    public Optional<OpeningHours> getOpeningHours() {
        return openingHoursRepository.getOpeningHours();
    }
}
