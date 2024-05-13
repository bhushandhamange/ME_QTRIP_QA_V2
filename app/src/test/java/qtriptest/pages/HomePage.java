package qtriptest.pages;
import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage {

    WebDriver driver;

    String homePageURL = "https://qtripdynamic-qa-frontend.vercel.app/";
    String loginPageURL = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";
    String registerPageURL = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    
    @FindBy (xpath = "//input[@id='autocomplete']")
    private WebElement searchBox;

    @FindBy (xpath = "//a[text()='Login Here']")
    private WebElement loginLink;

    @FindBy (xpath = "//a[text()='Register']")
    private WebElement registerLink;

    @FindBy (xpath = "//ul[@id='results']//li")
    private WebElement autoResult;

    @FindBy (xpath = "//div[@class='activity-card']")
    private List<WebElement> activityCards;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public boolean navigateToHomePage(){    
        try{
            SeleniumWrapper.navigate(driver, homePageURL);
            SeleniumWrapper.logInfo("Navigate to Home Page");
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            SeleniumWrapper.logFail("Navigate to Home Page failed");
            return false;
        }
    }

    public boolean searchForCity(String city){
        try{
            searchBox.clear();

            for (char c : city.toCharArray()){
                searchBox.sendKeys(String.valueOf(c));
                Thread.sleep(500);
            }

            Thread.sleep(2000);
            boolean status = SeleniumWrapper.findElementWithRetry(driver, autoResult, 3).getText().equals(city);
            Assert.assertTrue(status);    
            SeleniumWrapper.logAndAttachScreenshot(status, "City present in automcomplete result");

            autoResult.click();           
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.visibilityOfAllElements(activityCards));

            return true;

        } catch (Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false, "city not present in automcomplete result");
            return false;
        }
    }


    public boolean navigateToLoginPage(){      
        try{
            SeleniumWrapper.navigate(driver, loginPageURL);
            SeleniumWrapper.logInfo("Navigate to Login Page");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            SeleniumWrapper.logFail("Navigate to Login Page failed");
            return false;
        }       
    }

    public boolean navigateToRegisterPage(){      
        try{
            SeleniumWrapper.navigate(driver, registerPageURL);
            SeleniumWrapper.logInfo("Navigate to Register Page");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            SeleniumWrapper.logFail("Navigate to Register Page");
            return false;
        }   
    }

}
