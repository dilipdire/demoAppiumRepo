package org.mis.pom;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngularModel;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FuelTypePage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngDriver;
    PageActions pageActions;

    public FuelTypePage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(xpath = "//i[contains(text(),'add')]")
    private WebElement addButton;
    @FindBy(xpath = "//input[@formcontrolname='name']")
    private WebElement nameTextField;
    @FindBy(id = "fueltype")
    private WebElement fuelTypePageLink;
    @FindBy(xpath = "//i[@class='material-icons'][contains(text(),'search')]")
    private WebElement clickOnSearchFuelButton;
    @FindBy(css = "#slider > div > span:nth-child(3) > input")
    private WebElement editSearchBox;
    @FindBy(xpath = "//i[contains(text(),'edit')]")
    private WebElement fuelTypeEditLink;
    @FindBy(xpath = "//input[@formcontrolname='baseFuelPrice']")
    private WebElement fuelBasePrice;
    @FindBy(xpath = "//input[@class='waves-effect waves-light btn save']")
    private WebElement saveButton;
    @FindBy(xpath = "//button[contains(text(),'YES')]")
    private WebElement yesButton;
    @FindBy(xpath = "//input[@class='waves-effect waves-light btn modal-close']")
    private WebElement closeButton;
    //@FindBy(xpath = "(//i[@class='modal-action modal-close material-icons'])[1]")
    @FindBy(xpath = "//body/app-root[1]/app-fueltype[1]/div[3]/i[1]")
    private WebElement closeIcon;
    @FindBy(css = "#disable")
    private WebElement disableFuelType;
    @FindBy(css = "#enable")
    private WebElement enableFuelType;
    @FindBy(linkText = "History")
    private WebElement history;
    @FindBy(xpath = "//body/app-root[1]/app-fueltype[1]/div[2]/div[1]/div[1]/div[2]/div[1]/label[1]/span[1]")
    private WebElement inactiveActiveToggle;
    @FindBy(css = "#toast-container")
    private WebElement validationMsg;
    public boolean navigateToFuleTypePage(){
        return Web.click(pageDriver,fuelTypePageLink);
    }
    public boolean addFuelType(String name,String baseFuelPrice) throws InterruptedException {
        addButton.click();
        Web.writeInTextBox(pageDriver,nameTextField,name);
        Web.writeInTextBox(pageDriver,fuelBasePrice,baseFuelPrice);
        saveButton.click();
        Thread.sleep(5000);
        return true;
    }

    public String validation(String name,String baseFuelPrice) throws InterruptedException {
        addButton.click();
        pageActions.waitToLoadElement(nameTextField);
        Web.writeInTextBox(pageDriver,nameTextField,name);
        Web.writeInTextBox(pageDriver,fuelBasePrice,baseFuelPrice);
        saveButton.click();
        Thread.sleep(2000);
        String actualMsg = validationMsg.getText();
        return actualMsg;
    }
    public boolean checkAddedFuelType(String fuelTypeName){
        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelTypeName+"')]")).isDisplayed()){
            System.out.println("Added Fuel Type Exists");
            Logger.log("Added Fuel Type Exists");
        }else{
            System.out.println("Added Fuel Type Does Not Exists");
            Logger.log("Added Fuel Type Does Not Exists");
        }
        return true;
    }
    public boolean closeButtonFunctionInAddFuelTypePage(){
        addButton.click();
        closeButton.click();
        return true;
    }
    public boolean closeIconFunctionInAddFuelTypePage(){
        addButton.click();
        closeIcon.click();
        return true;
    }
    public void AddFuelTypePage(){
        pageDriver.findElement(By.id("fueltype")).click();
        pageDriver.findElement(By.xpath("//i[contains(text(),'add')]")).click();
        pageDriver.findElement(By.cssSelector("#modal1 > div > form > div:nth-child(1) > div:nth-child(1) > input")).sendKeys("FuelTest2");
        pageDriver.findElement(By.cssSelector("#modal1 > div > form > div:nth-child(1) > div:nth-child(2) > input")).sendKeys("60");
        pageDriver.findElement(By.className("waves-button-input")).click();

    }

    public boolean takeBaseFuelPriceOfFuel(String BaseFuelPrice) throws InterruptedException {
        fuelTypePageLink.click();
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys("FuelForAutomation");
        pageActions.waitToLoadElement(fuelTypeEditLink);
        fuelTypeEditLink.click();
        fuelBasePrice.clear();
        fuelBasePrice.sendKeys(BaseFuelPrice);
        saveButton.click();
        Thread.sleep(5000);
        return true;
    }
    public boolean updateFuelType(String fuelName,String fuelNameForUpdate,String basePriceForUpdate) throws InterruptedException {
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys(fuelName);
        pageActions.waitToLoadElement(fuelTypeEditLink);
        fuelTypeEditLink.click();
        Web.writeInTextBox(pageDriver,nameTextField,fuelNameForUpdate);
        //Web.writeInTextBox(pageDriver,fuelBasePrice,basePriceForUpdate);
        saveButton.click();
        Thread.sleep(5000);
        return true;
    }
    public boolean FuelTypeSerch(String fuelName){
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
       clickOnSearchFuelButton.click();
        //pageActions.waitToLoadElement(editSearchBox);
       // Web.writeInTextBox(pageDriver,editSearchBox,fuelName);

        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelName+"')]")).isDisplayed()){
            Logger.log("Search Succesful");
        }else{
            Logger.log("Search Unsuccesful");
        }

        return true;
    }

    public boolean FuelTypeSerchWhenSearchIsAlreadySelected(String fuelName) throws InterruptedException {
        //pageActions.waitToLoadElement(clickOnSearchFuelButton);
       // clickOnSearchFuelButton.click();
        //pageActions.waitToLoadElement(editSearchBox);
        Web.writeInTextBox(pageDriver,editSearchBox,fuelName);
        //editSearchBox.clear();
        Thread.sleep(5000);
        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelName+"')]")).isDisplayed()){
            Logger.log("Search Succesful");
        }else{
            Logger.log("Search Unsuccesful");
        }

        return true;
    }
    public boolean fuelTypeInactiveActiveCheck(String fuelName) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys(fuelName);
        pageActions.waitToLoadElement(disableFuelType);
        disableFuelType.click();
        yesButton.click();
        Thread.sleep(5000);
        // FuelTypeSerch(fuelName);
        //Thread.sleep(5000);
        pageActions.waitToLoadElement(inactiveActiveToggle);
        Web.click(pageDriver,inactiveActiveToggle);
        Thread.sleep(5000);
        FuelTypeSerch(fuelName);
        return true;
    }
    public boolean fuelTypeActivate(String fuelName){
        closeButton.click();
        pageActions.waitToLoadElement(enableFuelType);
        enableFuelType.click();
        yesButton.click();
        return true;
    }
    public boolean historyCheck(String fuelName,String Date,String updatedFuelName) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys(updatedFuelName);
        pageActions.waitToLoadElement(history);
        history.click();
        Thread.sleep(5000);
        String date = pageDriver.findElement(By.xpath("//b[contains(text(),'" + Date + "')]")).getText();
        date = date.substring(0, date.length() - 8);
        if (Date.equals(date)) {
            Logger.log("Pettern Matched");
        } else {
            Logger.log("Pettern Does Not Matched");
        }
        String name = pageDriver.findElement(By.cssSelector("span:nth-child(2) p:nth-child(1) span:nth-child(1) > b:nth-child(1)")).getText();
        String fuelNameInHistory = pageDriver.findElement(By.xpath("//b[contains(text(),'" + fuelName + "')]")).getText();
        String fuelNameInHistoryUpdated = pageDriver.findElement(By.xpath("//body/app-root[1]/app-fueltype[1]/div[4]/div[1]/div[1]/div[1]/span[1]/p[1]/span[1]/b[4]")).getText();
        String totalText = name + " changed" + " from " + fuelNameInHistory + " to " + fuelNameInHistoryUpdated;
        String expectedTotalText = "name changed from " + fuelName + " to " + updatedFuelName;
        System.out.println(totalText);
        System.out.println(expectedTotalText);
        if (totalText.equals(expectedTotalText)) {
            Logger.log("Text Matched");
        } else {
            Logger.log("Text Does Not Matched");
        }
        return true;

    }
}
