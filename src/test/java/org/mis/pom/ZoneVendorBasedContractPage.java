package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.mis.misc.Logger;
import org.mis.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class ZoneVendorBasedContractPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public ZoneVendorBasedContractPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[contains(text(),'ZONE VENDOR SLAB')]")
    private WebElement zoneVendorSlab;
    @FindBy(xpath = "//ul[@class='collapsible row zoneVendorPackage d-background']//i[@class='material-icons'][contains(text(),'add')]")
    private WebElement zoneVendorSlabPlusIcon;
    @FindBy(id = "tripCount0")
    private WebElement zoneVendorSlabTripCount;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select")
    private WebElement billingModel;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(14) > select")
    private WebElement dutyParameter;
    @FindBy(xpath = "//div[contains(text(),'ZONE VENDOR DATE')]")
    private WebElement zoneVendorDateSlab;
    @FindBy(xpath = "//ul[@class='collapsible row zoneVendorDatePackage d-background']//i[@class='material-icons'][contains(text(),'add')]")
    private WebElement zoneVendorDateSlabPlusIcon;
    @FindBy(xpath = "//div[@class='col s10']//input[@id='fuelCost0']")
    private WebElement zoneVendorDateSlabFuelCost;
    @FindBy(xpath = "//input[@id='fixedCost0']")
    private WebElement zoneVendorDateSlabFixedCost;
    @FindBy(id = "zVendorDateTripCount0")
    private WebElement zoneVendorDateSlabTripCount;
    @FindBy(id = "zoneId0")
    private WebElement zoneSlabZoneName;

    public void clickOnZoneVendorSlab(){
            pageDriver.manage().window().maximize();
            JavascriptExecutor js = (JavascriptExecutor) pageDriver;
            js.executeScript("window.scrollBy(0,500)");
            System.out.println("scroll -----");
            pageDriver.findElement(By.xpath("//div[contains(text(),'ZONE VENDOR SLAB')]")).click();
            pageDriver.findElement(By.xpath("//ul[@class='collapsible row zoneVendorPackage d-background']//i[@class='material-icons'][contains(text(),'add')]")).click();
        }
        public boolean updateZoneVendorSlab(String zone,String fuelCost,String fixedCost){
            zoneSlabZoneName.sendKeys(zone);
            Web.click(pageDriver, zoneSlabZoneName);
            Web.writeInTextBox(pageDriver,zoneVendorDateSlabFuelCost,fuelCost);
            Web.writeInTextBox(pageDriver,zoneVendorDateSlabFixedCost,fixedCost);
            return true;
    }
    public void addTripCount(){
        pageDriver.findElement(By.id("tripCount0")).clear();
        String val = WebElementHelper.getZoneVendorBasedCellValue( 1, 20);
        pageDriver.findElement(By.id("tripCount0")).sendKeys(val);
    }
    public void selectBillingModel() {
        WebElement billingModel = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select"));
        WebElementHelper.putValueInDropdownZoneVendor(1, 2, billingModel);
    }
    public void selectDutyParameter() {
        WebElement dutyParameter = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(14) > select"));
        WebElementHelper.putValueInDropdownZoneVendor(1,11,dutyParameter);
    }
    public void clickOnZoneVendorDateSlab(){
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,200)");
        System.out.println("scroll -----");
        pageDriver.findElement(By.xpath("//div[contains(text(),'ZONE VENDOR DATE')]")).click();
        pageDriver.findElement(By.xpath("//ul[@class='collapsible row zoneVendorDatePackage d-background']//i[@class='material-icons'][contains(text(),'add')]")).click();
    }
    public void addFuelCost(){
        pageDriver.findElement(By.xpath("//div[@class='col s10']//input[@id='fuelCost0']")).clear();
        String val = WebElementHelper.getZoneVendorBasedCellValue( 1, 18);
        pageDriver.findElement(By.xpath("//div[@class='col s10']//input[@id='fuelCost0']")).sendKeys(val);
    }
    public void addFixedCost(){
        pageDriver.findElement(By.xpath("//input[@id='fixedCost0']")).clear();
        String val = WebElementHelper.getZoneVendorBasedCellValue( 1, 19);
        pageDriver.findElement(By.xpath("//input[@id='fixedCost0']")).sendKeys(val);
    }
    public void addTripCountinVendorDate(){
        pageDriver.findElement(By.id("zVendorDateTripCount0")).clear();
        String val = WebElementHelper.getZoneVendorBasedCellValue( 1, 20);
        pageDriver.findElement(By.id("zVendorDateTripCount0")).sendKeys(val);
    }
    public void selectSingleCalenderDate(String date) throws Exception {
        pageDriver.findElement(By.xpath("//my-date-picker[@name='zVendorDateStartDate']//button[@class='btnpicker btnpickerenabled']")).click();
        Thread.sleep(10000);
        DateAndTime dateAndTime = new DateAndTime();
        String tableDate = dateAndTime.switchDateFormat(date, "dd/MM/yyyy", "MMM d, yyyy");
        String month = tableDate.split(",")[0].split(" ")[0].trim();
        String year = tableDate.split(",")[1].trim();
        String day = tableDate.split(",")[0].split(" ")[1].trim();
        pageDriver.findElement(By.xpath("//button[@class='headerlabelbtn monthlabel']")).click();
        List<WebElement> months = pageDriver.findElements(By.xpath("//table[@class='monthtable']//td"));
        for (WebElement monthActual : months) {
            if (monthActual.getText().equals(month)) {
                monthActual.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }
        pageDriver.findElement(By.xpath("//button[@class='headerlabelbtn yearlabel']")).click();
        List<WebElement> years = pageDriver.findElements(By.xpath("//table[@class='yeartable']//td"));
        for (WebElement actualYear : years) {
            if (actualYear.getText().equals(year)) {
                actualYear.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }
        List<WebElement> days = pageDriver.findElements(By.xpath("//table[@class='caltable']//td//div//span"));
        for (WebElement actualDay : days) {
            if (actualDay.getText().equals(day)) {
                actualDay.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }

    }




    public void validateReportData(String tripId, String contractOutput) throws InterruptedException, IOException {
        DownloadBillReport downloadBillReport = new DownloadBillReport(pageDriver);
        String reportColVal = downloadBillReport.getTripInfo(tripId, 10);
        System.out.println(contractOutput);
        if (StringUtils.isNotBlank(reportColVal) && StringUtils.isNotBlank(contractOutput)) {
            Assert.assertEquals(reportColVal, contractOutput);
            System.out.println("Bill Matched");
        }
    }
}
