package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Map;

public class ContractTest extends BaseTest{

    @Test(priority = 1)
    public void testForSlabToBeVisibleInContract() throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickOnAddButton();
        contractPage.selectBillingModel("ZONE_BASED");
        zoneBasedPage.checkZoneSlabIsDisable();
        Thread.sleep(10000);
        homePage.switchToDefaultFrame();
        loginPage.logout();

    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "ContractDataForValidation", priority = 2)
    public void contractNameValidation(Map<String, String> map) throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        System.out.println(map.get("TestCaseName"));
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickOnAddButton();
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        contractPage.saveContract();
        String actualMsg = contractPage.validation();
        String expectedMsg = "Contract Name is missing";
        BillComparisionUtil.validation(actualMsg,expectedMsg);
        Thread.sleep(10000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "ContractDataForValidation", priority = 3)
    public void shortCodeValidation(Map<String, String> map) throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        System.out.println(map.get("TestCaseName"));
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickOnAddButton();
        String contractName= GenerateRandomString.generateRandomString(6);
        String shortCode=GenerateRandomString.generateRandomString(4);
        contractPage.addNameAndShortCode(contractName,"");
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        contractPage.saveContract();
        String actualMsg = contractPage.validation();
        String expectedMsg = "Short Code is missing";
        BillComparisionUtil.validation(actualMsg,expectedMsg);
        Thread.sleep(10000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "ContractDataForValidation", priority = 4)
    public void contractTypeValidation(Map<String, String> map) throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        System.out.println(map.get("TestCaseName"));
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickOnAddButton();
        String contractName= GenerateRandomString.generateRandomString(6);
        String shortCode=GenerateRandomString.generateRandomString(4);
        contractPage.addNameAndShortCode(contractName,shortCode);
        contractPage.updateMandatoryFieldOnContract("", map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        contractPage.saveContract();
        String actualMsg = contractPage.validation();
        String expectedMsg = "Contract Type is missing";
        BillComparisionUtil.validation(actualMsg,expectedMsg);
        Thread.sleep(10000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "ContractDataForValidation", priority = 5)
    public void slabEmptyValidation(Map<String, String> map) throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        System.out.println(map.get("TestCaseName"));
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickOnAddButton();
        String contractName= GenerateRandomString.generateRandomString(6);
        String shortCode=GenerateRandomString.generateRandomString(4);
        contractPage.addNameAndShortCode(contractName,shortCode);
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        contractPage.saveContract();
        String actualMsg = contractPage.validation();
        String expectedMsg="";
        if(map.get("BillingModel").equals("ZONE_BASED")) {
             expectedMsg = "Zone Package Slabs are Empty";
        }else if(map.get("BillingModel").equals("DAILY_PACKAGE")) {
            expectedMsg = "Daily Package Slabs are Empty";
        } else if(map.get("BillingModel").equals("BILL_CYCLE_PACKAGE")) {
            expectedMsg = "Billing Package Slabs are Empty";
        }else if(map.get("BillingModel").equals("TRIP_PACKAGE_BASED")) {
            expectedMsg = "Trip Package Slabs are Empty";
        }else if(map.get("BillingModel").equals("KM_SLAB")) {
            expectedMsg = "KM Package Slabs are Empty";
        }else if(map.get("BillingModel").equals("ZONE_VENDOR_BASED")) {
            expectedMsg = "Zone Vendor Slabs are Empty";
        }else if(map.get("BillingModel").equals("EMPLOYEE_BASED")) {
            expectedMsg = "Employee Package Slabs are Empty";
        }
        BillComparisionUtil.validation(actualMsg,expectedMsg);
        Thread.sleep(10000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }
    @Test(dataProviderClass = DataProviderTest.class, dataProvider = "ContractData", priority = 6)
    public void addContract(Map<String, String> map) throws InterruptedException, ParseException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        System.out.println(map.get("TestCaseName"));
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased kmSlabPage = new ContractKmBased(getChromeDriver());
        ContractZoneBasedPage zoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
        ContractPage contractPage = new ContractPage(getChromeDriver());
        homePage.navigateToSettings();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickOnAddButton();
        String contractName= GenerateRandomString.generateRandomString(6);
        String shortCode=GenerateRandomString.generateRandomString(4);
        contractPage.addNameAndShortCode(contractName,shortCode);
        contractPage.updateMandatoryFieldOnContract(map.get("BillingModel"), map.get("CabType"), map.get("GST%"), map.get("FuelType"), map.get("BillingContractType")
                , map.get("TripKmSchema"), map.get("Dutyparameter"));
        if(map.get("BillingModel").equals("ZONE_BASED")) {
            ContractZoneBasedPage contractZoneBasedPage = new ContractZoneBasedPage(getChromeDriver());
            contractZoneBasedPage.clickOnZoneBasedToAddContract();
            contractZoneBasedPage.updateZoneSlab("s1", "Testing1", "10", "50");
        }else if(map.get("BillingModel").equals("DAILY_PACKAGE")) {
            DailyPackage dailyPackage = new DailyPackage(getChromeDriver());
            dailyPackage.selectDistributionLogic(map.get("DistributionLogic"),map.get("Mileage"));
            dailyPackage.clickOnDailyPackageSlabToAddContract();
            dailyPackage.updateDailyPackageSlab(map.get("SlabName"), map.get("FixedKm"), map.get("FixedHour"), map.get("FixedCost"), map.get("Jumpcriteria"), map.get("ExtraKmCost"), map.get("ExtraHourCost"));
        } else if(map.get("BillingModel").equals("BILL_CYCLE_PACKAGE")) {
            BillCyclePackageContractPage billCycle = new BillCyclePackageContractPage(getChromeDriver());
            billCycle.clickOnBillCyclePackageSlabToAddContract();
            billCycle.updateBillCyclePackageSlab(map.get("Duties"),map.get("CummulativeLevel"),map.get("BillingCycleFuelHikeCalculationType"),map.get("Autoprorate"),map.get("MaxDutyHourPerDuty")
                    ,map.get("SlabName"),map.get("FixedKm"),map.get("FixedHourDurationType"),map.get("FixedHour"),map.get("MinimumKmPerDay"),map.get("AbsentCut"),map.get("ExtraKmCost")
                    ,map.get("ExtraHourCost"),map.get("JumpCriteria"),map.get("ExtraDutyCost"),map.get("FixedCost"));
        }else if(map.get("BillingModel").equals("TRIP_PACKAGE_BASED")) {
            TripPackage tripPackage = new TripPackage(getChromeDriver());
            tripPackage.clickOnTripPackageSlabForAddContract();
            tripPackage.updateTripPackageSlab(map.get("SlabName"), map.get("MaxKm"), map.get("FuelCost"), map.get("FixedCost"), map.get("Jumpcriteria"), map.get("Extrakmcost"), map.get("ACSurcharge"),
                    map.get("AdditionalExtraKmcost(AC)"), map.get("Escort(AC)"), map.get("Escort(Non-AC)"));
        }else if(map.get("BillingModel").equals("KM_SLAB")) {
            ContractKmBased contractKmBased = new ContractKmBased(getChromeDriver());
            contractKmBased.clickOnKmSlabForAddContract();
            contractKmBased.selectMileageAndDistributionLogic(map.get("Mileage"),map.get("DistributionLogic"));
            contractKmBased.updateKmSlab(map.get("SlabName"),map.get("MaxKm"),map.get("costPerKm"),map.get("minCost"));
        }else if(map.get("BillingModel").equals("ZONE_VENDOR_BASED")) {
            ZoneVendorBasedContractPage zoneVendorBasedContractPage = new ZoneVendorBasedContractPage(getChromeDriver());
        }else if(map.get("BillingModel").equals("EMPLOYEE_BASED")) {
            ContractEmployeeBasedPage contractEmployeeBasedPage = new ContractEmployeeBasedPage(getChromeDriver());
            contractEmployeeBasedPage.clickOnEmployeeBasedSlabForAddContract();
            contractEmployeeBasedPage.updateEmployeeSlab(map.get("LogicType"),map.get("EmployeeLogic"),map.get("SlabName"),map.get("FuelCost"),map.get("PerHeadCost"),map.get("MaximumKM"));
        }
        contractPage.saveContract();
        contractPage.checkAddedFuelType(contractName);
        contractPage.viewHistory(contractName);
        String Date = DateAndTime.getCurrentDateForHistoryPetternCheck();
        contractPage.historyCheck("",Date,"");
        Thread.sleep(10000);
        homePage.switchToDefaultFrame();
        loginPage.logout();
    }

}

