package com.orangehrm.qa.QAAutomationFramework.base;

import java.time.Duration;

import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.orangehrm.qa.QAAutomationFramework.utils.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver ;
	public static ExtentReports extent;
    public static ExtentTest test;
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
	
    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
    }
	@BeforeClass
	@Parameters("browser")
	public void setUp(String browser) {
		
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			driver = new ChromeDriver(options);
		}
		else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		}
		else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else {
			System.out.println("ERROR: Please pass the correct browser value in testng.xml: " + browser);
			return;
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	// CHANGED: Use @AfterMethod instead of @AfterClass to capture individual row results
    @AfterMethod
    public void tearDown(ITestResult result) {
        // This block now runs after EVERY row in your Excel sheet
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed Successfully");
        }
    }

    // KEEP: @AfterClass to close the browser only after all data rows are finished
    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
	@AfterSuite
    public void flushReport() {
        extent.flush(); // This actually writes the file to test-output/
    }
}
