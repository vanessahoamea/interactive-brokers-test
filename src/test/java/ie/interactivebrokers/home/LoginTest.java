package ie.interactivebrokers.home;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.home.LoginPage;
import ie.interactivebrokers.providers.JsonDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Flaky;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class LoginTest extends BaseTest {
    @Test(dataProvider = "json-users", dataProviderClass = JsonDataProvider.class)
    @Severity(CRITICAL)
    @Epic("Home Page")
    @Story("Authentication with invalid credentials")
    @Parameters({"Username", "Password"})
    @Flaky
    public void testLoginWithInvalidCredentials(String username, String password) {
        LoginPage loginPage = invalidLogin(username, password);
        Assert.assertTrue(
                loginPage.isErrorMessageVisible(),
                "The expected error message after an unsuccessful login attempt is not visible"
        );
        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Invalid username password combination",
                "The actual and expected error messages do not match"
        );
    }

    @Test
    @Severity(CRITICAL)
    @Epic("Home Page")
    @Story("Authentication with valid credentials")
    public void testLoginWithValidCredentials() {
        DashboardPage dashboardPage = validLogin();
        Assert.assertEquals(
                dashboardPage.getPaperMoneyAlertMessage(),
                "This is not a brokerage account. This is a Paper Trading account for Simulated Trading.",
                "The actual and expected alert messages do not match"
        );
    }
}
