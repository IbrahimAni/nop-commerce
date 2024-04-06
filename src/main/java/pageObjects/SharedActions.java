package pageObjects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

/***
 * This class is used to store shared cations that are used across the project.
 */
public class SharedActions {
    WebDriver lDriver;


    public SharedActions(WebDriver rDriver) {
        this.lDriver = rDriver;
        PageFactory.initElements(rDriver, this);
    }

    public static final Duration  EXPLICIT_WAIT = Duration.ofSeconds(30);

    /** Method to get a WebDriverWait instance */
    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, EXPLICIT_WAIT);
    }

    /** Method to wait for an element to be visible */
    public void waitForElementVisibility(WebElement element) {
        getWait(lDriver).until(ExpectedConditions.visibilityOf(element));
    }

    /** Method to wait for an element to be clickable */
    public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {
        getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    /** Method to make the screen scroll to an element */
    public void scrollTo (WebElement element){
        new Actions(lDriver).scrollToElement(element).perform();
    }

    /** Method to generate random numbers */
    public String generateRandomNum(){
        // Random number between 100000 and 999999
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }

    /** Method to generate random string */
    public String randomString(){
        return RandomStringUtils.randomAlphabetic(5);
    }

    /** Method to capture screenshots */
    public void captureScreen(String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) lDriver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir")+"/Screenshots/"+screenshotName+".png");
        FileUtils.copyFile(source, target);
        System.out.println("Screenshot taken");
    }
}
