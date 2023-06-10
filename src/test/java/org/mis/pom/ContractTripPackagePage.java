package org.mis.pom;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.mis.utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.IOException;
public class ContractTripPackagePage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;

    @FindBy(xpath = "//a[@id='contract']//span[contains(text(),'CONTRACT')]")
    private WebElement clickOnContractButton;
    @FindBy(xpath = "//i[@class='material-icons'][contains(text(),'search')]")
    private WebElement clickOnSearchContractButton;
    @FindBy(css = "#slider > div > span:nth-child(3) > input")
    private WebElement contractSearchBox;
    @FindBy(xpath = "//i[contains(text(),'edit')]")
    private WebElement editContractButton;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select")
    private WebElement contractTypeDropdown;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(5) > select")
    private WebElement cabTypeDropdown;
    @FindBy(css = "#gst")
    private WebElement gstTextBox;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(9) > select")
    private WebElement fuelTypeTextBox;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(10) > select")
    private WebElement contractTypeDropdownSelect;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(11) > select")
    private WebElement tripKmSchemaDropdown;
    @FindBy(css = "#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(13) > select")
    private WebElement dutyParameterDropdown;
    @FindBy(css = "#tPackageslabName0")
    private WebElement addTripPackageSlabNameTextBox;
    @FindBy(css = "#tFixedKm0")
    private WebElement addTripPackageSlabMaxKmTextBox;
    @FindBy(css = "#tFuelCost0")
    private WebElement addTripPackageSlabFuelCostTextBox;
    @FindBy(css = "#tFixedCost0")
    private WebElement addTripPackageSlabFixedCost;
    @FindBy(css = "#tJumpCriteria0")
    private WebElement addTripPackageSlabJumpCriteriaDropdown;
    @FindBy(css = "#tExtraKmCost0")
    private WebElement addTripPackageSlabExtraKmCostTextBox;
    @FindBy(css = "#tacCost0")
    private WebElement addTripPackageSlabACSurchargeTextBox;
    @FindBy(css = "#tacKm0")
    private WebElement addTripPackageSlabAdditionalExtraKmCostACTextBox;
    @FindBy(css = "#tacEscortCost0")
    private WebElement addTripPackageSlabEscortACTextBox;
    @FindBy(css = "#tescortCost0")
    private WebElement addTripPackageSlabEscortNonACTextBox;
    @FindBy(xpath = "//input[@class='waves-effect waves-light btn-large save']")
    private WebElement clickOnSaveContractButton;
    @FindBy(xpath = "//button[@class='swal2-confirm swal2-styled']")
    private WebElement acceptSaveContract;
    public ContractTripPackagePage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    public boolean clickOnContractPage() {
        return Web.click(pageDriver, clickOnContractButton);
    }
    public boolean clickToContractUpdate() throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchContractButton);
        return Web.click(pageDriver, clickOnSearchContractButton);
    }
    public boolean clickSearch(String ContractName) {
        pageActions.waitToLoadElement(contractSearchBox);
        Web.click(pageDriver, contractSearchBox);
        contractSearchBox.sendKeys(ContractName);
        return Web.click(pageDriver, editContractButton);
    }
    public boolean selectBillingModel(String BillingModel) {
        contractTypeDropdown.sendKeys(BillingModel);
        return Web.click(pageDriver, contractTypeDropdown);
    }
    public boolean selectCabType(String CabType) {
        cabTypeDropdown.sendKeys(CabType);
        return Web.click(pageDriver, cabTypeDropdown);
    }
    public boolean editGST(String gst) {
        return Web.writeInTextBox(pageDriver, gstTextBox, gst);
    }
    public boolean selectFuelType(String fuelType) {
        fuelTypeTextBox.sendKeys(fuelType);
        return Web.click(pageDriver, fuelTypeTextBox);
    }
    public boolean selectBillingContractType(String BillingContractType) {
        contractTypeDropdownSelect.sendKeys(BillingContractType);
        return Web.click(pageDriver, contractTypeDropdownSelect);
    }
    public boolean selectTripKmSchema(String TripKmSchema) {
        tripKmSchemaDropdown.sendKeys(TripKmSchema);
        return Web.click(pageDriver, tripKmSchemaDropdown);
    }
    public boolean selectDutyParameter(String Dutyparameter) {
        dutyParameterDropdown.sendKeys(Dutyparameter);
        return Web.click(pageDriver, dutyParameterDropdown);
    }
    public void scrollDown() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,600)");
        System.out.println("scroll -----");
    }
    public boolean addTripPackageSlabName(String SlabName) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabNameTextBox, SlabName);
    }
    public boolean addTripPackageSlabMaxKm(String MaxKm) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabMaxKmTextBox, MaxKm);
    }
    public boolean addTripPackageSlabFuelCost(String FuelCost) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabFuelCostTextBox, FuelCost);
    }
    public boolean addTripPackageSlabFixedCost(String FixedCost) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabFixedCost, FixedCost);
    }
    public boolean addTripPackageSlabJumpCriteria(String Jumpcriteria) {
        addTripPackageSlabJumpCriteriaDropdown.sendKeys(Jumpcriteria);
        return Web.click(pageDriver, addTripPackageSlabJumpCriteriaDropdown);
    }
    public boolean addTripPackageSlabExtraKmCost(String Extrakmcost) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabExtraKmCostTextBox, Extrakmcost);
    }
    public boolean addTripPackageSlabACSurcharge(String ACSurcharge) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabACSurchargeTextBox, ACSurcharge);
    }
    public boolean addTripPackageSlabAdditionalExtraKmCostAC(String AdditionalExtraKmcostAC) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabAdditionalExtraKmCostACTextBox, AdditionalExtraKmcostAC);
    }
    public boolean addTripPackageSlabEscortAC(String EscortAC) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabEscortACTextBox, EscortAC);
    }
    public boolean addTripPackageSlabEscortNonAC(String EscortNonAC) {
        return Web.writeInTextBox(pageDriver, addTripPackageSlabEscortNonACTextBox, EscortNonAC);
    }
    public boolean saveContract() {
        Web.click(pageDriver, clickOnContractButton);
        Web.click(pageDriver, acceptSaveContract);
        return true;
    }
    // validate bill
    public void validateReportData(String tripId) throws InterruptedException, IOException {
        DownloadBillReport downloadBillReport = new DownloadBillReport(pageDriver);
        String reportColVal = downloadBillReport.getTripInfo(tripId, 10);
        String contractOutput = WebElementHelper.getTripBasedCellValue(1, 25);
        System.out.println(contractOutput);
        if (StringUtils.isNotBlank(reportColVal) && StringUtils.isNotBlank(contractOutput)) {
            Assert.assertEquals(reportColVal, contractOutput);
            System.out.println("Bill Matched");
        }
    }
}