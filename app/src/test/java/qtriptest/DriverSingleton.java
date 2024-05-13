package qtriptest;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSingleton {

    private static DriverSingleton instance;
    private WebDriver driver;

    // Private constructor to prevent instantiation
    private DriverSingleton() throws MalformedURLException {
        // Launch Browser using Zalenium
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
		options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        System.out.println("createDriver()");
    }

    // Public method to get the instance of WebDriverSingleton
    public static synchronized DriverSingleton getInstance() throws MalformedURLException {
        if (instance == null) {
            instance = new DriverSingleton();
        }
        return instance;
    }

    // Public method to get the WebDriver instance
    public WebDriver getDriver() {
        return driver;
    }

}