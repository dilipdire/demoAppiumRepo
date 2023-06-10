package org.mis.pom;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.mis.utils.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BillComparision {

    private WebDriver pageDriver;
    public WebDriverWait wait;
    public NgWebDriver ngWebDriver;
    public BillComparision(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnContractPage()  {
        pageDriver.findElement(By.cssSelector("#contract > span")).click();

    }

   /* public void billComparision() {
        WebElementHelper.getValueInDropdown(2, 11);
        System.out.println("value");
     }*/

}
