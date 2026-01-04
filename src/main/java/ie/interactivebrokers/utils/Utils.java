package ie.interactivebrokers.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Utils {
    protected static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Error setting {} millisecond delay", milliseconds, e);
        }
    }
}
