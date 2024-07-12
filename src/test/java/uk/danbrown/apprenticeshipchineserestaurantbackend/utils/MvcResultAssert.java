package uk.danbrown.apprenticeshipchineserestaurantbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Fail.fail;

public class MvcResultAssert extends AbstractAssert<MvcResultAssert, MvcResult> {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    protected MvcResultAssert(MvcResult actual) {
        super(actual, MvcResultAssert.class);
    }

    public MvcResultAssert hasStatus(HttpStatus status) {
        isNotNull();
        if (actual.getResponse().getStatus() != status.value()) {
            failWithMessage("Expected result to have status %d but was %d", status.value(), actual.getResponse().getStatus());
        }
        return this;
    }

    public <T> MvcResultAssert hasBody(T expectedBody) {
        isNotNull();
        Assertions.assertThat(readBody(actual, expectedBody.getClass()))
                .usingRecursiveComparison()
                .isEqualTo(expectedBody);
        return this;
    }

    public MvcResultAssert hasBody(String expectedBody) {
        isNotNull();
        try {
            Assertions.assertThat(actual.getResponse().getContentAsString()).isEqualTo(expectedBody);
        } catch (UnsupportedEncodingException e) {
            return fail("Cannot read response body", e);
        }
        return this;
    }

    public MvcResultAssert hasNoBody() {
        isNotNull();
        try {
            Assertions.assertThat(actual.getResponse().getContentAsString()).isEmpty();
        } catch (UnsupportedEncodingException e) {
            return fail("Cannot read response body", e);
        }
        return this;
    }

    private <T> T readBody(MvcResult mvcResult, Class<T> clazz) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
        } catch (Exception e) {
            return fail("Cannot read response body", e);
        }
    }

    public static MvcResultAssert assertThat(MvcResult actual) {
        return new MvcResultAssert(actual);
    }
}
