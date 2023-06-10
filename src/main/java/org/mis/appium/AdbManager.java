package org.mis.appium;

import org.mis.misc.Logger;

import java.util.ArrayList;
import java.util.List;

public class AdbManager {

    Executor cmd = new Executor();
    List<String> devices = new ArrayList<>();

    /**
     * This method start adb server
     */
    private void startADB() throws Exception {
        String output = cmd.runCommand("adb start-server");
        String[] lines = output.split("\n");
        if (lines.length == 1)
            Logger.log("adb service already started");
        else if (lines[1].equalsIgnoreCase("* daemon started successfully *"))
            Logger.log("adb service started");
        else if (lines[0].contains("internal or external command")) {
            Logger.log("adb path not set in system varibale");
            System.exit(0);
        }
    }

    /**
     * This method stop adb server
     */
    private void stopADB() throws Exception {
        cmd.runCommand("adb kill-server");
    }

    /**
     * This method return connected devices
     *
     * @return hashmap of connected devices information
     */
    public List<String> getDevices() throws Exception {
        startADB(); // start adb service
        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");

        if (lines.length <= 1) {
            Logger.log("No Device Connected");
            stopADB();
            System.exit(0);    // exit if no connected devices found
        }

        for (int i = 1; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("\\s+", "");

            if (lines[i].contains("device")) {
                lines[i] = lines[i].replaceAll("device", "");
                String deviceID = lines[i];
                devices.add(deviceID);
                Logger.log("Device Connected with udid : " + deviceID);
            } else if (lines[i].contains("unauthorized")) {
                lines[i] = lines[i].replaceAll("unauthorized", "");
                String deviceID = lines[i];

                Logger.log("Following device is unauthorized");
                Logger.log(deviceID + "\n");
            } else if (lines[i].contains("offline")) {
                lines[i] = lines[i].replaceAll("offline", "");
                String deviceID = lines[i];

                Logger.log("Following device is offline");
                Logger.log(deviceID + "\n");
            }
        }
        return devices;
    }
}
