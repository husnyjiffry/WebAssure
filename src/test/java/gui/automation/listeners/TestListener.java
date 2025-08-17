package gui.automation.listeners;

import gui.automation.utils.ConfigUtils;
import gui.automation.utils.SeleniumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener for logging, statistics, and screenshot capture on failure.
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    static int totalTests;
    static int successTests;
    static int failureTests;
    static int skippedTests;

    public TestListener() {
        totalTests = 0;
        successTests = 0;
        failureTests = 0;
        skippedTests = 0;
    }

    public static int getTotalTests() {
        return totalTests;
    }

    public static int getSuccessTests() {
        return successTests;
    }

    public static int getFailureTests() {
        return failureTests;
    }

    public static int getSkippedTests() {
        return skippedTests;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("Test Case Started: {}", iTestResult.getName());
        totalTests += 1;
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info("Test Case Passed: {}", iTestResult.getName());
        successTests += 1;
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.error("Test Case Failed: {}", iTestResult.getName());
        failureTests += 1;
        try {
            if (SeleniumUtil.getDriver() != null) {
                String screenshotDir = ConfigUtils.get("screenshot.dir");
                if (screenshotDir == null || screenshotDir.isEmpty()) {
                    screenshotDir = "src/test/resources/screenshots/";
                }
                String screenshotPath = screenshotDir + SeleniumUtil.generateRandomNameWithTimestamp() + ".png";
                SeleniumUtil.captureScreenShot(screenshotPath);
                logger.info("Screenshot captured: {}", screenshotPath);
            }
        } catch (Exception e) {
            logger.warn("Could not capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.warn("Test Case Skipped: {}", iTestResult.getName());
        skippedTests += 1;
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        logger.info("Test Execution Summary - Total: {}, Passed: {}, Failed: {}, Skipped: {}",
                getTotalTests(), getSuccessTests(), getFailureTests(), getSkippedTests());
        try {
            if (SeleniumUtil.getDriver() != null) {
                SeleniumUtil.getDriver().quit();
                SeleniumUtil.setDriver(null);
                logger.info("Forced cleanup of remaining driver");
            }
        } catch (Exception e) {
            logger.warn("Error during forced cleanup: {}", e.getMessage());
        }
    }
}
