package gui.automation.pages;

import gui.automation.utils.SeleniumUtil;

public class BasePage {
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
