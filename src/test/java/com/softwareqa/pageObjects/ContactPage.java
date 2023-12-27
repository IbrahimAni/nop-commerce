package com.softwareqa.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ContactPage {
    WebDriver ldriver;

    public ContactPage(WebDriver rdriver){
        this.ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    @FindBy(how = How.XPATH, using = "//*[@id=\"name\"]")
    @CacheLookup
    WebElement txtContactName;

    @FindBy(how = How.XPATH, using = "//*[@id=\"email\"]")
    @CacheLookup
    WebElement txtContactEmail;

    @FindBy(how = How.XPATH, using = "//*[@id=\"message\"]")
    @CacheLookup
    WebElement txtContactMessage;

    @FindBy(how = How.XPATH, using = "//*[@id=\"contact-form\"]/div[4]/button")
    @CacheLookup
    WebElement clickSubmitBtn;

    public void fillContactForm(String name, String email, String message){
        txtContactName.sendKeys(name);
        txtContactEmail.sendKeys(email);
        txtContactMessage.sendKeys(message);
    }

    public void submitContactForm(){
        clickSubmitBtn.click();
    }
}
