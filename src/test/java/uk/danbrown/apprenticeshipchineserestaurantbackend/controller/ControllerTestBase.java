package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest
public class ControllerTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    RequestContextManager requestContextManager;

    @MockBean
    JWTVerifier jwtVerifier;

    @BeforeEach
    void setUp() {
        when(jwtVerifier.verify(anyString())).thenReturn(null);
    }

    public MvcResult get(String url) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .header("id", "test")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();
        } catch (Exception e) {
            return fail("Failed to make request to " + url, e);
        }
    }


    public MvcResult delete(String url) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.delete(url)
                            .header("id", "test")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();
        } catch (Exception e) {
            return fail("Failed to execute delete " + url, e);
        }
    }

    public MvcResult patch(String url, String patchJson) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.patch(url)
                            .header("id", "test")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(patchJson))
                    .andReturn();
        } catch (Exception e) {
            return fail("Failed to Patch " + url, e);
        }
    }


    public MvcResult post(String url, String body) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .header("id", "test")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(body))
                    .andReturn();
        } catch (Exception e) {
            return fail("Failed to execute delete " + url, e);
        }
    }

    public String stripJsonWhiteSpaceAndNewLines(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return fail("Cannot process json string", e);
        }
    }
}
