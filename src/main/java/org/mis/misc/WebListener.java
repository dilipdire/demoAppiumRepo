package org.mis.misc;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.ByteArrayInputStream;

import static org.mis.misc.Logger.log;

public class WebListener extends TestListenerAdapter {

    public static WebDriver driver;

    @Override
    public void onTestFailure(ITestResult result) {
        log("Executing listener on failure");
        Allure.addAttachment(result.getTestName(),
                new ByteArrayInputStream(((TakesScreenshot) driver).
                        getScreenshotAs(OutputType.BYTES)));
    }
}
