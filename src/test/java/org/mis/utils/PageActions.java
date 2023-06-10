package org.mis.utils;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import java.util.concurrent.TimeUnit;

public class PageActions {

    private WebDriver pageDriver;
    public NgWebDriver ngDriver;
    public PageActions (WebDriver pageDriver) {
        this.pageDriver = pageDriver;
    }

    public static void putValueInDropdown( WebElement webElement,String Value) {
        Select dropdown = new Select(webElement);
        dropdown.selectByVisibleText(Value);
    }

    // to check in every some time if element is open or not
    public void waitForElementToOpen(By elementToClick) {
        pageDriver.findElement(elementToClick).click();
        WebDriverWait wait = new WebDriverWait(pageDriver,60);
        wait.pollingEvery(10,TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
    }


    public void selectElement() {
        Actions act = new Actions(pageDriver);
        act.sendKeys(Keys.DOWN).perform();
        act.sendKeys(Keys.ENTER).click().perform();
    }

    // wait to load element used for @FindBy
    /*public void waitToLoadElement(WebElement element){
        WebDriverWait wait = new WebDriverWait(pageDriver, 40);
        wait.pollingEvery(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }*/
    public void waitToLoadElement(WebElement element){
        WebDriverWait wait = new WebDriverWait(pageDriver, 40);
        wait.pollingEvery(500, TimeUnit.MILLISECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
