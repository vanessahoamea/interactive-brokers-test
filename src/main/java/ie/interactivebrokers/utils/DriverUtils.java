package ie.interactivebrokers.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverUtils {
    protected static WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(DriverUtils.class);

    public static void setDriver(WebDriver driver) {
        DriverUtils.driver = driver;
    }

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Error setting {} millisecond delay", milliseconds, e);
        }
    }
}
