package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.utils.DatabaseHelper;

import static io.cucumber.junit.platform.engine.Constants.PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME;

@Suite
@SelectClasspathResource("features")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "true")
@CucumberContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "spring.main.allow-bean-definition-overriding=true",
        classes = {CucumberTestConfig.class}
)
public class CucumberTest {

    private static final DatabaseHelper DATABASE_HELPER = new DatabaseHelper();

    public CucumberTest() {
    }

    @BeforeAll
    public static void init() {
        System.out.println(SpringBootTest.WebEnvironment.DEFINED_PORT);
        DATABASE_HELPER.clearTables();
    }

    @AfterEach
    public void tearDown() {
        DATABASE_HELPER.clearTables();
    }
}
