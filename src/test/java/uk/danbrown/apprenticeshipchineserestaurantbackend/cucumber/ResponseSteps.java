package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;

import io.cucumber.java.en.Then;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.Articles;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.utils.ResponseAssert.assertThat;

public class ResponseSteps {

    private final ScenarioState scenario;

    public ResponseSteps(ScenarioState scenario) {
        this.scenario = scenario;
    }

    @Then("a response is returned with status {int} and articles")
    public void aResponseIsReturnedWithStatusAndArticles(int status, Articles articleResource) {
        assertThat(scenario.getLastResponse()).hasStatus(status).hasBody(articleResource);
    }

    @Then("a response is returned with status {int} and opening hours")
    public void aResponseIsReturnedWithStatusAndOpeningHours(int status, OpeningHours openingHours) {
        assertThat(scenario.getLastResponse()).hasStatus(status).hasBody(openingHours);
    }
}
