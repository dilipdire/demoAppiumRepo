package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.mis.misc.Logger;
import org.mis.utils.ExcelUtilities;
import org.mis.utils.UnzipUtility;
import org.mis.utils.Web;
import org.mis.utils.WebElementHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BillCyclePackageContractPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public BillCyclePackageContractPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@formcontrolname='dutyCount']")
    private WebElement duties;
    @FindBy(xpath = "//select[@formcontrolname='cumulativeLevel']")
    private WebElement cumulativeLevelDropdown;
    //@FindBy(xpath = "//select[@formcontrolname='billCycleFuelHikeCalcType']")
    @FindBy(css = ".collapsible.row.billPackage.d-background:nth-child(3) li.active div.collapsible-body div.col.s3.d-input:nth-child(4) > select.ng-untouched.ng-pristine.ng-valid:nth-child(2)")
    private WebElement billCycleFuelHikeCalcTypeDropdown;
    @FindBy(xpath = "//select[@formcontrolname='autoProrate']")
    private WebElement autoProrateDropdown;
    @FindBy(xpath = "(//input[@formcontrolname='maxDutyHour'])[2]")
    private WebElement maxDutyHourPerDuty;
    @FindBy(id = "billingCslabName0")
    private WebElement slabName;
    @FindBy(id = "pMaxKms10")
    private WebElement fixedKm;
    @FindBy(id = "bHourDurationType0")
    private WebElement hourDurationType;
    @FindBy(id = "pFixedOur0")
    private WebElement fixedHour;
    @FindBy(id = "bMinKmPDay0")
    private WebElement minKmPDay;
    @FindBy(id = "pKmCost10")
    private WebElement fixedCost;
    @FindBy(id = "pAbsentCut10")
    private WebElement absentCut;
    @FindBy(id = "pNumberDuty10")
    private WebElement extraKmCost;
    @FindBy(id = "pExtraHourCost10")
    private WebElement extraHourCost;
    @FindBy(id = "pJumpCriteria10")
    private WebElement jumpCriteria;
    @FindBy(id = "pCost10")
    private WebElement extraDutyCost;


    public boolean updateBillCyclePackageSlab(String Duties,String cumulativeLevel,String billCycleFuelHikeCalcType,String autoProrate,String MaxDutyHourPerDuty,
                              String SlabName,String FixedKm,String HourDurationType,String FixedHour,String MinKmPDay,String AbsentCut,String ExtraKmCost,
                              String ExtraHourCost,String JumpCriteria,String ExtraDutyCost,String FixedCost)  {
        cumulativeLevelDropdown.sendKeys(cumulativeLevel);
        Web.click(pageDriver, cumulativeLevelDropdown);
        cumulativeLevelDropdown.sendKeys(Keys.TAB);
        billCycleFuelHikeCalcTypeDropdown.sendKeys(billCycleFuelHikeCalcType);
        Web.click(pageDriver, billCycleFuelHikeCalcTypeDropdown);
        autoProrateDropdown.sendKeys(autoProrate);
        Web.click(pageDriver, autoProrateDropdown);
        Web.writeInTextBox(pageDriver,slabName,SlabName);
        Web.writeInTextBox(pageDriver,fixedKm,FixedKm);
        Web.writeInTextBox(pageDriver,fixedHour,FixedHour);
        Web.writeInTextBox(pageDriver,minKmPDay,MinKmPDay);
        Web.writeInTextBox(pageDriver,fixedCost,FixedCost);
        hourDurationType.sendKeys(HourDurationType);
        Web.click(pageDriver, hourDurationType);
        absentCut.sendKeys(AbsentCut);
        Web.click(pageDriver, absentCut);
        jumpCriteria.sendKeys(JumpCriteria);
        Web.click(pageDriver, jumpCriteria);
        Web.writeInTextBox(pageDriver,extraKmCost,ExtraKmCost);
        Web.writeInTextBox(pageDriver,extraHourCost,ExtraHourCost);
        Web.writeInTextBox(pageDriver,extraDutyCost,ExtraDutyCost);
        Web.writeInTextBox(pageDriver,maxDutyHourPerDuty,MaxDutyHourPerDuty);
        Web.writeInTextBox(pageDriver,duties,Duties);
        return true;

    }


    public void selectBillingModelBillCyclePackage() {
        WebElement billingModel = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1, 2, billingModel);
    }
    public void selectCabType() {
        WebElement cabType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(5) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1, 3, cabType);
    }
    public void editGST() {
        pageDriver.findElement(By.cssSelector("#gst")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 5);
        pageDriver.findElement(By.cssSelector("#gst")).sendKeys(val);
    }
    // public void editGST() {
    // pageDriver.findElement(By.cssSelector("#gst")).clear();
    //  String val = WebElementHelper.getTripBasedCellValue( 1, 5);
    //pageDriver.findElement(By.cssSelector("#gst")).sendKeys(val);
    //}
    public void selectMileage(){
        pageDriver.findElement(By.cssSelector("#mileage")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 7);
        pageDriver.findElement(By.cssSelector("#mileage")).sendKeys(val);
    }
    public void selectFuelType() {
        WebElement fuelType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(10) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1, 8, fuelType);
    }
    public void selectBillingContractType() {
        WebElement billingContractType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(11) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1, 9, billingContractType);
    }
    public void selectTripKmSchema() {
        WebElement tripKmSchema = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(12) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,10,tripKmSchema);
    }
    public void selectDistributionLogic(){
        WebElement tripKmSchema = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(13) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,11,tripKmSchema);
    }
    public void selectDutyParameter() {
        WebElement dutyParameter = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(15) > select"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,12,dutyParameter);
    }
    public void clickOnBillCyclePackageSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        //pageDriver.findElement(By.xpath("//div[contains(text(),'BILLING CYCLE PACKAGE SLAB')]")).click();
        //pageDriver.findElement(By.xpath("//ul[@class='collapsible row billPackage d-background']//i[@class='material-icons'][contains(text(),'add')]")).click();
    }
    public void clickOnBillCyclePackageSlabToAddContract() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
        pageDriver.findElement(By.xpath("//div[contains(text(),'BILLING CYCLE PACKAGE SLAB')]")).click();
        pageDriver.findElement(By.xpath("//ul[@class='collapsible row billPackage d-background']//i[@class='material-icons'][contains(text(),'add')]")).click();
    }
    public void addDuties(){
        pageDriver.findElement(By.xpath("//ul[@class='collapsible row billPackage d-background']//div[1]//input[1]")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 16);
        pageDriver.findElement(By.xpath("//ul[@class='collapsible row billPackage d-background']//div[1]//input[1]")).sendKeys(val);
    }
    public void addMaxDutyHourPerDuty(){
        pageDriver.findElement(By.xpath("(//input[@class='ng-pristine ng-valid ng-touched'])[2]")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 21);
        pageDriver.findElement(By.xpath("(//input[@class='ng-pristine ng-valid ng-touched'])[2]")).sendKeys(val);
    }
    public void selectCommulativeLevel() {
        WebElement cummuletiveLevel = pageDriver.findElement(By.xpath("//select[@class='ng-pristine ng-valid ng-touched']"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,18,cummuletiveLevel);
    }
    public void selectBillingCycleFuelHikeCalculationType() {
        WebElement cummuletiveLevel = pageDriver.findElement(By.cssSelector(".collapsible.row.billPackage.d-background:nth-child(3) li.active div.collapsible-body div.col.s3.d-input:nth-child(4) > select.ng-untouched.ng-pristine.ng-valid:nth-child(2)"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,19,cummuletiveLevel);
    }
    public void selectAutoProrate() {
        WebElement cummuletiveLevel = pageDriver.findElement(By.cssSelector("select.ng-pristine.ng-valid.ng-touched:nth-child(2)"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,20,cummuletiveLevel);
    }
    public void selectAutoProrateParameter() {
        WebElement cummuletiveLevel = pageDriver.findElement(By.cssSelector("div.collapsible-body div.col.s3.d-input:nth-child(6) > select.ng-pristine.ng-valid.ng-touched:nth-child(2)"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,13,cummuletiveLevel);
    }
    public void addProrateBufferDutyLow (){
        pageDriver.findElement(By.cssSelector("//ul[@class='collapsible row billPackage d-background']//div[1]//input[1]")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 7);
        pageDriver.findElement(By.xpath("//ul[@class='collapsible row billPackage d-background']//div[1]//input[1]")).sendKeys(val);
    }
    public void addSlabName (){
        pageDriver.findElement(By.id("billingCslabName0")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 22);
        pageDriver.findElement(By.id("billingCslabName0")).sendKeys(val);
    }
    public void addFixedKm (){
        pageDriver.findElement(By.id("pMaxKms10")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 23);
        pageDriver.findElement(By.id("pMaxKms10")).sendKeys(val);
    }
    public void selectHourDurationType() {
        WebElement cummuletiveLevel = pageDriver.findElement(By.id("bHourDurationType0"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,24,cummuletiveLevel);
    }
    public void addFixedHour (){
        pageDriver.findElement(By.id("pFixedOur0")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 25);
        pageDriver.findElement(By.id("pFixedOur0")).sendKeys(val);
    }
    public void addFixedCost (){
        pageDriver.findElement(By.id("pKmCost10")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 27);
        pageDriver.findElement(By.id("pKmCost10")).sendKeys(val);
    }
    public void selectAbsentCut() {
        WebElement cummuletiveLevel = pageDriver.findElement(By.id("pAbsentCut10"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,28,cummuletiveLevel);
    }
    public void addExtraKmCost(){
        pageDriver.findElement(By.id("pNumberDuty10")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 29);
        pageDriver.findElement(By.id("pNumberDuty10")).sendKeys(val);
    }
    public void addExtraHourCost(){
        pageDriver.findElement(By.id("pExtraHourCost10")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 30);
        pageDriver.findElement(By.id("pExtraHourCost10")).sendKeys(val);
    }
    public void addExtraDutyCost(){
        pageDriver.findElement(By.id("pCost10")).clear();
        String val = WebElementHelper.getBillCyclePackageellValue( 1, 32);
        pageDriver.findElement(By.id("pCost10")).sendKeys(val);
    }
    public void selectJumpCrieteria() {
        WebElement jumpCriteria = pageDriver.findElement(By.id("pJumpCriteria10"));
        WebElementHelper.putValueInDropdownBillCyclePackage(1,31,jumpCriteria);
    }



    public boolean downloadReportDefault(String tripId) {
        WebElement defaultEntry = pageDriver.findElement(By.xpath("//table[@id='offlineReportDownload']//tr[2]//td[2]"));
        boolean reportLocated = !defaultEntry.getText().isEmpty();
        boolean reportProcessed = false;
        if (reportLocated) {
            try {
                downloadReport(defaultEntry.getText(), tripId);
                reportProcessed = true;
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        return reportProcessed;
    }
    // download report and uzip the downloaded report (csv report) + read csv file
    public void downloadReport(String reportName, String tripId) throws InterruptedException, IOException {
        WebElement reportDownloadLink = pageDriver.findElement(By.xpath(String.format(
                "//td[contains(text(),'%s')]/..//a", reportName)));
        if (reportDownloadLink != null) {
            Logger.log("Download link clicked : " + Web.click(pageDriver, reportDownloadLink));
        }
        Web.waitForCommandToFinish();
        UnzipUtility.unzip(reportName);
        String downloadFile = UnzipUtility.getLastModified();
        String val = ExcelUtilities.getCsvVal(downloadFile, Integer.parseInt(tripId), 10);
        System.out.println(val);
        if (StringUtils.isBlank(val)){
            Logger.log("Trip Id not found");
            return;
        }else {
            Logger.log("Trip Id found");
        }
        String contractOutput = WebElementHelper.getBillCyclePackageellValue(1, 33);
        System.out.println(contractOutput);
        if (StringUtils.isNotBlank(val) && StringUtils.isNotBlank(contractOutput)) {
            Assert.assertEquals(val, contractOutput);
        }
    }



}
