package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class GarageSlabTest extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "billCycleGarageSlab", priority = 1)
    public void testBillCycleGarageSlab(Map<String, String> map) throws InterruptedException, ParseException, IOException {

        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        Thread.sleep(8000);
        HomePage homePage = new HomePage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        BillCyclePackageContractPage billCycle = new BillCyclePackageContractPage(getChromeDriver());
        GarageSlabPage garageSlabPage = new GarageSlabPage(getChromeDriver());
        homePage.navigateToSettings();
        Thread.sleep(20000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        Thread.sleep(10000);
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate(map.get("ContractName"));
        waitForCommandToFinish();
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        billCycle.clickOnBillCyclePackageSlab();
//        billCycle.updateB2BSlab(map.get("Duties"),map.get("CummulativeLevel"),map.get("BillingCycleFuelHikeCalculationType"),map.get("Autoprorate"),map.get("MaxDutyHourPerDuty")
//                ,map.get("SlabName"),map.get("FixedKm"),map.get("FixedHourDurationType"),map.get("FixedHour"),map.get("MinimumKmPerDay"),map.get("AbsentCut"),map.get("ExtraKmCost")
//                ,map.get("ExtraHourCost"),map.get("JumpCriteria"),map.get("ExtraDutyCost"),map.get("FixedCost"));
//        garageSlabPage.clickOnAddGarageSlab();
//        if (map.get("GarageKmIn").equals("FIXED")) {
//            garageSlabPage.updateGarageSlab(map.get("GarageKmInFlavour"), map.get("GarageKmInKm"), map.get("GarageKmOutFlavour"), map.get("GarageKmOutKm"), map.get("PerKmRate"), map.get("GarageKmDistributionLogic"));
//        } else if (map.get("GarageKmOut").equals("FIXED")) {
//            garageSlabPage.updateGarageSlab(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("ACPrice"), map.get("StartDate"));
//        } else if (map.get("GarageHourIn").equals("ADDITIONAL_PER_KM")) {
//            garageSlabPage.updateGarageSlab(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("ACPrice"), map.get("StartDate"));
//        }else {
//            garageSlabPage.updateGarageSlab(map.get("ACFlavour"), map.get("AcType"), map.get("AcTripType"), map.get("StartTime"), map.get("EndTime"), map.get("ACPriceType"), map.get("StartDate"));
//        }
//        contractPage.saveContract();
//        homePage.switchToDefaultFrame();
//        homePage.navigateToDashboard();
//        String contractAmount = BillCalculationUtils.calculationForTripPackageBasedSlabCost(map.get("FixedCost"),map.get("MaxKm"),map.get("Jumpcriteria"),"10", map.get("MaxKm"));
//        String expectedPrice = BillCalculationUtils.calculationForAc(map.get("ACPriceType"), map.get("ACPrice"), contractAmount);
//        if (map.get("ACFlavour").equals("NA")) {
//            return;
//        }
//        if (map.get("ACFlavour").equals("ALL")) {
//            if (map.get("ACPriceType").equals("FIXED")) {
//                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
//                String expectedPrice1 = BillCalculationUtils.acFixed(map.get("fixedCost"));
//                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
//            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
//                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
//                String expectedPrice1 = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
//                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
//            } else {
//                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
//                String expectedPrice1 = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
//                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
//            }
//        }
//        if (map.get("ACFlavour").equals("TRIP_ONLY")) {
//            if (map.get("ACPriceType").equals("FIXED")) {
//                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
//                String expectedPrice1 = BillCalculationUtils.acFixed(map.get("fixedCost"));
//                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
//            } else if (map.get("ACPriceType").equals("MULTIPLICATION_FACTOR")) {
//                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
//                String expectedPrice1 = BillCalculationUtils.acMultiplicationFactor(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
//                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
//            } else {
//                String actualPrice = BillCalculationUtils.apiACPrice(tripId);
//                String expectedPrice1 = BillCalculationUtils.acAdditionalPerKm(map.get("cost"), map.get("fixedCostForPerticularBillModel"));
//                BillComparisionUtil.compareBill(actualPrice,expectedPrice);
//            }
//        }
//        loginPage.logout();
    }

}
