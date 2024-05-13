package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class RegisterPage {

    WebDriver driver;
    public String lastGeneratedUserName;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailBox;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordBox;

    @FindBy(xpath = "//input[@name='confirmpassword']")
    private WebElement confirmpasswordBox;

    @FindBy(xpath = "//button[text()='Register Now']")
    private WebElement registerButton;


    public RegisterPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public boolean registerUser(String username, String password, Boolean generateRandomUsername){
        // System.out.println("register user");
        try {           
            if (generateRandomUsername == true)
                username = username.split("@")[0] + UUID.randomUUID().toString() + "@" + username.split("@")[1];
                SeleniumWrapper.logInfo("Random username : "+ username);

            SeleniumWrapper.sendKeys(emailBox, username);
            SeleniumWrapper.sendKeys(passwordBox, password);
            SeleniumWrapper.sendKeys(confirmpasswordBox, password);
            SeleniumWrapper.click(registerButton, driver);

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("login"));

            boolean status = driver.getCurrentUrl().endsWith("login");
            Assert.assertTrue(status);
            SeleniumWrapper.logAndAttachScreenshot(status, "Registration successfull");
            lastGeneratedUserName = username;

            return true;

        } catch(Exception e) {
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false,"Registration failed");
            return false;
        }
           
    }
}
