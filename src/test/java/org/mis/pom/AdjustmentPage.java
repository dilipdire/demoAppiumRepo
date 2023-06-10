package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AdjustmentPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;
    public AdjustmentPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        PageActions pageActions = new PageActions(pageDriver);
    }
    @FindBy(id = "adjustment")
    private WebElement clickOnAdjustentLink;
    @FindBy(xpath = "//i[contains(normalize-space(),'add')]")
    private WebElement addNewAdjustmentButton;
    @FindBy(css = "select[formcontrolname='entityType']")
    private WebElement entityTypeDropdown;
    @FindBy(xpath = "(//select[@formcontrolname='entityId'])[1]")
    private WebElement cabIdDropdown;
    @FindBy(xpath = "(//select[@formcontrolname='entityId'])[2]")
    private WebElement vendorDropdown;
    @FindBy(xpath = "//input[@formcontrolname='entityId']")
    private WebElement tripIdDropdown;
    @FindBy(css = "select[formcontrolname='adjustmentType']")
    private WebElement adjustmentTypeDropdown;
    @FindBy(xpath = "//my-date-picker[@name='date']//span[@class='mydpicon icon-mydpcalendar']")
    private WebElement datePicker;
    @FindBy(xpath = "//input[@formcontrolname='amount']")
    private WebElement amountTextBox;
    @FindBy(xpath = "//textarea[@formcontrolname='comment']")
    private WebElement commentTextBox;
    @FindBy(css = "button[class='waves-effect waves-light btn save']")
    private WebElement saveButtonInAddAdjustmentPage;
    @FindBy(xpath = "//i[@class='material-icons'][contains(text(),'search')]")
    private WebElement clickOnSearchForAdjustmentButton;
    @FindBy(xpath = "//i[contains(text(),'edit')]")
    private WebElement adjustmentEditLink;
    @FindBy(css = "my-date-picker[name='startDate'] span[class='mydpicon icon-mydpcalendar']")
    private WebElement startDatePickerInSearch;
    @FindBy(css = "my-date-picker[name='endDate'] span[class='mydpicon icon-mydpcalendar']")
    private WebElement endDatePickerInSearch;
    @FindBy(xpath = "//i[contains(text(),'file_upload')]")
    private WebElement clickOnBulkUpload;
    public boolean navigateToCabContractPage() {
        return Web.click(pageDriver, clickOnAdjustentLink);
    }
    public boolean clickOnAddNewCabContract() {
        return Web.click(pageDriver, addNewAdjustmentButton);
    }
    public boolean addCabContract(String entityType, String date, String adjustmentType,
                                  String amount, String comment) throws Exception {
        entityTypeDropdown.sendKeys(entityType);
        Web.click(pageDriver, entityTypeDropdown);
        Web.selectSingleCalenderDate(pageDriver,datePicker,date);
        adjustmentTypeDropdown.sendKeys(adjustmentType);
        Web.click(pageDriver, adjustmentTypeDropdown);
        Web.writeInTextBox(pageDriver,amountTextBox,amount);
        Web.writeInTextBox(pageDriver,commentTextBox,comment);
        Thread.sleep(5000);
        return true;
    }
    public void saveAdjustment() {
        saveButtonInAddAdjustmentPage.click();
    }
    public void search(String startDate,String endDate) throws Exception {
        Web.selectSingleCalenderDate(pageDriver,startDatePickerInSearch,startDate);
        Web.selectSingleCalenderDate(pageDriver,endDatePickerInSearch,endDate);
        Thread.sleep(5000);

    }
    public boolean clickOnEditNewCabContract() {
        return Web.click(pageDriver, adjustmentEditLink);
    }

    public void verifyCabContract(String entityType, String date, String adjustmentType,
                                  String amount, String comment) throws InterruptedException {
        Select select = new Select(entityTypeDropdown);
        WebElement option = select.getFirstSelectedOption();
        String getDayType = pageDriver.findElement(By.xpath("//span[contains(text(),'"+DayType+"')]")).getText();
        String getTripType = pageDriver.findElement(By.xpath("//span[contains(text(),'"+TripType+"')]")).getText();
//        String getStartTime = pageDriver.findElement(By.xpath("//span[contains(text(),'"+startTime+"')]")).getText();
//        String getEndTime = pageDriver.findElement(By.xpath("//span[contains(text(),'"+endTime+"')]")).getText();
        String getContract = pageDriver.findElement(By.xpath("//span[contains(text(),'"+Contract+"')]")).getText();
        Assert.assertEquals(getOffice, Office);
        Assert.assertEquals(getDayType, DayType);
        Assert.assertEquals(getTripType, TripType);
//        Assert.assertEquals(getStartTime, startTime);
//        Assert.assertEquals(getEndTime, endTime);
        Assert.assertEquals(getContract, Contract);
        refreshPage();
    }
    private void refreshPage() throws InterruptedException {
        pageDriver.navigate().refresh();
        Thread.sleep(2000);
    }
    //    private void bulkUploadCabContract() {
//        clickOnBulkUpload.click();
//        pageDriver.findElement(By.xpath("//input[@id='fileUpload']")).sendKeys("D:"+File.separator+"images"+File.separator+"Lighthouse.jpg"");
//
//
//    }
    // To select single calendar Date of cab contract page

    public boolean updateCabContract(String CabRegistration, String StartDate, String Office,
                                     String DayType, String TripType, String StartTime, String EndTime,
                                     String Direction, String Contract) {
        if (selectCabRegistration == null) {
            Logger.log("Failed to find Cab Registration");
            return false;
        }
        Select dropdownCabRegistration = new Select(selectCabRegistration);
        dropdownCabRegistration.selectByVisibleText(CabRegistration);
        if (selectStartDate == null) {
            Logger.log("Failed to find start date");
            return false;
        }
        selectStartDate.sendKeys(StartDate);
        if (selectOffice == null) {
            Logger.log("Failed to find office");
            return false;
        }
        Select dropdownOffice = new Select(selectOffice);
        dropdownOffice.selectByVisibleText(Office);
        if (selectDayType == null) {
            Logger.log("Failed to find DayType");
            return false;
        }
        Select dropdownDayType = new Select(selectDayType);
        dropdownDayType.selectByVisibleText(DayType);
        if (selectTripType == null) {
            Logger.log("Failed to find TripType");
            return false;
        }
        Select dropdownTripType = new Select(selectTripType);
        dropdownTripType.selectByVisibleText(TripType);
        if (selectStartTime == null) {
            Logger.log("Failed to find start time");
            return false;
        }
        selectStartTime.sendKeys(StartTime);
        if (selectEndTime == null) {
            Logger.log("Failed to find end time");
            return false;
        }
        selectEndTime.sendKeys(EndTime);
        if (selectDirection== null) {
            Logger.log("Failed to find Direction");
            return false;
        }
        Select dropdownDirection = new Select(selectDirection);
        dropdownDirection.selectByVisibleText(Direction);
        if (selectContract == null) {
            Logger.log("Failed to find Contract");
            return false;
        }
        Select dropdownContract = new Select(selectContract);
        dropdownContract.selectByVisibleText(Contract);
        return true;
    }
}
