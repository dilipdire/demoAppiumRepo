package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.DateAndTime;
import org.mis.utils.FileConstant;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class CabContractPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;

    public CabContractPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        PageActions pageActions = new PageActions(pageDriver);
    }

    @FindBy(id = "cabcontract")
    private WebElement clickOnCabContractLink;
    @FindBy(xpath = "//*[@id=\"slider\"]/div/div[3]/a[2]/i")
    private WebElement addNewCabContractButton;
    @FindBy(xpath = "//label[contains(text(),'Cab Registratiion')]/following-sibling::input")
    private WebElement selectCabRegistration;
    @FindBy(xpath = "//*[@id=\"modal1\"]/div/form/div[1]/div[3]/my-date-picker/div/div/input")
    private WebElement selectStartDate;
    @FindBy(xpath = "//*[@id=\"modal1\"]/div/form/div[2]/div/div[1]/select")
    private WebElement selectOffice;
    @FindBy(xpath = "//select[@formcontrolname='dayType']")
    private WebElement selectDayType;
    @FindBy(xpath = "//select[@formcontrolname='tripType']")
    private WebElement selectTripType;
    @FindBy(xpath = "//*[@id=\"modal1\"]/div/form/div[2]/div/div[4]/input")
    private WebElement selectStartTime;
    @FindBy(xpath = "//input[@formcontrolname='endTime']")
    private WebElement selectEndTime;
    @FindBy(xpath = "//select[@formcontrolname='direction']")
    private WebElement selectDirection;
    @FindBy(xpath = "//input[@formcontrolname='contractId']")
    private WebElement selectContract;
    @FindBy(xpath = "//form[@class='col s12 ng-untouched ng-pristine ng-invalid']//i[@class='material-icons'][contains(normalize-space(),'add')]")
    private WebElement addAdditionalCabContractButton;
    @FindBy(xpath = "//input[@value='ADD']")
    private WebElement addCabContractButton;
    @FindBy(xpath = "//button[contains(normalize-space(),'YES')]")
    private WebElement saveCabContractPopUpButton;
    @FindBy(xpath = "//*[@id=\"slider\"]/div/div[3]/div/div[2]/input")
    private WebElement searchCab;
    @FindBy(xpath = "//input[@placeholder='Search for office']")
    private WebElement searchOffice;
    @FindBy(xpath = "//input[@placeholder='Search for contract']")
    private WebElement searchContract;
    @FindBy(css = "#slider > div > div:nth-child(5) > form > div:nth-child(2) > table > tbody > tr > td.edit > label")
    private WebElement selectCabCheckbox;
    @FindBy(xpath = "//i[contains(normalize-space(),'edit')]")
    private WebElement editCabContractButton;
    @FindBy(xpath = "//i[contains(text(),'file_upload')]")
    private WebElement clickOnBulkUpload;
    @FindBy(xpath = "//i[contains(text(),'file_download')]")
    private WebElement clickOnBulkDownload;
    @FindBy(xpath = "//input[@id='fileUpload']")
    private WebElement chooseFile;
    @FindBy(xpath = "//input[@placeholder='Select Start Date']")
    private WebElement searchStartDate;
    @FindBy(css = "div.ng-untouched.ng-pristine.ng-valid > td:nth-child(4)")
    private List<WebElement> contractNameColumn;
    @FindBy(css = "div.ng-untouched.ng-pristine.ng-valid > td:nth-child(6)")
    private List<WebElement> officeNameColumn;
    @FindBy(xpath = "//select[@formcontrolname='officeIdBulk']")
    private WebElement editOffice;
    @FindBy(xpath = "//select[@formcontrolname='dayTypeBulk']")
    private WebElement editDayType;
    @FindBy(xpath = "//select[@formcontrolname='tripTypeBulk']")
    private WebElement editTripType;
    @FindBy(xpath = "//*[@id=\"modal3\"]/div/form/div[2]/div/div[5]/input")
    private WebElement editStartTime;
    @FindBy(xpath = "//*[@id=\"modal3\"]/div/form/div[2]/div/div[6]/input")
    private WebElement editEndTime;
    @FindBy(xpath = "//select[@formcontrolname='directionBulk']")
    private WebElement editDirection;
    @FindBy(xpath = "//select[@formcontrolname='contractIdBulk']")
    private WebElement editContract;
    @FindBy(xpath = "//a[normalize-space()='APPLY']")
    private WebElement clickOnApply;
    @FindBy(xpath = "//input[@value='SAVE']")
    private WebElement clickOnSave;

    public boolean navigateToCabContractPage() {
        return Web.click(pageDriver, clickOnCabContractLink);
    }
    public boolean clickOnAddNewCabContract() {
        return Web.click(pageDriver, addNewCabContractButton);
    }
    public boolean addCabContract(String CabRegistration, String startDate, String Office,
                                  String DayType, String TripType, String startTime, String endTime,
                                  String Direction, String Contract) throws InterruptedException {
        selectCabRegistration.sendKeys(CabRegistration);
        Web.click(pageDriver, selectCabRegistration);
        Web.writeInTextBox(pageDriver,selectStartDate,startDate);
        selectOffice.sendKeys(Office);
        Web.click(pageDriver, selectOffice);
        selectDayType.sendKeys(DayType);
        Web.click(pageDriver, selectDayType);
        selectTripType.sendKeys(TripType);
        Web.click(pageDriver, selectTripType);
        selectStartTime.sendKeys(startTime);
        Web.click(pageDriver, selectStartTime);
        selectEndTime.sendKeys(endTime);
        Web.click(pageDriver, selectEndTime);
        selectDirection.sendKeys(Direction);
        Web.click(pageDriver, selectDirection);
        selectContract.sendKeys(Contract);
        Web.click(pageDriver, selectContract);
        Thread.sleep(5000);
        return true;
    }
    public boolean addCabContractAdditionalSlab() {
        pageActions.waitToLoadElement(addAdditionalCabContractButton);
        return Web.click(pageDriver, addAdditionalCabContractButton);
    }
    public void saveCabContract() {
        addCabContractButton.click();
        saveCabContractPopUpButton.click();
    }
    public void searchByCab(String Cab) throws InterruptedException {
        searchCab.sendKeys(Cab);
        Web.click(pageDriver, searchCab);
        Thread.sleep(5000);
        selectCabCheckbox.click();
        Thread.sleep(5000);
//        editCabContractButton.click();
    }

    public boolean searchByContract(String contract) throws InterruptedException {
        pageActions.waitToLoadElement(searchCab);
        Web.writeInTextBox(pageDriver,searchContract,contract);
        Thread.sleep(5000);
        int counter = 0;
        List<WebElement> contractNames =  contractNameColumn;
        for (WebElement ContractName : contractNames) {
            if(counter>0) {
                if (!ContractName.getText().equals(ContractName)) {
                    Assert.fail();
                }
            }
            counter++;
        }

        return true;
    }

    public boolean searchByOffice(String office) throws InterruptedException {
        pageActions.waitToLoadElement(searchCab);
        Web.writeInTextBox(pageDriver,searchOffice,office);
        Thread.sleep(5000);
        int counter = 0;
        List<WebElement> officeNames =  officeNameColumn;
        for (WebElement OfficeName : officeNames) {
            if(counter>0) {
                if (!OfficeName.getText().equals(OfficeName)) {
                    Assert.fail();
                }
            }
            counter++;
        }

        return true;
    }

    public boolean clickOnEditNewCabContract() {
        return Web.click(pageDriver, editCabContractButton);
    }
    public boolean editCabContract(String startDate, String Office,
                                  String DayType, String TripType,String Direction, String Contract) throws InterruptedException {
        editCabContractButton.click();
        editOffice.sendKeys(Office);
        Web.click(pageDriver, editOffice);
        editDayType.sendKeys(DayType);
        Web.click(pageDriver, editDayType);
        editTripType.sendKeys(TripType);
        Web.click(pageDriver, editTripType);
//        editStartTime.sendKeys(startTime);
//        Web.click(pageDriver, editStartTime);
//        editEndTime.sendKeys(endTime);
//        Web.click(pageDriver, editEndTime);
        editDirection.sendKeys(Direction);
        Web.click(pageDriver, editDirection);
        editContract.sendKeys(Contract);
        Web.click(pageDriver, editContract);
        clickOnApply.click();
        Thread.sleep(5000);
        clickOnSave.click();
        Thread.sleep(5000);
        return true;
    }
    public void verifyCabContract(String Office,String DayType, String TripType, String startTime, String endTime,
                                  String Contract, String StartDate) throws InterruptedException {
        String getOffice = pageDriver.findElement(By.xpath("//span[contains(text(),'"+Office+"')]")).getText();
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
    public void bulkUploadCabContract() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.PARKING_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }
    public void bulkDownloadCabContract() throws InterruptedException {
        clickOnBulkDownload.click();
    }

    // To select single calendar Date of cab contract page
    public void selectSingleCalenderDate(String date) throws Exception {
        pageDriver.findElement(By.xpath("//my-date-picker[@formcontrolname='startDate']//span[@class='mydpicon icon-mydpcalendar']")).click();
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



    public void historyCheck(String testauto, String currentDate, String testaut) {
    }
}

