package com.softwareqa.testCases;

import com.softwareqa.utilities.ReadConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    ReadConfig readConfig = new ReadConfig();
    public String baseUrl = readConfig.getApplicationURL();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public  static WebDriver driver;
    public static Logger logger;
    private static boolean apiIsUp = false;

    @Parameters("browser")
    @BeforeClass
    public void setup(String br){
        logger = Logger.getLogger("iaportfolio");
        PropertyConfigurator.configure("Log4j.properties");

        switch (br) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", readConfig.getChromePath());
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", readConfig.getFirefoxPath());
                driver = new FirefoxDriver();
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", readConfig.getIEPath());
                driver = new InternetExplorerDriver();
                break;
            default:
                System.out.println("Enter the browser you want to run your test in");
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);

        // Only call pingAPI if it has not been called before
        if (!apiIsUp) {
            pingAPI();
            apiIsUp = true; // Set flag to true so we don't ping it again
        }
    }

    // This method will ping the API to ensure it's running
    private void pingAPI() {
        try {
            int maxAttempts = 12; // Set the maximum number of attempts
            int attempt = 0;
            boolean isApiUp = false;
            long waitTimeBetweenPings = 5000; // Wait for 5 seconds between pings

            while (attempt < maxAttempts && !isApiUp) {
                try {
                    Response response = Request.Get(baseUrl + "/ping").execute();
                    int statusCode = response.returnResponse().getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        logger.info("API is up and running!");
                        isApiUp = true;
                        break; // Exit the loop if the API responds with HTTP 200
                    } else {
                        logger.info("API responded with status code: " + statusCode);
                    }
                } catch (IOException e) {
                    logger.error("Attempt " + (attempt + 1) + " to ping API failed.", e);
                }

                // Increment the attempt count and wait before retrying
                attempt++;
                if (!isApiUp) {
                    logger.info("Waiting for the API to start up... Attempt " + attempt);
                    Thread.sleep(waitTimeBetweenPings); // Sleep for 5 seconds before the next ping
                }
            }

            if (!isApiUp) {
                throw new RuntimeException("API did not start up after " + maxAttempts + " attempts.");
            }


        } catch (InterruptedException e) {
            logger.error("The API ping thread was interrupted.", e);
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new RuntimeException("API ping interrupted.");
        }
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    public void captureScreen(WebDriver driver, String tname) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir")+"/Screenshots/"+tname+".png");
        FileUtils.copyFile(source, target);
        System.out.println("Screenshot taken");
    }

    public String randomString(){
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNum(){
        // Random number between 100000 and 999999
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
}
