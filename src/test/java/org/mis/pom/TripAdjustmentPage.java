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

public class TripAdjustmentPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public TripAdjustmentPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'TRIP ADJUSTMENTS')]")
    private WebElement tripAdjustmentSlab;
    @FindBy(id = "tripAdjsTriptype0")
    private WebElement tripTypeDropdown;
    @FindBy(id = "tripAdjsCaltype0")
    private WebElement calculationTypeDropdown;
    @FindBy(id = "tripAdjsServicetype0")
    private WebElement serviceTypeDropdown;
    @FindBy(id = "tripAdjsValue0")
    private WebElement adjustmentValue;
    @FindBy(xpath = "(//i[contains(text(),'add')])[13]")
    private WebElement addIconInTripAdjustSlab;


    public void clickOnTripAdjustmentSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,1600)");
        System.out.println("scroll -----");
       // if (!tripTypeDropdown.isDisplayed()) {
           // tripAdjustmentSlab.click();
        //}
    }

    public boolean updateTripAdjustSlab(String tripType,String calculationType,String ServiceType,String AdjustmentValue){
        if (tripTypeDropdown == null) {
            Logger.log("Failed to find TripType field");
            return false;
        }
        Select dropdownTripType = new Select(tripTypeDropdown);
        dropdownTripType.selectByVisibleText(tripType);
        if (calculationTypeDropdown == null) {
            Logger.log("Failed to find Calculation Type field");
            return false;
        }
        Select dropdownCalType = new Select(calculationTypeDropdown);
        dropdownCalType.selectByVisibleText(calculationType);
        if (serviceTypeDropdown == null) {
            Logger.log("Failed to find Service Type field");
            return false;
        }
        Select dropdownServiceType = new Select(serviceTypeDropdown);
        dropdownServiceType.selectByVisibleText(ServiceType);
        if(ServiceType.equals("NA")){

        }else{
            Web.writeInTextBox(pageDriver,adjustmentValue,AdjustmentValue);
        }
        return true;
    }
}
