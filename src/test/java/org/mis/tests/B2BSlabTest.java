package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public class B2BSlabTest extends BaseTest{

    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "kmSlabB2BData", priority = 1)
    public void b2BKmSlab(Map<String, String> map) throws InterruptedException, ParseException, IOException {

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
        B2BSlabPage b2BSlabPage = new B2BSlabPage(getChromeDriver());
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
        b2BSlabPage.clickOnB2BSlab();
        b2BSlabPage.updateB2BSlab(map.get("b2bType"), map.get("distributionLogic"), map.get("fixedFactor"),map.get("loginCost"),map.get("logoutCost"));
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
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKm(map.get("kmTravel"));
        String timeTenMinBefore =dateAndTime.tenMinAfterMin();
        String timeTenMinBeforeHour =dateAndTime.tenMinAfterHour();
        billingDashboardPage.b2BTrip(timeTenMinBefore,timeTenMinBeforeHour,"Adhoc - Login",map.get("empForB2BTrip"),map.get("ContractName"),map.get("zone"),map.get("BillingModel"),map.get("EscortUsage"),map.get("escortName"));
        billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.addKmForB2bLastTrip(map.get("kmTravel"));
        String tripId2 = billingDashboardPage.getMultipleTripId();
        billingDashboardPage.auditDone();
        ApiToGenerateBill.genetareBill();
        String contractAmount= BillCalculationUtils.calculationForKmSlabCost(map.get("kmTravel"),map.get("costPerKm"));
        if(map.get("distributionLogic").equals("NA")){
            String expectedPriceLogoutTrip = BillCalculationUtils.b2BSlab(map.get("b2bType"),map.get("fixedFactor"),map.get("distributionLogic"),contractAmount,contractAmount,map.get("Coefficient"),map.get("constant"));
            String actualPrice1 = BillCalculationUtils.apiContractPrice(tripId1);
            BillComparisionUtil.compareBill(actualPrice1, expectedPriceLogoutTrip);
            String actualPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
            BillComparisionUtil.compareBill(actualPrice2, expectedPriceLogoutTrip);
        }
        if(map.get("distributionLogic").equals("EQUAL")){
            String expectedPriceLogoutTrip = BillCalculationUtils.b2BSlab(map.get("b2bType"),map.get("fixedFactor"),map.get("distributionLogic"),contractAmount,contractAmount,map.get("Coefficient"),map.get("constant"));
            String actualPrice1 = BillCalculationUtils.apiContractPrice(tripId1);
            BillComparisionUtil.compareBill(actualPrice1, expectedPriceLogoutTrip);
            String actualPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
            BillComparisionUtil.compareBill(actualPrice2, expectedPriceLogoutTrip);
        }
         else if(map.get("distributionLogic").equals("ONLY_LOGIN")){
            String expectedPriceLogoutTrip = BillCalculationUtils.b2BSlab(map.get("b2bType"),map.get("fixedFactor"),map.get("distributionLogic"),contractAmount,contractAmount,map.get("Coefficient"),map.get("constant"));
            String actualPrice1 = BillCalculationUtils.apiContractPrice(tripId1);
            BillComparisionUtil.compareBill(actualPrice1, "0");
            String actualPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
            BillComparisionUtil.compareBill(actualPrice2, expectedPriceLogoutTrip);
        }
        else if(map.get("distributionLogic").equals("ONLY_LOGOUT")){
            String expectedPriceLogoutTrip = BillCalculationUtils.b2BSlab(map.get("b2bType"),map.get("fixedFactor"),map.get("distributionLogic"),contractAmount,contractAmount,map.get("Coefficient"),map.get("constant"));
            String actualPrice1 = BillCalculationUtils.apiContractPrice(tripId1);
            BillComparisionUtil.compareBill(actualPrice1, expectedPriceLogoutTrip);
            String actualPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
            BillComparisionUtil.compareBill(actualPrice2, "0");
        }
        else if(map.get("b2bType").equals("CONFIGURABLE_FIXED")){
            String actualPrice1 = BillCalculationUtils.apiContractPrice(tripId1);
            BillComparisionUtil.compareBill(actualPrice1, map.get("logoutCost"));
            String actualPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
            BillComparisionUtil.compareBill(actualPrice2, map.get("loginCost"));
        }
        else if(map.get("b2bType").equals("CONFIGURABLE_PERCENT")){
            int ContractPrice=Integer.parseInt(contractAmount);
            int loginFactor=Integer.parseInt(map.get("loginCost"));
            int logoutFactor=Integer.parseInt(map.get("logoutCost"));
            ContractPrice=ContractPrice + ContractPrice;
            int b2bCost = ContractPrice*loginFactor/100;
            String expectedCostLoginTrip=Integer.toString(b2bCost);
            int b2bCostLogout = ContractPrice*logoutFactor/100;
            String expectedCostLogoutTrip=Integer.toString(b2bCostLogout);
            String actualPrice1 = BillCalculationUtils.apiContractPrice(tripId1);
            BillComparisionUtil.compareBill(actualPrice1, expectedCostLogoutTrip);
            String actualPrice2 = BillCalculationUtils.apiContractPrice(tripId2);
            BillComparisionUtil.compareBill(actualPrice2, expectedCostLoginTrip);
        }
        //BillCalculationUtils.b2BSlab(map.get("b2bType"),map.get("fixedFactor"),map.get("distributionLogic"),"200","200");
        loginPage.logout();
    }

}
