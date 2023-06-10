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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class ContractKmBased {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public ContractKmBased(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'KM SLAB')]")
    private WebElement KMSlab;
    @FindBy(xpath = "(//div[@class='col s12 packageCost']//i)[10]")
    private WebElement KMSlabPlusIcon;
    @FindBy(id = "kSlabName0")
    private WebElement KMSlabName;
    @FindBy(id = "kSlab0")
    private WebElement KmSlabMaxKm;
    @FindBy(id = "cPerKmSlab0")
    private WebElement KMSlabCostPerKm;
    @FindBy(xpath = "(//input[@id='cPerKmSlab0'])[2]")
    private WebElement KMSlabMinCost;
    @FindBy(id = "mileage")
    private WebElement mileage;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(13) > select")
    private WebElement distributionLogic;


    public boolean selectMileageAndDistributionLogic(String Mileage,String DistributionLogic){
        Web.writeInTextBox(pageDriver, mileage, Mileage);
        distributionLogic.sendKeys(DistributionLogic);
        return Web.click(pageDriver, distributionLogic);
    }

    public void clickOnKmSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
       // pageDriver.findElement(By.xpath("//div[contains(text(),'KM SLAB')]")).click();
        //pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[10]")).click();
    }

    public void clickOnKmSlabForAddContract() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
         pageDriver.findElement(By.xpath("//div[contains(text(),'KM SLAB')]")).click();
        pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[10]")).click();
    }

    public boolean updateKmSlab(String slabName,String maxKm,String costPerKm,String minCost){
        Web.writeInTextBox(pageDriver, KMSlabName, slabName);
        Web.writeInTextBox(pageDriver, KmSlabMaxKm, maxKm);
        Web.writeInTextBox(pageDriver, KMSlabCostPerKm, costPerKm);
        Web.writeInTextBox(pageDriver, KMSlabMinCost, minCost);
        return true;
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