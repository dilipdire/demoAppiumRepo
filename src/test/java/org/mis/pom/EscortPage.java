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

import static org.mis.utils.PropertyReader.getProperty;

public class EscortPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public EscortPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[contains(text(),'ESCORT')]")
    private WebElement escortSlab;
    @FindBy(xpath = "//ul[@class='collapsible row escort d-background']//i[@class='material-icons'][contains(text(),'add')]" )
    private WebElement addEscortSlab;
    @FindBy(xpath = "(//i[@class='material-icons'][contains(text(),'close')])[3]")
    private WebElement closeEscort;
    @FindBy(css = "#slider > div:nth-child(2) > form > ul:nth-child(21) > li > div > div > select" )
    private WebElement selectEscortUsage;
    //@FindBy(css = "#slider > div:nth-child(2) > form > ul:nth-child(21) > li > div > div:nth-child(3) > div > div > select" )
    @FindBy(css = "select[formcontrolname=\"escortCostType\"]")
    private WebElement selectCostType;
    @FindBy(id = "escortCost0" )
    private WebElement addEscortCost;
    @FindBy(id = "escortFuelCost0" )
    private WebElement addEscortFuelCost;
    @FindBy(xpath = "//label[contains(text(),'Escort Slab Cost')]//span[@class='lever']")
    private WebElement toggleinEscort;

    public void clickOnEscortSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        //js.executeScript("window.scrollBy(0,1800)");
        js.executeScript("window.scrollBy(0,1600)");
        //Thread.sleep(10000);
        System.out.println("scroll -----");
        if(!selectEscortUsage.isDisplayed()) {
            escortSlab.click();
        }
        if(closeEscort.isDisplayed()) {
            closeEscort.click();
        }
        addEscortSlab.click();
    }
    public void clickOnEscortSlabTotalCal() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        //js.executeScript("window.scrollBy(0,1800)");
        js.executeScript("window.scrollBy(0,1000)");
        //Thread.sleep(10000);
        System.out.println("scroll -----");
        if(!selectEscortUsage.isDisplayed()) {
            escortSlab.click();
        }
        if(closeEscort.isDisplayed()) {
            closeEscort.click();
        }
        addEscortSlab.click();
    }
    public void clickOnAddEscortSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,1600)");
        System.out.println("scroll -----");
        if (!selectEscortUsage.isDisplayed()) {
            Web.click(pageDriver,escortSlab);
            Web.click(pageDriver, addEscortSlab);
        }
        if (closeEscort.isDisplayed()) {
            closeEscort.click();
            //Web.click(pageDriver, closeEscort);
        }
        Thread.sleep(5000);
        addEscortSlab.click();
       JavascriptExecutor js1 = (JavascriptExecutor) pageDriver;
        js1.executeScript("window.scrollBy(0,1000)");
        System.out.println("scroll -----");
    }
    public void clickOnEscortSlabbillcycle() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        //js.executeScript("window.scrollBy(0,1800)");
        js.executeScript("window.scrollBy(0,700)");
        //Thread.sleep(10000);
        System.out.println("scroll -----");
        if(!selectEscortUsage.isDisplayed()) {
            escortSlab.click();
        }
        if(closeEscort.isDisplayed()) {
            closeEscort.click();
        }
        addEscortSlab.click();
    }
    public void clickOnEscortSlabKm() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,2000)");
        System.out.println("scroll -----");
        if(!selectEscortUsage.isDisplayed()) {
            escortSlab.click();
        }
        if(closeEscort.isDisplayed()) {
            closeEscort.click();
        }
        addEscortSlab.click();
    }

    public void clickOnEscortSlabEmployee() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,3000)");
        System.out.println("scroll -----");
        if(!selectEscortUsage.isDisplayed()) {
            escortSlab.click();
        }
        if(closeEscort.isDisplayed()) {
            closeEscort.click();
        }
        addEscortSlab.click();
    }

    public boolean updateEscort(String escortUsage,String costType,String cost,String fuelCost){
       /* if(!toggleinEscort.isSelected())
        {
            toggleinEscort.click();
        }*/
        if (selectEscortUsage == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        Select dropdown = new Select(selectEscortUsage);
        dropdown.selectByVisibleText(escortUsage);
        if (selectCostType == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        Select dropdownCostType = new Select(selectCostType);
        dropdownCostType.selectByVisibleText(costType);
        if (addEscortCost == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        addEscortCost.sendKeys(cost);

        if (addEscortFuelCost == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        addEscortFuelCost.sendKeys(fuelCost);
        return true;
    }
    public boolean updateEscortForSlabRate(String escortUsage,String costType,String fuelCost){
        if(!toggleinEscort.isSelected())
        {
            toggleinEscort.click();
        }
        if (selectEscortUsage == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        Select dropdown = new Select(selectEscortUsage);
        dropdown.selectByVisibleText(escortUsage);
        if (selectCostType == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        Select dropdownCostType = new Select(selectCostType);
        dropdownCostType.selectByVisibleText(costType);
        if (addEscortFuelCost == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        addEscortFuelCost.sendKeys(fuelCost);
        return true;
    }




    public void validateReportData(String tripId, String contractOutput) throws InterruptedException, IOException {
        DownloadBillReport downloadBillReport = new DownloadBillReport(pageDriver);
        String reportColVal = downloadBillReport.getTripInfo(tripId, 13);
        //String contractOutput = WebElementHelper.getTripBasedCellValue(1, 25);
        System.out.println(contractOutput);
        if (StringUtils.isNotBlank(reportColVal) && StringUtils.isNotBlank(contractOutput)) {
            Assert.assertEquals(reportColVal, contractOutput);
            System.out.println("Bill Matched");
        }
    }

    public void compareBill(String output,String Result){
        if (StringUtils.isNotBlank(output) && StringUtils.isNotBlank(Result)) {
            Assert.assertEquals(output, Result);
            System.out.println("Bill Matched");
        }
    }
}
