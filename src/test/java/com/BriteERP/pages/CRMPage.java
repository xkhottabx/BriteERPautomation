package com.BriteERP.pages;

import com.BriteERP.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static com.BriteERP.utilities.BrowserUtilities.*;

public class CRMPage extends NavigationBar {

    public CRMPage() {
        super();
    }

    WebDriverWait wait = new WebDriverWait(Driver.get(), 5);

    @FindBy(css = "button[accesskey='c']")
    public WebElement createButton;

    @FindBy(css = "div[class='modal-content']")
    public WebElement appearedWindow;

    @FindBy(css = "input[name='name']")
    public WebElement opportunityTitleInput;

    @FindBy(css = "input[name='planned_revenue']")
    public WebElement expectedRevenueInput;

    @FindBy(xpath = "//button[.='Create']")
    public WebElement create;

    @FindBy(css = "button[aria-label='list']")
    public WebElement listMode;

    @FindBy(xpath = "(//input[@type='checkbox'])[3]")
    public WebElement selectAllOpportunities;

    @FindBy(xpath = "(//button[@aria-expanded])[2]")
    public WebElement actionMenu;

    @FindBy(css = "a[data-index='3']")
    public WebElement deleteOpportunity;

    @FindBy(xpath = "//span[.='Ok']")
    public WebElement confirmDelete;

    @FindBy(css = "button[aria-label='pivot']")
    public WebElement pivotMode;

    @FindBy(xpath = "//td[.='Total']")
    public WebElement total;

    @FindBy(xpath = "//a[.='Opportunity']")
    public WebElement opportunity;


    public void createAnOpportunity(String title, String revenue) {
        createButton.click();
        wait.until(ExpectedConditions.visibilityOf(appearedWindow));
        opportunityTitleInput.sendKeys(title);
        expectedRevenueInput.clear();
        expectedRevenueInput.sendKeys(revenue);
        create.click();
        waitForUIOverlay();
    }

    public void deleteAllOpportunities() {
        listMode.click();
        waitForUIOverlay();
        selectAllOpportunities.click();
        wait.until(ExpectedConditions.visibilityOf(actionMenu));
        actionMenu.click();
        deleteOpportunity.click();
        wait.until(ExpectedConditions.visibilityOf(appearedWindow));
        confirmDelete.click();
    }

    public void goToOpportunity() {
        total.click();
        total.click();
        opportunity.click();
        waitForUIOverlay();

    }

    public void switchToMode(String mode) {
        Driver.get().findElement(By.cssSelector("button[aria-label='" + mode.toLowerCase() + "']")).click();
        waitForUIOverlay();
    }
    public List<Double> expectedRevenueColumnPivot(){
        List<String> list= getElementsText
                (Driver.get().findElements(By.xpath("//table['table-hover table-condensed table-bordered']//tbody/tr/td[2]")));
        List<Double> column=new ArrayList<>();
        for(String str:list){
            column.add(Double.parseDouble(str.replace(",","")));
        }
        return column;
    }

    public double totalExpectedRevenuePivot(){
        return expectedRevenueColumnPivot().get(0);
    }

    public double calculateTotalExpectedRevenuePivot(){
        double sum=0;
        for (int i=1; i<expectedRevenueColumnPivot().size(); i++) {
            sum+=expectedRevenueColumnPivot().get(i);
        }
        return sum;
    }

    public double bookSaleRevenueInPivot(){
        List<String> opportunity=getElementsText
                (Driver.get().findElements(By.xpath("//table['table-hover table-condensed table-bordered']//tbody/tr/td[1]")));
        List<Double> revenue=expectedRevenueColumnPivot();
        for (int i=0; i<opportunity.size(); i++){
            if (opportunity.get(i).equalsIgnoreCase("Book Sale")){
                return revenue.get(i);
            }
        }

        return 0;
    }

    public double bookSaleRevenueInList(){
        List<String> opportunity=getElementsText
                (Driver.get().findElements
                        (By.xpath("//table['o_list_view table table-condensed table-striped o_list_view_ungrouped']//tbody/tr/td[3]")));
        List<String> revenue=getElementsText
                (Driver.get().findElements
                        (By.xpath("//table['o_list_view table table-condensed table-striped o_list_view_ungrouped']//tbody/tr/td[9]")));
        for (int i=0; i<opportunity.size(); i++){
            if (opportunity.get(i).equalsIgnoreCase("Book Sale")){
                return Double.parseDouble(revenue.get(i).replace(",",""));
            }
        }

        return 0;
    }





}
