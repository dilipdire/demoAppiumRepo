package org.mis.appium;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.mis.misc.Logger;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import static org.mis.misc.Logger.log;

public class AppiumServer {

    private Executor cp = new Executor();
    static String os = System.getProperty("os.name");
    static String logFileName;
    static String logDir = System.getProperty("user.dir") + File.separator + "logs";

    private int serverPort;
    private String serverUrl;

    public AppiumServer(String url, int port) {
        serverPort = port;
        serverUrl = url;
        File file = new File(logDir);
        if (!file.exists()) {
            log("Log folder created : " + file.mkdir());
        }
        logFileName = logDir + File.separator + System.currentTimeMillis() + "_appium.log";
    }

    public AppiumServer() {
        serverPort = 4723;
        serverUrl = "127.0.0.1";
        File file = new File(logDir);
        if (!file.exists()) {
            log("Log folder created : " + file.mkdir());
        }
        logFileName = logDir + File.separator + System.currentTimeMillis() + "_appium.log";
    }

    public void startServer() {
        File file = new File(logFileName);
        try {
            if (file.createNewFile()) {
                log("Log file created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(PathProperties.NODE_JS.getCommand(os), "Node js path not found");
        Assert.assertNotNull(PathProperties.APPIUM_JS.getCommand(os), "Appium main js path not found");

        CommandLine cmd = new CommandLine(PathProperties.NODE_JS.getCommand(os));
        cmd.addArgument(PathProperties.APPIUM_JS.getCommand(os));
        cmd.addArgument("--address");
        cmd.addArgument(serverUrl);
        cmd.addArgument("--port");
        cmd.addArgument(String.valueOf(serverPort));
        cmd.addArgument("--bootstrap-port");
        cmd.addArgument(String.valueOf(serverPort + 100));
        cmd.addArgument("--session-override");
        cmd.addArgument("--log-level");
        cmd.addArgument("error:debug");
        cmd.addArgument("--log");
        cmd.addArgument(logFileName);
        cmd.addArgument("--relaxed-security");
        Logger.log(cmd.toString());

        log("log dir is :" + logFileName);
        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        try {
            executor.execute(cmd, handler);
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() throws IOException, InterruptedException {
        log("Cleaning up old appium servers");
        cp.runCommand(PathProperties.KILL.getCommand(os));
    }

    public int getPort() {
        return serverPort;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public boolean isServerRunning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            isServerRunning = true;
        }
        return isServerRunning;
    }

    enum PathProperties {
        APPIUM_JS("/usr/local/lib/node_modules/appium/build/lib/main.js", ""),
        NODE_JS("/usr/local/bin/node", ""),
        KILL("killall node", "taskkill /F /IM node.exe");

        private String linuxCommand;
        private String winCommand;

        PathProperties(String linux, String win) {
            linuxCommand = linux;
            winCommand = win;
        }

        public String getCommand(String osType) {
            if (osType.contains("Windows")) {
                return winCommand;
            } else if (osType.equalsIgnoreCase("linux")) {
                return linuxCommand;
            } else {
                return null;
            }
        }
    }
}
