package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.DataProviderTest;
import org.mis.utils.DateAndTime;
import org.mis.utils.PropertyReader;
import org.testng.annotations.Test;

import java.util.Map;

public class CabContractTest extends BaseTest {

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 1)
    public void cabContractCheckAddFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.clickOnAddNewCabContract();
        String startDate = DateAndTime.getCabContractCurrentDate();
        cabContractPage.addCabContract(map.get("CabRegistration"), startDate, map.get("Office"),
                map.get("DayType"), map.get("TripType"), "10:00:00", "20:00:00", map.get("Direction"), map.get("Contract"));
//        cabContractPage.saveCabContract();
//        cabContractPage.searchByCab(map.get("Cab"));
//        cabContractPage.verifyCabContract(map.get("Office"), map.get("DayType"), map.get("TripType"),
//                "10:00:00", "20:00:00", map.get("Contract"), map.get("StartDate"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 2)
    public void cabContractCheckSearch(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.searchByCab("KA-01-AA-0154");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }


    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 3)
    public void cabContractCheckEditFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.searchByCab(map.get("Cab"));
        cabContractPage.editCabContract(map.get("StartDate"), map.get("Office"),
                map.get("DayType"), map.get("TripType"),map.get("Direction"), map.get("Contract"));
//        cabContractPage.saveCabContract();
//        cabContractPage.searchByCab(map.get("Cab"));
//        cabContractPage.verifyCabContract(map.get("Office"), map.get("DayType"), map.get("TripType"),
//                   "10:00:00", "20:00:00", map.get("Contract"), map.get("StartDate"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 4)
    public void cabContractCheckBulkUploadFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.bulkUploadCabContract();
//        cabContractPage.searchByCab(map.get("Cab"));
//        cabContractPage.verifyCabContract(map.get("Office"), map.get("DayType"), map.get("TripType"),
//                "10:00:00", "20:00:00", map.get("Contract"), map.get("StartDate"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 5)
    public void cabContractCheckBulkDownloadFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.bulkDownloadCabContract();
//        cabContractPage.searchByCab(map.get("Cab"));
//        cabContractPage.verifyCabContract(map.get("Office"), map.get("DayType"), map.get("TripType"),
//                "10:00:00", "20:00:00", map.get("Contract"), map.get("StartDate"));
        homePage.switchToDefaultFrame();
        loginPage.logout();;
    }

    @Test( priority = 6)
    public void CabContractCheckHistoryFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(5000);
        String currentDate = DateAndTime.getCurrentDateForHistoryPetternCheck();
        cabContractPage.historyCheck("testauto",currentDate,"testaut");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 7)
    public void searchByContract() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(5000);
        cabContractPage.searchByContract("Indica");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 8)
    public void cabContractSearchCab(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.searchByCab("KA-01-AA-0154");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 9)
    public void cabContractSearchContract(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.searchByContract("Indica");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addCabContract", priority = 10)
    public void cabContractSearchOffice(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        CabContractPage cabContractPage = new CabContractPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        cabContractPage.navigateToCabContractPage();
        Thread.sleep(7000);
        cabContractPage.searchByContract("ALL");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }



}
