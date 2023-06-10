package org.mis.pom;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
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
public class DailyPackage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;

    public DailyPackage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }

    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select")
    private WebElement contractTypeDropdown;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(5) > select")
    private WebElement cabTypeDropdown;
    @FindBy(css = "#gst")
    private WebElement gstTextBox;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(10) > select")
    private WebElement fuelTypeTextBox;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(11) > select")
    private WebElement contractTypeDropdownSelect;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(12) > select")
    private WebElement tripKmSchemaDropdown;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(13) > select")
    private WebElement distributionLogicDropdown;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(15) > select")
    private WebElement dutyParameterDropdown;
    @FindBy(xpath = "//input[@id='dSlabName0']")
    private WebElement addDailyPackageSlabNameTextBox;
    @FindBy(xpath = "//input[@id='dFixedKms0']")
    private WebElement addDailyPackageSlabFixedKmTextBox;
    @FindBy(xpath = "//input[@id='dFixedHour0']")
    private WebElement addDailyPackageSlabFixedHourTextBox;
    @FindBy(xpath = "//input[@id='dFixedCost0']")
    private WebElement addDailyPackageSlabFixedCostTextBox;
    @FindBy(xpath = "//select[@id='dJumpCriteria0']")
    private WebElement addDailyPackageSlabJumpCriteriaDropdown;
    @FindBy(xpath = "//input[@id='dExtraCost0']")
    private WebElement addDailyPackageSlabExtraKmCostTextBox;
    @FindBy(xpath = "//input[@id='dExtraHourCost0']")
    private WebElement addDailyPackageSlabExtraKmHourTextBox;
    @FindBy(xpath = "//input[@formcontrolname='milage']")
    private WebElement mileage;
    @FindBy(xpath = "//input[@formcontrolname='maxDutyHour']")
    private WebElement maxDutyHour;
    @FindBy(xpath = "//select[@formcontrolname='dailyPkgStartTimeType']")
    private WebElement dailyPkgStartTimeType;
    @FindBy(xpath = "//select[@formcontrolname='dailyPkgEndTimeType']")
    private WebElement dailyPkgEndTimeType;
    @FindBy(xpath = "//select[@formcontrolname='billCycleFuelHikeCalcType']")
    private WebElement billCycleFuelHikeCalcType;

    public void clickOnDailyPackageSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        //pageDriver.findElement(By.xpath("//div[contains(text(),'ZONE SLAB')]")).click();
        //pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[13]")).click();
    }

    public void clickOnDailyPackageSlabToAddContract() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        pageDriver.findElement(By.xpath("//div[contains(text(),'DAILY PACKAGE SLAB')]")).click();
        pageDriver.findElement(By.xpath("(//div[@class='col s12 packageCost']//i)[1]")).click();
    }
    public boolean selectDistributionLogic(String DistributionLogic,String Mileage) {
        distributionLogicDropdown.sendKeys(DistributionLogic);
        Web.click(pageDriver, distributionLogicDropdown);
        mileage.clear();
        mileage.sendKeys(Mileage);
        return true;
    }

    public boolean selectDutyParameter(String Dutyparameter) {
        dutyParameterDropdown.sendKeys(Dutyparameter);
        return Web.click(pageDriver, dutyParameterDropdown);
    }

    public boolean updateDailyPackageSlab(String SlabName, String FixedKm, String FixedHour, String FixedCost,
                                          String Jumpcriteria, String Extrakmcost, String ExtraHourcost) {
        Web.writeInTextBox(pageDriver, addDailyPackageSlabNameTextBox, SlabName);
        Web.writeInTextBox(pageDriver, addDailyPackageSlabFixedKmTextBox, FixedKm);
        Web.writeInTextBox(pageDriver, addDailyPackageSlabFixedHourTextBox, FixedHour);
        Web.writeInTextBox(pageDriver, addDailyPackageSlabFixedCostTextBox, FixedCost);
        addDailyPackageSlabJumpCriteriaDropdown.sendKeys(Jumpcriteria);
        Web.click(pageDriver, addDailyPackageSlabJumpCriteriaDropdown);
        Web.writeInTextBox(pageDriver, addDailyPackageSlabExtraKmCostTextBox, Extrakmcost);
        Web.writeInTextBox(pageDriver, addDailyPackageSlabExtraKmHourTextBox, ExtraHourcost);
        return true;
    }

    public boolean updateDlyPkg(String maxHourPerDuty,String dutyStartTime,String dutyEndTime,String fuelHikeCalculationType){
        maxDutyHour.clear();
        maxDutyHour.sendKeys(maxHourPerDuty);
        dailyPkgStartTimeType.sendKeys(dutyStartTime);
        Web.click(pageDriver, dailyPkgStartTimeType);
        dailyPkgEndTimeType.sendKeys(dutyEndTime);
        Web.click(pageDriver, dailyPkgEndTimeType);
        billCycleFuelHikeCalcType.sendKeys(fuelHikeCalculationType);
        pageActions.waitToLoadElement(billCycleFuelHikeCalcType);
        Web.click(pageDriver, billCycleFuelHikeCalcType);
        return true;
    }
}