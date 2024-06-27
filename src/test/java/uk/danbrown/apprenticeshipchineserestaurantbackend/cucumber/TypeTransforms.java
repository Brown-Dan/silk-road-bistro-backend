package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DocStringType;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;

public class TypeTransforms {

    private final ObjectMapper objectMapper;

    public TypeTransforms(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @DocStringType
    public ArticleResource articleResourceFromDocStringTransformer(String articleResourceJson) throws JsonProcessingException {
        return objectMapper.readValue(articleResourceJson, ArticleResource.class);
    }
}
