package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class KmSlabMandatoryFieldTest extends BaseTest {


    @Test(priority = 1)
    public void KmSlabTestingForMandatoryFields() throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased contractKmBased = new ContractKmBased(getChromeDriver());
        ContractPage contractPage= new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage= new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate("Trip_Test2");
        contractPage.updateMandatoryFieldOnContract("KM_SLAB","4seater","0","ABC","ACTUAL","ACTUAL",
                "DUTYID");
        contractKmBased.selectMileageAndDistributionLogic("15","EQUAL");
        contractKmBased.clickOnKmSlab();
        contractKmBased.updateKmSlab("s1","210","10","50");
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        dashboardPage.searchEmployeeInDasboard("princi","princi chauhan ( 290 )");
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet("Jai Travels - JAI-001 (IND4S) (KM 02 MA 1234)");
        String tripId1 = dashboardPage.saveTripsheet(hour2,min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract("Trip_Test2");
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm("10");
        billingDashboardPage.auditDone();
        billingDashboardPage.navigateToNewBillingReport();
        newBillingReportPage.generateBill();
        downloadBillReport.navigateToReportDownload();
        waitForCommandToFinish();
        downloadBillReport.downloadReportDefault();
        contractKmBased.validateReportData(tripId1, "100");
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabData", priority = 1)
    public void kmSlabTest(Map<String, String> map) throws InterruptedException, ParseException, IOException {

        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        HomePage homePage = new HomePage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        EscortPage escortPage = new EscortPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        System.out.println(map.get("TestCaseId"));
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract("KM_SLAB", map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        kmSlabPage.clickOnKmSlab();
        kmSlabPage.selectMileageAndDistributionLogic(map.get("Mileage"), map.get("DistributionLogic"));
        kmSlabPage.updateKmSlab(map.get("slabName"), map.get("maxKm"), map.get("costPerKm"), map.get("minCost"));
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        dashboardPage.searchEmployeeInDasboard("princi","princi chauhan ( 290 )");
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet("Jai Travels - JAI-001 (IND4S) (KM 02 MA 1234)");
        String tripId1 = dashboardPage.saveTripsheet(hour2,min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract("Trip_Test2");
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm(map.get("kmTravel"));
        billingDashboardPage.auditDone();
        String expectedResult = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
        System.out.println(expectedResult);
        ApiToGenerateBill.genetareBill();
        String actualResult = BillCalculationUtils.apiContractPrice(tripId1);
        BillComparisionUtil.compareBill(expectedResult,actualResult);
        loginPage.logout();
    }

    }