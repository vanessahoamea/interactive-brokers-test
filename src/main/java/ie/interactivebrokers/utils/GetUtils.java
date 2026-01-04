package ie.interactivebrokers.utils;

import org.openqa.selenium.By;

public class GetUtils extends DriverUtils {
    public static String getText(By locator) {
        return driver.findElement(locator).getText();
    }
}
