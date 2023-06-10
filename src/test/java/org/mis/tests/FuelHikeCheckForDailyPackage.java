package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class FuelHikeCheckForDailyPackage extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "fuelHikeDailyPackageData", priority = 1)
    public void fuelHikeDailyPackage(Map<String, String> map) throws Exception {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        BillingDashboardPage billingDashboardPage = new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        HomePage homePage = new HomePage(getChromeDriver());
        DailyPackage dailyPackage = new DailyPackage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        FuelHikePage fuelHikePage=new FuelHikePage(getChromeDriver());
        FuelTypePage fuelTypePage = new FuelTypePage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        Thread.sleep(5000);
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        fuelTypePage.takeBaseFuelPriceOfFuel(map.get("BaseFuelPrice"));
        String date=dateAndTime.getCurrentDate();
        fuelHikePage.updateFuelPrice(map.get("FuelPrice"),date);
        homePage.switchToDefaultFrame();
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
        //Thread.sleep(50000);
        dailyPackage.updateDlyPkg(map.get("MaxDutyHourPerDuty"),map.get("DutyStartTime"),map.get("DutyEndTime"),map.get("FuelHikeCalculationType"));
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
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
        billingDashboardPage.addKm(map.get("kmTravel"));
        billingDashboardPage.auditDone();
        ApiToGenerateBill.genetareBill();
        String contractAmount = BillCalculationUtils.dailyPackageSlab(map.get("FixedCost"), "10", map.get("FixedKm"), map.get("ExtraKmCost"), map.get("ExtraHourCost"), map.get("Dutyparameter"), map.get("DistributionLogic"));
        String fuelHikeAmount = BillCalculationUtils.fuelHikeCalculation(map.get("BaseFuelPrice"),map.get("FuelPrice"),map.get("Mileage"),map.get("kmTravel"),map.get("FixedKm"),map.get("FuelHikeCalculationType"));
        System.out.println(fuelHikeAmount);
        String TotalCost = BillCalculationUtils.fuelHikeTotalcal(contractAmount,fuelHikeAmount);
        String actualTotal = BillCalculationUtils.apiGrandTotal(tripId1);
        BillComparisionUtil.compareBill(TotalCost, actualTotal);
        loginPage.logout();
    }
}
