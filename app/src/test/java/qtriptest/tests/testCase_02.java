package qtriptest.tests;

import qtriptest.DP;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

    
public class testCase_02 extends BaseTest{

    // WebDriver driver;

    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, groups = "Search and Filter flow")
    @Parameters({ "CityName", "Category_Filter", "DurationFilter", "ExpectedFilteredResults", "ExpectedUnFilteredResults" })
    public void TestCase02(String cityName, String categoryFilter, String durationFilter, String expFiltResults, String expUnFiltResults) throws InterruptedException {

        WebDriver driver = getDriver();
        boolean status;

        HomePage homePage = new HomePage(driver);        
        homePage.navigateToHomePage();
        homePage.searchForCity(cityName);

        AdventurePage adventurePage = new AdventurePage(driver);
        status = adventurePage.selectFilters(durationFilter);
        Thread.sleep(2000);

        status = adventurePage.selectCategory(categoryFilter, Integer.valueOf(expFiltResults));
        Thread.sleep(2000);

        status = adventurePage.removeFilters(Integer.valueOf(expUnFiltResults));
        Assert.assertTrue(status);
        
    }
}
