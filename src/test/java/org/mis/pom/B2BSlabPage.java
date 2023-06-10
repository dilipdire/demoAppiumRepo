package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.Web;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class B2BSlabPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public B2BSlabPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'B2B')]")
    private WebElement b2bSlab;
    @FindBy(xpath = "//select[@formcontrolname='btobType']")
    private WebElement b2bTypeDropdown;
    @FindBy(xpath = "//select[@formcontrolname='b2bDistLogic']")
    private WebElement distributionLogicDropdown;
    @FindBy(id = "fix1")
    private WebElement fixedCost;
    @FindBy(id = "fac1")
    private WebElement factor;
    @FindBy(id = "b2bConfigLoginCost")
    private WebElement configurableLoginCost;
    @FindBy(id = "b2bConfigLogoutCost")
    private WebElement configurableLogoutCost;

    public void clickOnB2BSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,600)");
        System.out.println("scroll -----");
        if(!b2bTypeDropdown.isDisplayed()) {
            b2bSlab.click();
        }

    }

    public boolean updateB2BSlab(String b2bType,String distributionLogic,String cost,String loginCost,String logoutCost){
        /*if (b2bTypeDropdown == null) {
            Logger.log("Failed to find B2BType field");
            return false;
        }*/
        Select dropdown = new Select(b2bTypeDropdown);
        dropdown.selectByVisibleText(b2bType);
        if(b2bType.equals("NA")||b2bType.equals("FIXED")||b2bType.equals("MULTIPLICATION_FACTOR")||b2bType.equals("HIGHER_COST")) {
           /* if (distributionLogicDropdown == null) {
                Logger.log("Failed to find DistributionLogic field");
                return false;
            }*/
            Select dropdownCostType = new Select(distributionLogicDropdown);
            dropdownCostType.selectByVisibleText(distributionLogic);
            if(b2bType.equals("FIXED")){
                Web.writeInTextBox(pageDriver,fixedCost,cost);
            }else if(b2bType.equals("MULTIPLICATION_FACTOR")||b2bType.equals("HIGHER_COST")){
                Web.writeInTextBox(pageDriver,factor,cost);
            }
        }else if(b2bType.equals("CONFIGURABLE_FIXED")||b2bType.equals("CONFIGURABLE_PERCENT")){
            Web.writeInTextBox(pageDriver,configurableLoginCost,loginCost);
            Web.writeInTextBox(pageDriver,configurableLogoutCost,logoutCost);
        }else{
            //code for formula based
        }

        return true;
    }
}
