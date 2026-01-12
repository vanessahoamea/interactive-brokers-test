package ie.interactivebrokers.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Optional;

public class Config {
    private static final Dotenv env;
    public static final String BASE_URL;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final Boolean LOCAL;
    public static final String BROWSER;

    static {
        env = Dotenv.configure().directory(".").ignoreIfMissing().load();
        BASE_URL = get("BASE_URL");
        USERNAME = get("USERNAME");
        PASSWORD = get("PASSWORD");
        LOCAL = Boolean.valueOf(get("LOCAL"));
        BROWSER = get("BROWSER");
    }

    private static String get(String key) {
        return Optional.ofNullable(env.get(key)).orElse(System.getProperty(key));
    }
}
