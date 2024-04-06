package com.nopcommerce.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
    WebDriver lDriver;

    public RegisterPage(WebDriver rDriver){
        this.lDriver = rDriver;
        PageFactory.initElements(rDriver, this);
    }

    @FindBy(how = How.XPATH, using = "//div[@class='page-title']/h1")
    @CacheLookup
    WebElement registerPageTitle;

    @FindBy(how = How.XPATH, using = "//div[@class='title']/strong")
    @CacheLookup
    WebElement title;

    @FindBy(how = How.XPATH, using = "//input[@id='gender-male']")
    @CacheLookup
    WebElement maleGender;

    @FindBy(how = How.XPATH, using = "//input[@id='gender-female']")
    @CacheLookup
    WebElement femaleGender;

    @FindBy(how = How.XPATH, using = "//input[@id='FirstName']")
    @CacheLookup
    WebElement firstNameTxt;

    @FindBy(xpath = "/html/body/div[6]/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[2]/span[1]")
    @CacheLookup
    public WebElement firstNameSpanRequired;

    @FindBy(how = How.XPATH, using = "//input[@id='LastName']")
    @CacheLookup
    WebElement lastNameTxt;

    @FindBy(xpath = "/html/body/div[6]/div[3]/div/div/div/div[2]/form/div[1]/div[2]/div[3]/span[1]")
    @CacheLookup
    public WebElement lastNameSpanRequired;

    @FindBy(xpath = "//*[@id='Email']")
    @CacheLookup
    public WebElement emailTxt;

    @FindBy(xpath = "//select[@name='DateOfBirthDay']")
    @CacheLookup
    public  WebElement day;

    @FindBy(xpath = "//select[@name='DateOfBirthMonth']")
    @CacheLookup
    public  WebElement month;

    @FindBy(xpath = "//select[@name='DateOfBirthYear']")
    @CacheLookup
    public  WebElement year;

    @FindBy(xpath = "//input[@id='Company']")
    @CacheLookup
    public  WebElement companyNameTxt;

    @FindBy(xpath = "//input[@id='Newsletter']")
    @CacheLookup
    public  WebElement newsletterCheckbox;

    @FindBy(xpath = "//input[@id='Password']")
    @CacheLookup
    public  WebElement passwordTxt;

    @FindBy(xpath = "//input[@id='ConfirmPassword']")
    @CacheLookup
    public  WebElement confirmPasswordTxt;

    @FindBy(xpath = "//div[@class='master-wrapper-content']//div[4]//div[2]//div[1]//span[1]")
    @CacheLookup
    public  WebElement passwordReq;

    @FindBy(xpath = "//div[@class='master-wrapper-content']//div[4]//div[2]//div[1]//span[1]")
    @CacheLookup
    public  WebElement confirmPasswordReq;

    @FindBy(xpath = "//button[@id='register-button']")
    @CacheLookup
    public  WebElement registerBtn;

    public void selectGender(String gender){
        if(gender.equals("Male")){
            maleGender.click();
        }else{
            femaleGender.click();
        }
    }

    public void enterFirstName(String fname){
        firstNameTxt.clear();
        firstNameTxt.sendKeys(fname);
    }




    
    
    






}
