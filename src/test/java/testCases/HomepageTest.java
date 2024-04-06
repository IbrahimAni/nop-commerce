package testCases;

import pageObjects.Homepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.SharedActions;

import java.io.IOException;
import java.util.List;

public class HomepageTest extends BaseClass {
    @Test(description = "On the homepage, user can enter value in the search box and click the 'Search' button which takes then to the search result page.", groups = {"regression", "smoke"})
    public void homepageTest() throws IOException {
        try {
            log(" Homepage 'https://demo.nopcommerce.com/' is opened.");
            homepage = new Homepage(driver);
            sharedActions = new SharedActions(driver);

            log("User clicks and enter a search value in the search input field.");
            homepage.enterSearchItemValue("Cloth");
            log("User clicks the search button.");
            homepage.clickSearchBtn();
            log("User is navigated to the search result page.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='nopCommerce demo store']")));

            // Search items list
            List<WebElement> searchItems = driver.findElements(By.xpath("//div[contains(@class, 'item-box')]"));

            sharedActions.captureScreen("homepageTest");
            Assert.assertFalse(searchItems.isEmpty(), "There should be at least one element matching the class 'item-grid'");
        }catch(AssertionError | Exception  err){
            sharedActions.captureScreen("homepageTest");
            log("Test failed");
            throw err;
        }
    }
}
