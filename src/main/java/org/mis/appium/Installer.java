package org.mis.appium;

import org.mis.misc.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Installer {
    Executor executor = new Executor();

    public boolean installApp(String deviceId, String appPackage, String apkPath) {
        boolean appInstalled;
        try {
            String result = executor.runCommand(String.format(AppCommands.PACKAGE.getCommand(), deviceId, appPackage));
            if (result.isEmpty()) {
                System.out.printf("Installing app to device [%s] with app package [%s]\n", deviceId, appPackage);
                String appStatus = executor.runCommand(String.format(AppCommands.INSTALL_APP.getCommand(), deviceId, apkPath));
                Logger.log("App installed :" + appStatus);
                appInstalled = appStatus.equalsIgnoreCase("success");
            } else {
                ListIterator<String> iterator = Arrays.asList(result.split("\n")).listIterator();
                List<String> cleanedPackages = new ArrayList<>();
                while (iterator.hasNext()) {
                    String s = iterator.next().replace("package:", "");
                    cleanedPackages.add(s);
                }
                Logger.log("Installed packages found : " + cleanedPackages);
                if (!cleanedPackages.contains(appPackage)) {
                    uninstall(deviceId, cleanedPackages);
                    String appStatus = executor.runCommand(String.format(AppCommands.INSTALL_APP.getCommand(), deviceId, apkPath));
                    Logger.log("App installed :" + appStatus);
                    appInstalled = appStatus.equalsIgnoreCase("success");
                } else {
                    Logger.log("App is already installed with package name : " + appPackage);
                    appInstalled = true;
                }
            }
        } catch (InterruptedException | IOException e) {
            appInstalled = false;
        }
        return appInstalled;
    }

    public boolean resetAppPreferences(String deviceId, String appPackage) throws Exception {
        executor.runCommand(String.format(AppCommands.CLEAR_LOGCAT.getCommand(), deviceId));
        String status = executor.runCommand(String.format(AppCommands.RESET_APP.getCommand(), deviceId, appPackage));
        return status.equalsIgnoreCase("success");
    }

    public boolean stopApp(String deviceId, String appPackage) throws IOException, InterruptedException {
        String status = executor.runCommand(String.format(AppCommands.STOP_APP.getCommand(), deviceId, appPackage));
        return status.equalsIgnoreCase("success");
    }

    public void enableLocation(String deviceId) throws IOException, InterruptedException {
        executor.runCommand(String.format(AppCommands.ENABLE_LOCATION.getCommand(), deviceId));
    }

    private void uninstall(String deviceId, List<String> packagesToUninstall) throws IOException, InterruptedException {
        for (String pack : packagesToUninstall) {
            Logger.log("Uninstalling package : " + pack);
            executor.runCommand(String.format(AppCommands.UNINSTALL_APP.getCommand(), deviceId, pack));
            Thread.sleep(3000);
        }
    }

    public void unlockScreen(String deviceId) throws IOException, InterruptedException {
        String screenCommand = AppCommands.SCREEN_STATUS.getCommand();
        String screenStatus = executor.runCommand(String.format(screenCommand, deviceId)).trim();
        if (screenStatus.equalsIgnoreCase("mScreenState=OFF")) {
            Logger.log("Unlocking the screen");
            executor.runCommand(String.format(AppCommands.LOCK_UNLOCK_SCREEN.getCommand(), deviceId));
        }
    }

    public void disableWifi(String deviceId) throws IOException, InterruptedException {
        executor.runCommand(String.format(AppCommands.DISABLE_WIFI.getCommand(), deviceId));
    }

    public void enableWifi(String deviceId) throws IOException, InterruptedException {
        executor.runCommand(String.format(AppCommands.ENABLE_WIFI.getCommand(), deviceId));
    }

    public void uninstallAppiumDefaultPackages(String id) {
        try {
            uninstall(id, Arrays.asList("io.appium.settings",
                    "io.appium.uiautomator2.server",
                    "io.appium.uiautomator2.server.test"));
        } catch (Exception ignored) {
        }
    }

    enum AppCommands {
        INSTALL_APP("adb -s %s install %s"),
        UNINSTALL_APP("adb -s %s uninstall %s"),
        PACKAGE("adb -s %s shell pm list packages | grep com.moveinsync"),
        RESET_APP("adb -s %s shell pm clear %s"),
        STOP_APP("adb -s %s shell am force-stop %s"),
        SCREEN_STATUS("adb -s %s shell dumpsys display | grep mScreenState"),
        LOCK_UNLOCK_SCREEN("adb -s %s shell input keyevent 26"),
        CLEAR_LOGCAT("adb -s %s logcat -c"),
        ENABLE_LOCATION("adb -s %s shell settings put secure location_providers_allowed +gps,network"),
        ENABLE_WIFI("adb -s %s shell svc wifi enable"),
        DISABLE_WIFI("adb -s %s shell svc wifi disable");

        private String command;

        AppCommands(String s) {
            command = s;
        }

        public String getCommand() {
            return command;
        }
    }
}
