package ie.interactivebrokers.dashboard;

import ie.interactivebrokers.base.BaseTest;
import ie.interactivebrokers.pages.dashboard.DashboardPage;
import ie.interactivebrokers.pages.dashboard.OrderTicketPage;
import ie.interactivebrokers.providers.JsonDataProvider;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class OrderTicketTest extends BaseTest {
    @Test(dataProvider = "json-currencies", dataProviderClass = JsonDataProvider.class)
    public void testCurrencyConversionWithInvalidData(String sourceCurrency, String targetCurrency, Double sourceAmount, Double targetAmount) {
        DashboardPage dashboardPage = validLogin();
        OrderTicketPage orderTicketPage = dashboardPage.goToOrderTicketPage();
        skipTestIfCurrencyConversionIsNotPossible(orderTicketPage);
        orderTicketPage.selectSourceCurrency(sourceCurrency);
        orderTicketPage.selectTargetCurrency(targetCurrency);

        if (sourceAmount != null) {
            orderTicketPage.typeSourceCurrency(sourceAmount);
            Assert.assertFalse(
                    orderTicketPage.isSourceCurrencyValid(),
                    "The source amount input does not have the expected error styles after entering an invalid value"
            );
        } else if (targetAmount != null) {
            orderTicketPage.typeTargetCurrency(targetAmount);
            Assert.assertFalse(
                    orderTicketPage.isTargetCurrencyValid(),
                    "The target amount input does not have the expected error styles after entering an invalid value"
            );
        }

        Assert.assertFalse(
                orderTicketPage.isSubmitButtonEnabled(),
                "The submit button is enabled despite entering invalid data"
        );
    }

    @Test(dataProvider = "json-currencies", dataProviderClass = JsonDataProvider.class)
    public void testCurrencyConversionWithValidData(String sourceCurrency, String targetCurrency, Double sourceAmount, Double targetAmount) {
        DashboardPage dashboardPage = validLogin();
        OrderTicketPage orderTicketPage = dashboardPage.goToOrderTicketPage();
        skipTestIfCurrencyConversionIsNotPossible(orderTicketPage);
        orderTicketPage.selectSourceCurrency(sourceCurrency);
        orderTicketPage.selectTargetCurrency(targetCurrency);

        if (sourceAmount != null) {
            orderTicketPage.typeSourceCurrency(sourceAmount);
            Assert.assertTrue(
                    orderTicketPage.isSourceCurrencyValid(),
                    "The source amount input has error styles despite entering a valid value"
            );
        } else if (targetAmount != null) {
            orderTicketPage.typeTargetCurrency(targetAmount);
            Assert.assertTrue(
                    orderTicketPage.isTargetCurrencyValid(),
                    "The target amount input has error styles despite entering a valid value"
            );
        }

        Assert.assertTrue(
                orderTicketPage.isSubmitButtonEnabled(),
                "The submit button is disabled despite entering valid data"
        );

        orderTicketPage.clickSubmitButton();
        orderTicketPage.dismissOrderModal();
        Assert.assertTrue(
                orderTicketPage.isOrderSuccessful(),
                "The order was not successful"
        );
    }

    private void skipTestIfCurrencyConversionIsNotPossible(OrderTicketPage orderTicketPage) {
        if (!orderTicketPage.isCurrencyConversionPossible()) {
            throw new SkipException("Skipping test because conversion requires at least one positive cash balance.");
        }
    }
}
