package org.mis.pom;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.mis.misc.Logger.log;

public class PopupHandler {

    private AppiumDriver<MobileElement> pageDriver;

    public PopupHandler(AppiumDriver<MobileElement> driver) {
        pageDriver = driver;
        WebDriverWait wait = new WebDriverWait(pageDriver, 10);
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public boolean selectPopupAction(PopupActions action) {
        String osVersion = pageDriver.getCapabilities().getCapability("platformVersion").toString();
        if (osVersion.contains(".")) {
            osVersion = osVersion.split("\\.")[0];
        }
        log("os version : " + osVersion);

        if (Integer.parseInt(osVersion) >= 10) {
            if (action.equals(PopupActions.ALLOW)) {
                action = PopupActions.ALLOW_WHILE_USING_THE_APP;
            } else if (action.equals(PopupActions.DENY)) {
                action = PopupActions.DENY_SMALL;
            }
        }
        boolean success = false;
        String xpathFrame = "//android.widget.Button[@text='%s']";
        try {
            MobileElement element = pageDriver.findElementByXPath(String.format(xpathFrame, action.getActionToSet()));
            if (element != null) {
                element.click();
                log("Option selected on popup : " + action.getActionToSet());
                success = true;
            } else {
                log("Failed to select popup option : " + action.getActionToSet());
            }
        } catch (NoSuchElementException ne) {
            MobileElement element = pageDriver.findElementByXPath(String.format(xpathFrame,
                    StringUtils.capitalize(action.getActionToSet().toLowerCase())));
            if (element != null) {
                element.click();
                log("Option selected on popup : " + action.getActionToSet());
                success = true;
            } else {
                log("Failed to select popup option : " + action.getActionToSet());
            }
        }
        return success;
    }

    public enum PopupActions {
        YES("YES"), NO("NO"), ALLOW("ALLOW"), DENY("DENY"), CLOSE("CLOSE"), OKAY("OKAY"),
        ALLOW_WHILE_USING_THE_APP("Allow only while using the app"), DENY_SMALL("Deny");

        private String actionToSet;

        PopupActions(String action) {
            actionToSet = action;
        }

        public String getActionToSet() {
            return actionToSet;
        }
    }
}
