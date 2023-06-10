package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.utils.GenerateRandomString;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BillingZonePage {
    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public BillingZonePage(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean searchUsername(){
        boolean isFound = false;
        List<WebElement> rows = pageDriver.findElements(By.xpath("//table[@class='tablestyle1']/tbody/tr"));
        for(WebElement row : rows){
            if(row.findElement(By.cssSelector("td:nth-of-type(n)")).getText().equals("bzone")){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
    GenerateRandomString randomStringExample = new GenerateRandomString();
    public String zoneName = randomStringExample.generateRandomString(5);

    public void addbillingZone() throws InterruptedException {
        pageDriver.findElement(By.cssSelector("input[value='Add New Zone']")).click();
        Thread.sleep(10000);
        pageDriver.findElement(By.cssSelector("input[name='zoneName']")).sendKeys(zoneName);
        pageDriver.findElement(By.cssSelector("input[value='Submit']")).click();
        Alert alert=pageDriver.switchTo().alert();
        alert.accept();
        Thread.sleep(10000);
    }
    public void searchBillingZoneAndUpdate() throws InterruptedException {
        pageDriver.findElement(By.xpath("//i[@class='material-icons'][contains(text(),'search')]")).click();
        pageDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(10000);
        //GenerateRandomString generateRandomString = new GenerateRandomString();
        //String zoneName=GenerateRandomString.zoneNameRandom();
        pageDriver.findElement(By.cssSelector("#slider > div > span:nth-child(3) > input")).sendKeys(zoneName);
        pageDriver.findElement(By.xpath("//i[contains(text(),'edit')]")).click();
        Thread.sleep(10000);
        pageDriver.findElement(By.cssSelector("input[class='waves-effect waves-light btn save']")).click();
        pageDriver.findElement(By.xpath("//button[@class='swal2-confirm swal2-styled']")).click();
    }
    public void addZoneName(){

       // String zoneName=GenerateRandomString.getAlphaNumericString(6);
        Select property = new Select(pageDriver.findElement(By.id("#zoneId0")));
        property.selectByVisibleText(zoneName);
    }

}
