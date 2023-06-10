package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class Escort extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "tripPackageEscortData", priority = 1)
    public void testTripPackageEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        TripPackage tripPackage = new TripPackage(getChromeDriver());
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
        tripPackage.clickOnTripPackageSlab();
        tripPackage.updateTripPackageSlab(map.get("SlabName"), map.get("MaxKm"), map.get("FuelCost"), map.get("FixedCost"), map.get("Jumpcriteria"), map.get("Extrakmcost"), map.get("ACSurcharge"),
                map.get("AdditionalExtraKmcost(AC)"), map.get("Escort(AC)"), map.get("Escort(Non-AC)"));
        escortPage.clickOnAddEscortSlab();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        }
        else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if(map.get("EscortUsage").equals("ALL")||map.get("EscortUsage").equals("LOGIN")||map.get("EscortUsage").equals("LOGOUT")) {
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
            billingDashboardPage.auditDone();
            homePage.navigateToDashboard();
            waitForCommandToFinish();
            dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("SearchEmpName"));
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
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractAmount= BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("FixedCost"),map.get("MaxKm"),map.get("Jumpcriteria"),"10",map.get("MaxKm"));
            String expectedPrice = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractAmount);
            //String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
            //String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
            if (map.get("EscortUsage").equals("ALL")) {
                if(map.get("CostType").equals("SLAB_RATE")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, map.get("Escort(Non-AC)"));
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, map.get("Escort(Non-AC)"));
                }else {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, expectedPrice);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                }
            }
            if (map.get("EscortUsage").equals("LOGOUT")) {
                if(map.get("CostType").equals("SLAB_RATE")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, map.get("Escort(Non-AC)"));
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, "0");
                }else {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, expectedPrice);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, "0");
                }
            }
            if (map.get("EscortUsage").equals("LOGIN")) {
                if(map.get("CostType").equals("SLAB_RATE")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, "0");
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, map.get("Escort(Non-AC)"));
                }else {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, "0");
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                }

            }
        }else{
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
            String contractAmount= BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("FixedCost"),map.get("MaxKm"),map.get("Jumpcriteria"),"10",map.get("MaxKm"));
            String expectedPrice = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractAmount);
            if(map.get("EscortUsage").equals("FIRST_TRIP")){
                if(map.get("CostType").equals("SLAB_RATE")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, map.get("Escort(Non-AC)"));
                }else {
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                }
            }else if(map.get("EscortUsage").equals("LAST_TRIP")){
                if(map.get("CostType").equals("SLAB_RATE")){
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, map.get("Escort(Non-AC)"));
                }else {
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                }
            }else if(map.get("EscortUsage").equals("FIRST_LAST_TRIP")){
                if(map.get("CostType").equals("SLAB_RATE")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, map.get("Escort(Non-AC)"));
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, map.get("Escort(Non-AC)"));
                }else {
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                    String actualPrice3 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice3, expectedPrice);
                }
            }else if(map.get("EscortUsage").equals("IGNORE_B2B")){
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice2, "0");
                String actualPrice3 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice3, "0");
            }else{

            }
        }
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "KmSlabEscortData", priority = 2)
    public void testKmSlabEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        kmSlabPage.selectMileageAndDistributionLogic(map.get("Mileage"),map.get("DistributionLogic"));
        kmSlabPage.updateKmSlab(map.get("slabName"),map.get("maxKm"),map.get("costPerKm"),map.get("minCost"));
        escortPage.clickOnEscortSlabKm();
        if (map.get("CostType").equals("SLAB_RATE")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        } else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if(map.get("EscortUsage").equals("ALL")||map.get("EscortUsage").equals("LOGIN")||map.get("EscortUsage").equals("LOGOUT")) {
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
            dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("SearchEmpName"));
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
            String contractAmount= BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedPrice = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractAmount);
            //String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
            //String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
            if (map.get("EscortUsage").equals("ALL")) {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, expectedPrice);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }
            if (map.get("EscortUsage").equals("LOGOUT")) {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, expectedPrice);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, "0");
            }
            if (map.get("EscortUsage").equals("LOGIN")) {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice1, "0");
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }
        }else{
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
            billingDashboardPage.addKmForB2bLastTrip(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractAmount= BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedPrice = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractAmount);
            if(map.get("EscortUsage").equals("FIRST_TRIP")){
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }else if(map.get("EscortUsage").equals("LAST_TRIP")){
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }else if(map.get("EscortUsage").equals("FIRST_LAST_TRIP")){
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                    BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                    String actualPrice3 = BillCalculationUtils.apiEscortPrice(tripId2);
                    BillComparisionUtil.compareBill(actualPrice3, expectedPrice);

            }else if(map.get("EscortUsage").equals("IGNORE_B2B")){
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice2, "0");
                String actualPrice3 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice3, "0");
            }else{

            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "zoneBasedEscort", priority = 3)
    public void testZoneBasedEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        }
        else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        if(map.get("EscortUsage").equals("ALL")||map.get("EscortUsage").equals("LOGIN")||map.get("EscortUsage").equals("LOGOUT")) {
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId1 = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId1);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("zoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            homePage.navigateToDashboard();
            waitForCommandToFinish();
            dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("SearchEmpName"));
            String hour3 = dateAndTime.hour24formate();
            String min1 = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min1, hour3);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId2 = dashboardPage.saveTripsheet(hour3, min1);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId2);
            billingDashboardPage.selectContract(map.get("ContractName"));
            billingDashboardPage.selectZone(map.get("zoneName"));
            billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractAmount= BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
            String expectedPrice = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractAmount);
            //String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
            //String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
            if (map.get("EscortUsage").equals("ALL")) {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1, expectedPrice);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }
            if (map.get("EscortUsage").equals("LOGOUT")) {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1, expectedPrice);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2, "0");
            }
            if (map.get("EscortUsage").equals("LOGIN")) {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1, "0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }
        }else{
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
            billingDashboardPage.b2BTrip(timeTenMinBefore, timeTenMinBeforeHour, "Adhoc - Logout", map.get("empForB2BTrip"), map.get("ContractName"), map.get("zoneName"), map.get("BillingModel"),map.get("EscortUsage"),map.get("EscortNameSearch"));
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKmForB2bLastTrip(map.get("kmTravel"));
            String tripId2 = billingDashboardPage.getMultipleTripId();
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractAmount= BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("FixedCost"));
            String expectedPrice = BillCalculationUtils.calculationForEscort(map.get("CostType"), map.get("Cost"), contractAmount);
            if(map.get("EscortUsage").equals("FIRST_TRIP")){
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }else if(map.get("EscortUsage").equals("LAST_TRIP")){
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
            }else if(map.get("EscortUsage").equals("FIRST_LAST_TRIP")){
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice2, expectedPrice);
                String actualPrice3 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice3, expectedPrice);

            }else if(map.get("EscortUsage").equals("IGNORE_B2B")){
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice2, "0");
                String actualPrice3 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice3, "0");
            }else{

            }
        }
        loginPage.logout();
    }



    }