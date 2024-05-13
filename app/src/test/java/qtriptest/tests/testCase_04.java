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

public class testCase_04 extends BaseTest {

    // WebDriver driver;

    // @BeforeSuite(alwaysRun = true)
    // public void createDriver() throws MalformedURLException {
    //     // Get the instance of WebDriverSingleton
    //     DriverSingleton singletonInstance2 = DriverSingleton.getInstance();
    //     // Get the WebDriver instance
    //     driver = singletonInstance2.getDriver();
    // }


    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, groups = "Reliability Flow")
    @Parameters({ "NewUserName", "Password", "dataset1", "dataset2", "dataset3"})
    public void TestCase04(String username, String password, String dataset1, String dataset2, String dataset3) {

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
        boolean status = historyPage.verifyReservation();
        Assert.assertTrue(status);
        
        loginPage.logout();
        
    }


}
