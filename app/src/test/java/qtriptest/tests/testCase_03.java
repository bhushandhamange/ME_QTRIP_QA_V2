package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class testCase_03 extends BaseTest {

    // WebDriver driver;

    // @BeforeSuite(alwaysRun = true)
    // public void createDriver() throws MalformedURLException {
    //     // Get the instance of WebDriverSingleton
    //     DriverSingleton singletonInstance2 = DriverSingleton.getInstance();
    //     // Get the WebDriver instance
    //     driver = singletonInstance2.getDriver();
    // }


    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, 
        groups = "Booking and Cancellation Flow")
    @Parameters({ "NewUserName", "Password", "SearchCity", "AdventureName", "GuestName" , "Date", "count"})
    public void TestCase03(String username, String password, String city, String adventure, String guestName, String date, String count) {

        WebDriver driver = getDriver();
        boolean status;

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
        status = adventureDetailsPage.reserveAdventure(guestName, date, count);

        HistoryPage historyPage = new HistoryPage(driver);         
        status = historyPage.cancelReservation();
        Assert.assertTrue(status);
                 
        loginPage.logout();   
    }
}
