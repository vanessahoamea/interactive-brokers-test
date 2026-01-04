package ie.interactivebrokers.pages.dashboard;

import ie.interactivebrokers.pages.base.BasePage;
import org.openqa.selenium.By;

import static ie.interactivebrokers.utils.GetUtils.getText;
import static ie.interactivebrokers.utils.WaitUtils.waitUntilVisible;

public class DashboardPage extends BasePage {
    private final By paperMoneyAlert = By.cssSelector("div[role='alert'] p");

    public String getPaperMoneyAlertMessage() {
        waitUntilVisible(paperMoneyAlert, 5);
        return getText(paperMoneyAlert);
    }
}
