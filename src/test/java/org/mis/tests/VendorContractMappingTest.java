package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.BillComparisionUtil;
import org.mis.utils.DataProviderTest;
import org.mis.utils.DateAndTime;
import org.mis.utils.PropertyReader;
import org.testng.annotations.Test;
import org.mis.pom.VendorContractMappingPage;

import java.util.Map;

public class VendorContractMappingTest extends BaseTest{
    @Test( priority = 1)
    public void AddFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        String date = DateAndTime.getCurrentDate();
        vendorContractMapping.addVendorContract("MIS","Sapna_PETROL","4","Trip_Test9","ITPL",date);
        vendorContractMapping.checkAdd("MIS","Sapna_PETROL","4","Trip_Test9","ITPL");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 2)
    public void editFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        String date = DateAndTime.getCurrentDate();
        vendorContractMapping.clickEditLinkInVendorContract("MIS","AMB","Sapna_PETROL","4","Trip_Test9","ITPL",date);
        Thread.sleep(5000);
        vendorContractMapping.checkAdd("MIS","Sapna_PETROL","4","Trip_Test9","ITPL");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 3)
    public void validationOfVendor() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        String date = DateAndTime.getCurrentDate();
        String expectedMsg=vendorContractMapping.validation("","Sapna_PETROL","4","Trip_Test9","ITPL",date);
        Thread.sleep(5000);
        String actualMsg="Vendor Key is missing";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 4)
    public void validationOfSeatingCapacity() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        String date = DateAndTime.getCurrentDate();
        String expectedMsg=vendorContractMapping.validation("MIS","Sapna_PETROL","","Trip_Test9","ITPL",date);
        Thread.sleep(5000);
        String actualMsg="Seat Capacity is missing";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 5)
    public void validationOfContract() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        String date = DateAndTime.getCurrentDate();
        String expectedMsg=vendorContractMapping.validation("MIS","Sapna_PETROL","4","","ITPL",date);
        Thread.sleep(5000);
        String actualMsg="Contract Id is missing";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 6)
    public void searchByVendor() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        vendorContractMapping.searchByVendor("MIS");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 7)
    public void searchByOffice() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        vendorContractMapping.searchByOffice("ITPL");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 8)
    public void searchByFuelType() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        vendorContractMapping.searchByFuelName("Sapna_PETROL");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 9)
    public void searchBySeatCapacity() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(5000);
        vendorContractMapping.searchBySeatingCapacity("4");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(priority = 10)
    public void vendorContractCheckUploadFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        VendorContractMappingPage vendorContractMapping = new VendorContractMappingPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        vendorContractMapping.navigateToVendorContractMappingPage();
        Thread.sleep(7000);
        vendorContractMapping.bulkUploadVendorContract();
        //vendorContractMapping.checkAdd(map.get("Vendor Name"),map.get("Fuel Type"),map.get("Seating capacity"),map.get("Contract"),map.get("Office"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
}
