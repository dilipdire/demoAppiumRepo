package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ContractPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    PageActions pageActions;


   @FindBy(xpath = "//a[@id='contract']//span[contains(text(),'CONTRACT')]")
   //@FindBy(xpath = "//a[@id='contract']")
    private WebElement clickOnContractButton;
    @FindBy(xpath = "//i[@class='material-icons'][contains(text(),'search')]")
    private WebElement clickOnSearchContractButton;
    @FindBy(css = "#slider > div > span:nth-child(3) > input")
    private WebElement contractSearchBox;
    @FindBy(xpath = "//i[contains(text(),'edit')]")
    private WebElement editContractButton;
    @FindBy(xpath = "//i[contains(text(),'view_carousel')]")
    private WebElement viewContractButton;
    @FindBy(xpath = "//a[contains(text(),'History')]")
    private WebElement historyOfContractButton;
    @FindBy(css = "select[formcontrolname=\"billingType\"]")
    private WebElement contractTypeDropdown;
    @FindBy(css = "select[formcontrolname=\"cabModel\"]")
    private WebElement cabTypeDropdown;
    @FindBy(css = "#gst")
    private WebElement gstTextBox;
    @FindBy(xpath = "//i[contains(text(),'add')]")
    private WebElement addButton;
    @FindBy(css = "select[formcontrolname=\"fuelTypeId\"]")
    private WebElement fuelTypeTextBox;
    @FindBy(css = "select[formcontrolname=\"tripCalType\"]")
    private WebElement contractTypeDropdownSelect;
    @FindBy(css = "select[formcontrolname=\"tripKmSchema\"]")
    private WebElement tripKmSchemaDropdown;
    @FindBy(css = "select[formcontrolname=\"dutyCountLogicType\"]")
    private WebElement dutyParameterDropdown;
    @FindBy(xpath = "//input[@class='waves-effect waves-light btn-large save']")
    private WebElement clickOnSaveContractButton;
    @FindBy(xpath = "//button[@class='swal2-confirm swal2-styled']")
    private WebElement acceptSaveContract;
    //@FindBy(css = "#toast-container")
    @FindBy(xpath = "//span[@class='message']")
    private WebElement validationMsg;
    @FindBy(css = "#contractName")
    private WebElement contractNameTextBox;
    @FindBy(css = "#shortCode")
    private WebElement shortCodeTextBox;

    public ContractPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }

    public boolean clickOnContractPage() throws InterruptedException {
        pageActions.waitToLoadElement(clickOnContractButton);
        Thread.sleep(5000);
        clickOnContractButton.click();
        return Web.click(pageDriver, clickOnContractButton);
    }

    public boolean clickToContractUpdate(String contractName) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchContractButton);
        Web.click(pageDriver, clickOnSearchContractButton);
        pageActions.waitToLoadElement(contractSearchBox);
        Web.click(pageDriver, contractSearchBox);
        contractSearchBox.sendKeys(contractName);
        //JavascriptExecutor js = (JavascriptExecutor)pageDriver;
        //WebElement button =pageDriver.findElement(By.cssSelector("#slider i:nth-child(2)"));
        //js.executeScript("arguments[0].click();", button);
        //Thread.sleep(5000);
        //pageActions.waitToLoadElement(editContractButton);
        //editContractButton.click();
        return Web.click(pageDriver, editContractButton);
        //return true;
    }
    public boolean checkAddedFuelType(String contractName){
        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+contractName+"')]")).isDisplayed()){
            System.out.println("Added Fuel Type Exists");
            Logger.log("Added Fuel Type Exists");
        }else{
            System.out.println("Added Fuel Type Does Not Exists");
            Logger.log("Added Fuel Type Does Not Exists");
            Assert.fail();
        }
        return true;
    }

    public boolean viewHistory(String contractName){
        pageActions.waitToLoadElement(clickOnSearchContractButton);
        Web.click(pageDriver, clickOnSearchContractButton);
        pageActions.waitToLoadElement(contractSearchBox);
        Web.click(pageDriver, contractSearchBox);
        contractSearchBox.sendKeys(contractName);
        Web.click(pageDriver, viewContractButton);
        pageActions.waitToLoadElement(historyOfContractButton);
        return Web.click(pageDriver, historyOfContractButton);
    }
    public boolean historyCheck(String fuelName,String Date,String updatedFuelName) throws InterruptedException {
        Thread.sleep(5000);
        String date = pageDriver.findElement(By.xpath("//b[contains(text(),'" + Date + "')]")).getText();
        int dateLenght = date.length();
        if(dateLenght==23) {
            date = date.substring(0, date.length() - 8);
        }else {
            date = date.substring(0, date.length() - 9);
        }
        System.out.println(date);
        if (Date.equals(date)) {
            Logger.log("Pettern Matched");
        } else {
            Logger.log("Pettern Does Not Matched");
            Assert.fail();
        }
       /* String name = pageDriver.findElement(By.cssSelector("span:nth-child(2) p:nth-child(1) span:nth-child(1) > b:nth-child(1)")).getText();
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
        }*/
        return true;

    }
    public boolean updateMandatoryFieldOnContract(String BillingModel,String CabType,String gst,String fuelType,String BillingContractType,String TripKmSchema,String Dutyparameter) throws InterruptedException {
        //Thread.sleep(10000);
        pageActions.waitToLoadElement(contractTypeDropdown);
        contractTypeDropdown.sendKeys(BillingModel);
        Web.click(pageDriver, contractTypeDropdown);
        cabTypeDropdown.sendKeys(CabType);
        Web.click(pageDriver, cabTypeDropdown);
        Web.writeInTextBox(pageDriver, gstTextBox, gst);
        fuelTypeTextBox.sendKeys(fuelType);
        Web.click(pageDriver, fuelTypeTextBox);
        contractTypeDropdownSelect.sendKeys(BillingContractType);
        Web.click(pageDriver, contractTypeDropdownSelect);
        tripKmSchemaDropdown.sendKeys(TripKmSchema);
        Web.click(pageDriver, tripKmSchemaDropdown);
        dutyParameterDropdown.sendKeys(Dutyparameter);
        Web.click(pageDriver, dutyParameterDropdown);
        return true;
    }
    public boolean addNameAndShortCode(String contractName,String shortCode){
        Web.writeInTextBox(pageDriver,contractNameTextBox,contractName);
        Web.writeInTextBox(pageDriver,shortCodeTextBox,shortCode);
        return true;
    }
    public boolean saveContract() throws InterruptedException {
        Web.click(pageDriver, clickOnSaveContractButton);
        Thread.sleep(5000);
        Web.click(pageDriver, acceptSaveContract);
        Thread.sleep(5000);
        return true;
    }

    public boolean clickOnAddButton(){
        pageActions.waitToLoadElement(addButton);
        return Web.click(pageDriver,addButton);
    }

    public boolean selectBillingModel(String billingModel){
        contractTypeDropdown.sendKeys(billingModel);
        return Web.click(pageDriver, contractTypeDropdown);
    }
    public String validation() throws InterruptedException {
        //Thread.sleep(2000);
        String actualMsg = validationMsg.getText();
        return actualMsg;
    }


}
