package com.orangehrm.qa.QAAutomationFramework.base;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtility {

    // Define standard timeouts
    public static final Duration TIMEOUT_10 = Duration.ofSeconds(10); 
    public static final Duration TIMEOUT_30 = Duration.ofSeconds(30);

    public static WebElement waitForVisibility(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT_10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public static WebElement waitForPresence(WebDriver driver, By locator, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        return waitForElementToBeClickable(driver, locator, TIMEOUT_10);
    }

    public static boolean waitForInvisibilityOfElement(WebDriver driver, By locator, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean isElementPresent(WebDriver driver, By locator, Duration timeout) {
        try {
            // We use visibilityOfElementLocated as it's generally what a user means by "present"
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); 
            return true; 
        } catch (TimeoutException | NoSuchElementException e) {
            return false; 
        }
    }
    
   
    public static boolean waitForTitleContains(WebDriver driver, String titleSubstring, Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(ExpectedConditions.titleContains(titleSubstring));
        } catch (TimeoutException e) {
            return false;
        }
    }
    public static boolean waitForUrlContains(WebDriver driver, String urlSubstring, Duration timeout) {
    	try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(ExpectedConditions.urlContains(urlSubstring));
        } catch (TimeoutException e) {
            return false;
        }
    }
    
   
    public static boolean waitForTextPresentInElement(WebDriver driver, By locator, String text, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",element);
    }
    public static void scrollAndClick(WebDriver driver,By locator) {
        WebElement element = waitForElementToBeClickable(driver,locator);
        scrollIntoView(driver,element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

}
