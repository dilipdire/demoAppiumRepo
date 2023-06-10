package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class EmployeeAdjustmentSlabTest extends BaseTest{
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabEmployeeAdjustmentData", priority = 1)
    public void testKmSlabEmpAdjustment(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        EmployeeAdjustmentSlabPage empAdjustmentPage = new EmployeeAdjustmentSlabPage(getChromeDriver());
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
        empAdjustmentPage.clickOnEmpAdjustmentSlab();
        empAdjustmentPage.updateEmpAdjustSlab(map.get("tripType"),map.get("empType"),map.get("calculationType"),map.get("adjustmentValue"));
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        if(map.get("tripType").equals("LOGOUT")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            //String empCount = billingDashboardPage.saveTripsheetInformation();
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedResult = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab,map.get("empCount"),empCount);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }
        else if(map.get("tripType").equals("LOGIN")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedResult = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab,map.get("empCount"),empCount);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }
        else if(map.get("tripType").equals("BOTH")||map.get("tripType").equals("NA")){
                dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
                String hour2 = dateAndTime.hour24formate();
                String min = dateAndTime.minTwoHourLater();
                dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
                dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
                String tripId = dashboardPage.saveTripsheet(hour2, min);
                dashboardPage.navigateToBillingDashboard();
                billingDashboardPage.performTrip(tripId);
                billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
                String empCount1 = billingDashboardPage.saveTripsheetInformation();
                billingDashboardPage.addKm(map.get("kmTravel"));
                billingDashboardPage.auditDone();
                homePage.navigateToDashboard();
            dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("SearchEmpName"));
            String hour3 = dateAndTime.hour24formate();
            String min1 = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min1, hour3);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId2 = dashboardPage.saveTripsheet(hour3, min1);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId2);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount2 = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            if(map.get("tripType").equals("BOTH")) {
                String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                String expectedResult = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractPriceForSlab,map.get("empCount"),empCount1);
                BillComparisionUtil.compareBill(expectedResult, contractPrice);
                String contractPrice2 = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab2 = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                String expectedResult2 = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractPriceForSlab2,map.get("empCount"),empCount2);
                BillComparisionUtil.compareBill(expectedResult2, contractPrice2);
            }
            else{
                String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                BillComparisionUtil.compareBill(contractPriceForSlab, contractPrice);
                String contractPrice2 = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab2 = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                BillComparisionUtil.compareBill(contractPriceForSlab2, contractPrice2);
                }
            }
        loginPage.logout();
        }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "tripEmpAdjSlab", priority = 2)
    public void testEmpAdjTripBased(Map<String, String> map) throws InterruptedException, ParseException, IOException {
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
//        tripPackage.clickOnContractsSlab();
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
        if(map.get("tripType").equals("LOGOUT")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            //String empCount = billingDashboardPage.saveTripsheetInformation();
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedResult = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab,map.get("empCount"),empCount);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }
        else if(map.get("tripType").equals("LOGIN")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedResult = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab,map.get("empCount"),empCount);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }
        else if(map.get("tripType").equals("BOTH")||map.get("tripType").equals("NA")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount1 = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            homePage.navigateToDashboard();
            dashboardPage.searchEmployeeInDasboardForLoginTrip(map.get("SearchEmpName"));
            String hour3 = dateAndTime.hour24formate();
            String min1 = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min1, hour3);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId2 = dashboardPage.saveTripsheet(hour3, min1);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId2);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("empType").equals("PLANNED")||map.get("empType").equals("TRAVELLED")){
                billingDashboardPage.addEmployee("mamta");
                billingDashboardPage.makeATripNoShowTrip();
            }else{

            }
            Thread.sleep(5000);
            String empCount2 = billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            if(map.get("tripType").equals("BOTH")) {
                String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                String expectedResult = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractPriceForSlab,map.get("empCount"),empCount1);
                BillComparisionUtil.compareBill(expectedResult, contractPrice);
                String contractPrice2 = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab2 = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                String expectedResult2 = BillCalculationUtils.employeeAdjustmentSlab(map.get("empType"),map.get("calculationType"), map.get("adjustmentValue"), map.get("kmTravel"), map.get("costPerKm"), contractPriceForSlab2,map.get("empCount"),empCount2);
                BillComparisionUtil.compareBill(expectedResult2, contractPrice2);
            }
            else{
                String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                BillComparisionUtil.compareBill(contractPriceForSlab, contractPrice);
                String contractPrice2 = BillCalculationUtils.apiContractPrice(tripId);
                String contractPriceForSlab2 = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"), map.get("costPerKm"));
                BillComparisionUtil.compareBill(contractPriceForSlab2, contractPrice2);
            }
        }
        loginPage.logout();
    }


    }



