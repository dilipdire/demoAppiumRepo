package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mis.utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DashboardPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public HSSFWorkbook excel;
    public HashMap<String, String> dataCache = new HashMap<String, String>();
    PageActions pageActions;
    public DashboardPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(xpath = "//form[@id='createTripsheetForm']//table//tbody//tr//td//label//input[@id='startLoc']")
    private WebElement startLocationFixed;
    @FindBy(xpath = "//form[@id='createTripsheetForm']//table//tbody//tr//td//label//input[@id='endLoc']")
    private WebElement endLocationFixed;
    @FindBy(css = ".add-a-employee")
    private WebElement addEmployee;
    @FindBy(css = "tbody:nth-child(1) tr:nth-child(2) td:nth-child(2) div:nth-child(1) > input.input_combo")
    private WebElement sendEmployeeName;
    @FindBy(css = "tbody:nth-child(1) tr:nth-child(6) td:nth-child(1) > input.buttonstyle1")
    private WebElement saveEmployee;
    @FindBy(xpath = "//input[@placeholder='Search here  (Ctrl+E)']")
    private WebElement searchEmployeeDashboard;
    @FindBy(xpath = "//select[@name='location']")
    private WebElement location;
    @FindBy(xpath = "//select[@name='hours']")
    private WebElement hours;
    @FindBy(xpath = "//select[@name='mins']")
    private WebElement mins;
    @FindBy(xpath = "//input[@name='create']")
    private WebElement createNoshiftTripButton;
    @FindBy(xpath = "//input[@name='listCabs']")
    private WebElement cabList;
    @FindBy(xpath = "//div[@class='matchingCabs']//ul[@tabindex='2']//li")
    private List<WebElement> matchingCab;
    @FindBy(xpath = "//input[@placeholder='Search Cabs (alt + q)']")
    private WebElement searchCab;
    @FindBy(xpath = "//input[@name='save']")
    private WebElement saveTrip;
    //@FindBy(xpath = "//span[contains(.,'BILLING')]")
    @FindBy(xpath = "//span[@class='BillingDashboard roleBasedAccess']")
    private WebElement clickOnBilling;
    @FindBy(xpath = "//a[contains(.,'Manage duties of a cab')]")
    private WebElement clickOnManagedDutiesofCab;
    @FindBy(xpath = "(//input[@placeholder='Search here (Alt+T)'])[2]")
    private WebElement searchByTripId;
    @FindBy(linkText = "Assign New Cab")
    private WebElement assignCab;
    @FindBy(xpath = "//img[@class='closeButtn']")
    private WebElement closeButtonPopUp;
    @FindBy(xpath = "//img[@class='closeButtn']")
    private WebElement clickOnCloseButton;

    // To search Employee in dashboard for creating trip
    public void searchEmployeeInDasboard(String Name,String textToSelect) throws InterruptedException {
        searchEmployeeDashboard.sendKeys(Name);
        pageDriver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        List<WebElement> elements = pageDriver.findElements(By.xpath("//ul[@tabindex='2']//li"));
        for (WebElement element : elements) {
            if (element.getText().equals(textToSelect)) {
                element.click();
                break;
            }
        }
    }

    // To Generate Logout Tripsheet
    public boolean createNonShiftTripsheetForLogoutTrip(String time, String hour) throws InterruptedException {
        pageActions.waitToLoadElement(assignCab);
        Web.click(pageDriver,assignCab);
        Web.click(pageDriver,startLocationFixed);
        Thread.sleep(3000);
        new Select(location).selectByVisibleText("ITPL");
        new Select(hours).selectByVisibleText(hour);
        hours.sendKeys(Keys.TAB);
        new Select(mins).selectByVisibleText(time);
        return Web.click(pageDriver,createNoshiftTripButton);
    }

    //To Generate LogIn Tripsheet
    public boolean createNonShiftTripsheetForLoginTrip(String time, String hour) throws InterruptedException {
        pageActions.waitToLoadElement(assignCab);
        assignCab.click();
        endLocationFixed.click();
        new Select(location).selectByVisibleText("ITPL");
        new Select(hours).selectByVisibleText(hour);
        hours.sendKeys(Keys.TAB);
        new Select(mins).selectByVisibleText(time);
        createNoshiftTripButton.click();
        Thread.sleep(8000);
        return true;
    }

    //Assign cab to tripsheet
    public void assignCabToNewTripsheet(String Cab) throws InterruptedException {
        pageActions.waitToLoadElement(cabList);
        Thread.sleep(5000);
        Web.click(pageDriver,cabList);
        List<WebElement> cabs =  matchingCab;
        for (WebElement cab : cabs) {
            if (cab.getText().equals(Cab)) {
                cab.click();
                break;
            }
            try {
                pageDriver.switchTo().alert().accept();
            } catch (Exception e) {
            }
        }
        dataCache.put("CabAssignedToTripsheet", searchCab.getAttribute("value"));
    }

    //Save Tripsheet Information And Return TripId
    public String saveTripsheet(String hour,String min) throws InterruptedException {
        Web.click(pageDriver,saveTrip);
        Thread.sleep(3000);
        String path = hour+":"+min+ " "+"-"+" " +hour+":"+min;
        pageActions.waitToLoadElement(pageDriver.findElement(By.xpath("//span[contains(text(),'"+path+"')]//ancestor::tr/td[@class]/a")));
        String tripId = pageDriver.findElement(By.xpath("//span[contains(text(),'"+path+"')]//ancestor::tr/td[@class]/a")).getAttribute("tripid");
        System.out.println("TripId - " +tripId);
        Web.click(pageDriver,clickOnCloseButton);
        return tripId;
    }

    public boolean navigateToBillingDashboard() throws InterruptedException {
        Thread.sleep(6000);
        clickOnBilling.click();
        return true;
    }

    public void searchEmployeeInDasboardForLoginTrip(String emp) throws InterruptedException {
        Web.writeInTextBox(pageDriver,searchEmployeeDashboard,emp);
        Actions act = new Actions(pageDriver);
        act.sendKeys(Keys.DOWN).perform();
        Thread.sleep(1000);
        act.sendKeys(Keys.ENTER).click().perform();

    }

    public boolean b2bTripInEts(String hour,String min) throws InterruptedException {
        createNonShiftTripsheetForLogoutTrip(min,hour);

        return true;
    }

}