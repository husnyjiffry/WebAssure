package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;
import org.openqa.selenium.WebDriver;

public class ElementsPage extends BasePage {
    /**
     * Returns a locator for a left menu item by its visible text.
     * This allows for parameterized, reusable menu item selection.
     */
    private By menuItemByText(String text) {
        return By.xpath("//li[contains(@class,'btn') and .//span[contains(@class,'text') and contains(text(), '" + text + "')]]");
    }

    // ----------------------
    // Visibility Check Methods
    // ----------------------
    public boolean isMenuItemVisible(String text) {
        return SeleniumUtil.waitForVisible(menuItemByText(text)) != null;
    }

    // ----------------------
    // Click Methods
    // ----------------------

    /**
     * Beginner Tip:
     * This method tries to click the menu item after hiding the fixed banner and closing known popups.
     * Use this for simple cases where overlays may block the click, but scrolling is not needed.
     */
    public void clickMenuItem(String text) {
        hideFixedBanner();
        SeleniumUtil.closeKnownPopups();
        SeleniumUtil.click(menuItemByText(text));
    }

    /**
     * Beginner Tip:
     * This method tries to click the menu item. If a click is intercepted (e.g., by an ad popup),
     * it checks if the URL contains '#google_vignette', closes the popup, waits for the URL to clear, and retries the click.
     * Use this when ad popups may appear and block the click.
     */
    public void clickMenuItemWithAdHandling(String text) {
        try {
            SeleniumUtil.click(menuItemByText(text));
        } catch (Exception e) {
            if (SeleniumUtil.getDriver().getCurrentUrl().contains("#google_vignette")) {
                SeleniumUtil.closeKnownPopups();
                SeleniumUtil.waitForUrlDoesNotContain("#google_vignette", 10); // Use default wait time
                SeleniumUtil.click(menuItemByText(text));
            } else {
                throw e;
            }
        }
    }

    /**
     * Beginner Tip:
     * This method tries to click the menu item, scrolling into view first, then handling ads/popups if needed.
     * Use this for robust clicking when elements may be covered or require scrolling.
     */
    public void clickMenuItemWithAdAndScrollHandling(String text) {
        try {
            SeleniumUtil.scrollTo(menuItemByText(text));
            SeleniumUtil.waitSeconds(10); // For visual observation
            SeleniumUtil.click(menuItemByText(text));
        } catch (org.openqa.selenium.ElementNotInteractableException e) {
            hideFixedBanner();
            SeleniumUtil.closeKnownPopups();
            SeleniumUtil.click(menuItemByText(text));
        }
    }

    // ----------------------
    // Utility Methods
    // ----------------------

    /**
     * Utility method to hide the fixed banner ad that may cover elements on the page.
     * Uses JavaScript to set the banner's display to 'none' if present.
     */
    public void hideFixedBanner() {
        try {
            ((org.openqa.selenium.JavascriptExecutor) SeleniumUtil.getDriver())
                    .executeScript("var el = document.getElementById('fixedban'); if (el) el.style.display='none';");
        } catch (Exception ignored) {
        }
    }

    public ElementsPage(WebDriver driver) {
        super(driver);
    }
}
