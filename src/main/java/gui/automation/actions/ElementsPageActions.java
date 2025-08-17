package gui.automation.actions;

import gui.automation.pages.ElementsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

public class ElementsPageActions extends BaseActions {
    private static final Logger logger = LoggerFactory.getLogger(ElementsPageActions.class);
    private final ElementsPage elementsPage;

    public ElementsPageActions(WebDriver driver) {
        super(driver);
        this.elementsPage = new ElementsPage(driver);
    }

    // ----------------------
    // Verification Methods
    // ----------------------
    public boolean isMenuItemVisible(String text) {
        logger.info("Checking if menu item '{}' is visible", text);
        return elementsPage.isMenuItemVisible(text);
    }

    // ----------------------
    // Action/Click Methods
    // ----------------------
    public void clickMenuItem(String text) {
        logger.info("Clicking menu item '{}'", text);
        elementsPage.clickMenuItem(text);
    }
    public void clickMenuItemWithAdHandling(String text) {
        logger.info("Clicking menu item '{}' with ad handling", text);
        elementsPage.clickMenuItemWithAdHandling(text);
    }
    public void clickMenuItemWithAdAndScrollHandling(String text) {
        logger.info("Clicking menu item '{}' with ad and scroll handling", text);
        elementsPage.clickMenuItemWithAdAndScrollHandling(text);
    }
}
