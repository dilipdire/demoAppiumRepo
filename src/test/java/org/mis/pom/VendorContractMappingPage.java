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

public class VendorContractMappingPage {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngDriver;
    PageActions pageActions;
    public VendorContractMappingPage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(pageDriver);
    }
    @FindBy(css = "#buid")
    private WebElement vendorContractMappingPageLink;
    @FindBy(xpath = "//i[contains(text(),'add')]")
    private WebElement addButton;
    @FindBy(xpath = "//select[@formcontrolname='vendorKey']")
    private WebElement vendorKeyDropDown;
    @FindBy(xpath = "//select[@formcontrolname='fuelTypeId']")
    private WebElement fuelTypeDropDown;
    @FindBy(xpath = "//select[@formcontrolname='MaxSeatingCapacity']")
    private WebElement seatingCapacityDropdown;
    @FindBy(xpath = "//select[@formcontrolname='contractId']")
    private WebElement contractDropdown;
    @FindBy(xpath = "//select[@formcontrolname='officeId']")
    private WebElement officeDropdown;
    @FindBy(xpath = "//input[@class='waves-effect waves-light btn save']")
    private WebElement saveButton;
    @FindBy(xpath = "//i[@class='material-icons'][contains(text(),'search')]")
    private WebElement clickOnSearchVendorContractButton;
    @FindBy(xpath = " //body//app-root[@ng-version='5.2.5']//div[@id='slider']//div[@id='slider']//div[2]//div[1]//input[1]")
    private WebElement editSearchBoxForVendorName;
    @FindBy(xpath = "//i[contains(text(),'edit')]")
    private WebElement vendorContractEditLink;
    @FindBy(id = "filterSearch")
    private WebElement searchButton;
    @FindBy(xpath = "//select[@formcontrolname='vendorName']")
    private WebElement vendorNamedropdown;
    @FindBy(xpath = "//span[@class='mydpicon icon-mydpcalendar']")
    private WebElement activationDate;
    @FindBy(xpath = "(//span[@class='mydpicon icon-mydpcalendar'])[2]")
    private WebElement refreshDate;
    @FindBy(xpath = "//button[contains(text(),'YES')]")
    private WebElement yesButton;
    @FindBy(xpath = "//button[contains(text(),'OK')]")
    private WebElement okButton;
    @FindBy(css = "#toast-container")
    private WebElement validationMsg;
    @FindBy(xpath = "//div[@class='card-panel']//div[1]//div[1]//input[1]")
    private WebElement searchByOfficeTextField;
    @FindBy(xpath = "//div[@class='row']//div[3]//div[1]//input[1]")
    private WebElement searchByFuelTYpeTextField;
    @FindBy(xpath = "//div[@class='row']//div[4]//div[1]//input[1]")
    private WebElement searchBySeatingCapacityTextField;
    @FindBy(css = "input[type='date']")
    private WebElement searchByActivetionDateTextField;
    @FindBy(css = "div[class='col s12 content-border'] span:nth-child(4)")
    private List<WebElement> vendorNameColumn;
    @FindBy(css = "div[class='col s12 content-border']  span:nth-child(3)")
    private List<WebElement> officeColumn;
    @FindBy(css = "span:nth-child(8)")
    private List<WebElement> fuelTypeColumn;
    @FindBy(css = "div[class='col s12 content-border']  span:nth-child(5)")
    private List<WebElement> seatingCapacityColumn;
    @FindBy(xpath = "//i[contains(text(),'file_upload')]")
    private WebElement clickOnBulkUpload;
    @FindBy(xpath = "//input[@id='fileUpload']")
    private WebElement chooseFile;

    public boolean navigateToVendorContractMappingPage(){
        return Web.click(pageDriver,vendorContractMappingPageLink);
    }
    public boolean addVendorContract(String vendorKey,String fuelType,String seatingCapacity,String contract,String office,String date) throws Exception {
        addButton.click();
        vendorKeyDropDown.sendKeys(vendorKey);
        Web.click(pageDriver,vendorKeyDropDown);
        fuelTypeDropDown.sendKeys(fuelType);
        Web.click(pageDriver,fuelTypeDropDown);
        seatingCapacityDropdown.sendKeys(seatingCapacity);
        Web.click(pageDriver,seatingCapacityDropdown);
        contractDropdown.sendKeys(contract);
        Web.click(pageDriver,contractDropdown);
        officeDropdown.sendKeys(office);
        Web.click(pageDriver,officeDropdown);
        selectSingleCalenderDate(date);
        selectSingleCalenderDateLast(date);
        saveButton.click();
        return true;
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
    public void selectSingleCalenderDate(String date) throws Exception {
        pageDriver.findElement(By.xpath("(//span[@class='mydpicon icon-mydpcalendar'])[1]")).click();
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
    public void selectSingleCalenderDateLast(String date) throws Exception {
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
    public boolean clickEditLinkInVendorContract(String vendorName,String vendorKey,String fuelType,String seatingCapacity,String contract,String office,String date) throws Exception {
        pageActions.waitToLoadElement(clickOnSearchVendorContractButton);
        clickOnSearchVendorContractButton.click();
        pageActions.waitToLoadElement(editSearchBoxForVendorName);
        Web.writeInTextBox(pageDriver,editSearchBoxForVendorName,vendorName);
        searchButton.click();
        vendorContractEditLink.click();
        pageActions.waitToLoadElement(vendorNamedropdown);
        vendorNamedropdown.sendKeys(vendorKey);
        Web.click(pageDriver,vendorKeyDropDown);
        //fuelTypeDropDown.sendKeys(fuelType);
        // Web.click(pageDriver,fuelTypeDropDown);
        seatingCapacityDropdown.sendKeys(seatingCapacity);
        Web.click(pageDriver,seatingCapacityDropdown);
        contractDropdown.sendKeys(contract);
        Web.click(pageDriver,contractDropdown);
        officeDropdown.sendKeys(office);
        Web.click(pageDriver,officeDropdown);
        selectSingleCalenderDate(date);
        Web.selectSingleCalenderDate(pageDriver,refreshDate,date);
        saveButton.click();
        yesButton.click();
        return true;
    }
    public String validation(String vendorKey,String fuelType,String seatingCapacity,String contract,String office,String date) throws Exception {
        addVendorContract(vendorKey,fuelType,seatingCapacity,contract,office,date);
        Thread.sleep(2000);
        String actualMsg = validationMsg.getText();
        return actualMsg;
    }
    public boolean searchByVendor(String vendorName) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchVendorContractButton);
        clickOnSearchVendorContractButton.click();
        pageActions.waitToLoadElement(editSearchBoxForVendorName);
        Web.writeInTextBox(pageDriver,editSearchBoxForVendorName,vendorName);
        searchButton.click();
        Thread.sleep(5000);
        int counter = 0;
        List<WebElement> vendornames =  vendorNameColumn;
        for (WebElement vendorname : vendornames) {
            if(counter>0) {
                if (!vendorname.getText().equals(vendorName)) {
                    Assert.fail();
                }
            }
            counter++;
        }

        return true;
    }

    public boolean searchByOffice(String office) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchVendorContractButton);
        clickOnSearchVendorContractButton.click();
        pageActions.waitToLoadElement(editSearchBoxForVendorName);
        Web.writeInTextBox(pageDriver,searchByOfficeTextField,office);
        searchButton.click();
        Thread.sleep(5000);
        int counter = 0;
        List<WebElement> officess =  officeColumn;
        for (WebElement offices : officess) {
            if(counter>0) {
                if (!offices.getText().equals(office)) {
                    System.out.println("Differnt Office Also Came Please Check");
                    Assert.fail();
                }
            }
            counter++;
        }
        System.out.println("Matched");
        return true;
    }

    public boolean searchByFuelName(String fuelName) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchVendorContractButton);
        clickOnSearchVendorContractButton.click();
        pageActions.waitToLoadElement(editSearchBoxForVendorName);
        Web.writeInTextBox(pageDriver,searchByFuelTYpeTextField,fuelName);
        searchButton.click();
        Thread.sleep(5000);
        int counter = 0;
        List<WebElement> fuelTypes =  fuelTypeColumn;
        for (WebElement fuelType : fuelTypes) {
            if(counter>0) {
                if (!fuelType.getText().equals(fuelName)) {
                    System.out.println("Differnt Fuel Name Also Came Please Check");
                    Assert.fail();
                }
            }
            counter++;
        }
        System.out.println("Matched");
        return true;
    }
    public boolean searchBySeatingCapacity(String seatingCapacity) throws InterruptedException {
        pageActions.waitToLoadElement(clickOnSearchVendorContractButton);
        clickOnSearchVendorContractButton.click();
        pageActions.waitToLoadElement(editSearchBoxForVendorName);
        Web.writeInTextBox(pageDriver,searchBySeatingCapacityTextField,seatingCapacity);
        searchButton.click();
        Thread.sleep(5000);
        int counter = 0;
        List<WebElement> seatCapacities =  seatingCapacityColumn;
        for (WebElement seatCapacitie : seatCapacities) {
            if(counter>0) {
                if (!seatCapacitie.getText().equals(seatingCapacity)) {
                    System.out.println("Differnt Seat Capacitie Also Came Please Check");
                    Assert.fail();
                }
            }
            counter++;
        }
        System.out.println("Matched");
        return true;
    }

    public void bulkUploadVendorContract() throws InterruptedException {
        clickOnBulkUpload.click();
        String filePath = FileConstant.USER_DIR + FileConstant.VENDOR_CONTRACT_UPLOAD;
        chooseFile.sendKeys(filePath);
        Thread.sleep(5000);
    }
    public boolean checkAdd(String vendorName,String fuelType,String seatingCapacity,String contract,String office){
       // Amount = Amount.replaceAll("\\.0*$", "");
        if ((pageDriver.findElement(By.xpath("//span[contains(text(),'"+vendorName+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+contract+"')]")).isDisplayed())
                && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+fuelType+"')]")).isDisplayed()) && (pageDriver.findElement(By.xpath("//span[contains(text(),'"+office+"')]")).isDisplayed()) &&
                (pageDriver.findElement(By.xpath("//span[contains(text(),'"+seatingCapacity+"')]")).isDisplayed())) {
            System.out.println("Added vendor Contract Exists");
            Logger.log("Added vendor Contract Exists");
        }else{
            System.out.println("Added vendor Contract does not Exists");
            Logger.log("Added vendor Contract does not Exists");
            Assert.fail();
        }
        return true;
    }

}
