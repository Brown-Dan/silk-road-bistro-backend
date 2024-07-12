package uk.danbrown.apprenticeshipchineserestaurantbackend.context;

import org.springframework.stereotype.Component;

@Component
public class RequestContextManager {

    private static final ThreadLocal<RequestContext> THREAD_LOCAL_REQUEST_CONTEXT = new ThreadLocal<>();

    public RequestContext getRequestContext() {
        return THREAD_LOCAL_REQUEST_CONTEXT.get();
    }

    public void set(String id) {
        THREAD_LOCAL_REQUEST_CONTEXT.set(new RequestContext(id));
    }
}
