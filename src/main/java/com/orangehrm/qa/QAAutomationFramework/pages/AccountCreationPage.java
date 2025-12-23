package com.orangehrm.qa.QAAutomationFramework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.orangehrm.qa.QAAutomationFramework.base.WaitUtility;

public class AccountCreationPage {
	private WebDriver driver;

	public AccountCreationPage(WebDriver driver) {
		this.driver = driver;
	}

	// --- Locators ---
	private static final By mrTitleRadioBtn = By.id("id_gender1");
	private static final By mrsTitleRadioBtn = By.id("id_gender2");
	private static final By passwordInput = By.id("password");
	private static final By daysDropdown = By.id("days");
	private static final By monthsDropdown = By.id("months");
	private static final By yearsDropdown = By.id("years");
	private static final By firstNameInput = By.id("first_name");
	private static final By lastNameInput = By.id("last_name");
	private static final By address1Input = By.id("address1");
	private static final By registerButton = By.xpath("//button[normalize-space()='Create Account']");
	private static final By successMessageLocator = By.xpath("//h2[@data-qa='account-created']/b");
	private static final By newsletterCheckbox = By.id("newsletter");
	private static final By offersCheckbox = By.id("optin");
	private static final By companyInput = By.id("company");
	private static final By address2Input = By.id("address2");
	private static final By countryDropdown = By.id("country");
	private static final By stateInput = By.id("state");
	private static final By cityInput = By.id("city");
	private static final By zipcodeInput = By.id("zipcode");
	private static final By mobileNumberInput = By.id("mobile_number");


	public void selectTitle(String title) {
		if (title.equalsIgnoreCase("Mr")) {
			driver.findElement(mrTitleRadioBtn).click();
		} else if (title.equalsIgnoreCase("Mrs")) {
			driver.findElement(mrsTitleRadioBtn).click();
		} else {
			throw new IllegalArgumentException(
					"Invalid or unsupported title provided: " + title + ". Expected 'Mr' or 'Mrs'.");
		}
	}

	public void enterPassword(String password) {
		driver.findElement(passwordInput).sendKeys(password);
	}
	
	public void selectDOB(String day, String month, String year) {
		new Select(driver.findElement(daysDropdown)).selectByValue(day);
		new Select(driver.findElement(monthsDropdown)).selectByVisibleText(month);
		new Select(driver.findElement(yearsDropdown)).selectByValue(year);
	}
	
	public void setCheckboxes(String newsletter, String offers) {		
		if (newsletter.equalsIgnoreCase("TRUE") && !driver.findElement(newsletterCheckbox).isSelected()) {
			WaitUtility.scrollAndClick(driver,newsletterCheckbox);
		}
		if (offers.equalsIgnoreCase("TRUE") && !driver.findElement(offersCheckbox).isSelected()) {
			WaitUtility.scrollAndClick(driver,offersCheckbox);
		}
	}

	public void enterFullAddressAndContact(String firstName, String lastName, String address, String company, String address2, String country, String state, String city,
			String zipcode, String mobileNumber) {
		driver.findElement(firstNameInput).sendKeys(firstName);
		driver.findElement(lastNameInput).sendKeys(lastName);
		driver.findElement(companyInput).sendKeys(company);
		driver.findElement(address1Input).sendKeys(address);
		driver.findElement(address2Input).sendKeys(address2);

		new Select(driver.findElement(countryDropdown)).selectByVisibleText(country);

		driver.findElement(stateInput).sendKeys(state);
		driver.findElement(cityInput).sendKeys(city);
		driver.findElement(zipcodeInput).sendKeys(zipcode);
		driver.findElement(mobileNumberInput).sendKeys(mobileNumber);
	}


	public void clickRegister() {
		WaitUtility.scrollAndClick(driver, registerButton);
	}

	public String getSuccessMessage() {
		// You would use WaitUtility here to ensure the element is visible
		return driver.findElement(successMessageLocator).getText();
	}
}