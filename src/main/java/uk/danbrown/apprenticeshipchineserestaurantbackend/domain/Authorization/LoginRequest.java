package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization;

public record LoginRequest(String organizationId, String password) {
}
