package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.Error;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.ErrorResponse;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.Collections;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.Error.*;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(FailureInsertingEntityException.class)
    public ResponseEntity<ErrorResponse> handleFailureInsertingEntity(FailureInsertingEntityException exception) {
        return buildResponseEntity(failureInsertingEntity("Failed to insert entity - %s".formatted(exception.getEntity())));
    }

    @ExceptionHandler(EntityAlreadyExistsWithIdException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsWithId(EntityAlreadyExistsWithIdException exception) {
        return buildResponseEntity(entityAlreadyExistsWithId("Entity already exists with Id - '%s'".formatted(exception.getId())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException exception) {
        return buildResponseEntity(entityNotFound(exception.getMessage()));
    }

    private static ResponseEntity<ErrorResponse> buildResponseEntity(Error error) {
        return ResponseEntity.status(error.getHttpStatus()).body(new ErrorResponse(Collections.singletonList(error)));
    }
}
