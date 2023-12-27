package com.softwareqa.testCases;

import com.softwareqa.pageObjects.ContactPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class TC_ContactMessage_001 extends BaseClass {
    @Test
    public void senContactMessageTest() throws IOException {
        try {
            logger.info("URL is opened");
            ContactPage cp = new ContactPage(driver);
            cp.fillContactForm("Auto Test", "autotest@test.com", "This is a test message");
            logger.info("Entered contact details");
            cp.submitContactForm();
            logger.info("Contact form submitted");

            // Add a wait to ensure the message has time to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contact\"]/div/div[2]/div/span")));

            // Assert that the actual message matches the expected message
            String actualMessage = confirmationMessage.getText();
            String expectedMessage = "Message sent successfully";

            // Check if the actual message matches the expected message
            if(actualMessage.equals(expectedMessage)) {
                logger.info("Contact message test passed");
            } else {
                logger.error("Contact message test failed. Expected: '" + expectedMessage + "' but got: '" + actualMessage + "'");
                captureScreen(driver, "sendContactMessageTest"); // Take a screenshot if the message is not as expected
                Assert.fail("Contact message test failed. Expected: '" + expectedMessage + "' but got: '" + actualMessage + "'");
            }
        } catch (Exception e) {
            logger.error("Contact message test failed - " + e.getMessage());
            captureScreen(driver, "sendContactMessageTest");

            // Throw AssertionError with the message from the exception
            throw new AssertionError("Contact message test failed - " + e.getMessage());
        }
    }
}
