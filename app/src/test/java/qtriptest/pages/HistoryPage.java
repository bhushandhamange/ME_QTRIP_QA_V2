
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HistoryPage {

    WebDriver driver;

    @FindBy (xpath = "//a[text()='Reservations']")
    private WebElement reservationLink;

    @FindBy (xpath = "//tbody[@id='reservation-table']/tr/th")
    private List<WebElement> transactionId;

    @FindBy (xpath = "//tbody[@id='reservation-table']/tr//button")
    private WebElement cancelButton;


    public HistoryPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public boolean cancelReservation(){
        boolean status = false;
        try{
            // reservationLink.click();
            
            SeleniumWrapper.click(reservationLink, driver);
            Thread.sleep(5000);
            String transaction_Id = SeleniumWrapper.findElementWithRetry(driver, transactionId.get(0), 3).getText();
            SeleniumWrapper.logInfo("Transaction Id : "+ transaction_Id);

            // cancelButton.click();
            SeleniumWrapper.click(cancelButton, driver);

            Thread.sleep(5000);

            if(transactionId.size() == 0){
                status = true;
            }

            Assert.assertTrue(status);

            SeleniumWrapper.logAndAttachScreenshot(status, "Reservation cancelled successfully");

            return true;
        }catch(Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(status, "cancel reservation failed");
            return false;
        }
    }


    public boolean verifyReservation(){
        boolean status = false;
        try{
            // reservationLink.click();
            SeleniumWrapper.click(reservationLink, driver);
            Thread.sleep(5000);

            if (transactionId.size() == 3){
                status = true;
            }
            Assert.assertTrue(status);
            SeleniumWrapper.logAndAttachScreenshot(status, "3 Reservation done successfully");
            return true;
        }catch(Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(status, "3 Reservation not found");
            return false;
        }
    }
}