package org.mis.utils;


import org.mis.misc.Logger;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static Properties props = new Properties();

    static {
        String workingDir = System.getProperty("user.dir");

        try {
            loadPropertyFile(workingDir + File.separator + "project.properties");
        } catch (IOException realCause) {
            Assert.fail("Unable to load file!", realCause);
        }
    }

    public static void loadPropertyFile(String propertyFileName) throws IOException {
        props.load(new FileInputStream(propertyFileName));
    }

    public static String getProperty(String propertyKey) {
        String propertyValue = props.getProperty(propertyKey.trim());
        if (propertyValue == null || propertyValue.trim().length() == 0) {
            Logger.log("Failed to read property with key : " + propertyKey);
        }
        return propertyValue;
    }

    public static void setProperty(String propertyKey, String value) {
        props.setProperty(propertyKey, value);
    }
}

