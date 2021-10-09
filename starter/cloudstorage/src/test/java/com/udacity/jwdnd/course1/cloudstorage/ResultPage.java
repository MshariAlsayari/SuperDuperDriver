package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(id = "btnHome1")
    private WebElement homeButton1;

    @FindBy(id = "btnHome2")
    private WebElement homeButton2;

    @FindBy(id = "btnHome3")
    private WebElement homeButton3;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void clickHomeButton(WebDriver driver) {

        if (isElementPresent(By.id("btnHome1"), driver)){
            js.executeScript("arguments[0].click();", homeButton1);
        }else if (isElementPresent(By.id("btnHome2"), driver)){
            js.executeScript("arguments[0].click();", homeButton2);
        }else if (isElementPresent(By.id("btnHome3"), driver)){
            js.executeScript("arguments[0].click();", homeButton3);
        }

    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
