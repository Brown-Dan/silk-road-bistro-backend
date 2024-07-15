package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.homepage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidRequestBodyException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OpeningHoursService;

@RestController
@RequestMapping("opening-hours")
public class OpeningHoursController {

    private final OpeningHoursService openingHoursService;

    public OpeningHoursController(OpeningHoursService openingHoursService) {
        this.openingHoursService = openingHoursService;
    }

    @GetMapping
    public ResponseEntity<OpeningHours> getOpeningHours() throws EntityNotFoundException {
        OpeningHours openingHours = openingHoursService.getOpeningHours();

        return ResponseEntity.status(200).body(openingHours);
    }

    @PostMapping
    public ResponseEntity<OpeningHours> createOpeningHours(@RequestBody OpeningHours openingHours) throws InvalidRequestBodyException, FailureInsertingEntityException {
        validateOpeningHours(openingHours);
        return ResponseEntity.status(201).body(openingHoursService.insertOpeningHours(openingHours));
    }

    private void validateOpeningHours(OpeningHours openingHours) throws InvalidRequestBodyException {
        validateOpenCloseTime(openingHours.monday(), "Monday");
        validateOpenCloseTime(openingHours.tuesday(), "Tuesday");
        validateOpenCloseTime(openingHours.wednesday(), "Wednesday");
        validateOpenCloseTime(openingHours.thursday(), "Thursday");
        validateOpenCloseTime(openingHours.friday(), "Friday");
        validateOpenCloseTime(openingHours.saturday(), "Saturday");
        validateOpenCloseTime(openingHours.sunday(), "Saturday");
    }

    private void validateOpenCloseTime(OpenCloseTime openCloseTime, String day) throws InvalidRequestBodyException {
        if (openCloseTime == null) {
            throw new InvalidRequestBodyException("All fields must be provided for day - '%s'".formatted(day));
        }
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
            throw new InvalidRequestBodyException("opening time must take place before closing time");
        }
    }
}
