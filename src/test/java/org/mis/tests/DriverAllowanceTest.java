package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Map;

public class DriverAllowanceTest extends BaseTest {

    @Test(priority = 1)
    public void testForNightBataCost() throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        HomePage homePage = new HomePage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DriverAllowencePage driverAllowencePage = new DriverAllowencePage(getChromeDriver());
        TripAdjustmentPage tripAdjustmentPage = new TripAdjustmentPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate("Trip_Test2");
        contractPage.updateMandatoryFieldOnContract("KM_SLAB", "4seater", "0", "FuelType2", "ACTUAL"
                , "ACTUAL", "DUTYID");
        kmSlabPage.selectMileageAndDistributionLogic("15", "EQUAL");
        kmSlabPage.clickOnKmSlab();
        kmSlabPage.updateKmSlab("s1", "200", "10", "50");
        driverAllowencePage.clickOnDriverAllowanceSlab();
        String startTime= DateAndTime.startTimeForDriverAlowance();
        String endTime = DateAndTime.endTimeForDriverAlowance();
        driverAllowencePage.updateDriverAllowanceSlab("30",startTime,endTime,"0");
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet("Jai Travels - JAI-001 (IND4S) (KM 02 MA 1234)");
        String tripId1 = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract("Trip_Test2");
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        ApiToGenerateBill.genetareBill();
        String contractPrice = BillCalculationUtils.apiDriverBataAmount(tripId1);
        String expectedPrice = BillCalculationUtils.driverBataSlab("0","30");
        BillComparisionUtil.compareBill(expectedPrice,contractPrice);
        loginPage.logout();
    }
    @Test(priority = 2)
    public void testForDayBataCost() throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        HomePage homePage = new HomePage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DriverAllowencePage driverAllowencePage = new DriverAllowencePage(getChromeDriver());
        TripAdjustmentPage tripAdjustmentPage = new TripAdjustmentPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate("Trip_Test2");
        contractPage.updateMandatoryFieldOnContract("KM_SLAB", "4seater", "0", "FuelType2", "ACTUAL"
                , "ACTUAL", "DUTYID");
        kmSlabPage.selectMileageAndDistributionLogic("15", "EQUAL");
        kmSlabPage.clickOnKmSlab();
        kmSlabPage.updateKmSlab("s1", "200", "10", "50");
        driverAllowencePage.clickOnDriverAllowanceSlab();
        String startTime= DateAndTime.startTimeForDriverAlowance();
        String endTime = DateAndTime.endTimeForDriverAlowance();
        driverAllowencePage.updateDriverAllowanceSlab("0",startTime,endTime,"30");
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet("Jai Travels - JAI-001 (IND4S) (KM 02 MA 1234)");
        String tripId1 = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract("Trip_Test2");
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        ApiToGenerateBill.genetareBill();
        String contractPrice = BillCalculationUtils.apiDriverBataAmount(tripId1);
        String expectedPrice = BillCalculationUtils.driverBataSlab("30","0");
        BillComparisionUtil.compareBill(expectedPrice,contractPrice);
        loginPage.logout();
    }
}
