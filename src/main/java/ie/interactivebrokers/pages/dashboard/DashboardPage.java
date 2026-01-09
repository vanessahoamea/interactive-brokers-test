package ie.interactivebrokers.pages.dashboard;

import ie.interactivebrokers.pages.base.BasePage;
import ie.interactivebrokers.pages.quote.QuotePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.Set;

import static ie.interactivebrokers.utils.ActionUtils.clickWithPause;
import static ie.interactivebrokers.utils.GetUtils.getText;
import static ie.interactivebrokers.utils.JavaScriptUtils.scrollToElementJS;
import static ie.interactivebrokers.utils.Utils.delay;
import static ie.interactivebrokers.utils.WaitUtils.*;

public class DashboardPage extends BasePage {
    private final By paperMoneyAlert = By.cssSelector("div[role='alert'] p");
    private final By loadingSkeleton = By.className("tws-skeleton");
    private final By addWidgetButton = By.xpath("//div[contains(@class, 'tws-shortcuts__col')] //button[1]");
    private final By widgetButtons = By.cssSelector(".tws-shortcuts__col > button");
    private final By widgetsModalCloseButton = By.cssSelector("._dlg-modal a");
    private final By researchButton = By.xpath("//nav //div //button[text()='Research']");
    private final By newsAndResearchButton = By.xpath("//button[text()='News & Research']");
    private final By tradeButton = By.xpath("//nav //div //button[text()='Trade']");
    private final By convertCurrencyButton = By.xpath("//button[text()='Convert Currency']");
    private final By searchBar = By.cssSelector(".sl-search-bar input");

    public String getPaperMoneyAlertMessage() {
        waitUntilVisible(paperMoneyAlert, 5);
        return getText(paperMoneyAlert);
    }

    public void clickAddWidgetButton() {
        waitUntilInvisible(loadingSkeleton, 15);
        delay(10000); // prevents data initialization issues inside the modal
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
        delay(5000); // allows the dashboard to properly re-render the data after closing the modal
        Set<String> widgetNames = new HashSet<>();

        for (WebElement element : findAll(widgetButtons)) {
            if (!element.getText().isEmpty()) {
                widgetNames.add(element.getText());
            }
        }

        return widgetNames;
    }

    public NewsPage goToNewsPage() {
        waitUntilClickable(researchButton, 5);
        click(researchButton);
        waitUntilClickable(newsAndResearchButton, 5);
        delay(1000);
        click(newsAndResearchButton);
        return new NewsPage();
    }

    public OrderTicketPage goToOrderTicketPage() {
        waitUntilClickable(tradeButton, 5);
        click(tradeButton);
        waitUntilClickable(convertCurrencyButton, 5);
        delay(1000);
        click(convertCurrencyButton);
        return new OrderTicketPage();
    }

    public QuotePage searchSymbolAndGoToQuotePage(String symbol, String nameAndExchange) {
        waitUntilClickable(searchBar, 5);
        delay(1500);
        clickWithPause(searchBar, 1500);
        set(searchBar, symbol);

        By stockOption = By.xpath("//div[@tabindex=0] //span[text()='" + nameAndExchange + "']");
        waitUntilClickable(stockOption, 15);
        click(stockOption);

        return new QuotePage();
    }
}
