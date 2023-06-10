package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.Web;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverAllowencePage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public DriverAllowencePage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "driverNightBataCost")
    private WebElement driverNightBataCost;
    @FindBy(xpath = "//input[@formcontrolname='driverNightBataStartTime']")
    private WebElement driverNightBataStartTime;
    @FindBy(xpath = "//input[@formcontrolname='driverNightBataEndTime']")
    private WebElement driverNightBataEndTime;
    @FindBy(id = "driverDayBataCost")
    private WebElement driverDayBataCost;
    @FindBy(xpath = "//div[contains(text(),'DRIVER ALLOWANCE')]")
    private WebElement driverAllowanceSlab;

    public void clickOnDriverAllowanceSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        //js.executeScript("window.scrollBy(0,1600)");
        js.executeScript("window.scrollBy(0,600)");
        System.out.println("scroll -----");
         if (!driverNightBataCost.isDisplayed()) {
           //  driverAllowanceSlab.click();
        }else{

         }
    }
    public boolean updateDriverAllowanceSlab(String DriverNightCost,String startTime,String endTime,String DriverDayCost){
        Web.writeInTextBox(pageDriver,driverNightBataCost,DriverNightCost);
        if (driverNightBataStartTime == null) {
            Logger.log("Failed to find TripType field");
            return false;
        }
        Web.writeInTextBox(pageDriver,driverNightBataStartTime,startTime);
        //Select dropdownTripType = new Select(driverNightBataStartTime);
        //dropdownTripType.selectByVisibleText(startTime);
        if (driverNightBataEndTime == null) {
            Logger.log("Failed to find Calculation Type field");
            return false;
        }
        Web.writeInTextBox(pageDriver,driverNightBataEndTime,endTime);
        //Select dropdownCalType = new Select(driverNightBataEndTime);
        //dropdownCalType.selectByVisibleText(endTime);
        Web.writeInTextBox(pageDriver,driverDayBataCost,DriverDayCost);
        return true;
    }
}
