package qtriptest;

import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private WebDriver driver;

    // Private constructor to prevent instantiation
    public DriverFactory() throws MalformedURLException {
        // Launch Browser using Zalenium
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        System.out.println("create Driver");
    }

    
    // Public method to get the WebDriver instance
    public WebDriver getDriver() {    
        System.out.println("Get Driver");
        return driver;
    }

    public void killDriver(){
        driver.quit();
        System.out.println("kill Driver");
    }

}