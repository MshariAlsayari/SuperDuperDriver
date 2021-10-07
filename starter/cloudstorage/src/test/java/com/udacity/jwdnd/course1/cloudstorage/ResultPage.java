package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(id = "btnHome")
    private WebElement homeButton;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void clickHomeButton() {
        js.executeScript("arguments[0].click();", homeButton);
    }
}
