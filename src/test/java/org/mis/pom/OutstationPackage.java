package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.mis.misc.Logger;
import org.mis.utils.ExcelUtilities;
import org.mis.utils.UnzipUtility;
import org.mis.utils.Web;
import org.mis.utils.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OutstationPackage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public OutstationPackage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnContractPage() throws InterruptedException  {
        pageDriver.findElement(By.cssSelector("#contract > span")).click();

    }
    public void clickToContractUpdate() throws InterruptedException  {
        pageDriver.findElement(By.xpath("//i[@class='material-icons'][contains(text(),'search')]")).click();
        pageDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public void clickSearchOutstationPackage() throws InterruptedException {
        String contractVal = WebElementHelper.getOutstationBasedCellValue(1,0);
        WebElement SearchContract = pageDriver.findElement(
                By.cssSelector("#slider > div > span:nth-child(3) > input"));
        SearchContract.sendKeys(contractVal);
        pageDriver.findElement(By.xpath("//i[contains(text(),'edit')]")).click();
    }

    public void selectBillingModel() throws InterruptedException {
        WebElement billingModel = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(4) > select"));
        WebElementHelper.putValueInOutstationDropdown(1, 2, billingModel);
    }
    public void selectCabType() throws InterruptedException {
        WebElement cabType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(5) > select"));
        WebElementHelper.putValueInOutstationDropdown(1, 3, cabType);
    }
    public void editGST() throws InterruptedException {
        pageDriver.findElement(By.cssSelector("#gst")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 5);
        pageDriver.findElement(By.cssSelector("#gst")).sendKeys(val);
    }
    public void selectFuelType() throws InterruptedException {
        WebElement fuelType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(9) > select"));
        System.out.println("name- " + fuelType);
        WebElementHelper.putValueInOutstationDropdown(1, 8, fuelType);

    }
    public void selectBillingContractType() throws InterruptedException {
        WebElement billingContractType = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(10) > select"));
        WebElementHelper.putValueInOutstationDropdown(1, 9, billingContractType);
    }
    public void selectTripKmSchema() throws InterruptedException {
        WebElement tripKmSchema = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(11) > select"));
        WebElementHelper.putValueInOutstationDropdown(1,10,tripKmSchema);
    }
    public void selectDutyParameter() throws InterruptedException {
        WebElement dutyParameter = pageDriver.findElement(By.cssSelector("#slider > div:nth-child(2) > form > div.row.box.card-panel > div:nth-child(13) > select"));
        WebElementHelper.putValueInOutstationDropdown(1,13,dutyParameter);
    }
    public void clickOnOutstationPackageSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,500)");
        System.out.println("scroll -----");
    }
    public void addOutstationPackageSlabName() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@id='dSlabName0']")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 18);
        pageDriver.findElement(By.cssSelector("//input[@id='dSlabName0']")).sendKeys(val);
    }
    public void addOutstationPackageSlabFixedKm() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@id='dFixedKms0']")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 19);
        pageDriver.findElement(By.xpath("//input[@id='dFixedKms0']")).sendKeys(val);
    }
    public void addOutstationPackageSlabFixedHour() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@id='dFixedHour0']")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 20);
        pageDriver.findElement(By.xpath("//input[@id='dFixedHour0']")).sendKeys(val);
    }
    public void addOutstationPackageSlabFixedCost() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@id='dFixedCost0']")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 21);
        pageDriver.findElement(By.xpath("//input[@id='dFixedCost0']")).sendKeys(val);
    }
    public void addOutstationPackageSlabExtraKmCost() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@id='dExtraCost0']")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 22);
        pageDriver.findElement(By.xpath("//input[@id='dExtraCost0']")).sendKeys(val);
    }
    public void addOutstationPackageSlabExtraKmHour() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@id='dExtraHourCost0']")).clear();
        String val = WebElementHelper.getOutstationBasedCellValue( 1, 23);
        pageDriver.findElement(By.xpath("//input[@id='dExtraHourCost0']")).sendKeys(val);
    }

    public void saveContract() throws InterruptedException {
        pageDriver.findElement(By.xpath("//input[@class='waves-effect waves-light btn-large save']")).click();
        pageDriver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
        pageDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }
    public boolean downloadReportDefault(String tripId) {
        WebElement defaultEntry = pageDriver.findElement(By.xpath("//table[@id='offlineReportDownload']//tr[2]//td[2]"));
        boolean reportLocated = !defaultEntry.getText().isEmpty();
        boolean reportProcessed = false;
        if (reportLocated) {
            try {
                downloadReport(defaultEntry.getText(), tripId);
                reportProcessed = true;
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        return reportProcessed;
    }

    // download report and uzip the downloaded report (csv report) + read csv file
    public void downloadReport(String reportName, String tripId) throws InterruptedException, IOException {
        WebElement reportDownloadLink = pageDriver.findElement(By.xpath(String.format(
                "//td[contains(text(),'%s')]/..//a", reportName)));
        if (reportDownloadLink != null) {
            Logger.log("Download link clicked : " + Web.click(pageDriver, reportDownloadLink));
        }
        Web.waitForCommandToFinish();
        UnzipUtility.unzip(reportName);
        String downloadFile = UnzipUtility.getLastModified();
        String val = ExcelUtilities.getCsvVal(downloadFile, Integer.parseInt(tripId), 10);
        System.out.println(val);
        if (StringUtils.isBlank(val)){
            Logger.log("Trip Id not found");
            return;
        }else {
            Logger.log("Trip Id found");
        }
        String contractOutput = WebElementHelper.getOutstationBasedCellValue(1, 24);
        System.out.println(contractOutput);
        if (StringUtils.isNotBlank(val) && StringUtils.isNotBlank(contractOutput)) {
            Assert.assertEquals(val, contractOutput);
            Logger.log("Bill Matched");
        }
    }
}

