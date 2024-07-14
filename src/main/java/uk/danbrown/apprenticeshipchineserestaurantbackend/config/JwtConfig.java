package uk.danbrown.apprenticeshipchineserestaurantbackend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JWTVerifier getJwtVerifier(){
        Algorithm algorithm = Algorithm.HMAC256("backend");
        return JWT.require(algorithm)
                .withIssuer("backend")
                .build();
    }

    @Bean
    public Algorithm getAlgorithm(){
        return Algorithm.HMAC256("backend");
    }
}

