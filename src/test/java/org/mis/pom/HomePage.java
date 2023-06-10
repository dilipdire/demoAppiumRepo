package org.mis.pom;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.mis.misc.Logger;
import org.mis.utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class HomePage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public HashMap<String, String> dataCache = new HashMap<String, String>();
    public NgWebDriver ngDriver;
    PageActions pageActions;
    public HomePage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(xpath = "//span[contains(.,'SETTINGS')]")
    private WebElement settings;
    @FindBy(css = "#billing > ul > li.NewContractManagement.roleBasedAccess")
    private WebElement managedNewContract;
    @FindBy(xpath = "//div[@id='userprofile']")
    private WebElement userProfile;
    @FindBy(xpath = "//span/a[@class='switchMISUser']")
    private WebElement switchMISAdmin;
    @FindBy(xpath = "//span[contains(.,'Manage Properties')]")
    private WebElement managedProperties;
    @FindBy(xpath = "//a[@class='switchMISUser']")
    private WebElement switchMISUser;
    @FindBy(xpath = "//span[@class='roleBasedAccess']")
    private WebElement navigateToDashboardPage;
    @FindBy(xpath = "//li[contains(@href,'manageBillingZones')]")
    private WebElement manageBillingZones;
    @FindBy(xpath = "//span[@class='BillingDashboard roleBasedAccess']")
    private WebElement clickOnBilling;
    @FindBy(linkText = "Site Administrator")
    private WebElement siteAdministrator;

    public boolean navigateToSettings() throws InterruptedException {
        //Actions actions = new Actions(pageDriver);
        //actions.moveToElement(userProfile).perform();
        //userProfile.click();
        //Web.moveToElement(pageDriver,userProfile);
        pageActions.waitToLoadElement(settings);
        return Web.click(pageDriver,settings);

    }
    public boolean navigateToSiteAdministrator() throws InterruptedException {
        Web.moveToElement(pageDriver,userProfile);
       // pageActions.waitToLoadElement(siteAdministrator);
        return Web.click(pageDriver,siteAdministrator);
    }
    public boolean navigateToNewContract() {
        pageActions.waitToLoadElement(managedNewContract);
        return Web.click(pageDriver,managedNewContract);
    }
    public void switchIframe() {
        pageDriver.switchTo().frame("new-billing-page");
        System.out.println("iframe");
    }
    public void switchToDefaultFrame() {

        pageDriver.switchTo().defaultContent();
    }
    public boolean navigateToDashboard() {
        //System.out.println("dashboard");
        pageActions.waitToLoadElement(navigateToDashboardPage);
        return Web.click(pageDriver,navigateToDashboardPage);
    }
    public boolean switchRoleToMisAdmin() {
        pageActions.waitToLoadElement(userProfile);
        userProfile.click();
        return Web.click(pageDriver,switchMISAdmin);
    }
    public boolean clickManageProperties() {
        return Web.click(pageDriver,managedProperties);
    }
    public boolean switchRoleToMisUser() {
        pageActions.waitToLoadElement(userProfile);
        userProfile.click();
        return Web.click(pageDriver,switchMISUser);
    }

}