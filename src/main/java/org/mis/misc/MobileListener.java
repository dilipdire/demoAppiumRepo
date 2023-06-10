package org.mis.misc;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.ByteArrayInputStream;

import static org.mis.misc.Logger.log;

public class MobileListener extends TestListenerAdapter {

    public static AppiumDriver<MobileElement> driver;

    @Override
    public void onTestFailure(ITestResult result) {
        log("Executing listener on failure");
        Allure.addAttachment(result.getTestName(),
                new ByteArrayInputStream(((TakesScreenshot) driver).
                        getScreenshotAs(OutputType.BYTES)));
    }
}