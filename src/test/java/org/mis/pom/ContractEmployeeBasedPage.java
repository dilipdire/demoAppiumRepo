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
import java.util.concurrent.TimeUnit;
public class ContractEmployeeBasedPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public ContractEmployeeBasedPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[contains(text(),'EMPLOYEE MODEL')]")
    private WebElement employeeSlab;
    @FindBy(xpath = "(//div[@class='col s12 packageCost']//i)[17]")
    private WebElement employeeSlabPlusIcon;
    @FindBy(id = "logicTypeundefined")
    private WebElement logicTypeDropdown;
    @FindBy(id = "employeeTypeundefined")
    private WebElement employeeTypeDropdown;
    @FindBy(id = "slabName0")
    private WebElement employeeSlabName;
    @FindBy(id = "pEmpFuelCost0")
    private WebElement employeeSlabFuelCost;
    @FindBy(id = "pHeadCost0")
    private WebElement employeeSlabPerHeadCost;
    @FindBy(xpath = "(//input[@id='pHeadCost0'])[2]")
    private WebElement employeeSlabMaxKm;


    public void clickOnEmployeeBasedSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
       //pageDriver.findElement(By.xpath("//div[contains(text(),'EMPLOYEE MODEL')]")).click();
        //pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[17]")).click();
    }

    public void clickOnEmployeeBasedSlabForAddContract() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        pageDriver.findElement(By.xpath("//div[contains(text(),'EMPLOYEE MODEL')]")).click();
        pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[17]")).click();
    }
    public boolean updateEmployeeSlab(String logicType,String employeeType,String slabName,String fuelCost,String perHeadKm, String maxKm){
        logicTypeDropdown.sendKeys(logicType);
        Web.click(pageDriver, logicTypeDropdown);
        employeeTypeDropdown.sendKeys(employeeType);
        Web.click(pageDriver, employeeTypeDropdown);
        Web.writeInTextBox(pageDriver, employeeSlabName, slabName);
        Web.writeInTextBox(pageDriver, employeeSlabFuelCost, fuelCost);
        Web.writeInTextBox(pageDriver, employeeSlabPerHeadCost, perHeadKm);
        Web.writeInTextBox(pageDriver, employeeSlabMaxKm, maxKm);
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
