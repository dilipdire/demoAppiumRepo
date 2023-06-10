package org.mis.appium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor {

    Process p;
    ProcessBuilder builder;

    /**
     * This method run command on windows and mac
     *
     * @param command to run
     */
    public String runCommand(String command) throws InterruptedException, IOException {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            if (command.contains("grep")) {
                command = command.replace("grep", "findStr");
            }
            builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Thread.sleep(5000);
            p = builder.start();
        } else {
            p = Runtime.getRuntime().exec(command);
            Thread.sleep(5000);
        }
        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        String allLine = "";
        int i = 1;
        while ((line = r.readLine()) != null) {
//            TestLogger.log(i + ". " + line);
            allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug"))
                break;
            i++;
        }
        return allLine;
    }
}
