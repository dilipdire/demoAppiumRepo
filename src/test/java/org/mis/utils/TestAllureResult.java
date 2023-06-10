package org.mis.utils;



import org.apache.xmlbeans.impl.tool.XSTCTester;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.internal.BaseTestMethod;

import java.lang.reflect.Field;

public class TestAllureResult implements ITest {
    protected String testCaseName = "";
   // @Override
    public String getTestName() {

        this.testCaseName = "TCname";
        // this.testCaseName = this.serviceProcessData.getTestCaseCustomName();
        return this.testCaseName;
    }

   // @AfterMethod(alwaysRun = true)
    public void setResultTestName(ITestResult result) {
        try {
            BaseTestMethod baseTestMethod = (BaseTestMethod) result.getMethod();
            Field f = baseTestMethod.getClass().getSuperclass().getDeclaredField("m_methodName");
            f.setAccessible(true);
            f.set(baseTestMethod, this.testCaseName);
        } catch (Exception e) {
            //ErrorMessageHelper.getInstance().setErrorMessage(e);
            Reporter.log("Exception : " + e.getMessage());
        }
    }
}
