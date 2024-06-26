package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@WebMvcTest
public class ControllerTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    public MvcResult get(String url) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();
        } catch (Exception e) {
            return fail("Failed to make request to " + url, e);
        }
    }


    public MvcResult delete(String url) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.delete(url)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();
        } catch (Exception e) {
            return fail("Failed to execute delete " + url, e);
        }
    }

    public MvcResult patch(String url, String patchJson) {
        try {
            return mockMvc.perform(MockMvcRequestBuilders.patch(url)
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
