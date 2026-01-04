package ie.interactivebrokers.providers;

import ie.interactivebrokers.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
}
