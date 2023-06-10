package org.mis.tests;

import org.mis.pom.AdjustmentTabPage;
import org.mis.pom.HomePage;
import org.mis.pom.LoginPage;
import org.mis.pom.ManualTollPage;
import org.mis.utils.BillComparisionUtil;
import org.mis.utils.DataProviderTest;
import org.mis.utils.DateAndTime;
import org.mis.utils.PropertyReader;
import org.testng.annotations.Test;

import java.util.Map;

public class ManualTollTest extends BaseTest {

    @Test(priority = 1)
    public void manualTollCheckAddFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ManualTollPage manualTollPage = new ManualTollPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        manualTollPage.navigateToManualTollPage();
        Thread.sleep(7000);
        manualTollPage.clickOnAddNewManualToll();
        String date=DateAndTime.getCurrentDate();
        manualTollPage.addManualToll("CAB","KA-54-5709(13)",date,"50","50123");
        manualTollPage.saveManualToll();
        String dateToVerify=DateAndTime.getDateForAdjustmentSearchCheck();
        manualTollPage.checkAdd("KA-54-5709(13)","50",dateToVerify,"CAB");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test( priority = 2)
    public void searchAndVerifyAddedToll() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ManualTollPage manualTollPage = new ManualTollPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        manualTollPage.navigateToManualTollPage();
        Thread.sleep(7000);
        String date=DateAndTime.getCurrentDate();
        manualTollPage.search(date,date);
        manualTollPage.clickOnEditManualToll();
        manualTollPage.verifyManualToll("CAB",date,"KA-54-5709(13)","50");
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

   /* @Test(priority = 1)
    public void manualTollEditAndCheckHistoryFunctionality() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ManualTollPage manualTollPage = new ManualTollPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        manualTollPage.navigateToManualTollPage();
        Thread.sleep(7000);
        String date=DateAndTime.getCurrentDate();
        //manualTollPage.search(date,date);
        //manualTollPage.clickOnEditManualToll();
        //manualTollPage.addManualToll("CAB","KA-54-5709(13)",date,"56","50123");
        //manualTollPage.saveManualToll();
        String currentDate = DateAndTime.getCurrentDateForHistoryPetternCheck();
        manualTollPage.historyCheck("56",date,currentDate);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }*/

    @Test( priority = 5)
    public void validationOfEntityId() throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ManualTollPage manualTollPage = new ManualTollPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        manualTollPage.navigateToManualTollPage();
        Thread.sleep(7000);
        manualTollPage.clickOnAddNewManualToll();
        String date=DateAndTime.getCurrentDate();
        String expectedMsg = manualTollPage.validation("CAB","",date,"50","50123");
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
        ManualTollPage manualTollPage = new ManualTollPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        manualTollPage.navigateToManualTollPage();
        Thread.sleep(7000);
        manualTollPage.clickOnAddNewManualToll();
        String date=DateAndTime.getCurrentDate();
        String expectedMsg = manualTollPage.validation("","",date,"50","50123");
        String actualMsg="TripId/CabId can't be empty...";
        Thread.sleep(5000);
        BillComparisionUtil.validation(expectedMsg,actualMsg);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "addManualTollUpload",priority = 1)
    public void manualTollCheckUploadFunctionality(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ManualTollPage manualTollPage = new ManualTollPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(5000);
        homePage.navigateToNewContract();
        Thread.sleep(5000);
        homePage.switchIframe();
        Thread.sleep(5000);
        manualTollPage.navigateToManualTollPage();
        Thread.sleep(7000);
        manualTollPage.bulkUploadManualToll();
        String dateToVerify=DateAndTime.getDateForAdjustmentSearchCheck();
        manualTollPage.checkAdd(map.get("Entity ID"),map.get("Amount"),dateToVerify,map.get("Entity Type"));
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
}
