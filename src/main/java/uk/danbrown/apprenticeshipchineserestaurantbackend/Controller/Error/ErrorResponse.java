package uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Error;

import java.util.List;

public record ErrorResponse(List<Error> errors) {
}
