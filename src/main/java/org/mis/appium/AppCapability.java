package org.mis.appium;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

import static org.mis.misc.Logger.log;

public class AppCapability {

    private String udId;
    private String pack;
    private String activity;

    //use for mobile
    public AppCapability(String deviceId, String packageName, String activityToLaunch) {
        udId = deviceId;
        pack = packageName;
        activity = activityToLaunch;
    }

    public DesiredCapabilities getRequiredAndroidCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AndroidDevice");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.UDID, udId);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, pack);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, activity);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60000);
        /*if (skipAppiumPackages(udId)) {
            log("Skipping appium packages");
            capabilities.setCapability("skipDeviceInitialization", true);
            capabilities.setCapability("skipServerInstallation", true);
        }*/
        return capabilities;
    }

    public DesiredCapabilities getChromeCapabilities() {
        return DesiredCapabilities.chrome();
    }

    private boolean skipAppiumPackages(String deviceId) {
        Executor executor = new Executor();
        boolean toSkip = false;
        try {
            String output = executor.runCommand(
                    String.format("adb -s %s shell pm list packages | grep io.appium", deviceId));
            Thread.sleep(5);
            toSkip = !output.isEmpty();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return toSkip;
    }
}
