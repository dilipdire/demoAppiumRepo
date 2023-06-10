package org.mis.utils;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

public class BillComparisionUtil {

    public static void compareBill(String expectedResult,String actualResult){
        expectedResult= expectedResult.replaceAll("\\.0*$", "");
        actualResult= actualResult.replaceAll("\\.0*$", "");
        if (StringUtils.isNotBlank(expectedResult) && StringUtils.isNotBlank(actualResult)) {
            Assert.assertEquals(expectedResult, actualResult);
            System.out.println("Bill Matched");
        }
    }

    public static void validation(String expectedMsg,String actualMsg){
        if (StringUtils.isNotBlank(expectedMsg) && StringUtils.isNotBlank(actualMsg)) {
            Assert.assertEquals(expectedMsg, actualMsg);
            System.out.println("Validation Msg Matched");
        }
    }
}