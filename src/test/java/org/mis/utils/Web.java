package org.mis.utils;

import org.mis.misc.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class Web {
    public static boolean click(WebDriver driver, WebElement element) {
        boolean success;
        if (element == null) {
            Logger.log("Failed to find the element to click");
            success = false;
        } else {
            Logger.log("Clicking : " + element.getText());
            try {
                element.click();
                success = true;
            } catch (Exception e) {
                Actions actions = new Actions(driver);
                actions.moveToElement(element).click();
                success = true;
            }
        }
        return success;
    }

    public static void moveToElement(WebDriver driver, WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public static void waitForCommandToFinish() {
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Generic Clear method
    public static boolean genericClear(WebDriver driver, WebElement elementToClear) {

        try {
            elementToClear.clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean selectElement(WebDriver driver,WebElement element) {
        //Actions act = new Actions(driver);
        //act.sendKeys(element,Keys.DOWN).perform();
        //act.sendKeys(element,Keys.ENTER).click().perform();
        element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.ENTER);
        return true;
    }

    public static boolean writeInTextBox(WebDriver driver,WebElement element, String textToWrite){
        element.clear();
        element.sendKeys(textToWrite);
        return true;
    }

    public static void selectSingleCalenderDate(WebDriver driver,WebElement element,String date) throws Exception {
        element.click();
        Thread.sleep(10000);
        DateAndTime dateAndTime = new DateAndTime();
        String tableDate = dateAndTime.switchDateFormat(date, "dd/MM/yyyy", "MMM d, yyyy");
        String month = tableDate.split(",")[0].split(" ")[0].trim();
        String year = tableDate.split(",")[1].trim();
        String day = tableDate.split(",")[0].split(" ")[1].trim();
        driver.findElement(By.xpath("//button[@class='headerlabelbtn monthlabel']")).click();
        List<WebElement> months = driver.findElements(By.xpath("//table[@class='monthtable']//td"));
        for (WebElement monthActual : months) {
            if (monthActual.getText().equals(month)) {
                monthActual.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }
        driver.findElement(By.xpath("//button[@class='headerlabelbtn yearlabel']")).click();
        List<WebElement> years = driver.findElements(By.xpath("//table[@class='yeartable']//td"));
        for (WebElement actualYear : years) {
            if (actualYear.getText().equals(year)) {
                actualYear.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }
        List<WebElement> days = driver.findElements(By.xpath("//table[@class='caltable']//td//div//span"));
        for (WebElement actualDay : days) {
            if (actualDay.getText().equals(day)) {
                actualDay.click();
                //explicitWait();
                Thread.sleep(500);
                break;
            }
        }

    }
}