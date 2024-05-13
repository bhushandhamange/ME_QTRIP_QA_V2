package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage {

    WebDriver driver;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailBox;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordBox;

    @FindBy(xpath = "//button[text()='Login to QTrip']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[text()='Logout']")
    private WebElement logoutButton;

    @FindBy (xpath = "//a[text()='Login Here']")
    private WebElement loginLink;


    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean login(String username, String password){
        try{

            SeleniumWrapper.sendKeys(emailBox, username);
            SeleniumWrapper.sendKeys(passwordBox, password);
            SeleniumWrapper.click(loginButton, driver);

            boolean status = SeleniumWrapper.findElementWithRetry(driver, logoutButton, 3).isDisplayed();
            Assert.assertTrue(status);
            SeleniumWrapper.logAndAttachScreenshot(status, "Login Successfull");

            return true;

        }catch(Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false, "Login failed");
            return false;
        }
        
    }

    public boolean logout(){
        try{
            SeleniumWrapper.click(logoutButton, driver);

            Assert.assertTrue(SeleniumWrapper.findElementWithRetry(driver, loginLink, 3).isDisplayed());
            SeleniumWrapper.logInfo("Logout successfull");

            return true;

        }catch(Exception e){
            e.printStackTrace();
            SeleniumWrapper.logFail("Logout failed");    
            return false;
        }
    }
    
}
