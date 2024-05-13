package qtriptest.tests;

import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class testCase_01 extends BaseTest{
 
    // WebDriver driver;

    @Test(enabled = true, dataProvider = "data-provider", dataProviderClass = DP.class, groups = "Login Flow")
    @Parameters({ "username", "password" })
    public void TestCase01(String username, String password) {

        WebDriver driver = getDriver();
        boolean status;

        HomePage homePage = new HomePage(driver);    
        homePage.navigateToHomePage();     
        homePage.navigateToRegisterPage();

        RegisterPage registerPage = new RegisterPage(driver);
        status = registerPage.registerUser(username, password, true);
        username = registerPage.lastGeneratedUserName;
        
        LoginPage loginPage = new LoginPage(driver);
        status = loginPage.login(username, password);    
        Assert.assertTrue(status);

        loginPage.logout();
        
    }
    
}
