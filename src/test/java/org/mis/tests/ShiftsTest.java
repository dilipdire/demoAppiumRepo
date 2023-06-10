package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class ShiftsTest extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabShiftsData", priority = 1)
    public void testKmSlabWithSiftsSlab(Map<String, String> map) throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
       try {
           //getChromeDriver().get(PropertyReader.getProperty("base.url"));
           //getNgWebDriver().waitForAngularRequestsToFinish();
           //LoginPage loginPage = new LoginPage(getChromeDriver());
           loginPage.login();
           waitForCommandToFinish();
           BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
           HomePage homePage = new HomePage(getChromeDriver());
           DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
           ContractPage contractPage = new ContractPage(getChromeDriver());
           ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
           ShiftsSlabPage shiftsSlabPage = new ShiftsSlabPage(getChromeDriver());
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
           shiftsSlabPage.clickOnShiftsSlab();
           shiftsSlabPage.updateShiftsSlab(map.get("IgnoreTripType"), map.get("shiftDirection"));
           contractPage.saveContract();
           homePage.switchToDefaultFrame();
           homePage.navigateToDashboard();
           waitForCommandToFinish();
           if (map.get("IgnoreTripType").equals("NON_SHIFT") && map.get("shiftDirection").equals("LOGOUT")) {
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
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId1);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("NON_SHIFT") && map.get("shiftDirection").equals("LOGIN")) {
               dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
               dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
               String tripId1 = dashboardPage.saveTripsheet(hour2, min);
               dashboardPage.navigateToBillingDashboard();
               billingDashboardPage.performTrip(tripId1);
               billingDashboardPage.selectContract(map.get("ContractName"));
               billingDashboardPage.saveTripsheetInformation();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId1);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("NON_SHIFT") && map.get("shiftDirection").equals("BOTH")) {
               dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
               dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
               String tripIdLogoutTrip = dashboardPage.saveTripsheet(hour2, min);
               dashboardPage.navigateToBillingDashboard();
               billingDashboardPage.performTrip(tripIdLogoutTrip);
               billingDashboardPage.selectContract(map.get("ContractName"));
               billingDashboardPage.saveTripsheetInformation();
               billingDashboardPage.auditDone();
               homePage.navigateToDashboard();
               waitForCommandToFinish();
               dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("SearchEmpName"));
               String hour3 = dateAndTime.hour24formate();
               String min1 = dateAndTime.minTwoHourLater();
               dashboardPage.createNonShiftTripsheetForLoginTrip(min1, hour3);
               dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
               String tripIdLoginTrip = dashboardPage.saveTripsheet(hour3, min1);
               dashboardPage.navigateToBillingDashboard();
               billingDashboardPage.performTrip(tripIdLoginTrip);
               billingDashboardPage.selectContract(map.get("ContractName"));
               billingDashboardPage.saveTripsheetInformation();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPriceLogouttrip = BillCalculationUtils.apiContractPrice(tripIdLogoutTrip);
               String expectedPriceLogouttrip = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPriceLogouttrip, contractPriceLogouttrip);
               String contractPriceLoginTrip = BillCalculationUtils.apiContractPrice(tripIdLoginTrip);
               String expectedPriceLoginTrip = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPriceLoginTrip, contractPriceLoginTrip);
           } else if (map.get("IgnoreTripType").equals("ADHOC") && map.get("shiftDirection").equals("LOGIN")) {
               dashboardPage.navigateToBillingDashboard();
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min, hour2, map.get("ContractName"), map.get("SearchEmpName"), "Login");
               String tripId = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("ADHOC") && map.get("shiftDirection").equals("LOGOUT")) {
               dashboardPage.navigateToBillingDashboard();
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min, hour2, map.get("ContractName"), map.get("SearchEmpName"), "Logout");
               String tripId = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("ADHOC") && map.get("shiftDirection").equals("BOTH")) {
               dashboardPage.navigateToBillingDashboard();
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min, hour2, map.get("ContractName"), map.get("SearchEmpName"), "Login");
               String tripId = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               dashboardPage.navigateToBillingDashboard();
               String hour3 = dateAndTime.hour24formate();
               String min1 = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min1, hour3, map.get("ContractName"), map.get("SearchEmpName"), "Logout");
               String tripId2 = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
               String contractPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice2, contractPrice2);
           } else if (map.get("IgnoreTripType").equals("SINGLE_ESCORT") && map.get("shiftDirection").equals("LOGOUT")) {
               dashboardPage.searchEmployeeInDasboard(map.get("EscortNameSearch"), map.get("EscortName"));
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
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId1);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("SINGLE_ESCORT") && map.get("shiftDirection").equals("LOGIN")) {
               dashboardPage.searchEmployeeInDasboard(map.get("EscortNameSearch"), map.get("EscortName"));
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
               dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
               String tripId1 = dashboardPage.saveTripsheet(hour2, min);
               dashboardPage.navigateToBillingDashboard();
               billingDashboardPage.performTrip(tripId1);
               billingDashboardPage.selectContract(map.get("ContractName"));
               billingDashboardPage.saveTripsheetInformation();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId1);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("SINGLE_ESCORT") && map.get("shiftDirection").equals("BOTH")) {
               dashboardPage.searchEmployeeInDasboard(map.get("EscortNameSearch"), map.get("EscortName"));
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
               homePage.navigateToDashboard();
               waitForCommandToFinish();
               dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("EscortNameSearch"));
               String hour3 = dateAndTime.hour24formate();
               String min1 = dateAndTime.minTwoHourLater();
               dashboardPage.createNonShiftTripsheetForLoginTrip(min1, hour3);
               dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
               String tripId2 = dashboardPage.saveTripsheet(hour3, min1);
               dashboardPage.navigateToBillingDashboard();
               billingDashboardPage.performTrip(tripId2);
               billingDashboardPage.selectContract(map.get("ContractName"));
               billingDashboardPage.saveTripsheetInformation();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId1);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
               String contractPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice2, contractPrice2);
           } else if (map.get("IgnoreTripType").equals("SINGLE_ADHOC_ESCORT") && map.get("shiftDirection").equals("LOGIN")) {
               dashboardPage.navigateToBillingDashboard();
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min, hour2, map.get("ContractName"), map.get("EscortNameSearch"), "Login");
               String tripId = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("SINGLE_ADHOC_ESCORT") && map.get("shiftDirection").equals("LOGOUT")) {
               dashboardPage.navigateToBillingDashboard();
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min, hour2, map.get("ContractName"), map.get("EscortNameSearch"), "Logout");
               String tripId = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
               String expectedPrice = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPrice, contractPrice);
           } else if (map.get("IgnoreTripType").equals("SINGLE_ADHOC_ESCORT") && map.get("shiftDirection").equals("BOTH")) {
               dashboardPage.navigateToBillingDashboard();
               String hour2 = dateAndTime.hour24formate();
               String min = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min, hour2, map.get("ContractName"), map.get("EscortNameSearch"), "Logout");
               String tripId1 = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               dashboardPage.navigateToBillingDashboard();
               String hour3 = dateAndTime.hour24formate();
               String min1 = dateAndTime.minTwoHourLater();
               billingDashboardPage.adhocTrip(min1, hour3, map.get("ContractName"), map.get("EscortNameSearch"), "Login");
               String tripId2 = billingDashboardPage.getMultipleTripId();
               billingDashboardPage.auditDone();
               ApiToGenerateBill.genetareBill();
               String contractPriceLogoutTrip = BillCalculationUtils.apiContractPrice(tripId1);
               String expectedPriceLogoutTrip = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPriceLogoutTrip, contractPriceLogoutTrip);
               String contractPriceLoginTrip = BillCalculationUtils.apiContractPrice(tripId2);
               String expectedPriceLoginTrip = BillCalculationUtils.shiftsSlab();
               BillComparisionUtil.compareBill(expectedPriceLoginTrip, contractPriceLoginTrip);
           } else if (map.get("IgnoreTripType").equals("NO_SHOW") && map.get("shiftDirection").equals("LOGIN")) {

           } else if (map.get("IgnoreTripType").equals("NO_SHOW") && map.get("shiftDirection").equals("LOGOUT")) {

           } else if (map.get("IgnoreTripType").equals("NO_SHOW") && map.get("shiftDirection").equals("BOTH")) {

           } else {

           }
       } catch (Exception ex) {
           Assert.fail();
       }
       finally {
           loginPage.logout();
       }
    }

}
