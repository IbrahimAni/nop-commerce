package com.nopcommerce.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SearchResultPage {
    WebDriver lDriver;

    public SearchResultPage(WebDriver rDriver){
        this.lDriver = rDriver;
        PageFactory.initElements(rDriver, this);
    }

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'item-box')]")
    @CacheLookup
    WebElement searchResultGrid;



}
