package org.mis.pom;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.mis.misc.Logger;
import org.mis.utils.PropertyReader;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static org.mis.utils.PropertyReader.getProperty;

public class LoginPage {

    private WebDriver pageDriver;
    @FindBy(name = "j_username")
    private WebElement userNameField;
    @FindBy(name = "j_password")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@name='login']")
    private WebElement loginButton;
    @FindBy(id = "userprofile")
    private WebElement userProfileButton;
    @FindBy(linkText = "Sign out")
    private WebElement logoutButton;
    public LoginPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean login() {
        if (userNameField == null) {
            Logger.log("Failed to find username field");
            return false;
        
        }
        userNameField.sendKeys(getProperty("web.user.name"));
        if (passwordField == null) {
            Logger.log("Failed to find password field");
            return false;
        }
        passwordField.sendKeys(getProperty("web.user.password"));
        return Web.click(pageDriver, loginButton);
    }
    public boolean logout() {
        if (userProfileButton == null) {
            Logger.log("Failed to find userprofile");
            return false;
        }
        userProfileButton.click();
        if (logoutButton == null) {
            Logger.log("Failed to find logout button");
            return false;
        }
        //logoutButton.click();
        return Web.click(pageDriver, logoutButton);
    }

}
