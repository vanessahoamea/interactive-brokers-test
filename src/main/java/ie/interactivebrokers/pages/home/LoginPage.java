package ie.interactivebrokers.pages.home;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("xyz-field-username");
    private final By passwordField = By.id("xyz-field-password");
    private final By paperMoneyToggle = By.cssSelector("label[for='toggle1']");
    private final By loginButton = By.cssSelector(".xyzblock-username-submit button[type='submit']");

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
}
