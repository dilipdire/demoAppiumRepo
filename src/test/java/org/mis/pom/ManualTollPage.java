package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
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

public class ManualTollPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;
    public ManualTollPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        PageActions pageActions = new PageActions(pageDriver);
    }
    @FindBy(id = "toll")
    private WebElement clickOnTollLink;
    @FindBy(xpath = "//div[contains(text(),'MANUAL TOLL')]")
    private WebElement manualTollLink;
    @FindBy(xpath = "(//i[contains(normalize-space(),'add')])[4]")
    private WebElement addNewManualToll;
    @FindBy(css = "select[formcontrolname='entityType']")
    private WebElement entityTypeDropdown;
    @FindBy(xpath = "//input[@formcontrolname='entityId']")
    private WebElement cabIdDropdown;
    @FindBy(xpath = "//my-date-picker[@name='date']//span[@class='mydpicon icon-mydpcalendar']")
    private WebElement datePicker;
    @FindBy(xpath = "//input[@formcontrolname='amount']")
    private WebElement amountTextBox;
    @FindBy(xpath = "(//div[@class='col s2']//input[@class='waves-effect waves-light btn save'])[4]")
    //@FindBy(xpath = "(//input[@class='waves-effect waves-light btn save'])[4]")
    private WebElement addButton;
    @FindBy(css = "my-date-picker[name='startDate'] span[class='mydpicon icon-mydpcalendar']")
    private WebElement startDatePickerInSearch;
    @FindBy(css = "my-date-picker[name='endDate'] span[class='mydpicon icon-mydpcalendar']")
    private WebElement endDatePickerInSearch;
    //@FindBy(xpath = "//i[contains(text(),'edit')]")
    @FindBy(xpath = "//li[@class='active']//div[@class='col s12 content-border']//div[1]//div[1]//i[2]")
    private WebElement editLink;
    @FindBy(xpath = "//div[@id='toast-container']//div[@class='toast panning']")
    private WebElement validationMsg;
    @FindBy(xpath = "//body/app-root[1]/app-toll-details[1]/div[2]/ul[1]/li[4]/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/a[1]")
    private WebElement history;
    @FindBy(xpath = "(//i[contains(text(),'file_upload')])[2]")
    private WebElement clickOnBulkUpload;
    @FindBy(xpath = "//input[@id='fileUpload']")
    private WebElement chooseFile;

    public void bulkUploadManualToll() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.MANUAL_TOLL_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }
    public boolean navigateToManualTollPage() {
        Web.click(pageDriver, clickOnTollLink);
        return Web.click(pageDriver, manualTollLink);
    }
    public boolean clickOnAddNewManualToll() {

        return Web.click(pageDriver, addNewManualToll);
    }
    public boolean addManualToll(String entityType, String entityId,String date,
                                    String amount,String tripId) throws Exception {
        entityTypeDropdown.sendKeys(entityType);
        Web.click(pageDriver, entityTypeDropdown);
        if(entityType.equals("CAB")) {
            cabIdDropdown.sendKeys(entityId);
            Web.click(pageDriver, cabIdDropdown);
        }
        if(entityType.equals("TRIP")) {
            Web.writeInTextBox(pageDriver, cabIdDropdown, tripId);
        }
        Thread.sleep(5000);
            Web.writeInTextBox(pageDriver, amountTextBox, amount);
        if(date.equals("NULL")){
            //do nothing
        }else {
            Web.selectSingleCalenderDate(pageDriver, datePicker, date);
        }
        Thread.sleep(5000);
        return true;
    }
    public boolean checkAdd(String EntityId,String Amount,String date,String entityType){
        Amount = Amount.replaceAll("\\.0*$", "");
        if ((pageDriver.findElement(By.xpath("//span[contains(text(),'"+EntityId+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+Amount+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+date+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+entityType+"')]")).isDisplayed())) {
            System.out.println("Added Manual Toll Exists");
            Logger.log("Added Manual Toll Exists");
        }else{
            System.out.println("Added Manual Toll does not Exists");
            Logger.log("Added Manual Toll does not Exists");
            Assert.fail();
        }
        return true;
    }

    public boolean saveManualToll() {
        return Web.click(pageDriver,addButton);
    }
    public boolean clickOnEditManualToll() {

        return Web.click(pageDriver, editLink);
    }
    public void search(String startDate,String endDate) throws Exception {
        Web.selectSingleCalenderDate(pageDriver,startDatePickerInSearch,startDate);
        Web.selectSingleCalenderDate(pageDriver,endDatePickerInSearch,endDate);
        Thread.sleep(5000);

    }
    public void verifyManualToll(String entityType, String date, String entityId,
                                  String amount) throws InterruptedException {
        Select select = new Select(entityTypeDropdown);
        String option = select.getFirstSelectedOption().getText();
       //String getCabID = cabIdDropdown.getText();
       // Select select1 = new Select(cabIdDropdown);
       // String getCabID = select.getFirstSelectedOption().getText();
       // String getAmount = amountTextBox.getText();
//        String getStartTime = pageDriver.findElement(By.xpath("//span[contains(text(),'"+startTime+"')]")).getText();
//        String getEndTime = pageDriver.findElement(By.xpath("//span[contains(text(),'"+endTime+"')]")).getText();
        //String getContract = pageDriver.findElement(By.xpath("//span[contains(text(),'"+Contract+"')]")).getText();
        Assert.assertEquals(option, entityType);
       // Assert.assertEquals(getCabID, entityId);
       // Assert.assertEquals(getAmount, amount);
//        Assert.assertEquals(getStartTime, startTime);
//        Assert.assertEquals(getEndTime, endTime);
       // refreshPage();
    }

    public String validation(String entityType, String entityId,String date,
                             String amount, String tripId) throws Exception {
        clickOnAddNewManualToll();
        addManualToll(entityType,entityId,date,amount,tripId);
        saveManualToll();
       // pageActions.waitToLoadElement(validationMsg);
        Thread.sleep(3000);
        String actualMsg = validationMsg.getAttribute("innerHTML");
        System.out.println(actualMsg);
        return actualMsg;
    }

    public boolean historyCheck(String amount,String date,String Date) throws Exception {
       search(date,date);
       clickOnEditManualToll();
       Web.writeInTextBox(pageDriver,amountTextBox,amount);
       saveManualToll();
       Thread.sleep(5000);
       //pageActions.waitToLoadElement(history);
        history.click();
        Thread.sleep(5000);
       /* String dateToVerify = pageDriver.findElement(By.xpath("//b[contains(text(),'" + Date + "')]")).getText();
        dateToVerify = dateToVerify.substring(0, date.length() - 8);
        if (Date.equals(dateToVerify)) {
            Logger.log("Pettern Matched");
        } else {
            Logger.log("Pettern Does Not Matched");
        }*/
        String name = pageDriver.findElement(By.cssSelector("//body/app-root[1]/app-toll-details[1]/div[8]/div[1]/div[1]/div[1]/span[1]/p[1]")).getText();
     //   String fuelNameInHistory = pageDriver.findElement(By.xpath("//b[contains(text(),'" + fuelName + "')]")).getText();
     //   String fuelNameInHistoryUpdated = pageDriver.findElement(By.xpath("//body/app-root[1]/app-fueltype[1]/div[4]/div[1]/div[1]/div[1]/span[1]/p[1]/span[1]/b[4]")).getText();
     //   String totalText = name + " changed" + " from " + fuelNameInHistory + " to " + fuelNameInHistoryUpdated;
     //   String expectedTotalText = "name changed from " + fuelName + " to " + updatedFuelName;
     //   System.out.println(totalText);
      //  System.out.println(expectedTotalText);
        String totalText = name;
        String expectedTotalText = "amount changed from 50.0 to 56.0 " ;
        if (totalText.equals(expectedTotalText)) {
            Logger.log("Text Matched");
        } else {

            Logger.log("Text Does Not Matched");
        }
        return true;

    }


}
