package com.library.step_definitions;

import com.library.utilities.BrowserUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Smoke_Step_Defs {

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        System.out.println("DONE IN HOOKS");
    }
    @Then("title should be {string}")
    public void title_should_be(String expectedTitle) {
        BrowserUtils.sleep(1);
        BrowserUtils.verifyTitle(expectedTitle);
    }

}
