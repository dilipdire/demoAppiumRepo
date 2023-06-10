package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.utils.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TripPackageContractPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public TripPackageContractPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnContractPage()  {
        pageDriver.findElement(By.cssSelector("#contract > span")).click();

    }
    public void clickToContractUpdate()  {
        pageDriver.findElement(By.xpath("//i[@class='material-icons'][contains(text(),'search')]")).click();
        pageDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public void clickSearchTripPackage() {
        pageDriver.findElement(By.cssSelector("#slider > div > span:nth-child(3) > input")).sendKeys("Trip_Test1");
        pageDriver.findElement(By.xpath("//i[contains(text(),'edit')]")).click();
    }

    public void selectBillingModel() {
        WebElement billingModel = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select"));
        WebElementHelper.putValueInDropdown(1, 2, billingModel);
    }

    public void selectCabType() {
        WebElement cabType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(5) > select"));
        WebElementHelper.putValueInDropdown(1, 3, cabType);
    }
    public void editGST() {
        pageDriver.findElement(By.cssSelector("#gst")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 5);
        pageDriver.findElement(By.cssSelector("#gst")).sendKeys(val);
    }
    public void selectFuelType() {
        WebElement fuelType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(9) > select"));
        WebElementHelper.putValueInDropdown(1, 7, fuelType);
    }
    public void selectBillingContractType() {
        WebElement billingContractType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(10) > select"));
        WebElementHelper.putValueInDropdown(1, 8, billingContractType);
    }
    public void selectTripKmSchema() {
        WebElement tripKmSchema = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(11) > select"));
        WebElementHelper.putValueInDropdown(1,9,tripKmSchema);
    }
    public void selectDutyParameter() {
        WebElement dutyParameter = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(13) > select"));
        WebElementHelper.putValueInDropdown(1,11,dutyParameter);
    }
    public void clickOnTripPackageSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
//        pageDriver.findElement(By.xpath("//div[contains(text(),'TRIP PACKAGE SLAB')]")).click();
    }
    public void addTripPackageSlabName() {
        pageDriver.findElement(By.cssSelector("#tPackageslabName0")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 15);
        pageDriver.findElement(By.cssSelector("#tPackageslabName0")).sendKeys(val);
    }
    public void addTripPackageSlabMaxKm() {
        pageDriver.findElement(By.cssSelector("#tFixedKm0")).clear();
        String val = WebElementHelper.getTripBasedCellValue(1, 16);
        pageDriver.findElement(By.cssSelector("#tFixedKm0")).sendKeys(val);
    }
    public void addTripPackageSlabFuelCost() {
        pageDriver.findElement(By.cssSelector("#tFuelCost0")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 17);
        pageDriver.findElement(By.cssSelector("#tFuelCost0")).sendKeys(val);
    }
    public void addTripPackageSlabFixedCost() {
        pageDriver.findElement(By.cssSelector("#tFixedCost0")).clear();
        String val = WebElementHelper.getTripBasedCellValue(1, 18);
        pageDriver.findElement(By.cssSelector("#tFixedCost0")).sendKeys(val);
    }
    public void addTripPackageSlabJumpCriteria() {
        WebElement dutyParameter = pageDriver.findElement(By.cssSelector("#tJumpCriteria0"));
        WebElementHelper.putValueInDropdown(1,19,dutyParameter);
    }
    public void addTripPackageSlabExtraKmCost() {
        pageDriver.findElement(By.cssSelector("#tExtraKmCost0")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 20);
        pageDriver.findElement(By.cssSelector("#tExtraKmCost0")).sendKeys(val);
    }
    public void addTripPackageSlabACSurcharge() {
        pageDriver.findElement(By.cssSelector("#tacCost0")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 21);
        pageDriver.findElement(By.cssSelector("#tacCost0")).sendKeys(val);
    }
    public void addTripPackageSlabAdditionalExtraKmCostAC() {
        pageDriver.findElement(By.cssSelector("#tacKm0")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 22);
        pageDriver.findElement(By.cssSelector("#tacKm0")).sendKeys(val);
    }
    public void addTripPackageSlabEscortAC() {
        pageDriver.findElement(By.cssSelector("#tacEscortCost0")).clear();
        String val = WebElementHelper.getTripBasedCellValue( 1, 23);
        pageDriver.findElement(By.cssSelector("#tacEscortCost0")).sendKeys(val);
    }
    public void addTripPackageSlabEscortNonAC() {
        pageDriver.findElement(By.cssSelector("#tescortCost0")).clear();
        String val = WebElementHelper.getTripBasedCellValue(1, 24);
        pageDriver.findElement(By.cssSelector("#tescortCost0")).sendKeys(val);
    }
    public void saveContract() {
        pageDriver.findElement(By.xpath("//input[@class='waves-effect waves-light btn-large save']")).click();
        pageDriver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        pageDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

    }

}

