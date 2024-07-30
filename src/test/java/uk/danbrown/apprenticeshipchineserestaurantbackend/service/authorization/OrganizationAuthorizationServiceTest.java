//package uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.LoginRequest;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidPasswordException;
//import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.authorization.AuthorizationRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class OrganizationAuthorizationServiceTest {
//
//    @Mock
//    private AuthorizationRepository authorizationRepository;
//
//    @Mock
//    private JwtGenerator jwtGenerator;
//
//    private OrganizationAuthorizationService organizationAuthorizationService;
//
//    @BeforeEach
//    void setUp() {
//        organizationAuthorizationService = new OrganizationAuthorizationService(authorizationRepository, jwtGenerator);
//    }
//
//    @Test
//    void createAccount_givenValidAccount_shouldReturnJwt() throws EntityNotFoundException, FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
//        OrganizationAccount organizationAccount = new OrganizationAccount("id", "password", "email");
//
//        when(authorizationRepository.organizationAccountExists(any())).thenReturn(false);
//        when(authorizationRepository.insertOrganizationAccount(any())).thenReturn(organizationAccount);
//        when(jwtGenerator.generateJwtForOrganizationAccount(any())).thenReturn("123");
//
//        String jwt = organizationAuthorizationService.createAccount(organizationAccount);
//
//        verify(authorizationRepository).organizationAccountExists(organizationAccount.organizationId());
//        assertThat(jwt).isEqualTo("123");
//    }
//
//    @Test
//    void createAccount_givenInvalidAccount_shouldThrowException() {
//        OrganizationAccount organizationAccount = new OrganizationAccount("id", "password", "email");
//
//        when(authorizationRepository.organizationAccountExists(any())).thenReturn(true);
//
//        Assertions.assertThatThrownBy(() -> organizationAuthorizationService.createAccount(organizationAccount))
//                .isInstanceOf(EntityAlreadyExistsWithIdException.class);
//    }
//
//    @Test
//    void login_givenValidLoginRequest_shouldReturnJwt() throws EntityNotFoundException, InvalidPasswordException {
//        LoginRequest loginRequest = new LoginRequest("id", "password");
//        OrganizationAccount organizationAccount = new OrganizationAccount("id", "password", "email");
//
//        when(authorizationRepository.getOrganizationAccountById(any())).thenReturn(organizationAccount);
//        when(jwtGenerator.generateJwtForOrganizationAccount(any())).thenReturn("123");
//
//        String jwt = organizationAuthorizationService.login(loginRequest);
//
//        verify(authorizationRepository).getOrganizationAccountById(loginRequest.organizationId());
//        assertThat(jwt).isEqualTo("123");
//    }
//
//    @Test
//    void login_givenInvalidLoginRequest_shouldThrowException() {
//        LoginRequest loginRequest = new LoginRequest("id", "password");
////        OrganizationAccount organizationAccount = new OrganizationAccount
//        // TODO
//    }
//}