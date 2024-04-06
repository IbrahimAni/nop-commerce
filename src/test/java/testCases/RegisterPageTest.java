package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.Homepage;
import pageObjects.RegisterPage;
import pageObjects.SharedActions;

import java.io.IOException;

public class RegisterPageTest extends BaseClass {
    @BeforeMethod(groups = {"regression", "smoke"})
    public void setup() throws IOException {
        homepage = new Homepage(driver);
        registerPage = new RegisterPage(driver);
        sharedActions = new SharedActions(driver);
        homepage.clickRegisterBtn();
        log("User clicks the 'Register' link.");
        sharedActions.waitForElementVisibility(registerPage.registerPageTitle());
        log("User is navigated to the register page.");
    }

    private void fillRegistrationForm(String gender, String firstName, String lastName, String day, String month, String year, String email, String companyName, String password, String confirmPassword) {
        registerPage.selectGender(gender);
        registerPage.enterFirstName(firstName);
        registerPage.enterLastName(lastName);
        registerPage.enterDateOfBirth(day, "January", year);
        registerPage.enterEmail(email);
        registerPage.enterCompanyName(companyName);
        registerPage.enableNewsletter(true);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(confirmPassword);
        log("User enters the registration details.");
    }
    @Test(description = "User registration test case", groups = {"regression", "smoke"})
    public void registerUserSuccessfully() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName, newCustomerData.customerPassword, newCustomerData.customerPassword);

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            sharedActions.waitForElementVisibility(registerPage.registrationCompletedMsg());
            Assert.assertEquals(registerPage.getRegistrationSuccessMsg(), "Your registration completed");
            log("User is registered successfully.");
            sharedActions.captureScreen("registerUserSuccessfully");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserSuccessfully");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description = "User registration with blank password field", groups = {"regression"})
    public void registerUserWithBlankPasswordField() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName, "", newCustomerData.customerPassword);

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getPasswordErrorTxt(), "Password is required.");
            log("User unable to register with blank password field.");
            sharedActions.captureScreen("registerUserWithBlankPasswordField");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithBlankPasswordField");
            Assert.fail("Registration failed", err);
        }
    }
    @Test(description = "User registration with blank Confirm password field", groups = {"regression"})
    public void registerUserWithBlankConfirmPasswordField() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName,  newCustomerData.customerPassword, "");

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getConfirmPasswordErrorTxt(), "Password is required.");
            log("User unable to register with blank confirm password field.");
            sharedActions.captureScreen("registerUserWithBlankConfirmPasswordField");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithBlankConfirmPasswordField");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description = "User registration with unmatching password", groups = {"regression"})
    public void registerUserWithUnmatchingPassword() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName,  newCustomerData.customerPassword, "123456");

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getConfirmPasswordErrorTxt(), "The password and confirmation password do not match.");
            log("User unable to register with unmatching password and confirming password.");
            sharedActions.captureScreen("registerUserWithUnmatchingPassword");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithUnmatchingPassword");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description = "User registration with short password", groups = {"regression"})
    public void registerUserWithShortPassword() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName, "1234", "1234");

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertTrue(registerPage.getPasswordErrorTxt().contains("must have at least 6 characters"));
            log("User unable to register with less than 6 character password.");
            sharedActions.captureScreen("registerUserWithShortPassword");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithShortPassword");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description= "User registration with incorrect email format", groups = {"regression"})
    public void registerUserWithIncorrectEmailFormat() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), "john@", newCustomerData.customerCompanyName, newCustomerData.customerPassword, newCustomerData.customerPassword);

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getEmailErrorTxt(), "Wrong email");
            log("User unable to register with wrong email format "+ "'john@'" +".");
            sharedActions.captureScreen("registerUserWithIncorrectEmailFormat");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithIncorrectEmailFormat");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description = "User registration with existing email", groups = {"regression"})
    public void registerUserWithExistingEmail() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName, newCustomerData.customerPassword, newCustomerData.customerPassword);

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getExistingEmailErrorTxt(), "The specified email already exists");
            log("User unable to register with an existing email.");
            sharedActions.captureScreen("registerUserWithExistingEmail");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithExistingEmail");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description = "User registration with blank firstname field", groups = {"regression"})
    public void registerUserWithBlankFirstnameField() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, "", newCustomerData.customerLastname, newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName, newCustomerData.customerPassword, newCustomerData.customerPassword);

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getFirstNameErrorTxt(), "First name is required.");
            log("User unable to register with blank firstname field.");
            sharedActions.captureScreen("registerUserWithBlankFirstnameField");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithBlankFirstnameField");
            Assert.fail("Registration failed", err);
        }
    }

    @Test(description = "User registration with blank lastname field", groups = {"regression"})
    public void registerUserWithBlankLastnameField() throws IOException {
        try{
            fillRegistrationForm(newCustomerData.customerGender, newCustomerData.customerFirstname, "", newCustomerData.getDay(), newCustomerData.getMonth(), newCustomerData.getYear(), newCustomerData.customerEmail, newCustomerData.customerCompanyName, newCustomerData.customerPassword, newCustomerData.customerPassword);

            registerPage.clickRegisterBtn();
            log("User clicks the 'Register' button.");
            Assert.assertEquals(registerPage.getLastNameErrorTxt(), "Last name is required.");
            log("User unable to register with blank lastname field.");
            sharedActions.captureScreen("registerUserWithBlankLastnameField");
        }catch (Exception err) {
            log("Test failed");
            sharedActions.captureScreen("registerUserWithBlankLastnameField");
            Assert.fail("Registration failed", err);
        }
    }
}
