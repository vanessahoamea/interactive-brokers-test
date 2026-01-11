package ie.interactivebrokers.factory;

import ie.interactivebrokers.config.Config;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
        }
        return driver.get();
    }

    private static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        if (!Config.LOCAL) {
            options.addArguments("--headless");
        }
        WebDriver driver = new ChromeDriver(options);

        if (Config.LOCAL) {
            driver.manage().window().maximize();
        } else {
            driver.manage().window().setSize(new Dimension(1600, 700));
        }
        driver.manage().deleteAllCookies();

        return driver;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
