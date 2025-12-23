package com.orangehrm.qa.QAAutomationFramework.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.qa.QAAutomationFramework.base.BaseTest;
import com.orangehrm.qa.QAAutomationFramework.pages.SignupPage;
import com.orangehrm.qa.QAAutomationFramework.utils.ExcelReaderUtility;

public class SignupTest extends BaseTest {
	SignupPage signupPage; 
	
	@BeforeMethod
	public void openBrowser() {
		signupPage =new SignupPage(driver);
	}	
	@DataProvider(name = "SignupData")
    public Object[][] getSignupData() {
        return ExcelReaderUtility.getTestData("SignupData");
    }
	
	@Test(dataProvider = "SignupData")
	public void validateSignupScenarios(String name, String email, String expectedResult, String description) {
		test = extent.createTest("Signup Test: " + description);
		test.info("Starting test for: " + description);
		signupPage.goToHomePage();
        signupPage.clickOnSignupLoginLink(); 
        signupPage.enterSignupDetails(name, email);
        signupPage.clickOnSubmit();
        
		if (expectedResult.equalsIgnoreCase("Success")) {
            Assert.assertTrue(signupPage.isOnAccountCreationPage(), 
                "DDT Failed for " + description + ": Expected success but did not navigate to Account Creation page.");
            test.pass("Successfully navigated to Account Creation page.");
        
        } else if (expectedResult.equalsIgnoreCase("Fail: Exist")) {
            String actualError = signupPage.getErrorMessage();
            String expectedError = "Email Address already exist!";
            Assert.assertEquals(actualError, expectedError, 
                "DDT Failed for " + description + ": Did not show expected 'Email already exist' error.");
            test.pass("Correctly identified existing email error.");
            
        } else if (expectedResult.startsWith("Fail: Length")) {
            Assert.assertTrue(signupPage.isOnLoginPage(), 
                "DDT Failed for " + description + ": Expected failure, but form incorrectly proceeded.");
            test.pass("Form correctly blocked invalid name length.");
            
        
        } else if (expectedResult.startsWith("Fail: Invalid")) {
        	String actualEmailValidationTextString = signupPage.getEmailValidationMessage();
    		String exceptEmailValidationTextString = "Please include an '@' in the email address. '"+email+"' is missing an '@'.";
        	Assert.assertEquals(actualEmailValidationTextString, exceptEmailValidationTextString,
        			"DDT Failed for " + description + ": Expected failure, but form incorrectly proceeded.");
        	test.pass("Browser validation for invalid email worked correctly.");
        	
        } else {
            // Catch any unhandled expected results from the Excel sheet
        	test.fail("Invalid expected result in Excel.");
            Assert.fail("Test case setup error: Invalid expected result '" + expectedResult + "' in Excel.");
        }
	}
}
