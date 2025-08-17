package gui.automation.actions;

import gui.automation.pages.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Beginner Tip:
 * This BaseActions class contains common browser-level actions that are shared across all page actions classes.
 * By putting methods like getCurrentUrl(), goBack(), and refreshPage() in BasePage, we avoid repeating code in every actions class.
 * <p>
 * Why use a separate BaseActions class?
 * - Keeps your code DRY (Don't Repeat Yourself) and organized.
 * - Makes it easy to add or update shared actions in one place.
 * - All your specific actions classes (e.g., LandingPageActions, ElementsPageActions) can extend this class and use these methods.
 * <p>
 * Example usage:
 * landingPageActions.getCurrentUrl(); // Get the current browser URL
 * elementsPageActions.goBack();      // Go back to the previous page
 */
public class BaseActions extends BasePage {
    // Inherit browser-level actions from BasePage

    public BaseActions(WebDriver driver) {
        super(driver);
    }
}
