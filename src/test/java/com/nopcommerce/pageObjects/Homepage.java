package com.nopcommerce.pageObjects;

import com.nopcommerce.utilities.locators.HomepageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Homepage {
    WebDriver lDriver;

    public Homepage(WebDriver rDriver) {
        this.lDriver = rDriver;
        PageFactory.initElements(rDriver, this);
    }

    @FindBy(how = How.XPATH, using = "//img[@element-id='358']")
    @CacheLookup
    WebElement nopCommerceLogo;

    @FindBy(how = How.XPATH, using = "//input[@id='small-searchterms']")
    @CacheLookup
    WebElement searchInputField;

    @FindBy(how = How.XPATH, using = "//*[@id='small-search-box-form']/button")
    @CacheLookup
    WebElement searchBtn;

    @FindBy(how = How.XPATH, using = "//a[@class='ico-register']")
    @CacheLookup
    WebElement registerBtn;

    public void enterSearchItemValue(String searchValue){
        searchInputField.sendKeys(searchValue);
    }

    public void clickSearchBtn(){
        searchBtn.click();
    }

    public WebElement isNopCommerceLogoIsVisible(){
        return nopCommerceLogo;
    }

    public void clickRegisterBtn(){
        registerBtn.click();
    }
}
