package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.utils.Web;
import org.mis.utils.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TripPackage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public TripPackage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(id = "tPackageslabName0")
    private WebElement slabName;
    @FindBy(id = "tFixedKm0")
    private WebElement maxKm;
    @FindBy(id = "tFuelCost0")
    private WebElement fuelCost;
    @FindBy(id = "tFixedCost0")
    private WebElement fixedCost;
    @FindBy(id = "tJumpCriteria0")
    private WebElement jumpcrieteriaDropdown;
    @FindBy(id = "tExtraKmCost0")
    private WebElement extraKmCost;
    @FindBy(id = "tacCost0")
    private WebElement acSurcharge;
    @FindBy(id = "tacKm0")
    private WebElement additionalExtraKmCostAC;
    @FindBy(id = "tacEscortCost0")
    private WebElement escortAC;
    @FindBy(id = "tescortCost0")
    private WebElement escortNonAC;
    @FindBy(xpath = "//ul[@class='collapsible row tripPackage d-background']//i[@class='material-icons'][contains(text(),'add')]")
    private WebElement plus;

    public void clickOnTripPackageSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        //pageDriver.findElement(By.xpath("//div[contains(text(),'TRIP PACKAGE SLAB')]")).click();
        //plus.click();
    }

    public void clickOnTripPackageSlabForAddContract() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        pageDriver.findElement(By.xpath("//div[contains(text(),'TRIP PACKAGE SLAB')]")).click();
        plus.click();
    }
    public boolean updateTripPackageSlab(String SlabName,String MaxKm,String FuelCost,String FixedCost,String jumpcriteria,String ExtraKmCost,String ACSurcharge,String
            AdditionalExtraKmCostAC,String EscortAC,String EscortNonAC){
        Web.writeInTextBox(pageDriver,slabName,SlabName);
        Web.writeInTextBox(pageDriver,maxKm,MaxKm);
        Web.writeInTextBox(pageDriver,fuelCost,FuelCost);
        Web.writeInTextBox(pageDriver,fixedCost,FixedCost);
        jumpcrieteriaDropdown.sendKeys(jumpcriteria);
        Web.click(pageDriver, jumpcrieteriaDropdown);
        Web.writeInTextBox(pageDriver,extraKmCost,ExtraKmCost);
        Web.writeInTextBox(pageDriver,acSurcharge,ACSurcharge);
        Web.writeInTextBox(pageDriver,additionalExtraKmCostAC,AdditionalExtraKmCostAC);
        Web.writeInTextBox(pageDriver,escortAC,EscortAC);
        Web.writeInTextBox(pageDriver,escortNonAC,EscortNonAC);
        return true;
    }

}
