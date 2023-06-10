package org.mis.tests;

import com.google.common.io.Resources;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mis.appium.AdbManager;
import org.mis.appium.AppCapability;
import org.mis.appium.AppiumServer;
import org.mis.appium.Installer;
import org.mis.misc.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.mis.misc.Logger.log;
import static org.mis.utils.PropertyReader.getProperty;

public class BaseTest {
    private AppiumServer server = new AppiumServer();
    private Installer installer = new Installer();
    private AdbManager deviceManager = new AdbManager();
    private AndroidDriver<MobileElement> mobileDriver;
    private ChromeDriver chromeDriver;
    private NgWebDriver ngWebDriver;
    List<String> listOfDevicesAttached;
    private static String APK_LOCATION = Resources.getResource(getProperty("app.file.name")).getPath();
    private static String APP_PACKAGE = getProperty("app.package");
    private static String LAUNCH_ACTIVITY = getProperty("app.main.activity");
    private static String INSTALL_APP = getProperty("app.install");
    private static String RESET_APP = getProperty("app.reset");
    private static String STOP_APP = getProperty("stop.app.after.test");
    private static String RUN_MOBILE = getProperty("enable.appium.mobile");
    private static String APPIUM_URL = "http://%s:%s/wd/hub";
    public HSSFWorkbook excel;
    private ThreadLocal<String> testName = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void beforeSuite() {
        try {
            if (RUN_MOBILE.equals("true")) {
                listOfDevicesAttached = deviceManager.getDevices();
                for (String dId : listOfDevicesAttached) {
                    installer.uninstallAppiumDefaultPackages(dId);
                    if (Boolean.parseBoolean(INSTALL_APP)) {
                        log("Install Requested");
                        installer.installApp(dId, APP_PACKAGE, APK_LOCATION);
                    }
                    if (Boolean.parseBoolean(RESET_APP)) {
                        log("Reset Requested");
                        installer.resetAppPreferences(dId, APP_PACKAGE);
                    }
                    //enable location if not
                    installer.enableLocation(dId);
                    installer.enableWifi(dId);
                    //Unlock attempt one
                    installer.unlockScreen(dId);
                    waitForCommandToFinish();
                    if (!server.isServerRunning(server.getPort())) {
                        server.startServer();
                    }
                }
                log("Starting appium server please wait .......");
                String UDID = listOfDevicesAttached.get(0);
                AppCapability capability = new AppCapability(UDID, APP_PACKAGE, LAUNCH_ACTIVITY);
                APPIUM_URL = String.format(APPIUM_URL, server.getServerUrl(), server.getPort());
                mobileDriver = new AndroidDriver<>(new URL(APPIUM_URL), capability.getRequiredAndroidCapabilities());
                waitForCommandToFinish();
                System.out.printf("Mobile Driver created for device [%s] with sessionId [%s] \n",
                        UDID, mobileDriver.getSessionId());
            }
        } catch (Exception e) {
            Logger.log("Appium driver exception happened");
            e.printStackTrace();
        }
        String os = System.getProperty("os.name");
        System.out.println("Os Detected : " + os);
        if (os.equalsIgnoreCase("linux")) {
            System.setProperty("webdriver.chrome.driver", new File(
                    System.getProperty("user.dir") + "/src/test/resources/chromedriver").getAbsolutePath());
        } else {
            System.setProperty("webdriver.chrome.driver", new File(
                    System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe").getAbsolutePath());
        }
        try {
            ChromeOptions options = new ChromeOptions();
            options.setAcceptInsecureCerts(true);
            options.addArguments("disable-infobars");
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            chromeDriver = new ChromeDriver(options);
            ngWebDriver = new NgWebDriver(chromeDriver);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log("Web driver exception happened");
            e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            if (STOP_APP.equalsIgnoreCase("true")) {
                server.stopServer();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        AndroidDriver<MobileElement> toAct = getMobileDriver();
        if (toAct != null) {
            toAct.quit();
        }
        ChromeDriver toclose = getChromeDriver();
        if (toclose != null) {
            toclose.quit();
        }
    }

    public void waitForCommandToFinish() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected AndroidDriver<MobileElement> getMobileDriver() {
        return mobileDriver;
    }

    protected ChromeDriver getChromeDriver() {
        return chromeDriver;
    }

    protected NgWebDriver getNgWebDriver() {
        return ngWebDriver;
    }
}