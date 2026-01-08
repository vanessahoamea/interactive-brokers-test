package ie.interactivebrokers.pages.quote;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static ie.interactivebrokers.utils.ActionUtils.clickWithPause;
import static ie.interactivebrokers.utils.GetUtils.getText;
import static ie.interactivebrokers.utils.JavaScriptUtils.scrollToElementJS;
import static ie.interactivebrokers.utils.WaitUtils.*;

public class QuotePage extends BasePage {
    private final By loadingSpinner = By.className("quote-chart__loading");
    private final By buyButton = By.cssSelector("button.pane-action-btn-lg.buy");
    private final By sellButton = By.cssSelector("button.pane-action-btn-lg.sell");
    private final By progressBar = By.className("_plint-enter-active");
    private final By quantityInput = By.xpath("//label[text()='Quantity'] //following-sibling::div //input");
    private final By orderTypeDropdown = By.xpath("//label[text()='Order Type'] //following-sibling::div //span[@role='combobox']");
    private final By buyOrderButton = By.id("cp-submit-order-Buy-btn");
    private final By sellOrderButton = By.id("cp-submit-order-Sell-btn");
    private final By invalidQuantityMessage = By.cssSelector("div._dlg-alert p");
    private final By feedbackModal = By.cssSelector("div[aria-hidden=false] div.feedbackApp");
    private final By dismissFeedbackModalButton = By.xpath("//div[@class='feedbackApp'] //button[text()='Dismiss']");
    private final By orderSubmissionMessage = By.xpath("//p[text()='Your order has been submitted']");
    private final By orderConfirmationMessage = By.xpath("//p[text()='Your order has been filled']");
    private final By orderStatusMessage = By.className("order_ticket__status-text");

    public void openBuyPanel() {
        waitUntilInvisible(loadingSpinner, 5);
        waitUntilClickable(buyButton, 5);
        click(buyButton);
    }

    public void openSellPanel() {
        waitUntilInvisible(loadingSpinner, 5);
        waitUntilClickable(sellButton, 5);
        click(sellButton);
    }

    public void typeQuantity(Integer quantity) {
        waitUntilInvisible(progressBar, 5);
        waitUntilClickable(quantityInput, 5);
        delay(3000);
        set(quantityInput, quantity.toString());
    }

    public void selectOrderType(String orderType) {
        waitUntilClickable(orderTypeDropdown, 5);
        clickWithPause(orderTypeDropdown, 1500);

        By marketOption = By.cssSelector("li[aria-selected][data-value='" + orderType + "']");
        waitUntilClickable(marketOption, 5);
        scrollToElementJS(marketOption);
        click(marketOption);
    }

    public void clickBuyOrderButton() {
        waitUntilClickable(buyOrderButton, 5);
        scrollToElementJS(buyOrderButton);
        click(buyOrderButton);
    }

    public void clickSellOrderButton() {
        waitUntilClickable(sellOrderButton, 5);
        scrollToElementJS(sellOrderButton);
        click(sellOrderButton);
    }

    public String getInvalidQuantityMessage() {
        waitUntilVisible(invalidQuantityMessage, 5);
        return getText(invalidQuantityMessage);
    }

    public void dismissFeedbackModal() {
        try {
            waitUntilVisible(feedbackModal, 2);
            click(dismissFeedbackModalButton);
            waitUntilInvisible(feedbackModal, 2);
        } catch (TimeoutException | ElementNotInteractableException | NoSuchElementException e) {
            logger.info("Feedback modal did not trigger for instance {}", this);
        }
    }

    public boolean isOrderSuccessful() {
        waitUntilInvisible(orderSubmissionMessage, 15);
        delay(5000);
        return find(orderConfirmationMessage).isDisplayed();
    }

    public String getOrderStatusMessage() {
        waitUntilVisible(orderStatusMessage, 5);
        return getText(orderStatusMessage);
    }
}
