package ie.interactivebrokers.utils;

import ie.interactivebrokers.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {
    public static void scrollToElementJS(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        WebElement element = driver.findElement(locator);
        String jsScript = "arguments[0].scrollIntoView()";
        ((JavascriptExecutor) driver).executeScript(jsScript, element);
    }
}
