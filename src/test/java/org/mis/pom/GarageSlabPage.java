package org.mis.pom;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GarageSlabPage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;

    @FindBy(xpath = "//div[contains(text(),'GARAGE')]")
    private WebElement garageSlab;
    @FindBy(xpath = "//select[@formcontrolname='garageInKmType']")
    private WebElement selectGarageKmInFlavour;
    @FindBy(xpath = "//input[@id='gKms'])\n")
    private WebElement garageKmInKms;
    @FindBy(xpath = "//select[@formcontrolname='garageOutKmType']")
    private WebElement selectGarageKmOutFlavour;
    @FindBy(xpath = "//body/app-root[1]/app-contract-list[1]/div[2]/app-contract-form[1]/div[2]/div[2]/form[1]/ul[28]/li[1]/div[2]/div[1]/div[3]/div[2]/div[1]/div[2]/input[1]")
    private WebElement garageKmOutKms;
    @FindBy(xpath = "//input[@id='garageKmRate']")
    private WebElement garageKmRate;
    @FindBy(xpath = "select[formcontrolname='garageKmDistLogic']")
    private WebElement garageKmDistLogic;
    @FindBy(xpath = "select[formcontrolname='garageInHourType']")
    private WebElement selectGarageHourInFlavour;
    @FindBy(xpath = "XPath //body//div[@id='slider']//div//div//div[2]//div[2]//div[2]//div[1]//div[2]//input[1]")
    private WebElement garageHourInHours;
    @FindBy(xpath = "select[formcontrolname='garageOutHourType']")
    private WebElement selectGarageHourOutFlavour;
    @FindBy(xpath = "//body//div[@id='slider']//div//div//div[2]//div[3]//div[2]//div[1]//div[2]//input[1]")
    private WebElement garageHourOutHours;
    @FindBy(xpath = "//input[@id='garageHrRate']")
    private WebElement garageHourRate;

    public GarageSlabPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnAddGarageSlab() {
        pageDriver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) pageDriver;
        js.executeScript("window.scrollBy(0,3000)");
        System.out.println("scroll -----");
        Web.click(pageDriver, garageSlab);
//    JavascriptExecutor js1 = (JavascriptExecutor) pageDriver;
//    js1.executeScript("window.scrollBy(0,400)");
//    System.out.println("scroll -----");
    }

    public boolean updateGarageSlab(String GarageKmInFlavour, String GarageKmInKm, String GarageKmOutFlavour, String GarageKmOutKm,
                                    String PerKmRate, String GarageKmDistributionLogic, String GarageHourInFlavour, String GarageHourInHours,
                                    String GarageHourOutFlavour, String GarageHourOutHours, String PerHourRate) {
        Select dropdown = new Select(selectGarageKmInFlavour);
        dropdown.selectByVisibleText(GarageKmInFlavour);
        if (garageKmInKms == null) {
            Logger.log("Failed to find GarageKmInKm field");
            return false;
        }
        garageKmInKms.sendKeys(GarageKmInKm);
        if (selectGarageKmOutFlavour == null) {
            Logger.log("Failed to find GarageKmOutFlavour field");
            return false;
        }
        Select dropdownGarageKmInKm = new Select(selectGarageKmOutFlavour);
        dropdownGarageKmInKm.selectByVisibleText(GarageKmOutFlavour);
        if (garageKmOutKms == null) {
            Logger.log("Failed to find GarageKmOutKm field");
            return false;
        }
        garageKmOutKms.sendKeys(GarageKmOutKm);
        if (garageKmRate == null) {
            Logger.log("Failed to find PerKmRate field");
            return false;
        }
        garageKmRate.sendKeys(PerKmRate);
        if (garageKmDistLogic == null) {
            Logger.log("Failed to find garageKmDistLogic field");
        }
        Select dropgarageKmDistLogic = new Select(garageKmDistLogic);
        dropgarageKmDistLogic.selectByVisibleText(GarageKmDistributionLogic);
        if (garageHourInHours == null) {
            Logger.log("Failed to find garageHourInHours field");
            return false;
        }
        Select dropdowngarageHourInHours = new Select(selectGarageHourInFlavour);
        dropdowngarageHourInHours.selectByVisibleText(GarageHourInFlavour);
        if (garageHourInHours == null) {
            Logger.log("Failed to find GarageHourInHours field");
            return false;
        }
        garageHourInHours.sendKeys(GarageHourInHours);
        if (selectGarageHourOutFlavour == null) {
            Logger.log("Failed to find GarageHourOutFlavour field");
            return false;
        }
        Select dropdownGarageHourOutFlavour = new Select(selectGarageKmOutFlavour);
        dropdownGarageHourOutFlavour.selectByVisibleText(GarageHourOutFlavour);
        if (garageHourOutHours == null) {
            Logger.log("Failed to find GarageHourOutHours field");
            return false;
        }
        garageHourOutHours.sendKeys(GarageHourOutHours);
        if (garageHourRate == null) {
            Logger.log("Failed to find PerHourRate field");
            return false;
        }
        garageHourRate.sendKeys(PerHourRate);
        return true;
    }


}

