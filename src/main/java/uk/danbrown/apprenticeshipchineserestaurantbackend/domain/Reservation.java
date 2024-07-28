package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.time.LocalDateTime;

public record Reservation(String username, LocalDateTime reservationTime, Integer numberOfPeople) {
}
