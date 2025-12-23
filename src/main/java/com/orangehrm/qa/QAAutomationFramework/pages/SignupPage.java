package com.orangehrm.qa.QAAutomationFramework.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.orangehrm.qa.QAAutomationFramework.base.WaitUtility;

public class SignupPage {
	private WebDriver driver;
	private static final String BASE_STRING = "https://automationexercise.com/";
	public SignupPage(WebDriver driver) {
		this.driver = driver;
	}

	private static final By signUpLocator = By.xpath("//a[contains(text(),'Signup')]");
	private static final By logoutLocator = By.xpath("//a[contains(text(),'Logout')]");
	private static final By nameInputLocator = By.name("name");
	private static final By emailInputLocator = By.xpath("//form[@action='/signup']/input[@name='email']");
	private static final By signupButtonLocator = By.xpath("//button[text()='Signup']");
	private static final By ERROR_MESSAGE_BY = By.xpath("//form[@action='/signup']/p");
	
	public void goToHomePage() {
		driver.get(BASE_STRING);
	}
	public void clickOnSignupLoginLink() {
		driver.findElement(signUpLocator).click();
	}
	
	public void enterSignupDetails(String name, String email) {
	    System.out.println("Attempting to sign up with Name: " + name + " and Email: " + email);
	    driver.findElement(nameInputLocator).sendKeys(name);
	    driver.findElement(emailInputLocator).sendKeys(email);    
	}
	
	public void clickOnSubmit() {
		driver.findElement(signupButtonLocator).click();
	}
	
	
	public String getEmailValidationMessage() {
        WebElement emailInput = driver.findElement(emailInputLocator);
        return emailInput.getAttribute("validationMessage"); 
    }
	public String getErrorMessage() {
		if(WaitUtility.isElementPresent(driver, ERROR_MESSAGE_BY, Duration.ofSeconds(5))) {
			return driver.findElement(ERROR_MESSAGE_BY).getText();
		}
		else {
			return null;
		}
	}
	public boolean isOnAccountCreationPage() {
		if(WaitUtility.waitForUrlContains(driver, "signup", Duration.ofSeconds(5))) {
	        return true;
	    } else {
	        return false;
	    }
	}
	public boolean isOnLoginPage() {
		if(WaitUtility.waitForUrlContains(driver, "login", Duration.ofSeconds(5))) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public void clickOnLogoutButton() {
		driver.get(BASE_STRING);
		WebElement element =  WaitUtility.waitForElementToBeClickable(driver, logoutLocator);
		element.click();
		System.out.println("Logged out");
	}
}
