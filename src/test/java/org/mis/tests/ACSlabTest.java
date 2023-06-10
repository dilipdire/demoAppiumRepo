package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;


public class ACSlabTest extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "tripACSlab", priority = 1)
    public void testACTripBased(Map<String, String> map) throws InterruptedException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        TripPackage tripPackage = new TripPackage(getChromeDriver());
        ACSlabPage acSlabPage = new ACSlabPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("contractName"));
        waitForCommandToFinish();
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"),
                map.get("GST%"), map.get("FuelType"), map.get("BillingContractType"),
                map.get("TripKmSchema"), map.get("Dutyparameter"));
        tripPackage.clickOnTripPackageSlab();
        tripPackage.updateTripPackageSlab(map.get("SlabName"), map.get("MaxKm"), map.get("FuelCost"),
                map.get("FixedCost"), map.get("Jumpcriteria"), map.get("Extrakmcost"), map.get("ACSurcharge"),
                map.get("AdditionalExtraKmcostAC"), map.get("EscortAC"), map.get("EscortNonAC"));
        acSlabPage.clickOnAddACSlab();
        if (map.get("ACPriceType").equals("FIXED")) {
            acSlabPage.updateAC(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("ACPrice"), map.get("StartDate"));
        } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
            acSlabPage.updateAC(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("ACPrice"), map.get("StartDate"));
        } else if (map.get("ACPriceType").equals("ADDITIONAL_PER_KM")) {
            acSlabPage.updateAC(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("ACPrice"), map.get("StartDate"));
        }else {
            acSlabPage.updateACForSlabRate(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("StartDate"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
        String tripId = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId);
        billingDashboardPage.selectContract(map.get("contractName"));
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        homePage.navigateToDashboard();
        ApiToGenerateBill.genetareBill();
        String contractAmount = BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("FixedCost"),map.get("MaxKm"),map.get("Jumpcriteria"),"10", map.get("MaxKm"));
        String expectedPrice = BillCalculationUtils.calculationForAc(map.get("ACPriceType"), map.get("ACPrice"), contractAmount);
//        String fixedCost = BillCalculationUtils.apiACPrice(tripId);
        if (map.get("ACFlavour").equals("NA")) {
            return;
        }
        if (map.get("ACFlavour").equals("ALL")) {
            if (map.get("ACPriceType").equals("FIXED")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice1 = BillCalculationUtils.acFixed(map.get("fixedCost"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice1 = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice1 = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            }
        }
        if (map.get("ACFlavour").equals("TRIP_ONLY")) {
            if (map.get("ACPriceType").equals("FIXED")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice1 = BillCalculationUtils.acFixed(map.get("fixedCost"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice1 = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice1 = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "EmployeeACSlab", priority = 2)
    public void testACEmployeeBased(Map<String, String> map) throws InterruptedException, ParseException, IOException {
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
        ContractEmployeeBasedPage contractEmployeeBasedPage = new ContractEmployeeBasedPage(getChromeDriver());
        EscortPage escortPage = new EscortPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        System.out.println(map.get("TestCaseId"));
        homePage.navigateToSettings();
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
        dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
        String tripId = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId);
        billingDashboardPage.selectContract(map.get("ContractName"));
        billingDashboardPage.addEmployee("mamta");
        String empCountForFirstTrip = billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        ApiToGenerateBill.genetareBill();
        String fixedCost = BillCalculationUtils.apiACPrice(tripId);
        if (map.get("ACFlavour").equals("NA")) {
            return;
        }
        if (map.get("ACFlavour").equals("ALL")) {
            if (map.get("ACPriceType").equals("FIXED")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acFixed(map.get("fixedCost"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            }
        }
        if (map.get("ACFlavour").equals("TRIP_ONLY")) {
            if (map.get("ACPriceType").equals("FIXED")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acFixed(map.get("fixedCost"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "ZoneACSlab", priority = 3)
    public void testZoneBasedLastFirstEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        EscortPage escortPage = new EscortPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        System.out.println(map.get("TestCaseId"));
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        zoneBasedPage.clickOnZoneBased();
        zoneBasedPage.updateZoneSlab(map.get("slabName"), map.get("zoneName"), map.get("FuelCost"), map.get("FixedCost"));
        escortPage.clickOnEscortSlab();
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
        dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
        String tripId = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId);
        billingDashboardPage.selectContract(map.get("ContractName"));
        billingDashboardPage.selectZone(map.get("zoneName"));
        billingDashboardPage.saveTripsheetInformation();
        String timeTenMinBefore = dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour = dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore, timeTenMinBeforeHour,"Adhoc - Logout");
        billingDashboardPage.selectZone(map.get("zoneName"));
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        String zoneFixedCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("fixedCost"));
        System.out.println(zoneFixedCost);
        ApiToGenerateBill.genetareBill();
        String fixedCost = BillCalculationUtils.apiContractPrice(tripId);
        if (map.get("ACFlavour").equals("NA")) {
            return;
        }
        if (map.get("ACFlavour").equals("ALL")) {
            if (map.get("ACPriceType").equals("FIXED")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acFixed(map.get("fixedCost"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            }
        }
        if (map.get("ACFlavour").equals("TRIP_ONLY")) {
            if (map.get("ACPriceType").equals("FIXED")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acFixed(map.get("fixedCost"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            } else {
                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
                String expectedPrice = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
            }
        }
        loginPage.logout();
    }



}
