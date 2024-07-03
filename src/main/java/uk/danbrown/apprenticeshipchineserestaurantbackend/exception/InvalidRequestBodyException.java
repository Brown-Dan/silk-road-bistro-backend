package uk.danbrown.apprenticeshipchineserestaurantbackend.exception;

public class InvalidRequestBodyException extends Exception{

    private final String message;

    public InvalidRequestBodyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
