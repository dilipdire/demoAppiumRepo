package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.DataProviderTest;
import org.mis.utils.DateAndTime;
import org.mis.utils.PropertyReader;
import org.testng.annotations.Test;

import java.util.Map;

public class TestingTest extends BaseTest {

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
        Thread.sleep(5000);
        parkingPage.navigateToParkingPage();
        Thread.sleep(5000);
        String startDate = DateAndTime.getParkingCurrentDate();
        parkingPage.addParking(map.get("EntityType"),map.get("CabId"),startDate,map.get("Amount"),map.get("Comment"));
        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
//    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 2)
//    public void parkingCheckEditFunctionality(Map<String, String> map) throws Exception {
//        getChromeDriver().get(PropertyReader.getProperty("base.url"));
//        getNgWebDriver().waitForAngularRequestsToFinish();
//        LoginPage loginPage = new LoginPage(getChromeDriver());
//        loginPage.login();
//        waitForCommandToFinish();
//        HomePage homePage = new HomePage(getChromeDriver());
//        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
//        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
//        DateAndTime dateAndTime = new DateAndTime();
//        homePage.navigateToSettings();
//        Thread.sleep(5000);
//        homePage.navigateToNewContract();
//        Thread.sleep(5000);
//        homePage.switchIframe();
//        Thread.sleep(5000);
//        fuelTypePage.navigateToFuleTypePage();
//        Thread.sleep(5000);
//        parkingPage.navigateToParkingPage();
//        Thread.sleep(5000);
//        String startDate = DateAndTime.getParkingCurrentDate();
//        parkingPage.editParking(map.get("EntityType"),map.get("CabId"),startDate,map.get("Amount"),map.get("Comment"));
//        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
//        homePage.switchToDefaultFrame();
//        loginPage.logout();
//    }
//
//    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 3)
//    public void parkingCheckInactiveFunctionality(Map<String, String> map) throws Exception {
//        getChromeDriver().get(PropertyReader.getProperty("base.url"));
//        getNgWebDriver().waitForAngularRequestsToFinish();
//        LoginPage loginPage = new LoginPage(getChromeDriver());
//        loginPage.login();
//        waitForCommandToFinish();
//        HomePage homePage = new HomePage(getChromeDriver());
//        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
//        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
//        DateAndTime dateAndTime = new DateAndTime();
//        homePage.navigateToSettings();
//        Thread.sleep(5000);
//        homePage.navigateToNewContract();
//        Thread.sleep(5000);
//        homePage.switchIframe();
//        Thread.sleep(5000);
//        fuelTypePage.navigateToFuleTypePage();
//        Thread.sleep(5000);
//        parkingPage.navigateToParkingPage();
//        Thread.sleep(5000);
//        String startDate = DateAndTime.getParkingCurrentDate();
//        parkingPage.inactiveParking();
//        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
//        homePage.switchToDefaultFrame();
//        loginPage.logout();
//    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "parking", priority = 4)
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
        parkingPage.checkAdd(map.get("EntityId"),map.get("Amount"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    //    @Test(priority = 1)
//    public void parkingCabCheckAddFunctionality() throws Exception {
//        getChromeDriver().get(PropertyReader.getProperty("base.url"));
//        getNgWebDriver().waitForAngularRequestsToFinish();
//        LoginPage loginPage = new LoginPage(getChromeDriver());
//        loginPage.login();
//        waitForCommandToFinish();
//        HomePage homePage = new HomePage(getChromeDriver());
//        ParkingPage parkingPage = new ParkingPage(getChromeDriver());
//        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
//        homePage.navigateToSettings();
//        Thread.sleep(5000);
//        homePage.navigateToNewContract();
//        Thread.sleep(5000);
//        homePage.switchIframe();
//        Thread.sleep(5000);
//        fuelTypePage.navigateToFuleTypePage();
//        Thread.sleep(5000);
//        parkingPage.navigateToParkingPage();
//        Thread.sleep(7000);
//        parkingPage.clickOnAddParking();
//        String date=DateAndTime.getCurrentDate();
//        parkingPage.add("CAB","KA-54-5709(13)",date,"50","50123","AutomationParking");
//        parkingPage.saveParking();
//        homePage.switchToDefaultFrame();
//        loginPage.logout();
//    }



}
