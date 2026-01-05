package ie.interactivebrokers.pages.home;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;

import static ie.interactivebrokers.utils.WaitUtils.*;

public class HomePage extends BasePage {
    private final By loginButton = By.id("nav-log-in");
    private final By portalLoginLink = By.className("dropdown-portal");
    private final By cookieModal = By.id("cookie-modal");
    private final By rejectAllCookiesButton = By.id("gdpr-reject-all");
    private final By newsModal = By.id("modalNewsAtIBKR");
    private final By closeNewsModalButton = By.id("newsibkrform-cancel");

    public void dismissCookieModal() {
        try {
            waitUntilVisible(cookieModal, 2);
            click(rejectAllCookiesButton);
            waitUntilInvisible(cookieModal, 2);
        } catch (TimeoutException | ElementNotInteractableException e) {
            logger.info("Cookie modal did not trigger for instance {}", this);
        }
    }

    public void dismissNewsModal() {
        try {
            waitUntilVisible(newsModal, 2);
            click(closeNewsModalButton);
            waitUntilInvisible(newsModal, 2);
        } catch (TimeoutException | ElementNotInteractableException e) {
            logger.info("News modal did not trigger for instance {}", this);
        }
    }

    public LoginPage goToLoginPage() {
        waitUntilClickable(loginButton, 5);
        click(loginButton);
        waitUntilClickable(portalLoginLink, 5);
        click(portalLoginLink);
        return new LoginPage();
    }
}
