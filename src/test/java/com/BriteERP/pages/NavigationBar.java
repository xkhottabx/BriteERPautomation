package com.BriteERP.pages;

import com.BriteERP.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class NavigationBar {

    public NavigationBar(){
        PageFactory.initElements(Driver.get(), this);
    }

@FindBy(linkText = "CRM")
    public WebElement goToCRM;



}
