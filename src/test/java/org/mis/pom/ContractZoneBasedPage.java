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
import java.util.concurrent.TimeUnit;
public class ContractZoneBasedPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public ContractZoneBasedPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//div[contains(text(),'ZONE SLAB')]")
    private WebElement zoneSlab;
    @FindBy(xpath = "(//div[@class='col s12 packageCost']//i)[13]")
    private WebElement zoneSlabPlusIcon;
    @FindBy(id = "zoneSlabName0")
    private WebElement zoneSlabName;
    @FindBy(id = "zoneId0")
    private WebElement zoneSlabZoneName;
    @FindBy(id = "zFuelCost0")
    private WebElement zoneSlabFuelCost;
    @FindBy(id = "fixedCost0")
    private WebElement zoneSlabFixedCost;

    public void clickOnZoneBased() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        //pageDriver.findElement(By.xpath("//div[contains(text(),'ZONE SLAB')]")).click();
        //pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[13]")).click();
    }
    public void clickOnZoneBasedToAddContract() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        pageDriver.findElement(By.xpath("//div[contains(text(),'ZONE SLAB')]")).click();
        pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[13]")).click();
    }
    public boolean updateZoneSlab(String slabName,String zone,String fuelCost,String fixedCost){
        Web.writeInTextBox(pageDriver,zoneSlabName,slabName);
        zoneSlabZoneName.sendKeys(zone);
        Web.click(pageDriver, zoneSlabZoneName);
        Web.writeInTextBox(pageDriver,zoneSlabFuelCost,fuelCost);
        Web.writeInTextBox(pageDriver,zoneSlabFixedCost,fixedCost);
        return true;
    }

    public boolean checkZoneSlabIsDisable() throws InterruptedException {
        if(zoneSlab.isDisplayed()) {
            Logger.log("Corresponding Zone Slab Is Opening Properly");
        }else{
            Logger.log("Zone Slab Is Not Opening");
            Assert.fail();
        }
        Thread.sleep(5000);
        return true;
    }

}