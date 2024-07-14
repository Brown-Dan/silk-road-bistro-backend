package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DocStringType;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.Articles;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours;

public class TypeTransforms {

    private final ObjectMapper objectMapper;

    public TypeTransforms(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @DocStringType
    public Articles articleResourceFromDocStringTransformer(String articleResourceJson) throws JsonProcessingException {
        return objectMapper.readValue(articleResourceJson, Articles.class);
    }

    @DocStringType
    public OpeningHours openingHoursFromDocStringTransformer(String openingHours) throws JsonProcessingException {
        return objectMapper.readValue(openingHours, OpeningHours.class);
    }
}
