package uk.danbrown.apprenticeshipchineserestaurantbackend.exception;

public class FailureInsertingEntityException extends Exception {

    private final Object entity;

    public FailureInsertingEntityException(Object entity) {
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }
}
