package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.*;
import java.text.ParseException;
import java.util.Map;

public class EscortTest extends BaseTest {

    /*@Test(priority = 1)
    public void enableB2BConfigProperty() throws InterruptedException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
            HomePage homePage = new HomePage(getChromeDriver());
            BackToBackConfigurationPropertyPage b2bConfigPropertyPage = new BackToBackConfigurationPropertyPage(getChromeDriver());
            loginPage.login();
            waitForCommandToFinish();
            homePage.navigateToSiteAdministrator();
            b2bConfigPropertyPage.clickbackToBackConfigurationLink();
            Thread.sleep(5000);

        b2bConfigPropertyPage.enableMultipleOfficeB2B();
            b2bConfigPropertyPage.enableapplyB2bBetweenBillingTrips();
            b2bConfigPropertyPage.enableapplyB2BBetweenSelectedShiftsOnly();
            b2bConfigPropertyPage.addDeadLegKmsAbs();
        Thread.sleep(5000);
            b2bConfigPropertyPage.addminimumB2BTripLogoutInPercentage();
        Thread.sleep(5000);
            b2bConfigPropertyPage.addRoundJourneyTime();
            Thread.sleep(10000);
            b2bConfigPropertyPage.addTrafficBufferTime();
        Thread.sleep(5000);
            b2bConfigPropertyPage.addWaitingTime();
        Thread.sleep(5000);
            b2bConfigPropertyPage.saveB2BConfigProperty();

    }*/

   @Test(dataProviderClass = DataProviderMethod.class, dataProvider = "getdata", priority = 2)
    public void testEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
       //String FixedCost = BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("kmTravel"),map.get("costPerKm"),);
       //System.out.println(FixedCost);
       ApiToGenerateBill.genetareBill();
       String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
       String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
       if(map.get("EscortUsage").equals("ALL")){
           if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost2);
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
           else if(map.get("CostType").equals("FIXED")){
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
           else {
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortNa();
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortNa();
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
       }
       if(map.get("EscortUsage").equals("LOGOUT")){
           if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               BillComparisionUtil.compareBill(actualPrice2,"0");
           }
           else if(map.get("CostType").equals("FIXED")){
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               BillComparisionUtil.compareBill(actualPrice2,"0");
           }
           else {
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortNa();
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortNa();
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
       }
       if(map.get("EscortUsage").equals("LOGIN")){
           if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               BillComparisionUtil.compareBill(actualPrice1,"0");
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
           else if(map.get("CostType").equals("FIXED")){
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               BillComparisionUtil.compareBill(actualPrice1,"0");
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
           else {
               String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
               String expectedPrice1 = BillCalculationUtils.escortNa();
               BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
               String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
               String expectedPrice2 = BillCalculationUtils.escortNa();
               BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
           }
       }
       loginPage.logout();
   }




    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "TripPackageFirstLastEscortData", priority = 2)
    public void testEscortFirtLastTrip(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        //contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        tripPackage.clickOnTripPackageSlab();
        tripPackage.updateTripPackageSlab(map.get("SlabName"), map.get("MaxKm"), map.get("FuelCost"), map.get("FixedCost"), map.get("Jumpcriteria"), map.get("Extrakmcost"), map.get("ACSurcharge"),
                map.get("AdditionalExtraKmcost(AC)"), map.get("Escort(AC)"), map.get("Escort(Non-AC)"));
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
        String timeTenMinBefore =dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour =dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore,timeTenMinBeforeHour,"Adhoc - Logout");
        billingDashboardPage.saveTripsheetInformation();
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        ApiToGenerateBill.genetareBill();
        String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
        String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
        if(map.get("EscortUsage").equals("LAST_FIRST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LAST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("FIRST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        loginPage.logout();
    }



   /* @Test(dataProviderClass = DataProviderTest.class, dataProvider = "KmSlabEscortData", priority = 1)
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
        billingDashboardPage.addKm();
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
        billingDashboardPage.addKm();
        billingDashboardPage.auditDone();
        float output1 = ApiToGenerateBill.genetareBill(tripId1);
        float output2 = ApiToGenerateBill.genetareBill(tripId2);
        String expected1 = String.valueOf(output1);
        expected1 = expected1.replaceAll("\\.0*$", "");
        String expected2 = String.valueOf(output2);
        expected2 = expected2.replaceAll("\\.0*$", "");
        // Thread.sleep(20000);
        waitForCommandToFinish();
        String output = map.get("OutputResultFirstTrip").toString();
        output = output.replaceAll("\\.0*$", "");
        escortPage.compareBill(expected1,output);
        String Result = map.get("Result").toString();
        Result = Result.replaceAll("\\.0*$", "");
        escortPage.compareBill(expected2,Result);
        waitForCommandToFinish();
        loginPage.logout();
        waitForCommandToFinish();
   }
   @Test(dataProviderClass = DataProviderTest.class, dataProvider = "zoneBasedEscort", priority = 1)
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
       billingDashboardPage.auditDone();
       billingDashboardPage.navigateToNewBillingReport();
       float output1 = ApiToGenerateBill.genetareBill(tripId1);
       float output2 = ApiToGenerateBill.genetareBill(tripId2);
       String expected1 = String.valueOf(output1);
       expected1 = expected1.replaceAll("\\.0*$", "");
       String expected2 = String.valueOf(output2);
       expected2 = expected2.replaceAll("\\.0*$", "");
       waitForCommandToFinish();
       String output = map.get("OutputResultFirstTrip").toString();
       output = output.replaceAll("\\.0*$", "");
       escortPage.compareBill(expected1,output);
       String Result = map.get("Result").toString();
       Result = Result.replaceAll("\\.0*$", "");
       escortPage.compareBill(expected2,Result);
       waitForCommandToFinish();
       loginPage.logout();
       waitForCommandToFinish();

   }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabFirstLastTripEscortData", priority = 1)
    public void testKmSlabFirstLastTripEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        } else if (map.get("CostType").equals("NA")) {
            escortPage.updateEscortForSlabRate(map.get("EscortUsage"), map.get("CostType"), map.get("FuelCost"));
        }
        else {
            escortPage.updateEscort(map.get("EscortUsage"), map.get("CostType"), map.get("Cost"), map.get("FuelCost"));
        }
        Thread.sleep(10000);
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
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
        billingDashboardPage.addKm();
        String timeTenMinBefore =dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour =dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore,timeTenMinBeforeHour);
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm();
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        float output1 = ApiToGenerateBill.genetareBill(tripId1);
        float output2 = ApiToGenerateBill.genetareBill(tripId2);
        String expected1 = String.valueOf(output1);
        expected1 = expected1.replaceAll("\\.0*$", "");
        String expected2 = String.valueOf(output2);
        expected2 = expected2.replaceAll("\\.0*$", "");
        // Thread.sleep(20000);
        waitForCommandToFinish();
        String output = map.get("OutputResultFirstTrip").toString();
        output = output.replaceAll("\\.0*$", "");
        escortPage.compareBill(expected1,output);
        String Result = map.get("Result").toString();
        Result = Result.replaceAll("\\.0*$", "");
        escortPage.compareBill(expected2,Result);
        waitForCommandToFinish();
        loginPage.logout();
        waitForCommandToFinish();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "zoneBasedFirstLastEscortData", priority = 1)
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
        String tripId1 = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract(map.get("ContractName"));
        billingDashboardPage.selectZone(map.get("zoneName"));
        billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
        billingDashboardPage.saveTripsheetInformation();
        String timeTenMinBefore = dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour = dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore, timeTenMinBeforeHour);
        billingDashboardPage.selectZone(map.get("zoneName"));
        billingDashboardPage.saveTripsheetInformation();
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        billingDashboardPage.navigateToNewBillingReport();
        float output1 = ApiToGenerateBill.genetareBill(tripId1);
        float output2 = ApiToGenerateBill.genetareBill(tripId2);
        String expected1 = String.valueOf(output1);
        expected1 = expected1.replaceAll("\\.0*$", "");
        String expected2 = String.valueOf(output2);
        expected2 = expected2.replaceAll("\\.0*$", "");
        waitForCommandToFinish();
        String output = map.get("OutputResultFirstTrip").toString();
        output = output.replaceAll("\\.0*$", "");
        escortPage.compareBill(expected1, output);
        String Result = map.get("Result").toString();
        Result = Result.replaceAll("\\.0*$", "");
        escortPage.compareBill(expected2, Result);
        waitForCommandToFinish();
        loginPage.logout();
        waitForCommandToFinish();

    }*/

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "KmSlabEscortData", priority = 1)
    public void testKmSlabEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
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
        String kmFixedCost = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
        System.out.println(kmFixedCost);
        ApiToGenerateBill.genetareBill();
        String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
        String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
        if(map.get("EscortUsage").equals("ALL")){
                if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                    BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                    BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
                }
                else if(map.get("CostType").equals("FIXED")){
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                    BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                    BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
                }
                else {
                    String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                    String expectedPrice1 = BillCalculationUtils.escortNa();
                    BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                    String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                    String expectedPrice2 = BillCalculationUtils.escortNa();
                    BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
                }
        }
        if(map.get("EscortUsage").equals("LOGOUT")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LOGIN")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabFirstLastTripEscortData", priority = 1)
    public void testKmSlabFirstLastTripEscort(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        String timeTenMinBefore =dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour =dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore,timeTenMinBeforeHour,"Adhoc - Logout");
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm(map.get("kmTravel"));
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        String kmFixedCost = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
        System.out.println(kmFixedCost);
        ApiToGenerateBill.genetareBill();
        String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
        String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
        if(map.get("EscortUsage").equals("LAST_FIRST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LAST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("FIRST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabFirstLastTripEscortData", priority = 1)
    public void testKmSlabIgnoreB2B(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        escortPage.clickOnEscortSlabKm();
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
        dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
        String tripId1 = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract(map.get("ContractName"));
        billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm(map.get("kmTravel"));
        String timeTenMinBefore =dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour =dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore,timeTenMinBeforeHour,"Adhoc - Logout");
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm(map.get("kmTravel"));
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        String kmFixedCost = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
        System.out.println(kmFixedCost);
        ApiToGenerateBill.genetareBill();
        String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
        BillComparisionUtil.compareBill(actualPrice1,"0");
        String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
        BillComparisionUtil.compareBill(actualPrice2,"0");
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "zoneBasedEscort", priority = 1)
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
        billingDashboardPage.auditDone();
        String zoneFixedCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("fixedCost"));
        System.out.println(zoneFixedCost);
        ApiToGenerateBill.genetareBill();
        String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
        String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
        if(map.get("EscortUsage").equals("ALL")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost2);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LOGOUT")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LOGIN")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "zoneBasedFirstLastEscortData", priority = 1)
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
        String tripId1 = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract(map.get("ContractName"));
        billingDashboardPage.selectZone(map.get("zoneName"));
        billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
        billingDashboardPage.saveTripsheetInformation();
        String timeTenMinBefore = dateAndTime.tenMinBeforeMin();
        String timeTenMinBeforeHour = dateAndTime.tenMinBeforeHour();
        billingDashboardPage.addMultipleTripInOneDuty(timeTenMinBefore, timeTenMinBeforeHour,"Adhoc - Logout");
        billingDashboardPage.selectZone(map.get("zoneName"));
        billingDashboardPage.saveTripsheetInformation();
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        String zoneFixedCost = BillCalculationUtils.calculationForZoneBasedSlabCost(map.get("fixedCost"));
        System.out.println(zoneFixedCost);
        ApiToGenerateBill.genetareBill();
        String fixedCost1 = BillCalculationUtils.apiContractPrice(tripId1);
        String fixedCost2 = BillCalculationUtils.apiContractPrice(tripId2);
        if(map.get("EscortUsage").equals("LAST_FIRST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost2);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LAST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("FIRST_TRIP")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),fixedCost1);
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "EmployeeBasedEscortData", priority = 1)
    public void testEmployeeBased(Map<String, String> map) throws InterruptedException, ParseException, IOException {
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
        String tripId1 = dashboardPage.saveTripsheet(hour2, min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract(map.get("ContractName"));
        billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
        billingDashboardPage.addEmployee("mamta");
        String empCountForFirstTrip = billingDashboardPage.saveTripsheetInformation();
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
        billingDashboardPage.addEmployee("mamta");
        String empCount = billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        //String kmFixedCost = BillCalculationUtils.calculationForEmployeeBasedCost(empCount,map.get("PerHeadCost"));
        //System.out.println(kmFixedCost);
        ApiToGenerateBill.genetareBill();
        //String fixedCost1 = BillCalculationUtils.apiEscortPrice(tripId1);
        //String fixedCost2 = BillCalculationUtils.apiEscortPrice(tripId2);
        if(map.get("EscortUsage").equals("ALL")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),map.get("PerHeadCost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),map.get("PerHeadCost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LOGOUT")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),map.get("PerHeadCost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                BillComparisionUtil.compareBill(actualPrice2,"0");
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        if(map.get("EscortUsage").equals("LOGIN")){
            if(map.get("CostType").equals("MULTIPLICATION_FACTOR")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortMultiplicationFactor(map.get("Cost"),map.get("PerHeadCost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else if(map.get("CostType").equals("FIXED")){
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                BillComparisionUtil.compareBill(actualPrice1,"0");
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortFixed(map.get("Cost"));
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
            else {
                String actualPrice1 = BillCalculationUtils.apiEscortPrice(tripId1);
                String expectedPrice1 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice1,expectedPrice1);
                String actualPrice2 = BillCalculationUtils.apiEscortPrice(tripId2);
                String expectedPrice2 = BillCalculationUtils.escortNa();
                BillComparisionUtil.compareBill(actualPrice2,expectedPrice2);
            }
        }
        loginPage.logout();
    }



}

