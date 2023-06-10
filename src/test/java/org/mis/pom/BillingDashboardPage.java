package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BillingDashboardPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public HSSFWorkbook excel;
    public HashMap<String, String> dataCache = new HashMap<String, String>();
    PageActions pageActions;
    public BillingDashboardPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(xpath = "//span[contains(.,'BILLING')]")
    private WebElement clickOnBilling;
    @FindBy(xpath = "//a[contains(.,'Manage duties of a cab')]")
    private WebElement clickOnManagedDutiesofCab;
    @FindBy(xpath = "(//input[@placeholder='Search here (Alt+T)'])[2]")
    private WebElement searchByTripId;
    @FindBy(xpath = "//a[contains(.,'Add Trip')]")
    private WebElement addTrip;
    @FindBy(xpath = "//input[@name='addTripSheetType' and @value='searchTripsheet']")
    private WebElement searchTrip;
    @FindBy(xpath = "//input[@name='searchTripBox']")
    private WebElement searchTripBox;
    @FindBy(xpath = "//input[@value='Go']")
    private WebElement clickOnGo;
    @FindBy(xpath = "//input[@value='Proceed']")
    private WebElement clickOnProceed;
    @FindBy(xpath = "//a[contains(.,' Edit ')]")
    private WebElement clickOnEditTrip;
    //@FindBy(xpath = "//a[@class='chosen-single chosen-default']//div//b")
    @FindBy(xpath = "(//a[@class='chosen-single']//div//b)[2]")
    private WebElement selectTripContract;
    @FindBy(xpath = "//a[@class='chosen-single']//div//b")
    private WebElement chooseBillingZone;
    @FindBy(xpath = "(//ul[@class='chosen-results'])[1]/li")
    private List<WebElement> listOfBillingZone;
    @FindBy(xpath = "//input[@value='Done']")
    private WebElement DoneOnTripSheet;
    @FindBy(css = "tbody.tripTable:nth-child(4) tr:nth-child(1) td:nth-child(5) > span:nth-child(1)")
    private WebElement getEmpCount;
    @FindBy(xpath = "//input[@value='Save']")
    private WebElement clickOnSave;
    @FindBy(xpath = "//input[@value='Issues Resolved']")
    private WebElement clickOnIssueResolved;
    @FindBy(xpath = "//input[@value='Audit Done']")
    private WebElement clickOnAuditDone;
    @FindBy(xpath = "//li[contains(.,'New Billing Reports')]")
    private WebElement navigateToNewBillingReport;
    @FindBy(xpath = "//a[contains(.,' View on Map ')]")
    private WebElement clickViewOnMap;
    @FindBy(xpath = "//a[@class='editDistance roleBasedAccess']//img")
    private WebElement clickOnEditDistanceOnMap;
    @FindBy(xpath = "(//a[@class='editDistance roleBasedAccess']//img)[2]")
    private WebElement clickOnEditDistanceOnMapForLastTrip;
    @FindBy(xpath = "//input[@name='distance']")
    private WebElement addDistance;
    @FindBy(xpath = "//textarea[@name='comment']")
    private WebElement writeCommentForEditingDistance;
    @FindBy(xpath = "//div[@class='distancePopup']//div//input[@name='save']")
    private WebElement clickSaveInComment;
    @FindBy(xpath = "//img[@class='closeButtn']")
    private WebElement clickOnCloseButton;
    @FindBy(xpath = "//input[@name='save']")
    private WebElement clickOnSaveButtonInMap;
    @FindBy(css = ".add-a-employee")
    private WebElement addEmployee;
    @FindBy(css = "tbody:nth-child(1) tr:nth-child(2) td:nth-child(2) div:nth-child(1) > input.input_combo")
    private WebElement sendEmployeeName;
    @FindBy(css = "tbody:nth-child(1) tr:nth-child(6) td:nth-child(1) > input.buttonstyle1")
    private WebElement saveEmployee;
    @FindBy(xpath = "//select[@name='startMins']")
    private WebElement startTimeOfDutyMins;
    @FindBy(xpath = "//select[@name='startHours']")
    private WebElement startTimeOfDutyHour;
    @FindBy(xpath = "//select[@name='endMins']")
    private WebElement endTimeOfDutyMins;
    @FindBy(xpath = "//select[@name='endHours']")
    private WebElement endTimeOfDutyHour;
    @FindBy(xpath = "//a[@class='add-a-new-trip']")
    private WebElement addTripToDuty;
    @FindBy(xpath = "//table[@class='routeListTable tablestyle1']/tbody/tr[2]/td[2]")
    private WebElement secondTripId;
    @FindBy(xpath = "//select[@name='selectTripsheet']")
    private WebElement selectTripsheetsDropdown;
    @FindBy(xpath = "//input[contains(@name, 'noShow')]")
    private WebElement markNoShow;
    @FindBy(xpath = "//div[@id='searchCabsForDutyRestoreComboCont']//input[@placeholder='Search here (Alt+Q)']")
    private WebElement searchCabForDuty;

    public boolean navigateToNewBillingReportPage(){
        Web.moveToElement(pageDriver,clickOnBilling);
        return Web.click(pageDriver,navigateToNewBillingReport);
    }
    public boolean performTrip(String tripId) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnManagedDutiesofCab);
        Web.click(pageDriver,clickOnManagedDutiesofCab);
        searchByTripId.sendKeys(tripId);
        Thread.sleep(1000);
        Actions act = new Actions(pageDriver);
        act.sendKeys(Keys.DOWN).perform();
        act.sendKeys(Keys.ENTER).click().perform();
        Thread.sleep(4000);
        //Web.selectElement(pageDriver,searchByTripId);
        pageActions.waitToLoadElement(addTrip);
        Web.click(pageDriver,addTrip);
        pageActions.waitToLoadElement(searchTrip);
        Web.click(pageDriver,searchTrip);
        searchTripBox.sendKeys(tripId);
        pageActions.waitToLoadElement(clickOnGo);
        Web.click(pageDriver,clickOnGo);
        clickOnGo.sendKeys(Keys.TAB);
        pageActions.waitToLoadElement(clickOnProceed);
        Web.click(pageDriver,clickOnProceed);
        pageActions.waitToLoadElement(clickOnEditTrip);
        if(!clickOnEditTrip.isDisplayed()){
            clickOnProceed.click();
        }
       // pageActions.waitToLoadElement(clickOnEditTrip);
        //if(!clickOnEditTrip.isDisplayed()){
           // clickOnProceed.click();
        //}
        //clickOnProceed.click();
        Thread.sleep(90000);
        pageActions.waitToLoadElement(clickOnEditTrip);
        Web.click(pageDriver,clickOnEditTrip);
        return true;
    }
    public void selectContract(String contractName) throws InterruptedException {
        pageActions.waitToLoadElement(selectTripContract);
        Web.click(pageDriver,selectTripContract);
        List<WebElement> contracts = pageDriver.findElements(By.xpath("(//ul[@class='chosen-results'])[2]/li"));
        for (WebElement contract : contracts) {
            if (contract.getText().equals(contractName)) {
                contract.click();
                break;
            }
        }
    }
    public void selectZone(String zoneName) throws InterruptedException {
        Web.click(pageDriver,chooseBillingZone);
        List<WebElement> zones = listOfBillingZone;
        for (WebElement zone : zones) {
            if (zone.getText().equals(zoneName)) {
                zone.click();
                break;
            }
        }
    }
    public String saveTripsheetInformation() throws InterruptedException {
        pageActions.waitToLoadElement(DoneOnTripSheet);
        //Web.click(pageDriver,DoneOnTripSheet);
        DoneOnTripSheet.click();
        //Thread.sleep(4000);
        String empCount = getEmpCount.getText();
        Web.click(pageDriver,clickOnSave);
        Thread.sleep(5000);
        Web.click(pageDriver,clickOnCloseButton);
        //clickOnSave.click();
        //Thread.sleep(10000);
        return empCount;

    }
    public boolean auditDone() throws InterruptedException {

        //pageActions.waitToLoadElement(clickOnIssueResolved);
        //clickOnIssueResolved.click();
        //Web.click(pageDriver,clickOnCloseButton);
        //Thread.sleep(6000);
        pageActions.waitToLoadElement(clickOnAuditDone);
        clickOnAuditDone.click();
        Web.click(pageDriver,clickOnCloseButton);
        //Thread.sleep(6000);
        return true;
    }

    public boolean addMultipleTripInOneDuty(String min,String hour,String tripType) throws InterruptedException {
        if(tripType.equals("Adhoc - Logout")) {
            startTimeOfDutyMins.sendKeys(min);
            Web.click(pageDriver, startTimeOfDutyMins);
            startTimeOfDutyHour.sendKeys(hour);
            Web.click(pageDriver, startTimeOfDutyHour);
        }
        else{
            endTimeOfDutyMins.sendKeys(min);
            Web.click(pageDriver, endTimeOfDutyMins);
            endTimeOfDutyHour.sendKeys(hour);
            Web.click(pageDriver, endTimeOfDutyHour);
        }
        addTripToDuty.click();
        selectTripsheetsDropdown.sendKeys(tripType);
        Web.click(pageDriver,selectTripsheetsDropdown);
        pageActions.waitToLoadElement(clickOnProceed);
        clickOnProceed.click();
        Thread.sleep(5000);
        startTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver,startTimeOfDutyMins);
        startTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver,startTimeOfDutyHour);
        addEmployee("princi");
        addEmployee("rakesh");
        selectContract("Trip_Test2");
        return true;
    }

    public boolean saveMultipleTripsheetInformation(String tripId) throws InterruptedException {
        pageActions.waitToLoadElement(DoneOnTripSheet);
        //Web.click(pageDriver,DoneOnTripSheet);
        DoneOnTripSheet.click();
        Thread.sleep(4000);
        String empCount = getEmpCount.getText();
        Web.click(pageDriver,clickOnSave);
        pageDriver.navigate().refresh();
        pageActions.waitToLoadElement(clickOnManagedDutiesofCab);
        Web.click(pageDriver,clickOnManagedDutiesofCab);
        searchByTripId.sendKeys(tripId);
        Thread.sleep(1000);
        Actions act = new Actions(pageDriver);
        // act.sendKeys(Keys.DOWN).perform();
        act.sendKeys(Keys.ENTER).click().perform();
        Thread.sleep(4000);
        pageActions.waitToLoadElement(addTrip);
        Web.click(pageDriver,addTrip);
        pageActions.waitToLoadElement(searchTrip);
        Web.click(pageDriver,searchTrip);
        searchTripBox.sendKeys(tripId);
        pageActions.waitToLoadElement(clickOnGo);
        Web.click(pageDriver,clickOnGo);
        pageActions.waitToLoadElement(clickOnProceed);
        Web.click(pageDriver,clickOnProceed);
        return true;

    }
    public String getMultipleTripId() throws InterruptedException {
        clickViewOnMap.click();
        Thread.sleep(10000);
        pageActions.waitToLoadElement(secondTripId);
        String tripId = secondTripId.getText();
        System.out.println(tripId);
        clickOnCloseButton.click();
        Thread.sleep(10000);
        return tripId;
    }

    public void addEmployee(String escort ) throws InterruptedException {
        addEmployee.click();
        sendEmployeeName.sendKeys(escort);
        pageDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Actions act = new Actions(pageDriver);
        act.sendKeys(Keys.DOWN).perform();
        Thread.sleep(1000);
        act.sendKeys(Keys.ENTER).click().perform();
        Thread.sleep(2000);
        saveEmployee.click();
        Thread.sleep(10000);
    }
    public void addKm(String km) throws InterruptedException {
        clickViewOnMap.click();
        clickOnEditDistanceOnMap.click();
        addDistance.clear();
        addDistance.sendKeys(km);
        writeCommentForEditingDistance.sendKeys("AutomationTestingUI");
        clickSaveInComment.click();
        clickOnSaveButtonInMap.click();
        Thread.sleep(10000);
        clickOnCloseButton.click();
        Thread.sleep(10000);
    }

    public void addKmForB2bLastTrip(String km) throws InterruptedException {
        clickViewOnMap.click();
        clickOnEditDistanceOnMapForLastTrip.click();
        //pageActions.waitForElementToOpen(addDistance);
        addDistance.clear();
        addDistance.sendKeys(km);
        writeCommentForEditingDistance.sendKeys("AutomationTestingUI");
        clickSaveInComment.click();
        clickOnSaveButtonInMap.click();
        Thread.sleep(10000);
        clickOnCloseButton.click();
        Thread.sleep(10000);
    }
    public boolean navigateToNewBillingReport(){
        Web.moveToElement(pageDriver,clickOnBilling);
        return Web.click(pageDriver,navigateToNewBillingReport);
    }

    public boolean makeATripNoShowTrip(){
        markNoShow.click();
        return true;
    }
    public boolean adhocTrip(String min,String hour,String contract,String employee,String tripType) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnManagedDutiesofCab);
        //Web.click(pageDriver,clickOnManagedDutiesofCab);
        clickOnManagedDutiesofCab.click();
        Thread.sleep(5000);
        searchCabForDuty.sendKeys("jai");
        Thread.sleep(1000);
        Actions act = new Actions(pageDriver);
        act.sendKeys(Keys.DOWN).perform();
        act.sendKeys(Keys.ENTER).click().perform();
        Thread.sleep(4000);
        pageActions.waitToLoadElement(addTrip);
        Web.click(pageDriver,addTrip);
        pageActions.waitToLoadElement(selectTripsheetsDropdown);
        if(tripType.equals("Logout")) {
            selectTripsheetsDropdown.sendKeys("Adhoc - Logout");
        }
        else{
            selectTripsheetsDropdown.sendKeys("Adhoc - Login");
        }
        pageActions.waitToLoadElement(clickOnProceed);
        Web.click(pageDriver,clickOnProceed);
        Thread.sleep(10000);
        startTimeOfDutyMins.sendKeys(min);
        //Select mindropdown = new Select(startTimeOfDutyMins);
        //mindropdown.selectByVisibleText(min);
        Web.click(pageDriver,startTimeOfDutyMins);
        startTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver,startTimeOfDutyHour);
        endTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver,endTimeOfDutyMins);
        endTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver,endTimeOfDutyHour);
        pageActions.waitToLoadElement(clickOnEditTrip);
        Web.click(pageDriver,clickOnEditTrip);
        Thread.sleep(4000);
        startTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver,startTimeOfDutyMins);
        startTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver,startTimeOfDutyHour);
        endTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver,endTimeOfDutyMins);
        endTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver,endTimeOfDutyHour);
        selectContract(contract);
        Thread.sleep(4000);
        addEmployee(employee);
        pageActions.waitToLoadElement(DoneOnTripSheet);
        DoneOnTripSheet.click();
        Thread.sleep(4000);
        //pageDriver.switchTo().alert().accept();
        Web.click(pageDriver,clickOnSave);
        //clickOnSave.click();
        Thread.sleep(10000);
        return true;
    }

    public boolean b2BTrip(String min,String hour,String tripType,String emp,String contract,String Zone,String billingModel,String escortUsage,String escortName) throws InterruptedException {
        endTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver, endTimeOfDutyMins);
        endTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver, endTimeOfDutyHour);
        Thread.sleep(130000);
        addTripToDuty.click();
        selectTripsheetsDropdown.sendKeys(tripType);
        Web.click(pageDriver,selectTripsheetsDropdown);
        pageActions.waitToLoadElement(clickOnProceed);
        clickOnProceed.click();
        Thread.sleep(5000);
        startTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver,startTimeOfDutyMins);
        startTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver,startTimeOfDutyHour);
        endTimeOfDutyMins.sendKeys(min);
        Web.click(pageDriver, endTimeOfDutyMins);
        endTimeOfDutyHour.sendKeys(hour);
        Web.click(pageDriver, endTimeOfDutyHour);
        if(!escortUsage.equals("NA")) {
            addEmployee(escortName);
        }
        addEmployee(emp);
        selectContract(contract);
        if(billingModel.equals("ZONE_BASED")) {
            selectZone(Zone);
        }
        if(billingModel.equals("EMPLOYEE_BASED")) {
            addEmployee("mamta");
        }
        return true;
    }

}