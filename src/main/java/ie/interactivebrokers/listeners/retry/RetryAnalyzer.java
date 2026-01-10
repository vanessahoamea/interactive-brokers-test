package ie.interactivebrokers.listeners.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 3;
    private static final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.info("Retrying test case: {}\nArguments: {}\nAttempt number: {}", result.getName(), result.getParameters(), retryCount);
            return true;
        }
        return false;
    }
}
