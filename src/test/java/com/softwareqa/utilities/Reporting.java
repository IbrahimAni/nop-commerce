package com.softwareqa.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting extends TestListenerAdapter {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    @Override
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + repName);
        try {
            sparkReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Host name", "https://ia-software-qa-engineer-portfolio.vercel.app");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("user", "Ibrahim");

        sparkReporter.config().setDocumentTitle("Ibrahim Anifowoshe - Software QA Engineer Portfolio");
        sparkReporter.config().setReportName("IA Portfolio - Test Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        logger = extent.createTest(tr.getName());
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));

        String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getName() + ".png";
        File f = new File(screenshotPath);

        if (f.exists()) {
            logger.pass("Screenshot is below:" + logger.addScreenCaptureFromPath(screenshotPath));
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        if (logger == null) {
            logger = extent.createTest(tr.getName());
        }
        logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));

        String screenshotPath = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator + tr.getName() + ".png";
        File f = new File(screenshotPath);

        if (f.exists()) {
            logger.fail("Screenshot is below:", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            logger.warning("Screenshot was not found at: " + screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        logger = extent.createTest(tr.getName());
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
