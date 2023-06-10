package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BackToBackConfigurationPropertyPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;
    public BackToBackConfigurationPropertyPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);

    }
    @FindBy(id = "BACK_TO_BACK_CONFIGURATION")
    private WebElement backToBackConfigurationLink;
    @FindBy(xpath = "//div[@id='alertConfig']//div//div//form//table//tbody//tr[3]//td//fieldset//div//table//tbody//tr//td//input[@id='multiOfficeB2B']")
    private WebElement multiOfficeB2B;
    @FindBy(id = "selectedShiftsB2b")
    private WebElement applyB2BBetweenSelectedShiftsOnly;
    @FindBy(id = "runBackToBackLogicForBillingTrips")
    private WebElement applyB2bBetweenBillingTrips;
    @FindBy(id = "minB2bTrips")
    private WebElement minimumB2BTripLogoutInPercentage;
    @FindBy(id = "waitingTime")
    private WebElement waitingTime ;
    @FindBy(id = "roundJourneyTime")
    private WebElement roundJourneyTime;
    @FindBy(id = "trafficBufferTime")
    private WebElement trafficBufferTime;
    @FindBy(id = "deadLegKmsAbs")
    private WebElement deadLegKmsAbs;
    @FindBy(xpath = "//input[@name='save']")
    private WebElement saveButton;


    public boolean clickbackToBackConfigurationLink() throws InterruptedException {
        pageActions.waitToLoadElement(backToBackConfigurationLink);
        return Web.click(pageDriver,backToBackConfigurationLink);
       // pageDriver.switchTo().frame("settingsForm");
    }
    public void enableMultipleOfficeB2B() throws InterruptedException {
        if (!multiOfficeB2B.isSelected()) {
            multiOfficeB2B.click();
            Thread.sleep(5000);
        }
    }
    public void enableapplyB2BBetweenSelectedShiftsOnly() throws InterruptedException {
        if (!applyB2BBetweenSelectedShiftsOnly.isSelected()) {
            applyB2BBetweenSelectedShiftsOnly.click();
        }
    }
    public void enableapplyB2bBetweenBillingTrips() throws InterruptedException {
        if (!applyB2bBetweenBillingTrips.isSelected()) {
            applyB2bBetweenBillingTrips.click();
        }
    }
    public void addminimumB2BTripLogoutInPercentage() throws InterruptedException {
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        minimumB2BTripLogoutInPercentage.sendKeys(del + "10");
       // Web.writeInTextBox(pageDriver,minimumB2BTripLogoutInPercentage,"10");
    }
    public void addWaitingTime() throws InterruptedException {
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        waitingTime.sendKeys(del + "10");
       // Web.writeInTextBox(pageDriver,waitingTime,"10");
    }
    public void addRoundJourneyTime() throws InterruptedException {
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        roundJourneyTime.sendKeys(del + "5");
        //Web.writeInTextBox(pageDriver,roundJourneyTime,"5");
    }
    public void addTrafficBufferTime() throws InterruptedException {
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        trafficBufferTime.sendKeys(del + "15");
       // Web.writeInTextBox(pageDriver,trafficBufferTime,"15");
    }
    public void addDeadLegKmsAbs() throws InterruptedException {
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        deadLegKmsAbs.sendKeys(del + "5");
      //  Web.writeInTextBox(pageDriver,deadLegKmsAbs,"5");
    }
    public void saveB2BConfigProperty() throws InterruptedException {
       saveButton.click();
    }

}
