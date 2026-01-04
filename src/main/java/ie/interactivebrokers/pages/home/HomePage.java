package ie.interactivebrokers.pages.home;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;

import static ie.interactivebrokers.utils.WaitUtils.*;

public class HomePage extends BasePage {
    private final By loginButton = By.id("nav-log-in");
    private final By portalLoginLink = By.className("dropdown-portal");
    private final By cookieModal = By.id("cookie-modal");
    private final By rejectAllCookiesButton = By.id("gdpr-reject-all");

    public HomePage dismissCookieModal() {
        waitUntilVisible(cookieModal, 5);
        click(rejectAllCookiesButton);
        waitUntilInvisible(cookieModal, 5);
        return this;
    }

    public LoginPage goToLoginPage() {
        waitUntilClickable(loginButton, 5);
        click(loginButton);
        click(portalLoginLink);
        return new LoginPage();
    }
}
