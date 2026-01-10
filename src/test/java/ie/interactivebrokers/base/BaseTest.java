package ie.interactivebrokers.base;

import ie.interactivebrokers.config.Config;
import ie.interactivebrokers.factory.DriverFactory;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.home.HomePage;
import ie.interactivebrokers.pages.home.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.collections.Pair;

import static ie.interactivebrokers.utils.Utils.delay;

public abstract class BaseTest {
    @BeforeMethod
    public void setUp() {
        delay(3000); // simulating a small delay between tests
        initializeBrowser();

        HomePage homePage = new HomePage();
        for (int i = 0; i < 2; i++) {
            homePage.dismissCookieModal();
            homePage.dismissNewsModal();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        closeBrowser();
    }

    private void initializeBrowser() {
        WebDriver driver = DriverFactory.getDriver();
        driver.get(Config.BASE_URL);
    }

    private void closeBrowser() {
        if (Config.LOCAL) {
            delay(3000);
        }
        DriverFactory.quitDriver();
    }

    protected DashboardPage validLogin() {
        return login(Config.USERNAME, Config.PASSWORD).first();
    }

    protected LoginPage invalidLogin(String username, String password) {
        return login(username, password).second();
    }

    private Pair<DashboardPage, LoginPage> login(String username, String password) {
        LoginPage loginPage = new HomePage().goToLoginPage();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.togglePaperTradingAccount();
        loginPage.clickLoginButton();
        DashboardPage dashboardPage = loginPage.goToDashboardPage();
        return new Pair<>(dashboardPage, loginPage);
    }
}
