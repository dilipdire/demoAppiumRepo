package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.util.Map;

public class FuelTypeTest extends BaseTest {
    public static String fuelName=GenerateRandomString.generateRandomString(6);
    public static String updatedFuelName=GenerateRandomString.generateRandomString(5);
    @Test (priority=1)
    public void fuelTypeCheckAddFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        System.out.println(fuelName);
        fuelTypePage.addFuelType(fuelName,"60");
        Thread.sleep(5000);
        fuelTypePage.checkAddedFuelType(fuelName);
        Thread.sleep(5000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 2)
    public void fuelTypeCheckDuplicateFuel() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        System.out.println(fuelName);
        String actualMsg = fuelTypePage.validation(fuelName,"60");
        String expectedMsg = "Duplicate Fuel Type";
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        Thread.sleep(5000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(priority = 3)
    public void fuelTypeSearch() throws InterruptedException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.navigateToFuleTypePage();
        fuelTypePage.FuelTypeSerch(fuelName);
        Thread.sleep(5000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 4)
    public void fuelTypeCheckUpdateFunctionality() throws Exception {
        System.out.println(fuelName);
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        fuelTypePage.FuelTypeSerch(fuelName);
        fuelTypePage.updateFuelType(fuelName,updatedFuelName,"70");
        fuelTypePage.FuelTypeSerchWhenSearchIsAlreadySelected(updatedFuelName);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 5)
    public void fuelTypeCheckHistoryFunctionality() throws Exception {
        System.out.println(updatedFuelName);
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        String currentDate = DateAndTime.getCurrentDateForHistoryPetternCheck();
        fuelTypePage.historyCheck(fuelName,currentDate,updatedFuelName);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    //check toggle is working or not after deactivating fuel check duplication msg and then activate fuel
    @Test( priority = 6)
    public void fuelTypeCheckToggleFunctionality() throws Exception {
        System.out.println(updatedFuelName);
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.navigateToFuleTypePage();
        Thread.sleep(5000);
        fuelTypePage.fuelTypeInactiveActiveCheck(updatedFuelName);
        String actualMsg = fuelTypePage.validation(updatedFuelName,"60");
        String expectedMsg = "Duplicate Fuel Type";
        Thread.sleep(5000);
        fuelTypePage.fuelTypeActivate(updatedFuelName);
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 7)
    public void fuelHikeAddFunctionality() throws Exception {
        System.out.println(updatedFuelName);
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelHikePage fuelHikePage = new FuelHikePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelHikePage.navigateToFuelHikePage();
        Thread.sleep(5000);
        String date=DateAndTime.getCurrentDate();
        fuelHikePage.addFuelHike(updatedFuelName,date,"60");
        fuelHikePage.saveFuelHike();
        String dateToVerify = DateAndTime.getDateForAdjustmentSearchCheck();
        fuelHikePage.checkAdd(updatedFuelName,dateToVerify,"60");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    // edit fuelHike and check history is updated or not
    @Test( priority = 8)
    public void fuelHikeEditFunctionality() throws Exception {
        System.out.println(updatedFuelName);
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelHikePage fuelHikePage = new FuelHikePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelHikePage.navigateToFuelHikePage();
        Thread.sleep(5000);
        String date=DateAndTime.getCurrentDate();
        String Date = DateAndTime.getCurrentDateForHistoryPetternCheck();
        fuelHikePage.updateFuelHike(updatedFuelName,"55");
        fuelHikePage.historyCheck(updatedFuelName,"60",Date,"55");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 9)
    public void fuelHikeCheckStartDateValidation() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelHikePage fuelHikePage = new FuelHikePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelHikePage.navigateToFuelHikePage();
        Thread.sleep(5000);
        String date=DateAndTime.getCurrentDate();
        String actualMsg = fuelHikePage.validation(updatedFuelName,"60","");
        String expectedMsg = "start Date can't be empty...";
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        Thread.sleep(5000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 10)
    public void fuelHikeCheckFuelTypeValidation() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelHikePage fuelHikePage = new FuelHikePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelHikePage.navigateToFuelHikePage();
        Thread.sleep(5000);
        String date=DateAndTime.getCurrentDate();
        String actualMsg = fuelHikePage.validation("","60",date);
        String expectedMsg = "Fuel Type can't be empty...";
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        Thread.sleep(5000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    //check toggle is working or not and then activate fuel
    @Test( priority = 11)
    public void fuelHikeCheckToggleFunctionality() throws Exception {
        System.out.println(updatedFuelName);
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        FuelHikePage fuelHikePage = new FuelHikePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelHikePage.navigateToFuelHikePage();
        Thread.sleep(5000);
        fuelHikePage.fuelTypeInactiveActiveCheck(updatedFuelName);
        Thread.sleep(5000);
        fuelHikePage.fuelTypeActivate(updatedFuelName);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(priority = 12)
    public void fuelHikeCheckUploadFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        FuelHikePage fuelHikePage = new FuelHikePage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        fuelHikePage.navigateToFuelHikePage();
        Thread.sleep(7000);
        fuelHikePage.bulkUploadFuelHike();
        //String dateToVerify = DateAndTime.getDateForAdjustmentSearchCheck();
        //fuelHikePage.checkAddForUpload(map.get("FuelType"),dateToVerify,map.get("FuelPrice"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
}
