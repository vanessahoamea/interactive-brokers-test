package ie.interactivebrokers.providers;

import ie.interactivebrokers.models.Currency;
import ie.interactivebrokers.models.Quote;
import ie.interactivebrokers.models.User;
import ie.interactivebrokers.models.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ie.interactivebrokers.utils.FileUtils.getJsonFile;

public class JsonDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(JsonDataProvider.class);

    @DataProvider(name = "json-users", parallel = true)
    public static Object[][] getUsers() {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = getJsonFile("users.json");

        List<User> users = new ArrayList<>();
        try {
            users = mapper.readValue(jsonFile, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (JacksonException e) {
            logger.error("Failed to fetch data from users.json file", e);
        }

        Object[][] data = new Object[users.size()][2];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getUsername();
            data[i][1] = user.getPassword();
        }

        return data;
    }

    @DataProvider(name = "json-widgets", parallel = true)
    public static Object[][] getWidgets() {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = getJsonFile("widgets.json");

        List<Widget> widgets = new ArrayList<>();
        try {
            widgets = mapper.readValue(jsonFile, mapper.getTypeFactory().constructCollectionType(List.class, Widget.class));
        } catch (JacksonException e) {
            logger.error("Failed to fetch data from widgets.json file", e);
        }

        Object[][] data = new Object[widgets.size()][2];
        for (int i = 0; i < widgets.size(); i++) {
            Widget widget = widgets.get(i);
            data[i][0] = widget.getActive();
            data[i][1] = widget.getInactive();
        }

        return data;
    }

    @DataProvider(name = "json-currencies", parallel = true)
    public static Object[][] getCurrencies(Method method) {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = getJsonFile("currencies.json");

        List<Currency> currencies = new ArrayList<>();
        try {
            currencies = mapper.readValue(jsonFile, mapper.getTypeFactory().constructCollectionType(List.class, Currency.class));
        } catch (JacksonException e) {
            logger.error("Failed to fetch data from currencies.json file", e);
        }

        // filtering currencies based on the calling method
        Predicate<Currency> predicate = getCurrencyPredicate(method);
        currencies = currencies.stream().filter(predicate).collect(Collectors.toList());

        Object[][] data = new Object[currencies.size()][4];
        for (int i = 0; i < currencies.size(); i++) {
            Currency currency = currencies.get(i);
            data[i][0] = currency.getSourceCurrency();
            data[i][1] = currency.getTargetCurrency();
            data[i][2] = currency.getSourceAmount();
            data[i][3] = currency.getTargetAmount();
        }

        return data;
    }

    @DataProvider(name = "json-quotes", parallel = true)
    public static Object[][] getQuotes(Method method) {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = getJsonFile("quotes.json");

        List<Quote> quotes = new ArrayList<>();
        try {
            quotes = mapper.readValue(jsonFile, mapper.getTypeFactory().constructCollectionType(List.class, Quote.class));
        } catch (JacksonException e) {
            logger.error("Failed to fetch data from quotes.json file", e);
        }

        // filtering quotes based on the calling method
        Predicate<Quote> predicate = getQuotePredicate(method);
        quotes = quotes.stream().filter(predicate).collect(Collectors.toList());

        Object[][] data = new Object[quotes.size()][3];
        for (int i = 0; i < quotes.size(); i++) {
            Quote quote = quotes.get(i);
            data[i][0] = quote.getSymbol();
            data[i][1] = quote.getNameAndExchange();
            data[i][2] = quote.getQuantity();
        }

        return data;
    }

    private static Predicate<Currency> getCurrencyPredicate(Method method) {
        Predicate<Currency> predicate;

        if (method.getName().toLowerCase().contains("invalid")) {
            predicate = (currency) -> (
                    (currency.getSourceAmount() != null && currency.getSourceAmount() <= 0)
                    || (currency.getTargetAmount() != null && currency.getTargetAmount() <= 0)
            );
        } else {
            predicate = (currency) -> (
                    (currency.getSourceAmount() != null && currency.getSourceAmount() > 0)
                    || (currency.getTargetAmount() != null && currency.getTargetAmount() > 0)
            );
        }

        return predicate;
    }

    private static Predicate<Quote> getQuotePredicate(Method method) {
        Predicate<Quote> predicate;

        if (method.getName().toLowerCase().contains("sell")) {
            predicate = (quote) -> quote.getQuantity() <= 0;
        } else {
            predicate = (quote) -> quote.getQuantity() > 0;
        }

        return predicate;
    }
}
