package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidRequestBodyException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OpeningHoursService;

import java.util.Optional;

@RestController
@RequestMapping("opening-hours")
public class OpeningHoursController {

    private final OpeningHoursService openingHoursService;

    public OpeningHoursController(OpeningHoursService openingHoursService) {
        this.openingHoursService = openingHoursService;
    }

    @GetMapping
    public ResponseEntity<OpeningHours> getOpeningHours() throws EntityNotFoundException {
        Optional<OpeningHours> openingHours = openingHoursService.getOpeningHours();

        return ResponseEntity.status(200).body((openingHours.orElseThrow(() ->
                new EntityNotFoundException("Opening Hours Not Found"))));
    }

    @PostMapping
    public ResponseEntity<OpeningHours> createOpeningHours(@RequestBody OpeningHours openingHours) throws InvalidRequestBodyException, FailureInsertingEntityException {
        validateOpeningHours(openingHours);
        return ResponseEntity.status(201).body(openingHoursService.insertOpeningHours(openingHours));
    }

    private void validateOpeningHours(OpeningHours openingHours) throws InvalidRequestBodyException {
        validateOpenCloseTime(openingHours.monday());
        validateOpenCloseTime(openingHours.tuesday());
        validateOpenCloseTime(openingHours.wednesday());
        validateOpenCloseTime(openingHours.thursday());
        validateOpenCloseTime(openingHours.friday());
        validateOpenCloseTime(openingHours.saturday());
        validateOpenCloseTime(openingHours.sunday());
    }

    private void validateOpenCloseTime(OpenCloseTime openCloseTime) throws InvalidRequestBodyException {
        if (openCloseTime.openingTime() != null && openCloseTime.closingTime() == null ||
            openCloseTime.closingTime() != null && openCloseTime.openingTime() == null) {
            throw new InvalidRequestBodyException("'openingTime' and 'closingTime' must both be provided.");
        }
        if (openCloseTime.closed() && (openCloseTime.openingTime() != null)) {
            throw new InvalidRequestBodyException("'closed' must not be true if 'openingTime' and 'closingTime' are provided.");
        }
        if (!openCloseTime.closed() && (openCloseTime.openingTime() == null)) {
            throw new InvalidRequestBodyException("Either 'closed' must be true or 'openingTime' and 'closingTime' must be provided.");
        }
        if (!openCloseTime.closed() && openCloseTime.openingTime().isAfter(openCloseTime.closingTime())) {
            throw new InvalidRequestBodyException("'openingTime' must take place before 'closingTime'.");
        }
    }
}
