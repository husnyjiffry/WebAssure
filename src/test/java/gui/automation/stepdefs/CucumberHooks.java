package gui.automation.stepdefs;

import gui.automation.utils.DriverUtils;
import gui.automation.utils.SeleniumUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class CucumberHooks {
    @Before
    public void setUp() {
        WebDriver driver = DriverUtils.getDriver("chrome", "https://demoqa.com/");
        SeleniumUtil.setDriver(driver);
    }

    @After
    public void tearDown() {
        if (SeleniumUtil.getDriver() != null) {
            SeleniumUtil.getDriver().quit();
            SeleniumUtil.setDriver(null);
        }
    }
}
