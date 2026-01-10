package ie.interactivebrokers.quote;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.quote.QuotePage;
import ie.interactivebrokers.providers.JsonDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class QuoteTest extends BaseTest {
    @Test(dataProvider = "json-quotes", dataProviderClass = JsonDataProvider.class)
    @Severity(CRITICAL)
    @Epic("Quote Page")
    @Story("Selling an invalid amount of shares")
    @Parameters({"Symbol", "Name and exchange", "Quantity"})
    public void testSellingWithInvalidData(String symbol, String nameAndExchange, Integer quantity) {
        DashboardPage dashboardPage = validLogin();
        QuotePage quotePage = dashboardPage.searchSymbolAndGoToQuotePage(symbol, nameAndExchange);
        quotePage.openSellPanel();
        quotePage.typeQuantity(quantity);
        quotePage.selectOrderType("market");
        quotePage.clickSellOrderButton();
        Assert.assertEquals(
                quotePage.getInvalidQuantityMessage(),
                "Order size 0 is not valid. Please enter size greater than 0.",
                "The actual and expected invalid quantity messages do not match"
        );
    }

    @Test(dataProvider = "json-quotes", dataProviderClass = JsonDataProvider.class)
    @Severity(CRITICAL)
    @Epic("Quote Page")
    @Story("Buying a valid amount of shares")
    @Parameters({"Symbol", "Name and exchange", "Quantity"})
    public void testBuyingWithValidData(String symbol, String nameAndExchange, Integer quantity) {
        DashboardPage dashboardPage = validLogin();
        QuotePage quotePage = dashboardPage.searchSymbolAndGoToQuotePage(symbol, nameAndExchange);
        quotePage.openBuyPanel();
        quotePage.typeQuantity(quantity);
        quotePage.selectOrderType("market");
        quotePage.clickBuyOrderButton();
        quotePage.dismissFeedbackModal();
        Assert.assertTrue(
                quotePage.isOrderSuccessful(),
                "The order was not successful"
        );
        Assert.assertTrue(
                quotePage.getOrderStatusMessage().contains("Bought " + quantity + " " + symbol),
                "The actual and expected order status messages do not match"
        );
    }
}
