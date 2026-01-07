package ie.interactivebrokers.quote;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.quote.QuotePage;
import ie.interactivebrokers.providers.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class QuoteTest extends BaseTest {
    @Test(dataProvider = "json-quotes", dataProviderClass = JsonDataProvider.class)
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
