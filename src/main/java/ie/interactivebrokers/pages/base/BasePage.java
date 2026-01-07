package ie.interactivebrokers.pages.base;

import ie.interactivebrokers.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    protected WebElement find(By locator) {
        return DriverFactory.getDriver().findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return DriverFactory.getDriver().findElements(locator);
    }

    protected void set(By locator, String text) {
        find(locator).clear();
        find(locator).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        find(locator).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        find(locator).sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
        find(locator).sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.BACK_SPACE));
        find(locator).sendKeys(text);
    }

    protected void click(By locator) {
        find(locator).click();
    }
}
