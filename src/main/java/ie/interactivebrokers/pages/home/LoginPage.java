package ie.interactivebrokers.pages.home;

import ie.interactivebrokers.pages.base.BasePage;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import org.openqa.selenium.By;

import static ie.interactivebrokers.utils.GetUtils.getText;
import static ie.interactivebrokers.utils.WaitUtils.waitUntilInvisible;
import static ie.interactivebrokers.utils.WaitUtils.waitUntilVisible;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("xyz-field-username");
    private final By passwordField = By.id("xyz-field-password");
    private final By paperMoneyToggle = By.cssSelector("label[for='toggle1']");
    private final By loginButton = By.cssSelector(".xyzblock-username-submit button[type='submit']");
    private final By loginError = By.className("xyzblock-error");
    private final By loadingSpinner = By.className("xyzblock-loading");

    public void setUsername(String username) {
        set(usernameField, username);
    }

    public void setPassword(String password) {
        set(passwordField, password);
    }

    public void togglePaperTradingAccount() {
        click(paperMoneyToggle);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public boolean isErrorMessageVisible() {
        waitUntilInvisible(loadingSpinner, 5);
        return find(loginError).isDisplayed();
    }

    public String getErrorMessage() {
        waitUntilVisible(loginError, 5);
        return getText(loginError);
    }

    public DashboardPage goToDashboardPage() {
        waitUntilInvisible(loadingSpinner, 5);
        return new DashboardPage();
    }
}
