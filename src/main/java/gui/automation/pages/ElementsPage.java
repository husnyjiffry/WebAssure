package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;

public class ElementsPage extends BasePage {
    // Parameterized locator for left menu items by visible text
    private By menuItemByText(String text) {
        return By.xpath("//li[contains(@class,'btn') and .//span[contains(@class,'text') and contains(text(), '" + text + "')]]");
    }

    public boolean isMenuItemVisible(String text) {
        return SeleniumUtil.waitForVisible(menuItemByText(text)) != null;
    }

    public void hideFixedBanner() {
        try {
            ((org.openqa.selenium.JavascriptExecutor) SeleniumUtil.getDriver())
                    .executeScript("var el = document.getElementById('fixedban'); if (el) el.style.display='none';");
        } catch (Exception ignored) {
        }
    }

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
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            if (SeleniumUtil.getDriver().getCurrentUrl().contains("#google_vignette")) {
                SeleniumUtil.closeKnownPopups();
                SeleniumUtil.waitForUrlDoesNotContain("#google_vignette", 5);
            }
            SeleniumUtil.click(menuItemByText(text));
        }
    }

    /**
     * Beginner Tip:
     * This method first scrolls the menu item into view and tries to click it.
     * If the click fails (e.g., element is not interactable), it then tries to hide the fixed banner and close popups, then retries.
     * This is the most robust method for handling both scrolling and ad popups.
     * Use this when menu items may be out of view or covered by overlays.
     * TEMP: Includes a 2-second wait after scroll for visual confirmation (remove for faster tests).
     */
    public void clickMenuItemWithAdAndScrollHandling(String text) {
        By locator = menuItemByText(text);
        int attempts = 0;
        while (attempts < 3) {
            try {
                // Always scroll first
                ((org.openqa.selenium.JavascriptExecutor) SeleniumUtil.getDriver())
                        .executeScript("arguments[0].scrollIntoView({block: 'center'});", SeleniumUtil.find(locator));
                SeleniumUtil.waitSeconds(2); // TEMP: Wait for visual confirmation after scroll
                SeleniumUtil.click(locator);
                return; // Success
            } catch (org.openqa.selenium.ElementNotInteractableException e) {
                // Only try to close popups if scroll+click fails
                hideFixedBanner();
                SeleniumUtil.closeKnownPopups();
                SeleniumUtil.waitSeconds(1);
            }
            attempts++;
        }
        // Final attempt, let the exception propagate if it fails
        SeleniumUtil.click(locator);
    }
}
