package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.Error;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.ErrorResponse;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionHandlersTest {

    private ExceptionHandlers exceptionHandlers;

    @BeforeEach
    void setUp() {
        exceptionHandlers = new ExceptionHandlers();
    }

    @Test
    void handleFailureInsertingEntity_givenException_shouldReturnResponseEntity() {
        FailureInsertingEntityException exception = new FailureInsertingEntityException(null);

        ResponseEntity<ErrorResponse> expectedResult = ResponseEntity
                .status(500)
                .body(new ErrorResponse(singletonList(Error.failureInsertingEntity("Failed to insert entity - null"))));

        ResponseEntity<ErrorResponse> result = exceptionHandlers.handleFailureInsertingEntity(exception);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void handleEntityAlreadyExistsWithId_givenException_shouldReturnResponseEntity() {
        EntityAlreadyExistsWithIdException exception = new EntityAlreadyExistsWithIdException("ID");

        ResponseEntity<ErrorResponse> expectedResult = ResponseEntity
                .status(409)
                .body(new ErrorResponse(singletonList(Error.entityAlreadyExistsWithId("Entity already exists with Id - 'ID'"))));

        ResponseEntity<ErrorResponse> result = exceptionHandlers.handleEntityAlreadyExistsWithId(exception);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void handleEntityNotFound_givenException_shouldReturnResponseEntity() {
        EntityNotFoundException exception = new EntityNotFoundException("Not found");

        ResponseEntity<ErrorResponse> expectedResult =ResponseEntity
                .status(404)
                .body(new ErrorResponse(singletonList(Error.entityNotFound("Not found"))));

        ResponseEntity<ErrorResponse> result = exceptionHandlers.handleEntityNotFound(exception);

        assertThat(result).isEqualTo(expectedResult);
    }
}
