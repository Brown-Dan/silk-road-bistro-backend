package uk.danbrown.apprenticeshipchineserestaurantbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.UUID;

public class JwtProvider {

    public static String getJwt() {
        return JWT.create()
                .withIssuer("backend")
                .withSubject("test")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .withJWTId(UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256("backend"));
    }
}
