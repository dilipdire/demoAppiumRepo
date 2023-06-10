package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewBillingReportPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public HSSFWorkbook excel;
    public HashMap<String, String> dataCache = new HashMap<String, String>();
    PageActions pageActions;
    public NewBillingReportPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }

    @FindBy(xpath = "//div[@class='chosen-container chosen-container-single']/a/div/b")
    private WebElement selectBillingCycle;
    @FindBy(xpath = "(//ul[@class='chosen-results']/li)[2]")
    private WebElement chooseResult;
    @FindBy(xpath = "//input[@value='Regenerate Billing']")
    private WebElement clickOnGenerateBilling;
    @FindBy(xpath = "//div[@class='billing-reports-footer']//div//select")
    private WebElement selectReport;
    @FindBy(xpath = "//a[@class='downloadReport']")
    private WebElement clickOnDownloadReport;
    //@FindBy(xpath = "(//*[contains(text(),'ABCD')]/following::a)[1]")
    @FindBy(xpath = "//tr[2]//td[17]//a[1]")
    private WebElement cabBreakupLink;


    public boolean generateBill() throws InterruptedException {
        Web.click(pageDriver,selectBillingCycle);
        pageActions.waitToLoadElement( chooseResult);
        Web.click(pageDriver,chooseResult);
        Thread.sleep(10000);
        // pageActions.waitToLoadElement(clickOnGenerateBilling);
        //Web.click(pageDriver,clickOnGenerateBilling);
        clickOnGenerateBilling.click();
        TimeUnit.MINUTES.sleep(15);
        pageDriver.navigate().refresh();
        pageActions.waitToLoadElement( selectBillingCycle);
        selectBillingCycle.click();
        Web.click(pageDriver,chooseResult);
        Select property = new Select(selectReport);
        property.selectByVisibleText("Trip Wise Cost");
        return Web.click(pageDriver,clickOnDownloadReport);
    }
    public boolean chooseBillCycle(){
        Web.click(pageDriver,selectBillingCycle);
        //selectBillingCycle.click();
        pageActions.waitToLoadElement( chooseResult);
        Web.click(pageDriver,chooseResult);

        return true;
    }
    public String getTotalNoOfDutiesForBillCyclePackage() throws InterruptedException {
        Thread.sleep(15000);
        pageActions.waitToLoadElement(cabBreakupLink);
        //Web.click(pageDriver,cabBreakupLink);
        cabBreakupLink.click();
        Thread.sleep(10000);
        //pageDriver.navigate().refresh();
        //chooseBillCycle();
        //Thread.sleep(15000);
        //pageActions.waitToLoadElement(cabBreakupLink);
        //Web.click(pageDriver,cabBreakupLink);
        //cabBreakupLink.click();
        //Thread.sleep(10000);
        String noOfDuties = pageDriver.findElement(By.xpath("(//table[@id='VendorBillingReport']//tbody//tr//td[contains(text(),'ABCD - cabv9090 (KA-73-AE-9522)')]/following::td[contains(text(),'Trip_Test1')]/following::td)[1]")).getText();
        System.out.println(noOfDuties);
        String totalKm = pageDriver.findElement(By.xpath("//table[@id='VendorBillingReport']//tbody//tr//td[contains(text(),'ABCD - cabv9090 (KA-73-AE-9522)')]/following::td[contains(text(),'Trip_Test1')]/following::td/following::td/following::td/following::td")).getText();
        System.out.println(noOfDuties);
        System.out.println(totalKm);
        return noOfDuties;
    }
    public String getTotalKmForBillCyclePackage() throws InterruptedException {
        // Thread.sleep(5000);
        //cabBreakupLink.click();
        // Thread.sleep(10000);
        String noOfDuties = pageDriver.findElement(By.xpath("//table[@id='VendorBillingReport']//tbody//tr//td[contains(text(),'ABCD - cabv9090 (KA-73-AE-9522)')]/following::td[contains(text(),'Trip_Test1')]/following::td")).getText();
        System.out.println(noOfDuties);
        String totalKm = pageDriver.findElement(By.xpath("(//table[@id='VendorBillingReport']//tbody//tr//td[contains(text(),'ABCD - cabv9090 (KA-73-AE-9522)')]/following::td[contains(text(),'Trip_Test1')]/following::td/following::td/following::td/following::td)[1]")).getText();
        System.out.println(noOfDuties);
        System.out.println(totalKm);
        return totalKm;
    }


}