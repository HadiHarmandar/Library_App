package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//span[normalize-space()='Dashboard']")
    public WebElement dashboardLink;

    @FindBy(xpath = "//span[normalize-space()='Users']")
    public WebElement usersLink;

    @FindBy(xpath = "//span[normalize-space()='Books']")
    public WebElement booksLink;

    @FindBy(xpath = "//a[@id='navbarDropdown']")
    public WebElement userIcon;

    public void navigate(String page) {

        switch (page) {
            case "Books":
                booksLink.click();
                break;
            case "Users":
                usersLink.click();
                break;
            case "Dashboard":
                dashboardLink.click();
                break;
            default:
                throw new RuntimeException("Invalid page: " + page);
        }
    }


}
