package qtriptest;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverSingleton_New {

    private static DriverSingleton_New instance;
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Private constructor to prevent instantiation
    private DriverSingleton_New() throws MalformedURLException {
        // Launch Browser using Zalenium
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

        driver.set(new ChromeDriver(options));
        driver.get().manage().window().maximize();
        System.out.println("createDriver()");
    }

    // Public method to get the instance of WebDriverSingleton
    public static synchronized DriverSingleton_New getInstance() throws MalformedURLException {
        if (instance == null) {
            instance = new DriverSingleton_New();
        }
        return instance;
    }

    // Public method to get the WebDriver instance
    public WebDriver getDriver() {
        return driver.get();
    }

    public void killDriver(){
        driver.get().quit();
        driver.set(null);
        System.out.println("Kill Driver");

    }

}



// public class DriverSingleton_New {

//     private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

//     // Private constructor to prevent instantiation
//     public static WebDriver get() {
//         if (driver.get() == null) {
//             WebDriverManager.chromedriver().setup();
//             ChromeOptions options = new ChromeOptions();
//             options.addArguments("--remote-allow-origins=*");
//             driver.set(new ChromeDriver(options));
//             driver.get().manage().window().maximize();
//             System.out.println("create Driver()");
//         }
//         return driver.get();
//     }

    
//     public static void quit(){
//         driver.get().quit();
//         driver.set(null);
//         System.err.println("Kill driver");
//     }
   
   
// }