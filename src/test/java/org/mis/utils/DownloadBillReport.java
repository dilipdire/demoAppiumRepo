package org.mis.utils;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.mis.misc.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.IOException;
public class DownloadBillReport {
    private WebDriver pageDriver;
    public DownloadBillReport(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToReportDownload() throws InterruptedException {
        pageDriver.findElement(By.id("userprofile")).click();
        Thread.sleep(4000);
        pageDriver.findElement(By.linkText("Downloads")).click();
        Thread.sleep(2000);
    }
    // copy the report and call download and unzip the report method
    public boolean downloadReportDefault() {
        WebElement defaultEntry = pageDriver.findElement(By.xpath("//table[@id='offlineReportDownload']//tr[2]//td[2]"));
        boolean reportLocated = !defaultEntry.getText().isEmpty();
        boolean reportProcessed = false;
        if (reportLocated) {
            try {
                downloadUnzipLatestReport(defaultEntry.getText());
                reportProcessed = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reportProcessed;
    }
    // download and unzip the report
    public void downloadUnzipLatestReport(String reportName) throws IOException {
        WebElement reportDownloadLink = pageDriver.findElement(By.xpath(String.format(
                "//td[contains(text(),'%s')]/..//a", reportName)));
        if (reportDownloadLink != null) {
            Logger.log("Download link clicked : " + Web.click(pageDriver, reportDownloadLink));
        }
        Web.waitForCommandToFinish();
        UnzipUtility.unzip(reportName);
    }
    // search added Trip Id in downloaded report
    public String getTripInfo(String tripId, int csvCol) throws InterruptedException, IOException {
        String downloadFile = UnzipUtility.getLastModified();
        String val = ExcelUtilities.getCsvVal(downloadFile, Integer.parseInt(tripId), csvCol);
        System.out.println(val);
        if (StringUtils.isBlank(val)) {
            Logger.log("Trip Id not found");
        } else {
            Logger.log("Trip Id found");
        }
        return val;
    }
}
