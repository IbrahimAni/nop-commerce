package testCases;

import pageObjects.BasePage;
import pageObjects.Homepage;
import pageObjects.RegisterPage;
import pageObjects.SearchResultPage;
import testData.NewCustomerData;
import utilities.ReadConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.bonigarcia.wdm.WebDriverManager;

import javax.swing.*;

public class BaseClass extends BasePage {
    ReadConfig readConfig = new ReadConfig();
    public String baseUrl = readConfig.getApplicationURL();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public  static WebDriver driver;
    public static Logger logger;
    public WebDriverWait wait;
    public  List<String>reportLogMessage;

    public NewCustomerData newCustomerData = new NewCustomerData();

    @Parameters({"browser", "headless"})
    @BeforeClass
    public void setup(String br, String headless){
        boolean isHeadless = Boolean.parseBoolean(headless);
        reportLogMessage = new ArrayList<>();
        logger = Logger.getLogger("NOPCommerce");
        PropertyConfigurator.configure("Log4j.properties");

        ChromeOptions chromeOptions = new ChromeOptions();
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (isHeadless) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--window-size=1920,1080");

//            firefoxOptions.setHeadless(true);
        }

        switch (br) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            default:
                System.out.println("Enter the browser you want to run your test in");
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }


    public void log(String message){
        logger.info(message);
        reportLogMessage.add(message);
    }
}
