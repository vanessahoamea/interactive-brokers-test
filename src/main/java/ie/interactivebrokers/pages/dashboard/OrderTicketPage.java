package ie.interactivebrokers.pages.dashboard;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static ie.interactivebrokers.utils.ActionUtils.clickWithPause;
import static ie.interactivebrokers.utils.GetUtils.getAttribute;
import static ie.interactivebrokers.utils.JavaScriptUtils.scrollToElementJS;
import static ie.interactivebrokers.utils.WaitUtils.*;

public class OrderTicketPage extends BasePage {
    private final By fromCurrencyDropdown = By.className("convert-currency-from__block");
    private final By fromCurrencyInput = By.id("from_currency_input");
    private final By fromCurrencyInputContainer = By.cssSelector("span:has(> span > #from_currency_input)");
    private final By toCurrencyDropdown = By.className("convert-currency-to__block");
    private final By toCurrencyInput = By.id("to_currency_input");
    private final By toCurrencyInputContainer = By.cssSelector("span:has(> span > #to_currency_input)");
    private final By submitButton = By.className("ccy-conv-submit-btn");
    private final By orderModal = By.cssSelector("._dlg-modal[aria-hidden=false]");
    private final By closeOrderModalButton = By.cssSelector("._dlg-modal[aria-hidden=false] button");
    private final By orderConfirmation = By.cssSelector("div[aria-label='Order Submitted Successfully']");

    public void selectSourceCurrency(String currency) {
        waitUntilVisible(fromCurrencyDropdown, 5);
        scrollToElementJS(fromCurrencyDropdown);
        clickWithPause(fromCurrencyDropdown, 1500);
        clickCurrencyOption(currency);
    }

    public void selectTargetCurrency(String currency) {
        waitUntilVisible(toCurrencyDropdown, 5);
        scrollToElementJS(toCurrencyDropdown);
        clickWithPause(toCurrencyDropdown, 1500);
        clickCurrencyOption(currency);
    }

    public void typeSourceCurrency(Double amount) {
        scrollToElementJS(fromCurrencyInput);
        waitUntilClickable(fromCurrencyInput, 5);
        set(fromCurrencyInput, amount.toString());
        delay(1000); // ensures the currency pair can be properly validated
    }

    public void typeTargetCurrency(Double amount) {
        scrollToElementJS(toCurrencyInput);
        waitUntilClickable(toCurrencyInput, 5);
        set(toCurrencyInput, amount.toString());
        delay(1000); // ensures the currency pair can be properly validated
    }

    public boolean isSourceCurrencyValid() {
        String classList = find(fromCurrencyInputContainer).getAttribute("class");
        if (classList != null) {
            return !classList.contains("_inv");
        }
        return false;
    }

    public boolean isTargetCurrencyValid() {
        String classList = find(toCurrencyInputContainer).getAttribute("class");
        if (classList != null) {
            return !classList.contains("_inv");
        }
        return false;
    }

    public boolean isSubmitButtonEnabled() {
        scrollToElementJS(submitButton);
        String disabled = getAttribute(submitButton, "disabled");
        return disabled == null || (!disabled.equals("true") && !disabled.equals("disabled"));
    }

    public void clickSubmitButton() {
        waitUntilClickable(submitButton, 5);
        scrollToElementJS(submitButton);
        click(submitButton);
    }

    public void dismissOrderModal() {
        try {
            waitUntilVisible(orderModal, 2);
            click(closeOrderModalButton);
            waitUntilInvisible(orderModal, 2);
        } catch (TimeoutException | ElementNotInteractableException | NoSuchElementException e) {
            logger.info("Order modal did not trigger for instance {}", this);
        }
    }

    public boolean isOrderSuccessful() {
        return !findAll(orderConfirmation).isEmpty();
    }

    private void clickCurrencyOption(String currency) {
        By currencyOption = By.cssSelector("li[aria-selected][data-value='" + currency + "']");
        waitUntilVisible(currencyOption, 15);
        scrollToElementJS(currencyOption);
        click(currencyOption);
    }
}
