package com.softwareqa.testCases;

import com.softwareqa.pageObjects.BlogPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class TC_BlogSubscribe_001 extends BaseClass{
    @Test
    public void blogSubscribeTest() throws IOException {
        String email = randomString();
        try {
            logger.info("URL is opened");
            BlogPage bp = new BlogPage(driver);
            bp.clickBlogLink();
            logger.info("Clicked blog link");
            bp.fillSubscribeForm(email+"@test.com");
            logger.info("Filled subscribe form");
            bp.submitSubscribeForm();
            logger.info("Submitted subscribe form");

            // Add a wait to ensure the message has time to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/main/div/div/div/div/div/span")));

            // Assert that the actual message matches the expected message
            String actualMessage = confirmationMessage.getText();
            String expectedMessage = "Successfully Subscribed";

            // Check if the actual message matches the expected message
            if(actualMessage.contains(expectedMessage)) {
                logger.info("Blog subscribe test passed");
            } else {
                logger.error("Blog subscribe test failed. Expected: '" + expectedMessage + "' but got: '" + actualMessage + "'");
                captureScreen(driver, "BlogSubscribeTest"); // Take a screenshot if the message is not as expected
                Assert.fail("Blog subscribe test failed. Expected: '" + expectedMessage + "' but got: '" + actualMessage + "'");
            }
        } catch (AssertionError e) {
            captureScreen(driver, "BlogSubscribeTest");
            logger.error("Blog subscribe test failed", e);
            throw e; // Rethrow the exception to ensure the test is marked as failed
        }
    }
}
