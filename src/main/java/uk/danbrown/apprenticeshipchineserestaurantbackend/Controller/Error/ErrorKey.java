package uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Error;

import org.springframework.http.HttpStatus;

public enum ErrorKey {
    FAILURE_INSERTING_ENTITY(HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_ALREADY_EXISTS_WITH_ID(HttpStatus.CONFLICT);

    private final HttpStatus httpStatus;

    ErrorKey(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
