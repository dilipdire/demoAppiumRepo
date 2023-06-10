package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShiftsSlabPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public ShiftsSlabPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),'SHIFTS')]")
    private WebElement siftsSlab;
    @FindBy(id = "ignoreTripType0")
    private WebElement ignoreTripType;
    @FindBy(id = "shiftDirectionType0")
    private WebElement shiftDirectionType;
    @FindBy(xpath = "(//i[@class='material-icons'][contains(text(),'close')])[2]")
    private WebElement closeIcon;
    @FindBy(xpath = "(//i[@class='material-icons'][contains(text(),'add')])[10]")
    private WebElement addIcon;


    public void clickOnShiftsSlab() throws InterruptedException {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,300)");
        System.out.println("scroll -----");
        //if(!ignoreTripType.isDisplayed()) {
            //siftsSlab.click();
        //}
        if(closeIcon.isDisplayed()) {
            closeIcon.click();
        }
        Thread.sleep(10000);
        addIcon.click();
    }

    public boolean updateShiftsSlab(String IgnoreTripType,String shiftDirection){
        if (ignoreTripType == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        Select dropdown = new Select(ignoreTripType);
        dropdown.selectByVisibleText(IgnoreTripType);
        if (shiftDirectionType == null) {
            Logger.log("Failed to find EscortUsage field");
            return false;
        }
        Select dropdownCostType = new Select(shiftDirectionType);
        dropdownCostType.selectByVisibleText(shiftDirection);

        return true;
    }
}
