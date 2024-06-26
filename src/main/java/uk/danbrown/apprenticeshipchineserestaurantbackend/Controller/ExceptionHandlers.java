package uk.danbrown.apprenticeshipchineserestaurantbackend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Error.Error;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Error.ErrorResponse;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.FailureInsertingEntityException;

import java.util.Collections;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Error.Error.entityAlreadyExistsWithId;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Error.Error.failureInsertingEntity;

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

    private static ResponseEntity<ErrorResponse> buildResponseEntity(Error error) {
        return ResponseEntity.status(error.getHttpStatus()).body(new ErrorResponse(Collections.singletonList(error)));
    }
}
