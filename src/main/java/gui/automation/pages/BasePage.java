package gui.automation.pages;

import gui.automation.utils.SeleniumUtil;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Returns the current URL of the browser.
     */
    public String getCurrentUrl() {
        return SeleniumUtil.getDriver().getCurrentUrl();
    }

    /**
     * Navigates the browser back to the previous page.
     */
    public void goBack() {
        SeleniumUtil.getDriver().navigate().back();
    }

    /**
     * Refreshes the current browser page.
     */
    public void refreshPage() {
        SeleniumUtil.getDriver().navigate().refresh();
    }

    /**
     * Navigates the browser to the given URL.
     */
    public void navigateTo(String url) {
        SeleniumUtil.getDriver().get(url);
    }
}
