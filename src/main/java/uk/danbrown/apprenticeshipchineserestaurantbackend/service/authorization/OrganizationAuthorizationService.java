package uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.LoginRequest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidPasswordException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.authorization.AuthorizationRepository;

import java.time.Instant;
import java.util.UUID;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount.Builder.aCreateOrganizationRequest;

@Service
public class OrganizationAuthorizationService {

    private final AuthorizationRepository authorizationRepository;
    private final Algorithm algorithm;

    public OrganizationAuthorizationService(AuthorizationRepository authorizationRepository, Algorithm algorithm) {
        this.authorizationRepository = authorizationRepository;
        this.algorithm = algorithm;
    }

    public String createAccount(OrganizationAccount organizationAccount) throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        if (authorizationRepository.organizationAccountExists(organizationAccount.organizationId())) {
            throw new EntityAlreadyExistsWithIdException(organizationAccount.organizationId());
        }
        OrganizationAccount encryptedOrganizationAccount = aCreateOrganizationRequest()
                .withOrganizationId(organizationAccount.organizationId())
                .withEmail(organizationAccount.email())
                .withPassword(BCrypt.hashpw(organizationAccount.password(), BCrypt.gensalt(12)))
                .build();
        OrganizationAccount insertedOrganization = authorizationRepository.insertOrganizationAccount(encryptedOrganizationAccount);
        return generateJwtForOrganizationAccount(insertedOrganization);
    }

    public String login(LoginRequest loginRequest) throws EntityNotFoundException, InvalidPasswordException {
        OrganizationAccount organizationAccount = authorizationRepository.getOrganizationAccountById(loginRequest.organizationId());

        if (BCrypt.checkpw(loginRequest.password(), organizationAccount.password())) {
            return generateJwtForOrganizationAccount(organizationAccount);
        } else {
            throw new InvalidPasswordException();
        }
    }

    private String generateJwtForOrganizationAccount(OrganizationAccount organizationAccount) {
        return JWT.create()
                .withIssuer("backend")
                .withSubject(organizationAccount.organizationId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }
}
