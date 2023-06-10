package org.mis.tests;

import org.mis.pom.*;
import org.mis.utils.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class EmployeeBasedMandatoryFieldTest extends BaseTest{

    @Test(priority = 1)
    public void employeeBasedTesting() throws InterruptedException, ParseException, ParseException, IOException {
        getChromeDriver().get(PropertyReader.getProperty("base.url"));
        getNgWebDriver().waitForAngularRequestsToFinish();
        LoginPage loginPage = new LoginPage(getChromeDriver());
        loginPage.login();
        waitForCommandToFinish();
        HomePage homePage = new HomePage(getChromeDriver());
        ContractKmBased contractKmBased = new ContractKmBased(getChromeDriver());
        ContractPage contractPage= new ContractPage(getChromeDriver());
        DashboardPage dashboardPage = new DashboardPage(getChromeDriver());
        BillingDashboardPage billingDashboardPage= new BillingDashboardPage(getChromeDriver());
        NewBillingReportPage newBillingReportPage = new NewBillingReportPage(getChromeDriver());
        DownloadBillReport downloadBillReport = new DownloadBillReport(getChromeDriver());
        ContractEmployeeBasedPage contractEmployeeBasedPage = new ContractEmployeeBasedPage(getChromeDriver());
        DateAndTime dateAndTime = new DateAndTime();
        homePage.navigateToSettings();
        waitForCommandToFinish();
        homePage.navigateToNewContract();
        homePage.switchIframe();
        contractPage.clickOnContractPage();
        contractPage.clickToContractUpdate("Trip_Test2");
        contractPage.updateMandatoryFieldOnContract("EMPLOYEE_BASED","4seater","0","ABC","ACTUAL","ACTUAL",
                "DUTYID");
        contractEmployeeBasedPage.clickOnEmployeeBasedSlab();
        contractEmployeeBasedPage.updateEmployeeSlab("TRIP","PLANNED_AND_ADHOC","S1","60","10","100");
        contractPage.saveContract();
        homePage.switchToDefaultFrame();
        homePage.navigateToDashboard();
        waitForCommandToFinish();
        dashboardPage.searchEmployeeInDasboard("princi","princi chauhan ( 290 )");
        String hour2 = dateAndTime.hour24formate();
        String min = dateAndTime.minTwoHourLater();
        dashboardPage.createNonShiftTripsheetForLogoutTrip(min, hour2);
        dashboardPage.assignCabToNewTripsheet("Jai Travels - JAI-001 (IND4S) (KM 02 MA 1234)");
        String tripId1 = dashboardPage.saveTripsheet(hour2,min);
        dashboardPage.navigateToBillingDashboard();
        billingDashboardPage.performTrip(tripId1);
        billingDashboardPage.selectContract("Trip_Test2");
        billingDashboardPage.addEmployee("mamta");
        String noOfEmp = billingDashboardPage.saveTripsheetInformation();
        billingDashboardPage.auditDone();
        String expectedResult = BillCalculationUtils.calculationForEmployeeBasedCost("PLANNED_AND_ADHOC",noOfEmp,"10","2","2");
        System.out.println(expectedResult);
        ApiToGenerateBill.genetareBill();
        String actualResult = BillCalculationUtils.apiContractPrice(tripId1);
        BillComparisionUtil.compareBill(expectedResult,actualResult);
        loginPage.logout();
    }

}
