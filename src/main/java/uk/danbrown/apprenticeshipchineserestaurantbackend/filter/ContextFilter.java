package uk.danbrown.apprenticeshipchineserestaurantbackend.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidRequestIdException;

import java.io.IOException;
import java.util.Set;

@Component
@Order(2)
public class ContextFilter implements Filter {

    private final RequestContextManager requestContextManager;

    public ContextFilter(RequestContextManager requestContextManager) {
        this.requestContextManager = requestContextManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String id = request.getHeader("id");

        if (id == null) {
            throw new ServletException("Invalid Request ID Provided", new InvalidRequestIdException(null));
        }
        requestContextManager.set(id);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

