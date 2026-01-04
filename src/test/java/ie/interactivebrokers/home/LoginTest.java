package ie.interactivebrokers.home;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.config.Config;
import ie.interactivebrokers.providers.JsonDataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(dataProvider = "json-users", dataProviderClass = JsonDataProvider.class)
    public void testLoginWithInvalidCredentials(String username, String password) {
        login(username, password);
        // TODO
    }

    @Test
    public void testLoginWithValidCredentials() {
        login(Config.USERNAME, Config.PASSWORD);
        // TODO
    }
}
