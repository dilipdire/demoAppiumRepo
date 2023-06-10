package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.BillComparisionUtil;
import org.mis.utils.DataProviderTest;
import org.mis.utils.DateAndTime;
import org.mis.utils.PropertyReader;
import org.testng.annotations.Test;

import java.util.Map;

public class ParkingPageTest extends BaseTest {

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 1)
    public void parkingCheckAddFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(2000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        String startDate = DateAndTime.getParkingCurrentDate();
        parkingPage.addParking(map.get("EntityType"),map.get("CabId"),startDate,map.get("Amount"),map.get("Comment"));
//        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 2)
    public void parkingCheckEditFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        String startDate = DateAndTime.getParkingCurrentDate();
        parkingPage.edit("CAB", "KA-01-AA-0154(1)", "854622", startDate, "450", "editParking");
//        parkingPage.checkAdd(map.get("EntityId"), "450");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 3)
    public void parkingCheckInactiveFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        String startDate = DateAndTime.getParkingCurrentDate();
        parkingPage.inactiveParking();
//        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 4)
    public void validationOfParkingEntityId() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(7000);
        parkingPage.clickOnAddParking();
        String date=DateAndTime.getCurrentDate();
        String expectedMsg = parkingPage.validation("CAB","","50123", date,"50","validation");
        String actualMsg="TripId/CabId can't be empty... ";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 5)
    public void validationOfParkingEntityType() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(7000);
        parkingPage.clickOnAddParking();
        String date=DateAndTime.getCurrentDate();
        String expectedMsg = parkingPage.validation("","","50123", date,"50","validation");
        String actualMsg="entityType can't be empty... ";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(priority = 6)
    public void parkingCheckBulkUploadFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(7000);
        parkingPage.bulkUploadParking();
//        parkingPage.checkAdd("50123", "40");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 7)
    public void parkingCheckActiveFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        String startDate = DateAndTime.getParkingCurrentDate();
        parkingPage.activeParking();
//        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 8)
    public void searchByEntityType() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        parkingPage.searchByEntityType("CAB");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 8)
    public void searchByEntityId() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        parkingPage.searchByEntityId("KA-01-AA-0154");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }



}
