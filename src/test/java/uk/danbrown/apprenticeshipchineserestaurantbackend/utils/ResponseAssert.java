package uk.danbrown.apprenticeshipchineserestaurantbackend.utils;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import uk.co.autotrader.traverson.http.Response;

import static org.assertj.core.api.Fail.fail;

public class ResponseAssert extends AbstractAssert<ResponseAssert, Response<JSONObject>> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .registerModule(new ParameterNamesModule());

    protected ResponseAssert(Response<JSONObject> actual) {
        super(actual, ResponseAssert.class);
    }

    public ResponseAssert hasStatus(int status) {
        isNotNull();
        if (actual.getStatusCode() != status) {
            failWithMessage("Expected response to have status %d but was %d", status, actual.getStatusCode());
        }
        return this;
    }

    public <T> ResponseAssert hasBody(T expectedBody) {
        isNotNull();
        Assertions.assertThat(readBody(actual, expectedBody.getClass()))
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedBody);
        return this;
    }

    private <T> T readBody(Response<JSONObject> mvcResult, Class<T> clazz) {
        try {
            return objectMapper.readValue(mvcResult.getResource().toString(), clazz);
        } catch (Exception e) {
            return fail("Cannot read response body", e);
        }
    }

    public static ResponseAssert assertThat(Response<JSONObject> actual) {
        return new ResponseAssert(actual);
    }
}
