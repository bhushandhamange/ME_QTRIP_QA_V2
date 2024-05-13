package qtriptest.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {
    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(BaseTest.class.getName());

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
		options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        logger.info("create Driver");
        driverThreadLocal.set(driver);
    }

    @AfterMethod
    public void tearDown() {
        driverThreadLocal.get().quit();
        logger.info("quit Driver");
    }

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }


}

