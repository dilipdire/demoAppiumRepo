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

public class EmployeeAdjustmentSlabPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public EmployeeAdjustmentSlabPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'EMPLOYEE ADJUSTMENTS')]")
    private WebElement employeeAdjustmentSlab;
    @FindBy(id = "tripType0")
    private WebElement tripTypeDropdown;
    @FindBy(id = "employeeType0")
    private WebElement employeeTypeDropdown;
    @FindBy(id = "empAdjsCaltype0")
    private WebElement calculationTypeDropdown;
    @FindBy(id = "empAdjsValue0")
    private WebElement adjustmentValue;


    public void clickOnEmpAdjustmentSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,1600)");
        System.out.println("scroll -----");
        // if (!tripTypeDropdown.isDisplayed()) {
        // employeeAdjustmentSlab.click();
        //}
    }

    public boolean updateEmpAdjustSlab(String tripType,String empType,String calculationType,String AdjustmentValue){
        if (tripTypeDropdown == null) {
            Logger.log("Failed to find TripType field");
            return false;
        }
        Select dropdownTripType = new Select(tripTypeDropdown);
        dropdownTripType.selectByVisibleText(tripType);
        if (employeeTypeDropdown == null) {
            Logger.log("Failed to find Calculation Type field");
            return false;
        }
        Select dropdownCalType = new Select(employeeTypeDropdown);
        dropdownCalType.selectByVisibleText(empType);
        if (calculationTypeDropdown == null) {
            Logger.log("Failed to find Service Type field");
            return false;
        }
        Select dropdownServiceType = new Select(calculationTypeDropdown);
        dropdownServiceType.selectByVisibleText(calculationType);
        Web.writeInTextBox(pageDriver,adjustmentValue,AdjustmentValue);

        return true;
    }
}
