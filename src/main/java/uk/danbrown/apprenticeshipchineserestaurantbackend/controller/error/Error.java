package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.ErrorKey.*;

public record Error(ErrorKey key, String message) {

    @JsonIgnore
    public HttpStatus getHttpStatus() {
        return key.getHttpStatus();
    }

    public static Error failureInsertingEntity(String message) {
        return new Error(FAILURE_INSERTING_ENTITY, message);
    }

    public static Error entityAlreadyExistsWithId(String message) {
        return new Error(ENTITY_ALREADY_EXISTS_WITH_ID, message);
    }

    public static Error entityNotFound(String message) {
        return new Error(ENTITY_NOT_FOUND, message);
    }

    public static Error invalidRequestBody(String message) {
        return new Error(INVALID_REQUEST_BODY, message);
    }

    public static Error invalidRequestId(String message) {
        return new Error(INVALID_REQUEST_ID, message);
    }
}
