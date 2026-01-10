package ie.interactivebrokers.dashboard;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.providers.JsonDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Flaky;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static io.qameta.allure.SeverityLevel.MINOR;

public class DashboardTest extends BaseTest {
    @Test(dataProvider = "json-widgets", dataProviderClass = JsonDataProvider.class)
    @Severity(MINOR)
    @Epic("Dashboard Page")
    @Story("Widget modification")
    @Parameters({"Active widgets", "Inactive widgets"})
    @Flaky
    public void testWidgetModification(List<String> activeWidgets, List<String> inactiveWidgets) {
        DashboardPage dashboardPage = validLogin();
        dashboardPage.clickAddWidgetButton();

        for (String widgetName : activeWidgets) {
            dashboardPage.modifyWidget(widgetName, true);
        }
        for (String widgetName : inactiveWidgets) {
            dashboardPage.modifyWidget(widgetName, false);
        }

        dashboardPage.clickCloseWidgetsModalButton();
        Set<String> actualActiveWidgets = dashboardPage.getActiveWidgets();

        Assert.assertTrue(
                actualActiveWidgets.containsAll(activeWidgets),
                "The dashboard overview does not contain all of the expected widgets"
        );
        Assert.assertTrue(
                Collections.disjoint(actualActiveWidgets, inactiveWidgets),
                "The dashboard overview contains more widgets than expected"
        );
    }
}
