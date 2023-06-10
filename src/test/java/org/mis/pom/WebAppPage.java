package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class WebAppPage {
    private NgWebDriver basePageDriver;

    public WebAppPage(NgWebDriver ngWebDriver) {
        basePageDriver = ngWebDriver;
    }
}
