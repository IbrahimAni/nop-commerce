package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class RegisterPage {
    WebDriver lDriver;

    public RegisterPage(WebDriver rDriver){
        this.lDriver = rDriver;
        PageFactory.initElements(rDriver, this);
    }

    @FindBy(how = How.XPATH, using = "//div[@class='page-title']/h1")
    @CacheLookup
    WebElement registerPageTitleWebElement;

    @FindBy(how = How.XPATH, using = "//div[@class='title']/strong")
    @CacheLookup
    WebElement title;

    @FindBy(xpath = "//input[@name='Gender']")
    @CacheLookup
    List<WebElement> customerGender;

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
    public  WebElement dayDropdown;

    @FindBy(xpath = "//select[@name='DateOfBirthMonth']")
    @CacheLookup
    public  WebElement monthDropdown;

    @FindBy(xpath = "//select[@name='DateOfBirthYear']")
    @CacheLookup
    public  WebElement yearDropdown;

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

    @FindBy(xpath = "//span[@id='FirstName-error']")
    @CacheLookup
    public  WebElement firstnameErr;

    @FindBy(xpath = "//span[@id='LastName-error']")
    @CacheLookup
    public  WebElement lastnameErr;

    @FindBy(xpath = "//span[@id='Password-error']")
    @CacheLookup
    public  WebElement passwordErr;

    @FindBy(xpath = "//span[@id='Email-error']")
    @CacheLookup
    public  WebElement emailErr;

    @FindBy(xpath = "//li[normalize-space()='The specified email already exists']")
    @CacheLookup
    public  WebElement existingEmailErr;

    @FindBy(xpath = "//span[@id='ConfirmPassword-error']")
    @CacheLookup
    public  WebElement confirmPasswordErr;

    @FindBy(xpath = "//button[@id='register-button']")
    @CacheLookup
    public  WebElement registerBtn;

    @FindBy(xpath = "//div[@class='result']")
    @CacheLookup
    public  WebElement registrationCompletedMsgWebElement;

    public WebElement registerPageTitle(){
        return registerPageTitleWebElement;
    }

    public WebElement registrationCompletedMsg() {
        return registrationCompletedMsgWebElement;
    }

    public void selectGender(String gender){
       for(WebElement e: customerGender){
          if(e.getText().equalsIgnoreCase(gender)){
                e.click();
          }
       }
    }

    public void enterFirstName(String fname){
        firstNameTxt.clear();
        firstNameTxt.sendKeys(fname);
    }

    public void enterLastName(String lname) {
        lastNameTxt.clear();
        lastNameTxt.sendKeys(lname);
    }

    public void enterDateOfBirth(String day, String month, String year) {
        new Select(dayDropdown).selectByVisibleText(day);
        new Select(monthDropdown).selectByVisibleText(month);
        new Select(yearDropdown).selectByVisibleText(year);
    }

    public void enterEmail(String email){
        emailTxt.clear();
        emailTxt.sendKeys(email);
    }

    public void enterCompanyName(String companyName){
        companyNameTxt.clear();
        companyNameTxt.sendKeys(companyName);
    }

    public void enableNewsletter(boolean shouldEnableNewsletter) {
        boolean isChecked = newsletterCheckbox.isSelected();
        if (shouldEnableNewsletter && !isChecked) {
            newsletterCheckbox.click();
        }else if(!shouldEnableNewsletter && isChecked) {
            newsletterCheckbox.click();
        }
    }

    public void enterPassword(String password) {
        passwordTxt.clear();
        passwordTxt.sendKeys(password);
    }


    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordTxt.clear();
        confirmPasswordTxt.sendKeys(confirmPassword);
    }


    public void clickRegisterBtn() {
        registerBtn.click();
    }

    public String getRegistrationSuccessMsg() {
        return registrationCompletedMsgWebElement.getText();
    }

    public String getPasswordErrorTxt(){
        return passwordErr.getText();
    }

    public String getConfirmPasswordErrorTxt() {
        return confirmPasswordErr.getText();
    }

    public String getEmailErrorTxt() {
        return emailErr.getText();
    }

    public String getFirstNameErrorTxt() {
        return firstnameErr.getText();
    }

    public String getLastNameErrorTxt() {
        return lastnameErr.getText();
    }

    public String getExistingEmailErrorTxt() {
        return existingEmailErr.getText();
    }

}
