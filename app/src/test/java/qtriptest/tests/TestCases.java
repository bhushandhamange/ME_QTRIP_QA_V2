package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverFactory;
import qtriptest.DriverSingleton;
import qtriptest.DriverSingleton_New;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCases extends BaseTest{
 

    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, groups = "Login Flow")
    @Parameters({ "username", "password" })
    public void TestCase01(String username, String password) throws MalformedURLException {

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();   
        homePage.navigateToRegisterPage();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerUser(username, password, true);

        username = registerPage.lastGeneratedUserName;              
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);   
        loginPage.logout();
        
    }


    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, groups = "Search and Filter flow")
    @Parameters({ "CityName", "Category_Filter", "DurationFilter", "ExpectedFilteredResults", "ExpectedUnFilteredResults" })
    public void TestCase02(String cityName, String categoryFilter, String durationFilter, String expFiltResults, String expUnFiltResults) throws InterruptedException, MalformedURLException {

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();   
        homePage.searchForCity(cityName);
        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.selectFilters(durationFilter);
        Thread.sleep(2000);
        adventurePage.selectCategory(categoryFilter, Integer.valueOf(expFiltResults));
        Thread.sleep(2000);
        adventurePage.removeFilters(Integer.valueOf(expUnFiltResults));
    
    }

    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, 
        groups = "Booking and Cancellation Flow")
    @Parameters({ "NewUserName", "Password", "SearchCity", "AdventureName", "GuestName" , "Date", "count"})
    public void TestCase03(String username, String password, String city, String adventure, String guestName, String date, String count) throws MalformedURLException {

        WebDriver driver = getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();   
        homePage.navigateToRegisterPage();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerUser(username, password, true);

        username = registerPage.lastGeneratedUserName;
       
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        homePage.searchForCity(city);

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.searchAdventure(adventure);

        AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
        if (adventureDetailsPage.reserveAdventure(guestName, date, count)){
            HistoryPage historyPage = new HistoryPage(driver);           
            historyPage.cancelReservation();
        }         
        loginPage.logout();    

    }


    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, groups = "Reliability Flow")
    @Parameters({ "NewUserName", "Password", "dataset1", "dataset2", "dataset3"})
    public void TestCase04(String username, String password, String dataset1, String dataset2, String dataset3) throws MalformedURLException {

        WebDriver driver = getDriver();

        String city = dataset1.split(";")[0];
        String adventure = dataset1.split(";")[1];
        String guestName = dataset1.split(";")[2];
        String date = dataset1.split(";")[3];
        String count = dataset1.split(";")[4];

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();   
        homePage.navigateToRegisterPage();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registerUser(username, password, true);

        username = registerPage.lastGeneratedUserName;
       
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        homePage.searchForCity(city);

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.searchAdventure(adventure);

        AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
        adventureDetailsPage.reserveAdventure(guestName, date, count);
        
        city = dataset2.split(";")[0];
        adventure = dataset2.split(";")[1];
        guestName = dataset2.split(";")[2];
        date = dataset2.split(";")[3];
        count = dataset2.split(";")[4];

        homePage.navigateToHomePage();
        homePage.searchForCity(city);
        adventurePage.searchAdventure(adventure);

        adventureDetailsPage = new AdventureDetailsPage(driver);
        adventureDetailsPage.reserveAdventure(guestName, date, count);
        
        city = dataset3.split(";")[0];
        adventure = dataset3.split(";")[1];
        guestName = dataset3.split(";")[2];
        date = dataset3.split(";")[3];
        count = dataset3.split(";")[4];

        homePage.navigateToHomePage();
        homePage.searchForCity(city);
        adventurePage.searchAdventure(adventure);

        adventureDetailsPage = new AdventureDetailsPage(driver);
        adventureDetailsPage.reserveAdventure(guestName, date, count);

        HistoryPage historyPage = new HistoryPage(driver);
        historyPage.verifyReservation();
        
        loginPage.logout();
     
    }
}
