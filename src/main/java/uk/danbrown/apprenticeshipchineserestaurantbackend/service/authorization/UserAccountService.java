package uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.UserAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.UserLoginRequest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidPasswordException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.authorization.AuthorizationRepository;

import java.time.Instant;
import java.util.UUID;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.UserAccount.Builder.anUserAccount;

@Service
public class UserAccountService {

    private final AuthorizationRepository authorizationRepository;
    private final Algorithm algorithm;

    public UserAccountService(AuthorizationRepository authorizationRepository, Algorithm algorithm) {
        this.authorizationRepository = authorizationRepository;
        this.algorithm = algorithm;
    }

    public String createAccount(UserAccount userAccount) throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        if (authorizationRepository.userAccountExists(userAccount.username())) {
            throw new EntityAlreadyExistsWithIdException(userAccount.username());
        }
        UserAccount encryptedUserAccount = anUserAccount()
                .withName(userAccount.name())
                .withUsername(userAccount.username())
                .withEmail(userAccount.email())
                .withPassword(BCrypt.hashpw(userAccount.password(), BCrypt.gensalt(12)))
                .build();

        UserAccount insertedUserAccount = authorizationRepository.insertUserAccount(encryptedUserAccount);
        return generateJwtForUserAccount(insertedUserAccount);
    }

    public String login(UserLoginRequest loginRequest) throws EntityNotFoundException, InvalidPasswordException {
        UserAccount userAccount = authorizationRepository.getUserAccountByUsername(loginRequest.username());

        if (BCrypt.checkpw(loginRequest.password(), userAccount.password())) {
            return generateJwtForUserAccount(userAccount);
        } else {
            throw new InvalidPasswordException();
        }
    }

    private String generateJwtForUserAccount(UserAccount userAccount) {
        return JWT.create()
                .withIssuer("backend")
                .withSubject(userAccount.username())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }
}
