package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;



public class TotalCaculation extends BaseTest {
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "TotalCalTripPackageData", priority = 3)
    public void testTripPackageTotalCalculation(Map<String, String> map) throws InterruptedException, ParseException, IOException {

        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        Thread.sleep(8000);
        waitForCommandToFinish();
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        B2BSlabPage b2BSlabPage = new B2BSlabPage(getChromeDriver());
        HomePage homePage = new HomePage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        TripPackage tripPackage = new TripPackage(getChromeDriver());
        EscortPage escortPage = new EscortPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        System.out.println(map.get("TestCaseId"));
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        Thread.sleep(30000);
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        tripPackage.clickOnTripPackageSlab();
        tripPackage.updateTripPackageSlab(map.get("SlabName"), map.get("MaxKm"), map.get("FuelCost"), map.get("FixedCost"), map.get("Jumpcriteria"), map.get("Extrakmcost"), map.get("ACSurcharge"),
                map.get("AdditionalExtraKmcost(AC)"), map.get("Escort(AC)"), map.get("Escort(Non-AC)"));
        b2BSlabPage.clickOnB2BSlab();
        b2BSlabPage.updateB2BSlab(map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"), map.get("loginCost"), map.get("logoutCost"));
        escortPage.clickOnEscortSlabTotalCal();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if (map.get("b2bType").equals("CONFIGURABLE_FIXED")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Login", map.get("empForB2BTrip"), map.get("ContractName"), map.get("zone"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String b2bCostLogout = map.get("logoutCost");
            String ActualGrandTotalLogout = BillCalculationUtils.apiGrandTotal(tripId1);
            BillComparisionUtil.compareBill(b2bCostLogout, ActualGrandTotalLogout);
            String b2bCostLogin = map.get("loginCost");
            String ActualGrandTotalLogin = BillCalculationUtils.apiGrandTotal(tripId2);
            BillComparisionUtil.compareBill(b2bCostLogin, ActualGrandTotalLogin);
        } else if (map.get("b2bType").equals("NA") && map.get("EscortUsage").equals("ALL")) {
            homePage.navigateToDashboard();
            waitForCommandToFinish();
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            homePage.navigateToDashboard();
            waitForCommandToFinish();
            dashboardPage.searchEmployeeInDasboardForLoginTrip("princi");
            String hour3 = dateAndTime.hour24formate();
            String min1 = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min1, hour3);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId2 = dashboardPage.saveTripsheet(hour3, min1);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId2);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractCost = BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("FixedCost"), map.get("MaxKm"), map.get("Jumpcriteria"), map.get("kmTravel"), "0");
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
            String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
            String total = BillCalculationUtils.grandTotalBillWithGst("0", b2bCost, "NA", map.get("b2bType"), "NA", "0", contractCost, escortCost, "0", "0");
            String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
            String actualTotal2 = BillCalculationUtils.apiGrandTotal(tripId2);
            BillComparisionUtil.compareBill(total, actualTotal);
            BillComparisionUtil.compareBill(total, actualTotal2);

        }
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "TotalCalZoneBasedData", priority = 1)
    public void testZoneBasedTotalCalculation(Map<String, String> map) throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        Thread.sleep(8000);
        HomePage homePage = new HomePage(getChromeDriver());
        ContractZoneBasedPage contractZoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        EscortPage escortPage = new EscortPage(getChromeDriver());
        B2BSlabPage b2BSlabPage = new B2BSlabPage(getChromeDriver());
        System.out.println(map.get("testCaseName"));
        homePage.navigateToSettings();
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        Thread.sleep(15000);
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        contractZoneBasedPage.clickOnZoneBased();
        contractZoneBasedPage.updateZoneSlab(map.get("SlabName"), map.get("ZoneName"), map.get("FuelCost"), map.get("FixedCost"));
        b2BSlabPage.clickOnB2BSlab();
        b2BSlabPage.updateB2BSlab(map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"), map.get("loginCost"), map.get("logoutCost"));
        escortPage.clickOnEscortSlabTotalCal();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if (map.get("EscortUsage").equals("ALL")) {
            dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
            String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), "0", "0",map.get("Coefficient"),map.get("constant"));
            String ShiftCost = "0";
            String inclusionLogicCost = "0";
            String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
            String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
            String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
            BillComparisionUtil.compareBill(total, actualTotal);
        } else if (map.get("EscortUsage").equals("LOGIN")) {
            dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
            String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), "0", "0",map.get("Coefficient"),map.get("constant"));
            String ShiftCost = "0";
            String inclusionLogicCost = "0";
            String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
            String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
            String actualTotal = BillCalculationUtils.apiGrandTotal(tripId);
            BillComparisionUtil.compareBill(total, actualTotal);
        } else if (map.get("EscortUsage").equals("LAST_TRIP") || map.get("EscortUsage").equals("FIRST_LAST_TRIP")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Logout", map.get("empForB2BTrip"), map.get("ContractName"), map.get("ZoneName"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            if (map.get("EscortUsage").equals("LAST_TRIP")) {
                String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal);
            } else {
                String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
                String actualTotal1 = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal1);
            }
        } else if (!map.get("b2bType").equals("NA")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            System.out.println(timeTenMinBeforeHour);
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Login", map.get("empForB2BTrip"), map.get("ContractName"), map.get("ZoneName"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            if (map.get("distributionLogic").equals("EQUAL")) {
                String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
                String actualTotal1 = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal1);
            } else if (map.get("distributionLogic").equals("ONLY_LOGIN")) {
                String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal);
            } else if (map.get("distributionLogic").equals("ONLY_LOGOUT")) {
                String contractCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String total = BillCalculationUtils.grandTotalForZoneBased(map.get("FixedCost"), map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
            } else {

            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "TotalCalDailyPackageData", priority = 2)
    public void testDailyPackageTotalCalculation(Map<String, String> map) throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        Thread.sleep(10000);
        HomePage homePage = new HomePage(getChromeDriver());
        DailyPackage dailyPackage = new DailyPackage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        EscortPage escortPage = new EscortPage(getChromeDriver());
        B2BSlabPage b2BSlabPage = new B2BSlabPage(getChromeDriver());
        System.out.println(map.get("testCaseName"));
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        dailyPackage.selectDistributionLogic(map.get("DistributionLogic"),map.get("Mileage"));
        dailyPackage.clickOnDailyPackageSlab();
        dailyPackage.updateDailyPackageSlab(map.get("SlabName"), map.get("FixedKm"), map.get("FixedHour"), map.get("FixedCost"), map.get("Jumpcriteria"), map.get("ExtraKmCost"), map.get("ExtraHourCost"));
        b2BSlabPage.clickOnB2BSlab();
        b2BSlabPage.updateB2BSlab(map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"), map.get("loginCost"), map.get("logoutCost"));
        escortPage.clickOnEscortSlabTotalCal();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if (map.get("EscortUsage").equals("ALL")) {
            dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractCost = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
            String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), "0", "0",map.get("Coefficient"),map.get("constant"));
            String ShiftCost = "0";
            String inclusionLogicCost = "0";
            String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
            String total = BillCalculationUtils.grandTotalForDailyPackage(contractCost, map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, "0","0" ,inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"),map.get("EscortUsage"));
            String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
            BillComparisionUtil.compareBill(total, actualTotal);
        } else if (map.get("EscortUsage").equals("LAST_TRIP") || map.get("EscortUsage").equals("FIRST_LAST_TRIP")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Logout", map.get("empForB2BTrip"), map.get("ContractName"), map.get("zone"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            if (map.get("EscortUsage").equals("LAST_TRIP")) {
                String contractCost = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
                int Cost=Integer.parseInt(contractCost);
                Cost=Cost/2;
                String ContractCost=Integer.toString(Cost);
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), ContractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String empAdjustmentCost = "0";
                String total = BillCalculationUtils.grandTotalForDailyPackage(ContractCost, map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, empAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"),map.get("EscortUsage"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal);
            } else {
                String contractCost = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
                int Cost=Integer.parseInt(contractCost);
                Cost=Cost/2;
                String ContractCost=Integer.toString(Cost);
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), ContractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), contractCost, contractCost,map.get("Coefficient"),map.get("constant"));
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String empAdjustmentCost = "0";
                String total = BillCalculationUtils.grandTotalForDailyPackage(ContractCost, map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, empAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"),map.get("EscortUsage"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
                String actualTotal1 = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal1);
            }
        } else if (!map.get("b2bType").equals("NA")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            //billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Login", map.get("empForB2BTrip"), map.get("ContractName"), map.get("zone"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            if (map.get("distributionLogic").equals("EQUAL")) {
                String contractCost = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
                System.out.println(contractCost);
                int Cost=Integer.parseInt(contractCost);
                Cost=Cost/2;
                String ContractCost=Integer.toString(Cost);
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), ContractCost, ContractCost,map.get("Coefficient"),map.get("constant"));
                System.out.println(b2bCost);
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = "0";
                String empAdjustmentCost = "0";
                String total = BillCalculationUtils.grandTotalForDailyPackage(ContractCost, map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, empAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"),map.get("EscortUsage"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
                String actualTotal1 = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal1);
            } else if (map.get("distributionLogic").equals("ONLY_LOGIN")) {
                String contractCost = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
                int Cost=Integer.parseInt(contractCost);
                Cost=Cost/2;
                String ContractCost=Integer.toString(Cost);
                System.out.println(contractCost);
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), ContractCost, ContractCost,map.get("Coefficient"),map.get("constant"));
                System.out.println(b2bCost);
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String empAdjustmentCost = "0";
                String total = BillCalculationUtils.grandTotalForDailyPackage(ContractCost, map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, empAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"),map.get("EscortUsage"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal);
            } else if (map.get("distributionLogic").equals("ONLY_LOGOUT")) {
                String contractCost = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
                System.out.println(contractCost);
                int Cost=Integer.parseInt(contractCost);
                Cost=Cost/2;
                String ContractCost=Integer.toString(Cost);
                String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
                String b2bCost = BillCalculationUtils.b2BSlab(map.get("b2bType"), map.get("fixedFactor"), map.get("distributionLogic"), ContractCost, ContractCost,map.get("Coefficient"),map.get("constant"));
                System.out.println(b2bCost);
                String ShiftCost = "0";
                String inclusionLogicCost = "0";
                String tripAdjustmentCost = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"), map.get("serviceType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractCost);
                String empAdjustmentCost = "0";
                String total = BillCalculationUtils.grandTotalForDailyPackage(ContractCost, map.get("b2bType"), b2bCost, escortCost, tripAdjustmentCost, empAdjustmentCost, "0", inclusionLogicCost, map.get("IgnoreTripType"), map.get("IncludingTripType"),map.get("EscortUsage"));
                String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
            } else {

            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "TotalCalBillCyclePackageData", priority = 4)
    public void testBillCyclePackageTotalCalculation(Map<String, String> map) throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        Thread.sleep(8000);
        HomePage homePage = new HomePage(getChromeDriver());
        DailyPackage dailyPackage = new DailyPackage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        EscortPage escortPage = new EscortPage(getChromeDriver());
        B2BSlabPage b2BSlabPage = new B2BSlabPage(getChromeDriver());
        System.out.println(map.get("testCaseName"));
        BillCyclePackageContractPage billCycle = new BillCyclePackageContractPage(getChromeDriver());
       homePage.navigateToSettings();
        Thread.sleep(20000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        Thread.sleep(10000);
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        //dailyPackage.selectDistributionLogic(map.get("DistributionLogic"));
        billCycle.clickOnBillCyclePackageSlab();
        billCycle.updateBillCyclePackageSlab(map.get("Duties"),map.get("CummulativeLevel"),map.get("BillingCycleFuelHikeCalculationType"),map.get("Autoprorate"),map.get("MaxDutyHourPerDuty")
        ,map.get("SlabName"),map.get("FixedKm"),map.get("FixedHourDurationType"),map.get("FixedHour"),map.get("MinimumKmPerDay"),map.get("AbsentCut"),map.get("ExtraKmCost")
        ,map.get("ExtraHourCost"),map.get("JumpCriteria"),map.get("ExtraDutyCost"),map.get("FixedCost"));
        b2BSlabPage.clickOnB2BSlab();
        b2BSlabPage.updateB2BSlab(map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"), map.get("loginCost"), map.get("logoutCost"));
        escortPage.clickOnEscortSlabbillcycle();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if(map.get("EscortUsage").equals("NA")&&map.get("b2bType").equals("NA")) {
            dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            billingDashboardPage.navigateToNewBillingReport();
            newBillingReportPage.chooseBillCycle();
            String totalnoofduties = newBillingReportPage.getTotalNoOfDutiesForBillCyclePackage();
            String totalKm = newBillingReportPage.getTotalKmForBillCyclePackage();
            String contractCots = BillCalculationUtils.billCyclePackageSlab(map.get("FixedCost"), totalnoofduties);
            String total = BillCalculationUtils.grandTotalForBillCyclePackage(totalnoofduties, totalKm, map.get("Duties"), map.get("FixedKm"), map.get("FixedCost"), map.get("ExtraDutyCost"), map.get("ExtraKmCost"), map.get("EscortUsage"), map.get("CostType"), map.get("Cost"),map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"));
            System.out.println(total);
            String actualTotal = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId1);
            BillComparisionUtil.compareBill(total, actualTotal);
        }
        else if (map.get("EscortUsage").equals("ALL")) {
            dashboardPage.searchEmployeeInDasboard("princi", "princi chauhan ( 290 )");
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            billingDashboardPage.navigateToNewBillingReport();
            newBillingReportPage.chooseBillCycle();
            String totalnoofduties = newBillingReportPage.getTotalNoOfDutiesForBillCyclePackage();
            String totalKm = newBillingReportPage.getTotalKmForBillCyclePackage();
            String contractCots = BillCalculationUtils.billCyclePackageSlab(map.get("FixedCost"), totalnoofduties);
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCots);
            String total = BillCalculationUtils.grandTotalForBillCyclePackage(totalnoofduties, totalKm, map.get("Duties"), map.get("FixedKm"), map.get("FixedCost"), map.get("ExtraDutyCost"), map.get("ExtraKmCost"), map.get("EscortUsage"), map.get("CostType"), map.get("Cost"),map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"));
            System.out.println(total);
            String actualTotal = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId1);
            BillComparisionUtil.compareBill(total, actualTotal);
        }
        else if(map.get("EscortUsage").equals("FIRST_LAST_TRIP")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("ZoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Logout", map.get("empForB2BTrip"), map.get("ContractName"), map.get("ZoneName"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            billingDashboardPage.navigateToNewBillingReport();
            newBillingReportPage.chooseBillCycle();
            String totalnoofduties = newBillingReportPage.getTotalNoOfDutiesForBillCyclePackage();
            String totalKm = newBillingReportPage.getTotalKmForBillCyclePackage();
            String contractCots = BillCalculationUtils.billCyclePackageSlab(map.get("FixedCost"), totalnoofduties);
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCots);
            String total = BillCalculationUtils.grandTotalForBillCyclePackage(totalnoofduties, totalKm, map.get("Duties"), map.get("FixedKm"), map.get("FixedCost"), map.get("ExtraDutyCost"), map.get("ExtraKmCost"), map.get("EscortUsage"), map.get("CostType"), map.get("Cost"),map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"));
            System.out.println(total);
            String actualTotal = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId1);
            String actualTotal2 = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId2);
            BillComparisionUtil.compareBill(total, actualTotal);
            BillComparisionUtil.compareBill(total, actualTotal2);
        }else if(!map.get("b2bType").equals("NA")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.addEmployee("mamta");
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Login", map.get("empForB2BTrip"), map.get("ContractName"), map.get("zone"), map.get("BillingModel"), map.get("EscortUsage"), map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            //billingDashboardPage.addKm(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            billingDashboardPage.navigateToNewBillingReport();
            newBillingReportPage.chooseBillCycle();
            String totalnoofduties = newBillingReportPage.getTotalNoOfDutiesForBillCyclePackage();
            String totalKm = newBillingReportPage.getTotalKmForBillCyclePackage();
            if (map.get("distributionLogic").equals("EQUAL")) {
                String total = BillCalculationUtils.grandTotalForBillCyclePackage(totalnoofduties, totalKm, map.get("Duties"), map.get("FixedKm"), map.get("FixedCost"), map.get("ExtraDutyCost"), map.get("ExtraKmCost"), map.get("EscortUsage"), map.get("CostType"), map.get("Cost"),map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"));
                String actualTotal = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
                String actualTotal1 = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal1);
            } else if (map.get("distributionLogic").equals("ONLY_LOGIN")) {
                String total = BillCalculationUtils.grandTotalForBillCyclePackage(totalnoofduties, totalKm, map.get("Duties"), map.get("FixedKm"), map.get("FixedCost"), map.get("ExtraDutyCost"), map.get("ExtraKmCost"), map.get("EscortUsage"), map.get("CostType"), map.get("Cost"),map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"));
                String actualTotal1 = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId2);
                BillComparisionUtil.compareBill(total, actualTotal1);
            } else if (map.get("distributionLogic").equals("ONLY_LOGOUT")) {
                String total = BillCalculationUtils.grandTotalForBillCyclePackage(totalnoofduties, totalKm, map.get("Duties"), map.get("FixedKm"), map.get("FixedCost"), map.get("ExtraDutyCost"), map.get("ExtraKmCost"), map.get("EscortUsage"), map.get("CostType"), map.get("Cost"),map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"));
                String actualTotal = BillCalculationUtils.apiGrandTotalForBillCyclePackage(tripId1);
                BillComparisionUtil.compareBill(total, actualTotal);
            }
        }
        loginPage.logout();
    }
   /* @Test(dataProviderClass = DataProviderTest.class, dataProvider = "TotalCalEmployeeBasedData", priority = 4)
    public void testEmployeeBasedTotalCalculation(Map<String, String> map) throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        Thread.sleep(8000);
        HomePage homePage = new HomePage(getChromeDriver());
        DailyPackage dailyPackage = new DailyPackage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        EscortPage escortPage = new EscortPage(getChromeDriver());
        B2BSlabPage b2BSlabPage = new B2BSlabPage(getChromeDriver());
        System.out.println(map.get("testCaseName"));
        ContractEmployeeBasedPage contractEmployeeBasedPage = new ContractEmployeeBasedPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(20000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        contractEmployeeBasedPage.clickOnEmployeeBasedSlab();
        contractEmployeeBasedPage.updateEmployeeSlab(map.get("LogicType"),map.get("EmployeeLogic"),map.get("SlabName"),map.get("FuelCost"),map.get("PerHeadCost"),map.get("MaximumKM"));
        escortPage.clickOnEscortSlabEmployee();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if (map.get("EscortUsage").equals("ALL")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.addEmployee("mamta");
            if(map.get("EmployeeLogic").equals("TRAVELLED")){
                billingDashboardPage.makeATripNoShowTrip();
            }
            String empCountForFirstTrip = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractCost = BillCalculationUtils.calculationForEmployeeBasedCost(map.get("EmployeeLogic"),empCountForFirstTrip,map.get("PerHeadCost"),map.get("plannedEmpCount"),map.get("travelledEmpCount"));
            String escortCost = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractCost);
            String total = BillCalculationUtils.grandTotalForEmployeeBased(contractCost,"NA","0",escortCost,"0","0","0","0","NA","NA");
            String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
            BillComparisionUtil.compareBill(total, actualTotal);
        }else if (!map.get("EscortUsage").equals("IGNORE_B2B")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.addEmployee("EscortNameSearch");
            billingDashboardPage.addEmployee("mamta");
            if(map.get("EmployeeLogic").equals("TRAVELLED")){
                billingDashboardPage.makeATripNoShowTrip();
            }
            String empCountForFirstTrip = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.saveTripsheetInformation();
            String timeTenMinBefore = dateAndTime.tenMinAfterMin();
            String timeTenMinBeforeHour = dateAndTime.tenMinAfterHour();
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Login", map.get("empForB2BTrip"), map.get("ContractName"), map.get("zone"), map.get("BillingModel"), map.get("EscortUsage"), map.get("EscortNameSearch"));
            if(map.get("EmployeeLogic").equals("TRAVELLED")){
                billingDashboardPage.makeATripNoShowTrip();
            }
            billingDashboardPage.saveTripsheetInformation();
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractCost = BillCalculationUtils.calculationForEmployeeBasedCost(map.get("EmployeeLogic"),empCountForFirstTrip,map.get("PerHeadCost"),map.get("plannedEmpCount"),map.get("travelledEmpCount"));
            String total = BillCalculationUtils.grandTotalForEmployeeBased(contractCost,"NA","0","0","0","0","0","0","NA","NA");
            String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
            BillComparisionUtil.compareBill(total, actualTotal);
            String actualTotal2 = BillCalculationUtils.apiGrandTotal(tripId2);
            BillComparisionUtil.compareBill(total, actualTotal2);
        }
            loginPage.logout();
        }*/
    }
