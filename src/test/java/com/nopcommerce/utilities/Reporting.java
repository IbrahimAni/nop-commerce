package com.nopcommerce.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nopcommerce.testCases.BaseClass;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Reporting extends TestListenerAdapter {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    private final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public ExtentTest logger;
//    BaseClass baseClass = new BaseClass();

    @Override
    public void onStart(ITestContext testContext) {
        String repName = "NOP Commerce Regression Test-Report.html";

        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + repName);
        try {
            sparkReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Host name", "https://demo.nopcommerce.com/");
        extent.setSystemInfo("Environment", "Testing");
        extent.setSystemInfo("QA Engineer", "Ibrahim");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getMethod().getGroups());

        if(result.getParameters() != null && result.getParameters().length > 0){
            StringBuilder parameters = new StringBuilder();
            for(Object parameter : result.getParameters()){
                parameters.append(parameter.toString()).append(" ");
            }
            extentTest.info("Test with parameters: " + parameters);
        }
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        // Get the actual instance of the test class that contains the log messages
        Object testClassInstance = tr.getInstance();
        BaseClass actualTestClass = (BaseClass) testClassInstance;

        //Add test author
        String authorName = System.getProperty("test.author", "QA");
        ExtentTest extentTest = test.get()
                .pass(MarkupHelper.createLabel(tr.getMethod().getMethodName(), ExtentColor.GREEN))
                .assignAuthor(authorName);

        // Add test description, if available
        if (tr.getMethod().getDescription() != null) {
            extentTest.info("Description: " + tr.getMethod().getDescription());
        }

        // Add categories
        String[] categories = tr.getMethod().getGroups();
        if (categories.length > 0) {
            extentTest.assignCategory(categories);
        }

        // Add all the log messages to the extent report
        addTestLogsToReport(actualTestClass, extentTest);

        // Attach screenshot
        attachScreenshot(tr.getName(), Status.PASS);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        // Get the actual instance of the test class that contains the log messages
        Object testClassInstance = tr.getInstance();
        BaseClass actualTestClass = (BaseClass) testClassInstance;

        //Add test author
        String authorName = System.getProperty("test.author", "QA");
        ExtentTest extentTest = test.get()
                .fail(MarkupHelper.createLabel(tr.getMethod().getMethodName(), ExtentColor.RED))
                .assignAuthor(authorName);


        // Add test description, if available
        if (tr.getMethod().getDescription() != null) {
            extentTest.info("Description: " + tr.getMethod().getDescription());
        }

        // Add categories
        String[] categories = tr.getMethod().getGroups();
        if (categories.length > 0) {
            extentTest.assignCategory(categories);
        }

        // Add all the log messages to the extent report
        addTestLogsToReport(actualTestClass, extentTest);

        // Log exception details along with the stack trace
        Throwable throwable = tr.getThrowable();
        if (throwable != null) {
            StringBuilder stackTrace = new StringBuilder("<details><summary>Exception Stacktrace</summary><pre>");
            for (StackTraceElement element : throwable.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            stackTrace.append("</pre></details>");

            // Log the stack trace
            extentTest.log(Status.INFO, "Test failed with exception: " + throwable + "<br>" + stackTrace.toString());
        }

        // Attach screenshot
        attachScreenshot(tr.getName(), Status.FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        //Add test author
        String authorName = System.getProperty("test.author", "QA");
        ExtentTest extentTest = test.get()
                .skip(MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE))
                .assignAuthor(authorName);

        // Log the reason for skipping the test
        if (tr.getThrowable() != null) {
            extentTest.log(Status.SKIP, "Test skipped due to: " + tr.getThrowable().getMessage());
        } else {
            extentTest.log(Status.SKIP, "Test skipped");
        }

        // Add category annotation, if any
        String[] categories = tr.getMethod().getGroups();
        if (categories.length > 0) {
            extentTest.assignCategory(categories);
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();
    }

    private void attachScreenshot(String testName, Status status) {
        String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + testName + ".png";
        File screenshot = new File(screenshotPath);

        if (screenshot.exists()) {
            try {
                test.get().log(status, "Screenshot attached",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception e) {
                test.get().log(status, "An exception occurred while attaching a screenshot.");
            }
        } else {
            test.get().log(Status.WARNING, "Screenshot was not found at: " + screenshotPath);
        }
    }


    private void addTestLogsToReport(BaseClass testClass, ExtentTest extentTest) {
        if (testClass != null && testClass.reportLogMessage != null && !testClass.reportLogMessage.isEmpty()) {
            StringBuilder bulletPoints = new StringBuilder();
            bulletPoints.append("<div class='report-header'><h5>TEST STEPS:</h5></div>");
            bulletPoints.append("<ol class='report-steps'>");

            for (String message : testClass.reportLogMessage) {
                bulletPoints.append("<li>").append(message).append("</li>");
            }
            bulletPoints.append("</ol>");
            extentTest.info(bulletPoints.toString());
            testClass.reportLogMessage.clear();
        }
    }
}
