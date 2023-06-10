package org.mis.pom;

import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PropertyPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    PageActions pageActions;
    public PropertyPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(name = "selectType")
    private WebElement propertyDropdown;
    @FindBy(xpath = "//table/tbody//tr[64]//td[2]/input[@name='LEVEL_2_BILLING_DASHBOARD_ENABLED']")
    private WebElement BILLING_DASHBOARD_ENABLED;
    @FindBy(xpath = "//table/tbody//tr[222]//td[2]/input[@name='LEVEL_2_BILLING_DASHBOARD_AUTH_TOKEN']")
    private WebElement BILLING_DASHBOARD_AUTH_TOKEN;
    @FindBy(xpath = "//table/tbody/tr[105]/td[2]/input[@name='LEVEL_2_BILLING_DASHBOARD_URL_V2']")
    private WebElement BILLING_DASHBOARD_URL_V2;
    @FindBy(xpath = "//table/tbody/tr[27]/td[2]/input[@name='LEVEL_2_BILLING_CALCULATION_URL']")
    private WebElement LEVEL_2_BILLING_CALCULATION_URL;
    @FindBy(xpath = "//input[@name='LEVEL_3_NEW_BILLING_REPORTS_ENABLED']")
    private WebElement NEW_BILLING_REPORTS_ENABLED;
    @FindBy(xpath = "//input[@name='LEVEL_3_NEW_RBD_REPORT_ENABLED']")
    private WebElement LEVEL_3_NEW_RBD_REPORT_ENABLED;
    @FindBy(css = "input[name='LEVEL_3_FEATURE_ZONE_BASED_BILLING_ENABLED']")
    private WebElement LEVEL_3_FEATURE_ZONE_BASED_BILLING_ENABLED;
    @FindBy(xpath = "//input[@type='button' and @name='saveConfigProps']")
    private  WebElement saveButton;
    @FindBy(xpath = "//textarea[@name='propertiesComment']")
    private WebElement propertyComment;
    @FindBy(xpath = "//input[@name='confirmSaveConfigProps']")
    private WebElement confirmConfigProperty;
    @FindBy(xpath = "//span[contains(.,'Misc.')]")
    private WebElement miscLink;
    @FindBy(xpath = "//input[@name='reloadProps']")
    private WebElement reloadProperty;
    @FindBy(xpath = "//input[@id='yesConfirm']")
    private WebElement yesConfirm;
    public boolean selectPropertyEnginerring() {
        propertyDropdown.sendKeys("Engineering");
        return Web.click(pageDriver, propertyDropdown);
    }
    public void updatePropertyEngineering() {
        if (!BILLING_DASHBOARD_ENABLED.isSelected()) {
            BILLING_DASHBOARD_ENABLED.click();
            saveUpdatedProperty();
        }
        if (!BILLING_DASHBOARD_AUTH_TOKEN.getAttribute("value").equals("LrmXabm55R")) {
            BILLING_DASHBOARD_AUTH_TOKEN.sendKeys("LrmXabm55R");
            saveUpdatedProperty();
        }
        if (!BILLING_DASHBOARD_URL_V2.getAttribute("value").equals("https://staging2.moveinsync.com/mis-billing-contract/")) {
            BILLING_DASHBOARD_URL_V2.sendKeys("https://staging2.moveinsync.com/mis-billing-contract/");
            saveUpdatedProperty();
        }
        if (!LEVEL_2_BILLING_CALCULATION_URL.getAttribute("value").equals("http://staging2.moveinsync.com:8082/")) {
            LEVEL_2_BILLING_CALCULATION_URL.sendKeys("http://staging2.moveinsync.com:8082/");
            saveUpdatedProperty();
        }
    }
    public boolean selectPropertyOperations() {
        propertyDropdown.sendKeys("Operations");
        return Web.click(pageDriver, propertyDropdown);

    }

    public void updatePropertyOperations() {
        if (!NEW_BILLING_REPORTS_ENABLED.isSelected()) {
            NEW_BILLING_REPORTS_ENABLED.click();
            saveUpdatedProperty();
        }
        if (!LEVEL_3_NEW_RBD_REPORT_ENABLED.isSelected()) {
            LEVEL_3_NEW_RBD_REPORT_ENABLED.click();
            saveUpdatedProperty();
        }
        if (!LEVEL_3_FEATURE_ZONE_BASED_BILLING_ENABLED.isSelected()) {
            LEVEL_3_FEATURE_ZONE_BASED_BILLING_ENABLED.click();
            saveUpdatedProperty();
        }
    }
    public void saveUpdatedProperty(){
        saveButton.click();
        propertyComment.sendKeys("Automation Testing");
        confirmConfigProperty.click();
    }
    public void reloadProperty() throws InterruptedException {
        pageActions.waitToLoadElement(miscLink);
        miscLink.click();
        pageActions.waitToLoadElement(reloadProperty);
        reloadProperty.click();
        pageActions.waitToLoadElement(yesConfirm);
        yesConfirm.click();
        Thread.sleep(10000);
    }
}