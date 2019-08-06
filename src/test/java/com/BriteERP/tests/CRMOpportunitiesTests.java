package com.BriteERP.tests;

import com.BriteERP.pages.CRMPage;
import com.BriteERP.pages.LoginPage;
import com.BriteERP.utilities.ConfigurationReader;
import com.BriteERP.utilities.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.BriteERP.utilities.BrowserUtilities.waitForUIOverlay;

public class CRMOpportunitiesTests extends TestBase {

    @BeforeMethod
    public void goToCRM() {
        driver.get(ConfigurationReader.get("url"));
        LoginPage loginPage=new LoginPage();
        loginPage.login(ConfigurationReader.get("eventsCRMManager_username"), ConfigurationReader.get("eventsCRMManager_password"));
        waitForUIOverlay();
        CRMPage crmPage=new CRMPage();
        crmPage.goToCRM.click();
        waitForUIOverlay();
        crmPage.createAnOpportunity("Astodon", "120");
        crmPage.createAnOpportunity("Book Sale", "600");
        crmPage.createAnOpportunity("Master", "450");
        crmPage.switchToMode("pivot");
        crmPage.goToOpportunity();
    }

    @Test
    public void test01_Revenue(){
        CRMPage crmPage=new CRMPage();
        extentLogger = report.createTest("Comparing 'Expected revenue' values for Book Sale in PIVOT and LIST modes");
        extentLogger.info("Getting Expected revenue for Book Sale in Pivot Mode.");
        double revenuePivot=crmPage.bookSaleRevenueInPivot();
        extentLogger.info("Value = "+revenuePivot);
        extentLogger.info("Switching to List mode");
        crmPage.switchToMode("list");
        extentLogger.info("Getting Expected revenue for Book Sale in List Mode.");
        double revenueList=crmPage.bookSaleRevenueInList();
        extentLogger.info("Value = "+revenueList);
        Assert.assertEquals(revenuePivot, revenueList, "Values in Pivot and List are not equal");
    }

    @Test
    public void test02_checkSumRevenue(){
        CRMPage crmPage=new CRMPage();
        extentLogger = report.createTest("Comparing Total value of Revenues and sum of all revenues in the table");
        extentLogger.info("Total value: "+crmPage.totalExpectedRevenuePivot()+"." +
                        " \tCalculated sum of all revenues: "+crmPage.calculateTotalExpectedRevenuePivot());
        Assert.assertEquals(crmPage.totalExpectedRevenuePivot(), crmPage.calculateTotalExpectedRevenuePivot(), "Total does not match");
    }



    @AfterMethod
    public void deleteOpportunities(){
        waitForUIOverlay();
        CRMPage crmPage=new CRMPage();
        crmPage.deleteAllOpportunities();
    }

}
