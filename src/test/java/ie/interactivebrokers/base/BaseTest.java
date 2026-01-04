package ie.interactivebrokers.base;

import ie.interactivebrokers.config.Config;
import ie.interactivebrokers.pages.base.BasePage;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.home.HomePage;
import ie.interactivebrokers.pages.home.LoginPage;
import ie.interactivebrokers.utils.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.collections.Pair;

import static ie.interactivebrokers.utils.DriverUtils.delay;
import static ie.interactivebrokers.utils.FileUtils.saveScreenshot;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;
    protected HomePage homePage;

    @BeforeMethod
    public void setUp() {
        initializeBrowser();

        basePage = new BasePage();
        basePage.setDriver(driver);
        DriverUtils.setDriver(driver);

        homePage = new HomePage();
        for (int i = 0; i < 2; i++) {
            homePage.dismissCookieModal();
            homePage.dismissNewsModal();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            saveScreenshot(testResult.getName());
        }

        closeBrowser();
    }

    private void initializeBrowser() {
        ChromeOptions options = new ChromeOptions();
        if (!Config.LOCAL) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(Config.BASE_URL);
    }

    private void closeBrowser() {
        if (Config.LOCAL) {
            delay(3000);
        }
        driver.quit();
    }

    protected DashboardPage validLogin() {
        return login(Config.USERNAME, Config.PASSWORD).first();
    }

    protected LoginPage invalidLogin(String username, String password) {
        return login(username, password).second();
    }

    private Pair<DashboardPage, LoginPage> login(String username, String password) {
        LoginPage loginPage = homePage.goToLoginPage();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        loginPage.togglePaperTradingAccount();
        loginPage.clickLoginButton();
        DashboardPage dashboardPage = loginPage.goToDashboardPage();
        return new Pair<>(dashboardPage, loginPage);
    }
}
