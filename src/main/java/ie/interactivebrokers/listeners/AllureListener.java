package ie.interactivebrokers.listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import static ie.interactivebrokers.utils.FileUtils.saveScreenshot;

public class AllureListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            saveScreenshot(testResult.getName());
        }
    }
}
