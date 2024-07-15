package uk.danbrown.apprenticeshipchineserestaurantbackend.filter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.core5.http.ContentType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.error.Error;

import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
@NonNullApi
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JWTVerifier jwtVerifier;
    private final ObjectMapper objectMapper;
    private final RequestContextManager requestContextManager;

    private static final Set<String> PROTECTED_PATHS = Set.of(
      "/articles",
      "/offer",
     "/opening-hour"
    );

    public AuthorizationFilter(JWTVerifier jwtVerifier, ObjectMapper objectMapper, RequestContextManager requestContextManager) {
        this.jwtVerifier = jwtVerifier;
        this.objectMapper = objectMapper;
        this.requestContextManager = requestContextManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if (PROTECTED_PATHS.contains(path)) {
            try {
                String jwt = request.getHeader("Authorization");
                jwtVerifier.verify(jwt);
            } catch (JWTVerificationException exception) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                response.getWriter().write(objectMapper.writeValueAsString(Error.jwtVerificationException(exception)));
                response.getWriter().close();
            }
        }
        String id = request.getHeader("id");
        if (id == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            response.getWriter().write(objectMapper.writeValueAsString(Error.invalidRequestId(null)));
            response.getWriter().close();
        }
        requestContextManager.set(id);
        filterChain.doFilter(request, response);
    }
}
