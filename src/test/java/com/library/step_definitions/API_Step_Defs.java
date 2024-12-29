package com.library.step_definitions;

import com.library.utilities.LibraryUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class API_Step_Defs {

    RequestSpecification givenPart = given().log().uri();
    Response response;
    JsonPath jsonPath;
    ValidatableResponse thenPart;

    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String role) {
        String token = LibraryUtil.getTokenByRole(role);
        givenPart.header("x-library-token", token);
    }
    @Given("Accept header is {string}")
    public void accept_header_is(String header) {
        givenPart.accept(header);
    }
    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endpoint) {
        response = givenPart
                .when()
                .get(endpoint);
        jsonPath = response.jsonPath();
        thenPart = response.then();
    }
    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        thenPart.statusCode(expectedStatusCode);
    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String responseContentType) {
        thenPart.contentType(responseContentType);
    }
    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        String actualValue = jsonPath.getString(path);
        assertNotNull(actualValue);
    }
}
