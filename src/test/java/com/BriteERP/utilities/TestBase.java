package com.BriteERP.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;

    protected ExtentReports report;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentTest extentLogger;

    @BeforeTest
    public void setUpTest() {
        report = new ExtentReports();

        String filePath = System.getProperty("user.dir") + "/test-output/report.html";
        htmlReporter = new ExtentHtmlReporter(filePath);
        report.attachReporter(htmlReporter);

        htmlReporter.config().setReportName("BriteERP automated test reports");

        report.setSystemInfo("Environment", "QA3");
        report.setSystemInfo("OS", System.getProperty("os.name"));
        report.setSystemInfo("Browser", ConfigurationReader.get("browser"));
    }

    @AfterTest
    public void tearDownTest() {
        report.flush();
    }

    @BeforeMethod
    public void setUpMethod() {
        driver = Driver.get();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10) ;

        actions = new Actions(driver);

    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws InterruptedException, IOException {
        // if the test failed
        if (result.getStatus() == ITestResult.FAILURE) {
            // record the failed test
            extentLogger.fail(result.getName());
            // take screen shot and add to report0
            String screenshotLocation = BrowserUtilities.getScreenshot(result.getName());
            extentLogger.addScreenCaptureFromPath(screenshotLocation);
            // capture the exception
            extentLogger.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentLogger.skip("Test case skipper: " + result.getName());
        }

        Thread.sleep(4000);
        Driver.closeDriver();
    }


}
