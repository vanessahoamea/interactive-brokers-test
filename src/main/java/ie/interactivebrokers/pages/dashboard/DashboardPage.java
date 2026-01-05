package ie.interactivebrokers.pages.dashboard;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.Set;

import static ie.interactivebrokers.utils.GetUtils.getText;
import static ie.interactivebrokers.utils.JavaScriptUtils.scrollToElementJS;
import static ie.interactivebrokers.utils.Utils.delay;
import static ie.interactivebrokers.utils.WaitUtils.waitUntilClickable;
import static ie.interactivebrokers.utils.WaitUtils.waitUntilVisible;

public class DashboardPage extends BasePage {
    private final By paperMoneyAlert = By.cssSelector("div[role='alert'] p");
    private final By addWidgetButton = By.xpath("//div[contains(@class, 'tws-shortcuts__col')] //button[1]");
    private final By widgetButtons = By.cssSelector(".tws-shortcuts__col > button");
    private final By widgetsModalCloseButton = By.cssSelector("._dlg-modal a");

    public String getPaperMoneyAlertMessage() {
        waitUntilVisible(paperMoneyAlert, 5);
        return getText(paperMoneyAlert);
    }

    public void clickAddWidgetButton() {
        delay(3000); // prevents data initialization issues inside the modal
        waitUntilClickable(addWidgetButton, 5);
        scrollToElementJS(addWidgetButton);
        click(addWidgetButton);
    }

    public void clickCloseWidgetsModalButton() {
        waitUntilClickable(widgetsModalCloseButton, 5);
        click(widgetsModalCloseButton);
    }

    public void modifyWidget(String widgetName, boolean shouldActivate) {
        By container = By.xpath("//div[@data-draggable and .//p[text()='" + widgetName + "']]");
        By button = By.tagName("button");
        By icon = By.tagName("span");

        WebElement buttonElement = find(container).findElement(button);
        WebElement iconElement = buttonElement.findElement(icon);
        String classList = iconElement.getAttribute("class");
        String requiredClass = shouldActivate ? "fg-add" : "fg-remove";
        if (classList != null && classList.contains(requiredClass)) {
            buttonElement.click();
        }
    }

    public Set<String> getActiveWidgets() {
        delay(3000); // allows the dashboard to properly re-render the data after closing the modal
        Set<String> widgetNames = new HashSet<>();

        for (WebElement element : findAll(widgetButtons)) {
            if (!element.getText().isEmpty()) {
                widgetNames.add(element.getText());
            }
        }

        return widgetNames;
    }
}
