package org.mis.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.lang3.StringUtils;
import org.mis.pom.MobileAppPage;

import java.time.LocalDate;

import static org.mis.misc.Logger.log;

public class CalenderActions extends MobileAppPage {

    public CalenderActions(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public boolean clickNextMonth() {
        return click(getDriver().findElementById(APP_PACKAGE + ":id/mdtp_next_month_arrow"));
    }

    @Override
    public boolean validatePage() {
        return getDriver().findElementById(APP_PACKAGE + ":id/mdtp_animator") != null;
    }

    public boolean clickCancel() {
        return click(getDriver().findElementById(APP_PACKAGE + ":id/mdtp_cancel"));
    }

    public boolean clickSet() {
        return click(getDriver().findElementById(APP_PACKAGE + ":id/mdtp_ok"));
    }

    public boolean setDate(int date) {
        if (String.valueOf(date).length() != 2) {
            date = Integer.parseInt("0" + date);
        }
        String setDate = getDateCurrentMonthYear(date);
        log("Date to set : " + setDate);
        return click(getDriver().findElementByAccessibilityId(setDate));
    }

    private String getDateCurrentMonthYear(int date) {
        LocalDate currentDate = LocalDate.now();
        return String.format("%d %s %s",
                date, StringUtils.capitalize(currentDate.getMonth().toString().toLowerCase()), currentDate.getYear());
    }

    public int getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }
}
