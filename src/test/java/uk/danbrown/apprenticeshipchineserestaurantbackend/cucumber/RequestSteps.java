package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber.utils.TraversonClient;

public class RequestSteps {

    private final TraversonClient traversonClient = new TraversonClient();
    private final ScenarioState scenario;

    public RequestSteps(ScenarioState scenario) {
        this.scenario = scenario;
    }

    @Given("a request is made to create an article with body")
    public void aRequestIsMadeToCreateAnArticleWithBody(String articleResourceJson) {
        scenario.setLastResponse(traversonClient.createArticle(articleResourceJson));
    }

    @When("a request is made to retrieve recent articles with limit {int}")
    public void aRequestIsMadeToRetrieveRecentArticles(int limit) {
        scenario.setLastResponse(traversonClient.getArticles(limit));
    }

    @Given("a request is made to create opening hours with body")
    public void aRequestIsMadeToCreateOpeningHoursWithBody(String openingHours) {
        scenario.setLastResponse(traversonClient.createOpeningHours(openingHours));
    }

    @When("a request is made to retrieve opening hours")
    public void aRequestIsMadeToRetrieveOpeningHours() {
        scenario.setLastResponse(traversonClient.getOpeningHours());
    }
}
