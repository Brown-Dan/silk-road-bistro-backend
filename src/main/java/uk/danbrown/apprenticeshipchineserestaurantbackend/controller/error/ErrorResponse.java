package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error;

import java.util.List;

public record ErrorResponse(List<Error> errors) {
}
