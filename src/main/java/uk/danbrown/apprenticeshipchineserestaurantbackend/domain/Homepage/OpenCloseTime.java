package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;

import java.time.LocalTime;

public record OpenCloseTime(LocalTime openingTime, LocalTime closingTime, boolean closed) {
}
