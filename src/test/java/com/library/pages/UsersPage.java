package com.library.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersPage extends BasePage {

    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchInput;



}
