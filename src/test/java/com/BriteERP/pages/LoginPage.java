package com.BriteERP.pages;

import com.BriteERP.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.BriteERP.utilities.BrowserUtilities.*;

public class LoginPage {

    public LoginPage(){
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(id="login")
    public WebElement email;

    @FindBy(id="password")
    public WebElement password;

    @FindBy (css = "button[type='submit']")
    public WebElement submit;

    public void login(String emailInput, String passwordInput){
        email.sendKeys(emailInput);
        password.sendKeys(passwordInput);
        submit.click();
        waitForUIOverlay();

    }

}
