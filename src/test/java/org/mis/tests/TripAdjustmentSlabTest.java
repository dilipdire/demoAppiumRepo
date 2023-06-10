package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class TripAdjustmentSlabTest extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabTripAdjustmentData", priority = 1)
    public void testKmSlabTripAdjustment(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        TripAdjustmentPage tripAdjustmentPage = new TripAdjustmentPage(getChromeDriver());
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
        tripAdjustmentPage.clickOnTripAdjustmentSlab();
        tripAdjustmentPage.updateTripAdjustSlab(map.get("tripType"),map.get("calculationType"),map.get("serviceType"),map.get("adjustmentValue"));
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        if(map.get("tripType").equals("LOGOUT")||map.get("tripType").equals("ESCORT_LOGOUT")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("tripType").equals("ESCORT_LOGOUT")){
                billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            }else{

            }
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedResult = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"),map.get("serviceType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }
        else if(map.get("tripType").equals("LOGIN")||map.get("tripType").equals("ESCORT_LOGIN")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("tripType").equals("ESCORT_LOGIN")){
                billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            }else{

            }
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
            String expectedResult = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"),map.get("serviceType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }else{

        }
        loginPage.logout();
    }

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "tripPackageTripAdjustmentData", priority = 2)
    public void testTripPackageTripAdjustment(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        TripPackage tripPackagePage = new TripPackage(getChromeDriver());
        TripAdjustmentPage tripAdjustmentPage = new TripAdjustmentPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        System.out.println(map.get("TestCaseId"));
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        tripPackagePage.clickOnTripPackageSlab();
        tripPackagePage.updateTripPackageSlab(map.get("SlabName"),map.get("MaxKm"),map.get("FuelCost"),map.get("FixedCost"),map.get("Jumpcriteria"),map.get("Extrakmcost"),
                map.get("ACSurcharge"),map.get("AdditionalExtraKmcost(AC)"),map.get("Escort(AC)"),map.get("Escort(Non-AC)"));
        tripAdjustmentPage.clickOnTripAdjustmentSlab();
        tripAdjustmentPage.updateTripAdjustSlab(map.get("tripType"),map.get("calculationType"),map.get("serviceType"),map.get("adjustmentValue"));
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        if(map.get("tripType").equals("LOGOUT")||map.get("tripType").equals("ESCORT_LOGOUT")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("tripType").equals("ESCORT_LOGOUT")){
                billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            }else{

            }
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("SlabName"),map.get("MaxKm"),map.get("Jumpcriteria"),map.get("kmTravel"),map.get("MAxKm2"));
            String expectedResult = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"),map.get("serviceType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }
        else if(map.get("tripType").equals("LOGIN")||map.get("tripType").equals("ESCORT_LOGIN")){
            dashboardPage.searchEmployeeInDasboard(map.get("SearchEmpName"), map.get("Name"));
            String hour2 = dateAndTime.hour24formate();
            String min = dateAndTime.minTwoHourLater();
            dashboardPage.createNonShiftTripsheetForLoginTrip(min, hour2);
            dashboardPage.assignCabToNewTripsheet(map.get("Cab"));
            String tripId = dashboardPage.saveTripsheet(hour2, min);
            dashboardPage.navigateToBillingDashboard();
            billingDashboardPage.performTrip(tripId);
            billingDashboardPage.selectContract(map.get("ContractName"));
            if(map.get("tripType").equals("ESCORT_LOGIN")){
                billingDashboardPage.addEmployee(map.get("EscortNameSearch"));
            }else{

            }
            billingDashboardPage.saveTripsheetInformation();
            billingDashboardPage.addKm(map.get("kmTravel"));
            billingDashboardPage.auditDone();
            ApiToGenerateBill.genetareBill();
            String contractPrice = BillCalculationUtils.apiContractPrice(tripId);
            String contractPriceForSlab = BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("SlabName"),map.get("MaxKm"),map.get("Jumpcriteria"),map.get("kmTravel"),map.get("MAxKm2"));
            String expectedResult = BillCalculationUtils.tripAdjustmentSlab(map.get("calculationType"),map.get("serviceType"),map.get("adjustmentValue"),map.get("kmTravel"),map.get("costPerKm"),contractPriceForSlab);
            BillComparisionUtil.compareBill(expectedResult,contractPrice);
        }else{

        }
        loginPage.logout();
    }

}
