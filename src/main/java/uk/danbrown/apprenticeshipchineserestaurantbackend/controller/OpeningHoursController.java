package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.OpeningHoursService;

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
    public ResponseEntity<OpeningHours> createOpeningHours(@RequestBody OpeningHours openingHours) {
        // todo should we validate here or on the frontend?
        return ResponseEntity.ok(openingHoursService.insertOpeningHours(openingHours));
    }
}
