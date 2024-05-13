package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import qtriptest.SeleniumWrapper;

public class AdventurePage {

    WebDriver driver;

    @FindBy (xpath = "//select[@name='duration']")
    private WebElement selectFilter;

    @FindBy (xpath = "//div[contains(@class,'d-md-flex')]/p[contains(text(),'Hours')]")
    private List<WebElement> hours;

    @FindBy (xpath = "//select[@id='category-select']")
    private WebElement selectCategory;

    @FindBy (xpath = "//div[@class='activity-card']")
    private List<WebElement> activityCards;

    @FindBy (xpath = "(//div[@class='ms-3'])[1]")
    private WebElement clearFilter;

    @FindBy (xpath = "(//div[@class='ms-3'])[2]")
    private WebElement clearCategory;

    @FindBy (xpath = "//input[@id='search-adventures']")
    private WebElement adventureBox;

    public AdventurePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean selectFilters(String filter){
        try {
            Select select = new Select(selectFilter);
            select.selectByVisibleText(filter);

            int lowerBoundary = Integer.valueOf(filter.split(" ")[0].split("-")[0]);
            int upperBoundary = Integer.valueOf(filter.split(" ")[0].split("-")[1]);

            for(WebElement hour : hours){
                int hr = Integer.valueOf(hour.getText().split(" ")[0]);
                Assert.assertTrue(hr >= lowerBoundary && hr <= upperBoundary );
            }
            SeleniumWrapper.logAndAttachScreenshot(true, "Filtered data displayed correctly");
            return true;

        } catch (Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false, "Filtered data did not displayed correctly");
            return false;
        }        
    }


    public boolean selectCategory(String category, int filtResCount){
        try{
            Select select = new Select(selectCategory);
            select.selectByVisibleText(category);

            Assert.assertTrue(activityCards.size() == filtResCount);
            SeleniumWrapper.logAndAttachScreenshot(true, "Category data displayed correctly");
            return true;

        }catch (Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false, "Category data did not displayed correctly");
            return false;
        }
    }


    public boolean removeFilters(int unFiltResCount){
        try{
            clearFilter.click();
            clearCategory.click();

            Assert.assertTrue(activityCards.size() == unFiltResCount);
            SeleniumWrapper.logAndAttachScreenshot(true, "Activity cards count is matching after removing filters");
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            SeleniumWrapper.logAndAttachScreenshot(false, "Activity cards count is not matching after removing filters");
            return false;
        }
    }


    public boolean searchAdventure(String adventure){
        try{
            adventureBox.clear();

            for (char adv : adventure.toCharArray()){
                adventureBox.sendKeys(String.valueOf(adv));
                Thread.sleep(200);
            }

            Thread.sleep(2000);
            activityCards.get(0).click();
            Thread.sleep(2000);
            SeleniumWrapper.logInfo("Search Adventure passed");
            return true;
        }catch(Exception e){
            e.printStackTrace();
            SeleniumWrapper.logFail("Search Adventure failed");
            return false;
        }
    }
}