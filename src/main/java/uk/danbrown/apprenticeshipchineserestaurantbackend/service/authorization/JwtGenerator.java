package uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;

import java.time.Instant;
import java.util.UUID;

@Component
public class JwtGenerator {

    private Algorithm algorithm;

    public JwtGenerator(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
    public String generateJwtForOrganizationAccount(OrganizationAccount organizationAccount) {
        return JWT.create()
                .withIssuer("backend")
                .withSubject(organizationAccount.organizationId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }
}
