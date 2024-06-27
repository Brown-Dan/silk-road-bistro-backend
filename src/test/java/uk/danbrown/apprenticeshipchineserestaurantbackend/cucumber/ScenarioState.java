package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;


import com.alibaba.fastjson2.JSONObject;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import uk.co.autotrader.traverson.http.Response;

import java.util.LinkedList;

@Component
@ScenarioScope(proxyMode = ScopedProxyMode.NO)
public class ScenarioState {

    private final LinkedList<Response<JSONObject>> responses = new LinkedList<>();

    Response<JSONObject> getLastResponse() {
        return responses.getLast();
    }

    void setLastResponse(Response<JSONObject> lastHttpResponse) {
        responses.add(lastHttpResponse);
    }
}