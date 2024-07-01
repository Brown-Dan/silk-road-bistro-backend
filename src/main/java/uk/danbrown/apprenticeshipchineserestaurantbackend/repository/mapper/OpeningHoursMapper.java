package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.OpeningHoursRepository;

@Component
public class OpeningHoursMapper {

    private static final Logger LOG = LoggerFactory.getLogger(OpeningHoursRepository.class);

    private final ObjectMapper objectMapper;

    public OpeningHoursMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OpeningHours toDomain(String openingHours) {
        try {
            return objectMapper.readValue(openingHours, OpeningHours.class);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to map opening hours - {}", openingHours);
            return null;
        }
    }

    public String toJsonString(OpeningHours openingHours) {
        try {
            return objectMapper.writeValueAsString(openingHours);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to map opening hours - {}", openingHours);
            return null;
        }
    }
}
