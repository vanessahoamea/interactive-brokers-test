package ie.interactivebrokers.utils;

import ie.interactivebrokers.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class ActionUtils extends Utils {
    public static void clickWithPause(By locator, int milliseconds) {
        WebDriver driver = DriverFactory.getDriver();
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(locator)).pause(Duration.ofMillis(milliseconds)).click().perform();
    }
}
