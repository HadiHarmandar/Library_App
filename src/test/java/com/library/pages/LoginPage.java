package com.library.pages;

import com.library.utilities.Driver;
import com.library.utilities.LibraryUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//input[@id='inputEmail']")
    public WebElement emailInput;

    @FindBy(xpath = "//input[@id='inputPassword']")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement signInButton;

    public void login(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    public void login(String role) {
        Map<String, String> credentials = LibraryUtil.retrieveCredentials(role);
        login(credentials.get("email"), credentials.get("password"));
    }

}
