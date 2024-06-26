package uk.danbrown.apprenticeshipchineserestaurantbackend.Exception;

public class EntityAlreadyExistsWithIdException extends Exception {

    private String id;

    public EntityAlreadyExistsWithIdException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

