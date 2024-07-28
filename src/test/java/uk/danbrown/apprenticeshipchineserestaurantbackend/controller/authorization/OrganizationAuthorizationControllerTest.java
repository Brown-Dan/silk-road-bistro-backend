package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.JwtResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.LoginRequest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidPasswordException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization.OrganizationAuthorizationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationAuthorizationControllerTest {

    @Mock
    private OrganizationAuthorizationService organizationAuthorizationService;

    private OrganizationAuthorizationController organizationAuthorizationController;

    @BeforeEach
    void setUp() {
        organizationAuthorizationController = new OrganizationAuthorizationController(organizationAuthorizationService);
    }

    @Test
    void createAccount_givenAccountRequest_shouldReturnJwt() throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        OrganizationAccount organizationAccount = new OrganizationAccount("id", "password", "email");

        when(organizationAuthorizationService.createAccount(any())).thenReturn("jwt");

        ResponseEntity<JwtResource> jwt = organizationAuthorizationController.createAccount(organizationAccount);

        verify(organizationAuthorizationService).createAccount(organizationAccount);
        assertThat(jwt).isEqualTo(ResponseEntity.status(201).body(new JwtResource("jwt")));
    }

    @Test
    void login_givenUserLoginRequest_shouldReturnJwt() throws InvalidPasswordException, EntityNotFoundException {
        LoginRequest loginRequest = new LoginRequest("id", "password");

        when(organizationAuthorizationService.login(any())).thenReturn("jwt");

        ResponseEntity<JwtResource> jwt = organizationAuthorizationController.login(loginRequest);

        verify(organizationAuthorizationService).login(loginRequest);
        assertThat(jwt).isEqualTo(ResponseEntity.status(200).body(new JwtResource("jwt")));
    }
}
