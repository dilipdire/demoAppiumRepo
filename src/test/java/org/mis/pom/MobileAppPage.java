package org.mis.pom;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.mis.utils.PropertyReader;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.mis.misc.Logger.log;

public abstract class MobileAppPage {

    private static AppiumDriver<MobileElement> basePageDriver;
    protected static MobileElement validatorElement;
    protected static MobileElement elementToWaitFor;
    protected static String activityToWait;
    protected static String APP_PACKAGE = PropertyReader.getProperty("app.package");

    public MobileAppPage(AppiumDriver<MobileElement> driver) {
        basePageDriver = driver;
    }

    public boolean validatePage() {
        waitForElementToAppear();
        verifyActivity();
        return validatorElement != null;
    }

    private void waitForElementToAppear() {
        if (elementToWaitFor != null) {
            log("Waiting for element : " + elementToWaitFor.toString());
            WebDriverWait wait = new WebDriverWait(basePageDriver, 20);
            wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
        }
    }

    private void verifyActivity() {
        if (activityToWait != null) {
            waitForActivity(activityToWait);
        }
    }

    public void waitForCommandToFinish(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean clickBack() {
        return click(getDriver().findElementById(APP_PACKAGE + ":id/back_iv"));
    }

    public AppiumDriver<MobileElement> getDriver() {
        return basePageDriver;
    }

    public boolean click(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(basePageDriver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
        if (element == null) {
            log("Element to click provided is null");
            return false;
        } else {
            log("Element to click with text : " + element.getText());
            element.click();
            log("Element clicked");
            return true;
        }
    }

    public void doubleTap(MobileElement element){
        TouchActions action = new TouchActions(basePageDriver);
        action.doubleTap(element);
        action.perform();
    }

    private void waitForActivity(String desiredActivity) {
        log("Activity to wait : " + desiredActivity);
        int counter = 0;
        try {
            do {
                Thread.sleep(1000);
                counter++;
            } while (((AndroidDriver<MobileElement>) getDriver()).currentActivity().contains(desiredActivity) && (counter <= 10));
        } catch (Exception e) {
            log("Activity appeared :" + ((AndroidDriver<MobileElement>) getDriver()).currentActivity());
        }
    }
}
