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

public class ACSlabPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;

    @FindBy(xpath = "//ul[@class='collapsible row ac d-background']//i[@class='material-icons'][contains(text(),'add')]")
    private WebElement addACSlab;
    @FindBy(xpath = "//ul[@class='collapsible row ac d-background']//div[@class='collapsible-header'][contains(text(),'AC')]")
    private WebElement acSlab;
    @FindBy(xpath = "//ul[@class='collapsible row ac d-background']//i[@class='material-icons'][contains(text(),'add')]")
    private WebElement clickOnAddACSlab;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/i")
    private WebElement closedACSlab;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[1]/select")
    private WebElement selectACFlavor;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[3]/select")
    private WebElement selectACType;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[4]/select")
    private WebElement selectACTripType;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[5]/input")
    private WebElement addACStartTime;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[6]/input")
    private WebElement addACEndTime;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[7]/select")
    private WebElement selectACPriceType;
    @FindBy(xpath = "//*[@id=\"oPT1\"]")
    private WebElement addACPrice;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/form/ul[29]/li/div[2]/div[1]/div/div[9]/my-date-picker/div/div/input")
    private WebElement addACStartDate;

    public ACSlabPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean clickOnAddAC() {
        Web.click(pageDriver, closedACSlab);
        Web.click(pageDriver, clickOnAddACSlab);
        return true;
    }
    public void clickOnAddACSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,3000)");
        System.out.println("scroll -----");
        if (!selectACFlavor.isDisplayed()) {
            Web.click(pageDriver,acSlab);
            Web.click(pageDriver, clickOnAddACSlab);
        }
        if (closedACSlab.isDisplayed()) {
            Web.click(pageDriver, closedACSlab);
        }
        addACSlab.click();
        JavascriptExecutor js1 = (JavascriptExecutor) pageDriver;
        js1.executeScript("window.scrollBy(0,400)");
        System.out.println("scroll -----");
    }
    public boolean updateAC(String ACFlavour, String AcType, String AcTripType, String StartTime,
                            String EndTime, String ACPriceType, String ACPrice, String StartDate) {
        if (selectACFlavor == null) {
            Logger.log("Failed to find ACFlavour field");
            return false;
        }
        Select dropdown = new Select(selectACFlavor);
        dropdown.selectByVisibleText(ACFlavour);
        if (selectACType == null) {
            Logger.log("Failed to find ACType field");
            return false;
        }
        Select dropdownACType = new Select(selectACType);
        dropdownACType.selectByVisibleText(AcType);
        if (selectACTripType == null) {
            Logger.log("Failed to find ACTripType field");
            return false;
        }
        Select dropdownTripType = new Select(selectACTripType);
        dropdownTripType.selectByVisibleText(AcTripType);
        if (selectACPriceType == null) {
            Logger.log("Failed to find ACProceType field");
            return false;
        }
        Select dropdownPriceType = new Select(selectACPriceType);
        dropdownPriceType.selectByVisibleText(ACPriceType);
        if (addACStartTime == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACStartTime.sendKeys(StartTime);
        if (addACEndTime == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACEndTime.sendKeys(EndTime);
        if (addACPrice == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACPrice.sendKeys(ACPrice);
        if (addACStartDate == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACStartDate.sendKeys(StartDate);
        return true;
    }
    public boolean updateACForSlabRate(String ACFlavour, String AcType, String AcTripType, String StartTime,
                                       String EndTime, String ACPriceType, String StartDate) {
        Select dropdown = new Select(selectACFlavor);
        dropdown.selectByVisibleText(ACFlavour);
        if (selectACType == null) {
            Logger.log("Failed to find ACType field");
            return false;
        }
        Select dropdownACType = new Select(selectACType);
        dropdownACType.selectByVisibleText(AcType);
        if (selectACTripType == null) {
            Logger.log("Failed to find ACTripType field");
            return false;
        }
        Select dropdownTripType = new Select(selectACTripType);
        dropdownTripType.selectByVisibleText(AcTripType);
        if (selectACPriceType == null) {
            Logger.log("Failed to find ACProceType field");
            return false;
        }
        Select dropdownPriceType = new Select(selectACPriceType);
        dropdownPriceType.selectByVisibleText(ACPriceType);
        if (addACStartTime == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACStartTime.sendKeys(StartTime);
        if (addACEndTime == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACEndTime.sendKeys(EndTime);
        if (addACStartDate == null) {
            Logger.log("Failed to find PriceType field");
            return false;
        }
        addACStartDate.sendKeys(StartDate);
        return true;
    }
    // select AC date
    public void selectACSingleCalenderDate(String date) throws Exception {
        pageDriver.findElement(By.xpath("//input[@class='selection ng-pristine ng-valid ng-touched']")).click();
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
    public void validateReportData(String tripId, String ACCostOutput) throws InterruptedException, IOException {
        DownloadBillReport downloadBillReport = new DownloadBillReport(pageDriver);
        String reportColVal = downloadBillReport.getTripInfo(tripId, 25);
        System.out.println(ACCostOutput);
        if (StringUtils.isNotBlank(reportColVal) && StringUtils.isNotBlank(ACCostOutput)) {
            Assert.assertEquals(reportColVal, ACCostOutput);
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