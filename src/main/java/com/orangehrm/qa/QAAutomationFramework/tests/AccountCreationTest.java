package com.orangehrm.qa.QAAutomationFramework.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.DataProvider;

import com.orangehrm.qa.QAAutomationFramework.base.BaseTest;
import com.orangehrm.qa.QAAutomationFramework.pages.SignupPage;
import com.orangehrm.qa.QAAutomationFramework.pages.AccountCreationPage;
import com.orangehrm.qa.QAAutomationFramework.utils.ExcelReaderUtility; 

public class AccountCreationTest extends BaseTest {
	SignupPage signupPage; 
	AccountCreationPage accountPage; 
	
	@BeforeMethod
	public void setupPages() {
		signupPage = new SignupPage(driver);
		accountPage = new AccountCreationPage(driver);
	}
    

    @DataProvider(name = "AccountData")
    public Object[][] getAccountCreationData() {
        return ExcelReaderUtility.getTestData("AccountData");
    }

    @Test(dataProvider = "AccountData", description = "DDT: Full end-to-end Account Creation scenarios")
    public void createAccountTest(
    		String title, String password, String day, String month, String year,String newsletter, String offers,
    		String firstName, String lastName, String company, String address, String address2,
    		String country, String state, String city, String zipcode, String mobileNumber,String expectedResult, String description) 
    {
    	SoftAssert softAssert = new SoftAssert();
    	test = extent.createTest("Signup Test: " + description);
    	log.info("--- Starting Test Scenario: " + description + " ---");
    	test.info("Testing Scenario: " + description);
        // 1. Arrange: Execute successful signup to reach the Account Creation Page
    	log.info("Navigating to Home Page and clicking Signup.");
        signupPage.goToHomePage();
        signupPage.clickOnSignupLoginLink();
        // Use unique, valid data for this prerequisite step
        String tempEmail = "e2e_user_" + System.currentTimeMillis() + "@test.com";
        log.info("Registering with temporary email: " + tempEmail);
        signupPage.enterSignupDetails("ValidUser", tempEmail); 
        signupPage.clickOnSubmit();

        // 2. Act: Fill out Account Creation details (from DataProvider)
        log.info("Filling Account Details for: " + firstName + " " + lastName);
        accountPage.selectTitle(title);
        accountPage.enterPassword(password);
        accountPage.selectDOB(day, month, year);
        accountPage.setCheckboxes(newsletter, offers);
        accountPage.enterFullAddressAndContact(firstName, lastName, address, company, address2, country, state, city, zipcode, mobileNumber);
        accountPage.clickRegister();
        
        if (expectedResult.equalsIgnoreCase("Success")) {
        	log.info("Validating Success Scenario.");
            String actualSuccessMsg = accountPage.getSuccessMessage();
            softAssert.assertEquals(actualSuccessMsg, "ACCOUNT CREATED!", 
                "E2E Test Failed for " + description + ": Expected success but message was incorrect.");
            test.pass("Account successfully created.");
            log.info("Account Created Successfully verified.");
            
        
        } else if (expectedResult.startsWith("Fail: Length") || expectedResult.startsWith("Fail: Future")) {
            // For negative tests (like short password or future date), assert that the registration page is still visible
        	log.warn("Validating Negative Scenario: " + description);
        	softAssert.assertFalse(driver.getCurrentUrl().contains("account_created"), 
                "Negative Test Failed for " + description + ": Expected failure, but navigated away from creation page.");
            test.pass("Negative scenario handled correctly: Submission blocked.");
            log.info("Form submission blocked as expected.");
        }
       signupPage.clickOnLogoutButton();
       log.info("User logged out. Test Scenario Complete.");
       test.info("Test completed and user logged out.");
       softAssert.assertAll();
    }
}