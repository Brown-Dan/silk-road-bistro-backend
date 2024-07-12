package uk.danbrown.apprenticeshipchineserestaurantbackend.exception;

public class InvalidRequestIdException extends Exception {

    private final String id;

    public InvalidRequestIdException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
