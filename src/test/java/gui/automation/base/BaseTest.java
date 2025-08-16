package gui.automation.base;

import gui.automation.utils.ConfigUtils;
import gui.automation.utils.DriverUtils;
import gui.automation.utils.SeleniumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void startDriver() {
        String baseUrl = ConfigUtils.get("base.url");
        driver = DriverUtils.getDriver("chrome", baseUrl);
        SeleniumUtil.setDriver(driver);
        logger.info("Navigated to URL: {}", SeleniumUtil.getDriver().getCurrentUrl());
    }

    @AfterMethod
    public void closeDriver() {
        try {
            if (SeleniumUtil.getDriver() != null) {
                SeleniumUtil.getDriver().quit();
                SeleniumUtil.setDriver(null);
                logger.info("Driver successfully closed and cleaned up");
            }
        } catch (Exception e) {
            logger.warn("Error during driver cleanup: {}", e.getMessage());
            try {
                if (SeleniumUtil.getDriver() != null) {
                    SeleniumUtil.getDriver().quit();
                }
            } catch (Exception e2) {
                logger.error("Failed to force quit driver: {}", e2.getMessage());
            } finally {
                SeleniumUtil.setDriver(null);
            }
        }
    }
}
