package qtriptest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


public class SeleniumWrapper {

    private static final Logger logger = LogManager.getLogger(SeleniumWrapper.class.getName());

    public static boolean click(WebElement element, WebDriver driver){
        try{
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(element));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500); 
            element.click();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendKeys(WebElement input, String keysToSend){
        try{
            input.clear();
            input.sendKeys(keysToSend);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean navigate(WebDriver driver, String url){
        try{
            if( ! driver.getCurrentUrl().equals(url)){
                driver.get(url);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static WebElement findElementWithRetry(WebDriver driver, WebElement element, int retryCount){
        boolean elementAvailable = false;
        try{
            WebDriverWait wait = new WebDriverWait(driver, 10);

            for (int i = 1; i <= retryCount ; i++){
                wait.until(ExpectedConditions.visibilityOf(element));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                Thread.sleep(500); 
                if (element.isDisplayed()){
                    elementAvailable = true;    
                    break;
                }             
            }
            if(elementAvailable){
                return element;
            } else {
                return null;
            }        
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


     
    public static void logAndAttachScreenshot(boolean status, String description) {

        logger.info(description);
        
        ExtentTest extentTest = ListenerClass.getExtentTest();
        ITestResult result = Reporter.getCurrentTestResult();

        String ssType;
        Status logStatus;
         if(status) {
            ssType = "Step_Passed";
            logStatus = Status.PASS;
        } else {
            ssType = "Step_Failed";
            logStatus = Status.FAIL;
        }
        String ssPath = ListenerClass.takeScreenshot(result, ssType, description);
        extentTest.log(logStatus, description);
        extentTest.log(logStatus, MediaEntityBuilder.createScreenCaptureFromPath(ssPath).build());
    }

    public static void logInfo(String description){
        logger.info(description);
        ExtentTest extentTest = ListenerClass.getExtentTest();
        extentTest.log(Status.INFO, description);
    }


    public static void logFail(String description){
        logger.info(description);
        ExtentTest extentTest = ListenerClass.getExtentTest();
        extentTest.log(Status.FAIL, description);
    }

}
