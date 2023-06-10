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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class ParkingPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngDriver;
    PageActions pageActions;

    public ParkingPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }

    @FindBy(xpath = "//i[contains(text(),'add')]")
    private WebElement addButton;
    @FindBy(css = "#modal1 > div > form > div:nth-child(1) > div:nth-child(1) > select")
    private WebElement selectEntityType;
    @FindBy(id = "parking")
    private WebElement ParkingPageLink;
    @FindBy(xpath = "//*[@id=\"modal1\"]/div/form/div[1]/div[2]/input")
    private WebElement selectCabId;
    @FindBy(xpath = "//div[@id='modal1']//div[@class='row']//div[3]//input[1]")
    private WebElement selectTripId;
    @FindBy(css = "#modal1 > div > form > div:nth-child(2) > div:nth-child(2) > my-date-picker > div > div > input")
    private WebElement selectDate;
    @FindBy(xpath = "//input[@type='number']")
    private WebElement textFieldAmount;
    @FindBy(xpath = "//*[@id=\"modal1\"]/div/form/div[3]/div[2]/textarea")
    private WebElement textFieldComment;
    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement parkingSaveButton;
    @FindBy(xpath = "//span[contains(text(),'\"+CabId+\"')]")
    private WebElement cabId;
    @FindBy(xpath = "//span[contains(text(),'\"+Amount+\"')]")
    private WebElement amount;
    @FindBy(xpath = "//my-date-picker[@name='date']//span[@class='mydpicon icon-mydpcalendar']")
    private WebElement datePicker;
    @FindBy (css = "#edit")
    private WebElement editButton;
    @FindBy (css = "#disabled")
    private WebElement inactiveParking;
    @FindBy (css = "#disabled")
    private WebElement activeParking;
    @FindBy (xpath = "//i[contains(text(),'file_upload')]")
    private WebElement parkingFileUpload;
    @FindBy(xpath = "//*[@id=\"toast-container\"]/div")
    private WebElement validationMsg;
    @FindBy(xpath = "//i[contains(text(),'file_upload')]")
    private WebElement clickOnBulkUpload;
    @FindBy(xpath = "//i[contains(text(),'file_download')]")
    private WebElement clickOnBulkDownload;
    @FindBy(xpath = "//input[@id='fileUpload']")
    private WebElement chooseFile;
    @FindBy(xpath = "//i[contains(text(),'search')]")
    private WebElement clickOnSearchParkingButton;
    @FindBy(xpath = "//*[@id=\"slider\"]/div[2]/div[2]/select")
    private WebElement searchEntityType;
    @FindBy(xpath = "//input[@class='ng-pristine ng-valid ng-touched']")
    private WebElement searchEntityId;

    public boolean navigateToParkingPage(){
        return Web.click(pageDriver,ParkingPageLink);
    }

    public void searchByEntityType(String entityType) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchParkingButton);
        clickOnSearchParkingButton.click();
        Thread.sleep(2000);
        pageActions.waitToLoadElement(searchEntityType);
        Web.click(pageDriver,searchEntityType);

    }

    public void searchByEntityId(String entityId) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchParkingButton);
        clickOnSearchParkingButton.click();
        Thread.sleep(2000);
        pageActions.waitToLoadElement(searchEntityId);
        Web.click(pageDriver,searchEntityId);

    }


    public boolean addParking(String EntityType,String CabId,String startDate, String Amount,String Comment) throws InterruptedException {
        addButton.click();
        selectEntityType.sendKeys(EntityType);
        Web.click(pageDriver, selectEntityType);
        selectCabId.sendKeys(CabId);
        Web.click(pageDriver, selectCabId);
        Web.writeInTextBox(pageDriver,selectDate,startDate);
        Web.writeInTextBox(pageDriver,textFieldAmount,Amount);
        Web.writeInTextBox(pageDriver,textFieldComment,Comment);
        Thread.sleep(5000);
        parkingSaveButton.click();
        Thread.sleep(5000);
        return true;
    }
    public boolean clickOnAddParking() {
        return Web.click(pageDriver, addButton);
    }
    public boolean editParking(String EntityType,String CabId,String startDate, String Amount,String Comment) throws InterruptedException {
        editButton.click();
        selectEntityType.sendKeys(EntityType);
        Web.click(pageDriver, selectEntityType);
        selectCabId.sendKeys(CabId);
        Web.click(pageDriver, selectCabId);
        Web.writeInTextBox(pageDriver,selectDate,startDate);
        Web.writeInTextBox(pageDriver,textFieldAmount,Amount);
        Web.writeInTextBox(pageDriver,textFieldComment,Comment);
        parkingSaveButton.click();
        Thread.sleep(5000);
        return true;
    }
    public boolean saveParking() {
        return Web.click(pageDriver,parkingSaveButton);
    }
    public boolean inactiveParking() {
        return Web.click(pageDriver,inactiveParking);
    }
    public boolean activeParking() {
        return Web.click(pageDriver,activeParking);
    }

    public boolean add(String entityType, String entityId,String tripId,
                       String date,String amount, String comment) throws Exception {
        selectEntityType.sendKeys(entityType);
        Web.click(pageDriver, selectEntityType);
        if(entityType.equals("CAB")) {
            selectCabId.sendKeys(entityId);
            Web.click(pageDriver, selectCabId);
        }
        if(entityType.equals("TRIP")) {
            Web.writeInTextBox(pageDriver, selectTripId, tripId);
        }
        Thread.sleep(5000);
        Web.writeInTextBox(pageDriver, textFieldAmount, amount);
        Web.writeInTextBox(pageDriver, textFieldComment, comment);
        if(date.equals("NULL")){
            //do nothing
        }else {
            Web.selectSingleCalenderDate(pageDriver, datePicker, date);
        }
        Thread.sleep(5000);
        return true;
    }
    public boolean edit(String entityType, String entityId,String tripId,
                       String date,String amount, String comment) throws Exception {
        selectEntityType.sendKeys(entityType);
        Web.click(pageDriver, selectEntityType);
        if(entityType.equals("CAB")) {
            selectCabId.sendKeys(entityId);
            Web.click(pageDriver, selectCabId);
        }
        if(entityType.equals("TRIP")) {
            Web.writeInTextBox(pageDriver, selectTripId, tripId);
        }
        Thread.sleep(5000);
        Web.writeInTextBox(pageDriver, textFieldAmount, amount);
        Web.writeInTextBox(pageDriver, textFieldComment, comment);
        if(date.equals("NULL")){
            //do nothing
        }else {
            Web.selectSingleCalenderDate(pageDriver, datePicker, date);
        }
        Thread.sleep(5000);
        return true;
    }
    public String validation(String entityType, String entityId,String tripId,
                             String date,String amount, String comment) throws Exception {
        clickOnAddParking();
        add(entityType,entityId,tripId,date,amount,comment);
        saveParking();
        // pageActions.waitToLoadElement(validationMsg);
        Thread.sleep(3000);
        String actualMsg = validationMsg.getAttribute("innerHTML");
        System.out.println(actualMsg);
        return actualMsg;
    }
    public boolean verifyParking(String EntityType,String CabId,String startDate, String Amount){
        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+CabId+"')]")).isDisplayed()){
            System.out.println("Added Parking Exists");
            Logger.log("Added Parking Exists");
        }else{
            System.out.println("Added Parking Does Not Exists");
            Logger.log("Added Parking Does Not Exists");
        }
        return true;
    }
    public boolean checkAdd(String EntityId,String Amount){
        Amount = Amount.replaceAll("\\.0*$", "");
        if ((pageDriver.findElement(By.xpath("//span[contains(text(),'"+EntityId+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+Amount+"')]")).isDisplayed())) {
            System.out.println("Added Parking Exists");
            Logger.log("Added Parking Exists");
        }else{
            System.out.println("Added Parking does not Exists");
            Logger.log("Added Parking does not Exists");
        }
        return true;
    }
    public void bulkUploadCabContract() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.CAB_CONTRACT_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }
    public void bulkUploadParking() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.PARKING_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }

    public void AddFuelTypePage(){
        pageDriver.findElement(By.id("fueltype")).click();
        pageDriver.findElement(By.xpath("//i[contains(text(),'add')]")).click();
        pageDriver.findElement(By.cssSelector("#modal1 > div > form > div:nth-child(1) > div:nth-child(1) > input")).sendKeys("FuelTest2");
        pageDriver.findElement(By.cssSelector("#modal1 > div > form > div:nth-child(1) > div:nth-child(2) > input")).sendKeys("60");
        pageDriver.findElement(By.className("waves-button-input")).click();
    }



}
