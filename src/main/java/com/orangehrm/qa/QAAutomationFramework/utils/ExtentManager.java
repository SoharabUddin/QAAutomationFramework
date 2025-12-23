package com.orangehrm.qa.QAAutomationFramework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Define report name and location
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Signup & Account Creation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Soharab");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}