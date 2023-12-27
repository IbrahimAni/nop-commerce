package com.softwareqa.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class BlogPage {
    WebDriver ldriver;

    public BlogPage(WebDriver rdriver){
        this.ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    @FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/header/nav/div[3]/a[8]")
    @CacheLookup
    WebElement blogLink;

    @FindBy(how = How.XPATH, using = "//*[@id=\"email\"]")
    @CacheLookup
    WebElement txtSubscribeEmail;

    @FindBy(how = How.XPATH, using = "//*[@id=\"root\"]/div/main/div/div/div/div/form/button")
    @CacheLookup
    WebElement clickSubscribeBtn;

    public  void clickBlogLink(){
        blogLink.click();
    }

    public void fillSubscribeForm(String email){
        txtSubscribeEmail.sendKeys(email);
    }

    public void submitSubscribeForm(){
        clickSubscribeBtn.click();
    }
}
