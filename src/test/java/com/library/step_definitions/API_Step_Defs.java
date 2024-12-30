package com.library.step_definitions;

import com.library.pages.BooksPage;
import com.library.pages.LoginPage;
import com.library.utilities.DB_Util;
import com.library.utilities.LibraryUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class API_Step_Defs {

    RequestSpecification givenPart = given().log().uri();
    Response response;
    JsonPath jsonPath;
    ValidatableResponse thenPart;
    String expectedValue;
    Map<String, Object> randomDataMap = new HashMap<>();
    LoginPage loginPage = new LoginPage();
    BooksPage booksPage = new BooksPage();

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

    @Given("Path param is {string}")
    public void path_param_is(String value) {
        this.expectedValue = value;
        givenPart.pathParam("id", value);
    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String path) {
        String actualValue = jsonPath.getString(path);
        assertEquals(expectedValue, actualValue);
    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> fields) {
        for (String eachField : fields) {
            assertNotNull(eachField);
        }
    }

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String requestContentType) {
        givenPart.contentType(requestContentType);
    }
    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String dataType) {
        switch (dataType) {
            case "book":
                randomDataMap = LibraryUtil.createRandomBook();
                break;
            case "user":
//                randomDataMap = LibraryUtil.createRandomUser();
                break;
            default:
                new RuntimeException("Invalid datatype: " + dataType);
        }
        givenPart.formParams(randomDataMap);
    }
    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endpoint) {
        response = givenPart.when()
                .post(endpoint);
        jsonPath = response.jsonPath();
        thenPart = response.then();
    }
    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String path, String expectedValue) {
        String actualValue = jsonPath.getString(path);
        assertEquals(actualValue, expectedValue);
    }

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String role) {
        loginPage.login(role);
    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String page) {
        booksPage.navigate(page);
    }
    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {
        String bookId = jsonPath.getString("book_id");
        DB_Util.runQuery("select name from books where id = " + bookId);
        String actualBookNameInDB = DB_Util.getFirstRowFirstColumn();
        String expectedBookName = (String) randomDataMap.get("name");
        String actualBookNameInUI = booksPage.findBook(expectedBookName);
        assertEquals(expectedBookName, actualBookNameInDB);
        assertEquals(expectedBookName, actualBookNameInUI);
    }
}
