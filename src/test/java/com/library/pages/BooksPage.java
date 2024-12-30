package com.library.pages;

import com.library.utilities.BrowserUtils;
import com.library.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BooksPage extends BasePage {

    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchInput;

    @FindBy(xpath = "//td[3]")
    public WebElement bookRow;

    public String findBook(String bookName) {
        searchInput.sendKeys(bookName + Keys.ENTER);
        BrowserUtils.sleep(1);
        return Driver.getDriver().findElement(By.xpath("//td[3]")).getText();
    }

}
