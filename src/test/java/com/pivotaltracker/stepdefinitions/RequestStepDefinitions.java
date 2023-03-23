package com.pivotaltracker.stepdefinitions;

import com.pivotaltracker.ConfigReader;
import com.pivotaltracker.RequestManager;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;

public class RequestStepDefinitions {

    private RequestManager requestManager;
    private ConfigReader configReader;
    private String body;
    private Response response;
    private String projectId;

    @Given("I create a new request")
    public void iCreateANewRequest() {
        requestManager = new RequestManager();
        configReader = new ConfigReader();
    }

    @And("I set the following request body")
    public void iSetTheFollowingRequestBody(String bodyFromFeatureFile) {
        body = bodyFromFeatureFile;
    }

    @When("I send a POST request to the endpoint {string}")
    public void iSendAPOSTRequestToTheEndpoint(String endpointFromFeatureFile) {
        response = requestManager.sendPostRequest(body, endpointFromFeatureFile);
    }

    @Then("I verify that the response status code is {int}")
    public void iVerifyThatTheResponseStatusCodeIs(int statusCodeFromFeatureFile) {
        int actualStatusCode = response.statusCode();
        int expectedStatusCode = statusCodeFromFeatureFile;
        Assert.assertEquals(actualStatusCode, expectedStatusCode);
        projectId = response.jsonPath().getString("id");
    }

    @And("I verify that the body response matches the schema {string}")
    public void iVerifyThatTheBodyResponseMatchesTheSchema(String schemaFromFeatureFile) {
        String schemaPath = "src/test/resources/schemas/";
        File schemaFile = new File(schemaPath + schemaFromFeatureFile);
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }

    @After(value = "@deleteProjectPostCondition")
    public void deleteProject() {
        String endpoint = "projects/" + projectId;
        requestManager.sendDeleteRequest(endpoint);
    }
}
