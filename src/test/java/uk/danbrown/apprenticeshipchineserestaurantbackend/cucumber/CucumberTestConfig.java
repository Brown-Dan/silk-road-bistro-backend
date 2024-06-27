package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@TestConfiguration
public class CucumberTestConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.parse("2024-06-27T12:30:00Z"), ZoneOffset.UTC);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule());
    }

    @Bean
    public ScenarioState scenarioState() {
        return new ScenarioState();
    }
}
