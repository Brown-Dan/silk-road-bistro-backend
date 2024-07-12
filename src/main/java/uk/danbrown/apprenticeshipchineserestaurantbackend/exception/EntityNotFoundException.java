package uk.danbrown.apprenticeshipchineserestaurantbackend.exception;

public class EntityNotFoundException extends Exception {

    private final String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
