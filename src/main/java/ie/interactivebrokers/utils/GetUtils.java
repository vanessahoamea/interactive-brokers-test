package ie.interactivebrokers.utils;

import ie.interactivebrokers.factory.DriverFactory;
import org.openqa.selenium.By;

public class GetUtils extends Utils {
    public static String getText(By locator) {
        return DriverFactory.getDriver().findElement(locator).getText();
    }

    public static String getAttribute(By locator, String attribute) {
        return DriverFactory.getDriver().findElement(locator).getAttribute(attribute);
    }
}
