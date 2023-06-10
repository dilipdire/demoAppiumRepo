package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.DateAndTime;
import org.mis.utils.FileConstant;
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

import java.util.List;

public class AdjustmentTabPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;
    public AdjustmentTabPage(WebDriver driver) {
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
    @FindBy(xpath = "(//input[@formcontrolname='entityId'])[1]")
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
    @FindBy(xpath = "//button[contains(text(),'Save')]")
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
    @FindBy(css = "#toast-container")
    private WebElement validationMsg;
    @FindBy(xpath = "//input[@id='fileUpload']")
    private WebElement chooseFile;
    public boolean navigateToAdjustmentPage() {
        return Web.click(pageDriver, clickOnAdjustentLink);
    }
    public boolean clickOnAddNewAdjustment() {
        return Web.click(pageDriver, addNewAdjustmentButton);
    }
    public boolean addCabAdjustment(String entityType, String entityId,String date, String adjustmentType,
                                  String amount, String comment) throws Exception {
        entityTypeDropdown.sendKeys(entityType);
        Web.click(pageDriver, entityTypeDropdown);
        if(entityType.equals("CAB")) {
            cabIdDropdown.sendKeys(entityId);
            Web.click(pageDriver, cabIdDropdown);
        }
        if(entityType.equals("TRIP")) {
            Web.writeInTextBox(pageDriver, tripIdDropdown, amount);
        }
        if(entityType.equals("VENDOR")) {
            vendorDropdown.sendKeys(entityId);
            Web.click(pageDriver, vendorDropdown);
        }
        Thread.sleep(5000);
        adjustmentTypeDropdown.sendKeys(adjustmentType);
        Thread.sleep(5000);
        Web.click(pageDriver, adjustmentTypeDropdown);
        if(adjustmentType.equals("MISC")) {
            Web.writeInTextBox(pageDriver, amountTextBox, amount);
        }
        Web.writeInTextBox(pageDriver,commentTextBox,comment);
        if(date.equals("NULL")){
            //do nothing
        }else {
            Web.selectSingleCalenderDate(pageDriver, datePicker, date);
        }
        Thread.sleep(5000);
        return true;
    }


    public boolean checkAdd(String EntityId,String Amount,String adjustmentType){
        Amount = Amount.replaceAll("\\.0*$", "");
        if ((pageDriver.findElement(By.xpath("//span[contains(text(),'"+EntityId+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+Amount+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+adjustmentType+"')]")).isDisplayed())) {
            System.out.println("Added Adjustment Exists");
            Logger.log("Added Adjustment Exists");
        }else{
            System.out.println("Added Adjustment does not Exists");
            Logger.log("Added Adjustment does not Exists");
            Assert.fail();
        }
        return true;
    }

    public boolean saveAdjustment() {
        return Web.click(pageDriver,saveButtonInAddAdjustmentPage);
    }
    public void search(String startDate,String endDate,String date) throws Exception {
        Web.selectSingleCalenderDate(pageDriver,startDatePickerInSearch,startDate);
        Web.selectSingleCalenderDate(pageDriver,endDatePickerInSearch,endDate);
        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+date+"')]")).isDisplayed()){
            Logger.log("Search Succesful");
        }else{
            Logger.log("Search Unsuccesful");
        }
        Thread.sleep(5000);

    }
    public boolean clickOnEditNewCabContract() {
        return Web.click(pageDriver, adjustmentEditLink);
    }
    public String validation(String entityType, String entityId,String date, String adjustmentType,
                             String amount, String comment) throws Exception {
        clickOnAddNewAdjustment();
        addCabAdjustment(entityType,entityId,date,adjustmentType,amount,comment);
        saveAdjustment();
        //Thread.sleep(3000);
        //pageActions.waitToLoadElement(validationMsg);
        String actualMsg = validationMsg.getText();
        return actualMsg;
    }

    public void bulkUploadCabContract() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.ADJUSTMENT_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }

   /* public void verifyCabContract(String entityType, String date, String adjustmentType,
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
    }*/
    private void refreshPage() throws InterruptedException {
        pageDriver.navigate().refresh();
        Thread.sleep(2000);
    }

}
