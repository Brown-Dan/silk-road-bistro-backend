package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.JwtResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.LoginRequest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidPasswordException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization.OrganizationAuthorizationService;

@RestController
@RequestMapping("/authorization")
public class OrganizationAuthorizationController {

    private final OrganizationAuthorizationService organizationAuthorizationService;

    public OrganizationAuthorizationController(OrganizationAuthorizationService organizationAuthorizationService) {
        this.organizationAuthorizationService = organizationAuthorizationService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<JwtResource> createAccount(@RequestBody OrganizationAccount organizationAccount) throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        return ResponseEntity.status(201).body(new JwtResource(organizationAuthorizationService.createAccount(organizationAccount)));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResource> login(@RequestBody LoginRequest loginRequest) throws InvalidPasswordException, EntityNotFoundException {
        return ResponseEntity.ok(new JwtResource(organizationAuthorizationService.login(loginRequest)));
    }
}
