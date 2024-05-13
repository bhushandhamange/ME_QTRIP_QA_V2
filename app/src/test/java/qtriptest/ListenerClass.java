package qtriptest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import qtriptest.tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {

    // WebDriver driver;
    private static ExtentReports extentReport;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(ListenerClass.class.getName());

    static{
        extentReport = ReportSingleton.getExtentReports();
        logger.info("create extent reports instance");
    }

    public void onStart(ITestContext context) {
        logger.info("onStart method started");
        
    }

    public void onFinish(ITestContext context) {
        logger.info("onFinish method started");
        ReportSingleton.flushExtentReports();
        extentTest.set(null);
        logger.info("flush extent reports");
    }

    public void onTestStart(ITestResult result) {

        logger.info("Test Started : "+ result.getName());
        extentTest.set(extentReport.createTest(result.getName()));
    }

    public void onTestSuccess(ITestResult result) {

        logger.info("Test Passed : "+ result.getName());
        extentTest.get().log(Status.PASS, "Test Case passed : "+ result.getName());
        // logger.info("Taking Screenshot");
        // String ssPath = takeScreenshot(result, "Test_Passed", result.getName());
        // logger.info("ssPath : "+ ssPath);
        // extentTest.get().log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(ssPath).build());

    }

    public void onTestFailure(ITestResult result) {  

        logger.info("Test Failed : "+ result.getName()+" Failure Reason : "+ result.getThrowable().toString());
        String ssPath = takeScreenshot(result, "Test_Failed", result.getThrowable().toString());
        extentTest.get().log(Status.FAIL, "Test Case failed : "+ result.getName() );
        extentTest.get().log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(ssPath).build());
    }

    public void onTestSkipped(ITestResult result) {
        logger.info("onTestSkipped Method : "+ result.getName());
        extentTest.get().log(Status.SKIP, "Test Case skipped : "+ result.getName());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("onTestFailedButWithinSuccessPercentage : "+ result.getName());
    }



    public static String takeScreenshot(ITestResult result, String screenshotType, String description) {

        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).getDriver();

        try {
            // Create "screenshots" directory if it doesn't exist
            File theDir = new File("screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(formatter);

            // Use absolute paths to avoid issues
            String baseDir = System.getProperty("user.dir");
            String fileName = String.format("screenshot_%s_%s_%s.png", timestamp, screenshotType, description);

            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

            // Use File.separator to ensure cross-platform compatibility
            String destFilePath = baseDir + File.separator + "screenshots" + File.separator + fileName;
            File destFile = new File(destFilePath);

            FileUtils.copyFile(srcFile, destFile);

            return destFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Exception occurred in takeScreenshot method");
            e.printStackTrace();
            return "";
        }
    }

    // Getter method to retrieve ExtentTest instance
    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }


}
