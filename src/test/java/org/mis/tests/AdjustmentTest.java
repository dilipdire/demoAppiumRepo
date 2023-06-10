package org.mis.tests;

import org.mis.pom.AdjustmentTabPage;
import org.mis.pom.HomePage;
import org.mis.pom.LoginPage;
import org.mis.pom.VendorContractMappingPage;
import org.mis.utils.BillComparisionUtil;
import org.mis.utils.DataProviderTest;
import org.mis.utils.DateAndTime;
import org.mis.utils.PropertyReader;
import org.testng.annotations.Test;

import java.util.Map;

public class AdjustmentTest extends BaseTest{

    @Test(priority = 1)
    public void adjustmentCheckAddFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentPage = new AdjustmentTabPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentPage.clickOnAddNewAdjustment();
        String date=DateAndTime.getCurrentDate();
        //String date= "27/10/2020";
        adjustmentPage.addCabAdjustment("CAB","KA-54-5709(13)",date,"AC","50","AutomationTest");
        adjustmentPage.saveAdjustment();
        Thread.sleep(5000);
        String verifyDate = DateAndTime.getDateForAdjustmentSearchCheck();
        adjustmentPage.search(date,date,verifyDate);
        adjustmentPage.checkAdd("KA-54-5709","50","AC");
        //adjustmentPage.selectSingleCalenderDate(date);
        adjustmentPage.saveAdjustment();
        homePage.switchToDefaultFrame();
        loginPage.logout();

    }
    @Test(priority = 2)
    public void adjustmentCheckSearchFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentPage = new AdjustmentTabPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        String date=DateAndTime.getCurrentDate();
        String dateToVerify = DateAndTime.getDateForAdjustmentSearchCheck();
        //System.out.println(date);
        adjustmentPage.search(date,date,dateToVerify);
        homePage.switchToDefaultFrame();
        loginPage.logout();

    }
    @Test( priority = 3)
    public void validationOfContract() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentTabPage = new AdjustmentTabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentTabPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentTabPage.clickOnAddNewAdjustment();
        String date=DateAndTime.getCurrentDate();
        //String date= "27/10/2020";
        String expectedMsg = adjustmentTabPage.validation("CAB","KA-54-5709(13)",date,"MISC","50","");
        String actualMsg="Please Add Comment.";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 4)
    public void validationOfEntityId() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentTabPage = new AdjustmentTabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentTabPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentTabPage.clickOnAddNewAdjustment();
        //String date=DateAndTime.getCurrentDate();
        String date= "27/10/2020";
        String expectedMsg = adjustmentTabPage.validation("CAB","",date,"MISC","50","qwer");
        String actualMsg="TripId/CabId can't be empty...";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 5)
    public void validationOfEntityType() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentTabPage = new AdjustmentTabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentTabPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentTabPage.clickOnAddNewAdjustment();
        //String date=DateAndTime.getCurrentDate();
        String date= "27/10/2020";
        String expectedMsg = adjustmentTabPage.validation("","",date,"MISC","50","qwer");
        String actualMsg="entityType can't be empty...\n" +
                "TripId/CabId can't be empty...";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test( priority = 6)
    public void validationOfAdjustmentType() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentTabPage = new AdjustmentTabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentTabPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentTabPage.clickOnAddNewAdjustment();
        String date=DateAndTime.getCurrentDate();
        //String date= "27/10/2020";
        String expectedMsg = adjustmentTabPage.validation("CAB","KA-54-5709(13)",date,"","50","qwer");
        String actualMsg="Amount can't be empty or invalid no ...\n" +
                "Adjustment Type can't be empty...";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 7)
    public void validationOfDate() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentTabPage = new AdjustmentTabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentTabPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentTabPage.clickOnAddNewAdjustment();
        //String date=DateAndTime.getCurrentDate();
        String date= "27/10/2020";
        String expectedMsg = adjustmentTabPage.validation("CAB","KA-54-5709(13)","NULL","MISC","50","qwer");
        String actualMsg="Date can't be empty...";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

    @Test(priority = 8)
    public void adjustmentCheckUploadFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        AdjustmentTabPage adjustmentPage = new AdjustmentTabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        adjustmentPage.navigateToAdjustmentPage();
        Thread.sleep(7000);
        adjustmentPage.bulkUploadCabContract();
        //adjustmentPage.checkAdd(map.get("EntityID"),map.get("Amount"),map.get("AdjustmentType"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
}
