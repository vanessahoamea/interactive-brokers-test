package ie.interactivebrokers.factory;

import ie.interactivebrokers.config.Config;
import ie.interactivebrokers.enums.Browser;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static synchronized WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriver newDriver = createDriver();

            if (Config.LOCAL) {
                newDriver.manage().window().maximize();
            } else {
                newDriver.manage().window().setSize(new Dimension(1600, 900));
            }
            newDriver.manage().deleteAllCookies();

            driver.set(newDriver);
        }
        return driver.get();
    }

    private static WebDriver createDriver() {
        Browser browser = getSelectedBrowser();
        return switch (browser) {
            case FIREFOX -> createFirefoxDriver();
            case EDGE -> createEdgeDriver();
            default -> createChromeDriver();
        };
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (!Config.LOCAL) {
            options.addArguments("--headless");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (!Config.LOCAL) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        if (!Config.LOCAL) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    private static Browser getSelectedBrowser() {
        String browserName = Config.BROWSER != null ? Config.BROWSER : "chrome";

        try {
            return Browser.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid browser specified: {}. Defaulting to Chrome.", browserName);
            return Browser.CHROME;
        }
    }
}
