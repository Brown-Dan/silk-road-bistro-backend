package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.Error;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.ErrorResponse;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.*;

import static java.util.Collections.singletonList;
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

        ResponseEntity<ErrorResponse> expectedResult = ResponseEntity
                .status(404)
                .body(new ErrorResponse(singletonList(Error.entityNotFound("Not found"))));

        ResponseEntity<ErrorResponse> result = exceptionHandlers.handleEntityNotFound(exception);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void handleInvalidRequestBody_givenException_shouldReturnResponseEntity() {
        InvalidRequestBodyException exception = new InvalidRequestBodyException("Invalid");

        ResponseEntity<ErrorResponse> expectedResult = ResponseEntity
                .status(400)
                .body(new ErrorResponse(singletonList(Error.invalidRequestBody("Invalid"))));

        ResponseEntity<ErrorResponse> result = exceptionHandlers.handleInvalidRequestBody(exception);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void handleInvalidRequestId_givenException_shouldReturnResponseEntity() {
        InvalidRequestIdException exception = new InvalidRequestIdException("123");

        ResponseEntity<ErrorResponse> expectedResult = ResponseEntity
                .status(400)
                .body(new ErrorResponse(singletonList(Error.invalidRequestId("Invalid request id - 123"))));

        ResponseEntity<ErrorResponse> result = exceptionHandlers.handleInvalidRequestId(exception);

        assertThat(result).isEqualTo(expectedResult);
    }
}
