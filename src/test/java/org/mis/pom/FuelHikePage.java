package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.misc.Logger;
import org.mis.utils.DateAndTime;
import org.mis.utils.FileConstant;
import org.mis.utils.PageActions;
import org.mis.utils.Web;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class FuelHikePage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngDriver;
    PageActions pageActions;
    public FuelHikePage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(id = "fuelhike")
    private WebElement fuelHikePageLink;
    @FindBy(xpath = "//i[@class='material-icons'][contains(text(),'search')]")
    private WebElement clickOnSearchFuelButton;
    @FindBy(css = "#slider > div > span:nth-child(3) > input")
    private WebElement editSearchBox;
    @FindBy(xpath = "//i[contains(text(),'edit')]")
    private WebElement fuelTypeEditLink;
    @FindBy(xpath = "(//input[@formcontrolname='fuelPrice'])[2]")
    private WebElement fuelPrice;
    @FindBy(xpath = "(//input[@class='waves-effect waves-light btn save'])[2]")
    private WebElement addButton;
    @FindBy(xpath = "(//input[@class='waves-effect waves-light btn save'])[1]")
    private WebElement addButton1;
    @FindBy(xpath = "//button[@class='swal2-confirm swal2-styled']")
    private WebElement acceptSaveFuelHike;
    @FindBy(xpath = "//i[contains(text(),'add')]")
    private WebElement addFuelHikeButton;
    @FindBy(xpath = "(//select[@formcontrolname='fuelTypeId'])[1]")
    private WebElement fuelTypeDropdown;
    @FindBy(xpath = "(//input[@formcontrolname='fuelPrice'])[1]")
    private WebElement fuelPriceInput;
    @FindBy(xpath = "(//span[@class='mydpicon icon-mydpcalendar'])[1]")
    private WebElement datePickerInAddFuelHike;
    @FindBy(linkText = "History")
    private WebElement history;
    @FindBy(css = "#toast-container")
    private WebElement validationMsg;
    @FindBy(xpath = "//i[contains(text(),'file_upload')]")
    private WebElement clickOnBulkUpload;
    @FindBy(xpath = "//input[@id='fileUpload']")
    private WebElement chooseFile;
    @FindBy(css = "#disabled")
    private WebElement disableFuelType;
    @FindBy(css = "#delete")
    private WebElement enableFuelType;
    @FindBy(xpath = "//body/app-root[1]/app-fuelhike[1]/div[2]/div[2]/div[1]/div[2]/div[1]/label[1]/span[1]")
    private WebElement inactiveActiveToggle;
    @FindBy(xpath = "//button[contains(text(),'YES')]")
    private WebElement yesButton;

    public boolean navigateToFuelHikePage(){
        pageActions.waitToLoadElement(fuelHikePageLink);
        return Web.click(pageDriver,fuelHikePageLink);
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
        FuelHikeSerch(fuelName);
        return true;
    }
    public boolean fuelTypeActivate(String fuelName){
        pageActions.waitToLoadElement(enableFuelType);
        enableFuelType.click();
        yesButton.click();
        return true;
    }
    public boolean FuelHikeSerch(String fuelName){
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        //pageActions.waitToLoadElement(editSearchBox);
        //Web.writeInTextBox(pageDriver,editSearchBox,fuelName);
        if(pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelName+"')]")).isDisplayed()){
            Logger.log("Search Succesful");
        }else{
            Logger.log("Search Unsuccesful");
        }

        return true;
    }

    public void selectSingleCalenderDate(String date) throws Exception {
        pageDriver.findElement(By.xpath("(//span[@class='mydpicon icon-mydpcalendar'])[2]")).click();
        Thread.sleep(10000);
        DateAndTime dateAndTime = new DateAndTime();
        String tableDate = dateAndTime.switchDateFormat(date, "dd/MM/yyyy", "MMM d, yyyy");
        String month = tableDate.split(",")[0].split(" ")[0].trim();
        String year = tableDate.split(",")[1].trim();
        String day = tableDate.split(",")[0].split(" ")[1].trim();
        pageDriver.findElement(By.xpath("//button[@class='headerlabelbtn monthlabel']")).click();
        List<WebElement> months = pageDriver.findElements(By.xpath("//table[@class='monthtable']//td"));
        for (WebElement monthActual : months) {
            if (monthActual.getText().equals(month)) {
                monthActual.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }
        pageDriver.findElement(By.xpath("//button[@class='headerlabelbtn yearlabel']")).click();
        List<WebElement> years = pageDriver.findElements(By.xpath("//table[@class='yeartable']//td"));
        for (WebElement actualYear : years) {
            if (actualYear.getText().equals(year)) {
                actualYear.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }
        List<WebElement> days = pageDriver.findElements(By.xpath("//table[@class='caltable']//td//div//span"));
        for (WebElement actualDay : days) {
            if (actualDay.getText().equals(day)) {
                actualDay.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }

    }

    public boolean fuelHikeSearch(String fuelHike){
        fuelHikePageLink.click();
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys(fuelHike);
        pageActions.waitToLoadElement(fuelTypeEditLink);
        fuelTypeEditLink.click();
        return true;
    }
    public boolean updateFuelPrice(String FuelPrice,String Date) throws Exception {
        fuelHikePageLink.click();
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys("FuelForAutomation");
        //selectSingleCalenderDate(Date);
        pageActions.waitToLoadElement(fuelTypeEditLink);
        fuelTypeEditLink.click();
        pageActions.waitToLoadElement(fuelPrice);
        fuelPrice.clear();
        fuelPrice.sendKeys(FuelPrice);
        selectSingleCalenderDate(Date);
        addButton.click();
        acceptSaveFuelHike.click();
        return true;
    }
    public String validation(String name,String fuelPrice,String date) throws Exception {
        pageActions.waitToLoadElement(addFuelHikeButton);
        addFuelHikeButton.click();
        pageActions.waitToLoadElement(fuelTypeDropdown);
        fuelTypeDropdown.sendKeys(name);
        Web.click(pageDriver,fuelTypeDropdown);
        Web.writeInTextBox(pageDriver,fuelPriceInput,fuelPrice);
        if(date.equals("")){
            //Do nothing with Date
        }else {
            Web.selectSingleCalenderDate(pageDriver, datePickerInAddFuelHike, date);
        }
        pageActions.waitToLoadElement(addButton1);
        addButton1.click();
        Thread.sleep(2000);
        String actualMsg = validationMsg.getText();
        return actualMsg;
    }

    public boolean checkAdd(String fuelTypeName,String date,String fuelPrice) throws InterruptedException {
        fuelPrice = fuelPrice.replaceAll("\\.0*$", "");
        Thread.sleep(5000);
        if ((pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelTypeName+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+date+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelPrice+"')]")).isDisplayed())) {
            System.out.println("Added Fuel Hike Exists");
            Logger.log("Added Fuel Hike Exists");
        }else{
            System.out.println("Added Fuel Hike does not Exists");
            Logger.log("Added Fuel Hike does not Exists");
            Assert.fail();
        }
        return true;
    }

    public boolean checkAddForUpload(String fuelTypeName,String date,String fuelPrice){
        fuelPrice = fuelPrice.replaceAll("\\.0*$", "");
        if ((pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelTypeName+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelPrice+"')]")).isDisplayed())) {
            System.out.println("Added Fuel Hike Exists");
            Logger.log("Added Fuel Hike Exists");
        }else{
            System.out.println("Added Fuel Hike does not Exists");
            Logger.log("Added Fuel Hike does not Exists");
            Assert.fail();
        }
        return true;
    }
    public boolean addFuelHike(String fuelTypeName,String date,String fuelPrice) throws Exception {
        pageActions.waitToLoadElement(addFuelHikeButton);
        addFuelHikeButton.click();
        fuelTypeDropdown.sendKeys(fuelTypeName);
        Web.click(pageDriver,fuelTypeDropdown);
        if(date.equals("")){
            //Do nothing with Date
        }else {
            Web.selectSingleCalenderDate(pageDriver, datePickerInAddFuelHike, date);
        }
        fuelPriceInput.sendKeys(fuelPrice);
        return true;
    }
    public boolean saveFuelHike(){
       return Web.click(pageDriver,addButton1);
    }
    public boolean updateFuelHike(String fuelName,String FuelPrice) throws Exception {
        fuelHikePageLink.click();
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        editSearchBox.sendKeys(fuelName);
        //selectSingleCalenderDate(Date);
        pageActions.waitToLoadElement(fuelTypeEditLink);
        fuelTypeEditLink.click();
        pageActions.waitToLoadElement(fuelPrice);
        fuelPrice.clear();
        fuelPrice.sendKeys(FuelPrice);
        //selectSingleCalenderDate(Date);
        addButton.click();
        acceptSaveFuelHike.click();
        return true;
    }
    public boolean historyCheck(String fuelName,String fuelPrice,String Date,String updatedFuelPrice) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchFuelButton);
        Thread.sleep(5000);
        //clickOnSearchFuelButton.click();
        pageActions.waitToLoadElement(editSearchBox);
        Web.writeInTextBox(pageDriver,editSearchBox,fuelName);
        //editSearchBox.sendKeys(fuelName);
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
       // String fuelNameInHistory = pageDriver.findElement(By.xpath("//b[contains(text(),'" + fuelName + "')]")).getText();
      //  String fuelNameInHistoryUpdated = pageDriver.findElement(By.xpath("//body/app-root[1]/app-fueltype[1]/div[4]/div[1]/div[1]/div[1]/span[1]/p[1]/span[1]/b[4]")).getText();
        String totalText = name + " changed" + " from " + fuelPrice + " to " + updatedFuelPrice;
        String expectedTotalText = "fuelPrice changed from " + fuelName + " to " + updatedFuelPrice;
        System.out.println(totalText);
        System.out.println(expectedTotalText);
        if (totalText.equals(expectedTotalText)) {
            Logger.log("Text Matched");
        } else {
            Logger.log("Text Does Not Matched");
        }
        return true;

    }

    public void bulkUploadFuelHike() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.FUELHIKE_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }
}