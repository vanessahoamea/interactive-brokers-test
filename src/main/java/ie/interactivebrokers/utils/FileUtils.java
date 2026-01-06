package ie.interactivebrokers.utils;

import ie.interactivebrokers.factory.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileUtils extends Utils {
    private static final String SOURCE_FOLDER = "src";
    private static final String DATA_FOLDER = "main/resources/data";

    public static File getJsonFile(String fileName) {
        return Paths.get(SOURCE_FOLDER, DATA_FOLDER).resolve(fileName).toFile();
    }

    public static void saveScreenshot(String testName) {
        TakesScreenshot screenshot = (TakesScreenshot) DriverFactory.getDriver();
        String directoryPath = System.getProperty("user.dir") + "/resources/screenshots/" + java.time.LocalDate.now();
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File(directoryPath + "/" + testName + ".png");

        try {
            File directory = destination.getParentFile();
            if (!directory.exists() && !directory.mkdirs()) {
                logger.error("Failed to create screenshot directory: {}", directory.getPath());
                return;
            }

            FileHandler.copy(source, destination);
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
        }
    }
}
