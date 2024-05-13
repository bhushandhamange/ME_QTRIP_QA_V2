package qtriptest.pages;

import qtriptest.ListenerClass;
import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AdventureDetailsPage {

    WebDriver driver;

    @FindBy (xpath = "//input[@name='name']")
    private WebElement nameBox;

    @FindBy (xpath = "//input[@name='date']")
    private WebElement dateBox;

    @FindBy (xpath = "//input[@name='person']")
    private WebElement personCountBox;

    @FindBy (xpath = "//button[@class='reserve-button']")
    private WebElement reserveButton;

    @FindBy (xpath = "//div[@id='reserved-banner']")
    private WebElement reservationSuccMessage;

    // @FindBy (xpath = "//input[@name='date']")
    // private WebElement datePicker;

    public AdventureDetailsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public Boolean reserveAdventure(String name, String date, String count){
        try {

            nameBox.sendKeys(name + Keys.TAB);
            
            selectDate(date);
          
            SeleniumWrapper.sendKeys(personCountBox, count);            
            SeleniumWrapper.click(reserveButton, driver);

            Assert.assertTrue(SeleniumWrapper.findElementWithRetry(driver, reservationSuccMessage, 3).isDisplayed());
            SeleniumWrapper.logAndAttachScreenshot(true, "Adventure reserved successfully");
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false, "Adventure reservation failed");
            return false;
        }
       
    }


    private void selectDate(String date){

        String datePart = date.split("-")[0];
        String monthPart = date.split("-")[1];
        String yearPart = date.split("-")[2];

        System.out.println("date part : "+ datePart);
        System.out.println("month part : "+ monthPart);
        System.out.println("year part : "+ yearPart);

        // dateBox.click();
        dateBox.sendKeys(datePart);
        dateBox.sendKeys("-");
        dateBox.sendKeys(monthPart);
        dateBox.sendKeys("-");
        dateBox.sendKeys(yearPart);

        System.out.println("Date Entered successfully");
    }


    
}