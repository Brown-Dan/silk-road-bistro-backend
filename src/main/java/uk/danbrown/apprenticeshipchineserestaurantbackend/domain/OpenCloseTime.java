package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.time.LocalTime;

public record OpenCloseTime(LocalTime openingTime, LocalTime closingTime, boolean closed) {
}
