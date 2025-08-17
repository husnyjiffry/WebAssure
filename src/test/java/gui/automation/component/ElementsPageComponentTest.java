package gui.automation.component;

import gui.automation.actions.ElementsPageActions;
import gui.automation.actions.LandingPageActions;
import gui.automation.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ElementsPageComponentTest extends BaseTest {
    private ElementsPageActions elementsPageActions;

    @BeforeMethod
    public void setUpActions() {
        elementsPageActions = new ElementsPageActions(getDriver());
        getDriver().get("https://demoqa.com/elements");
    }

    @DataProvider(name = "menuItems")
    public Object[][] menuItems() {
        return new Object[][]{
                {"Text Box"},
                {"Check Box"},
                {"Radio Button"},
                {"Web Tables"},
                {"Buttons"},
                {"Links"},
                {"Broken Links - Images"},
                {"Upload and Download"},
                {"Dynamic Properties"}
        };
    }

    @Test(dataProvider = "menuItems")
    public void testMenuItemIsVisible(String menuItem) {
        Assert.assertTrue(elementsPageActions.isMenuItemVisible(menuItem), menuItem + " menu item should be visible");
    }

    @DataProvider(name = "menuItemsAndUrls")
    public Object[][] menuItemsAndUrls() {
        return new Object[][]{
                {"Text Box", "text-box"},
                {"Check Box", "checkbox"},
                {"Radio Button", "radio-button"},
                {"Web Tables", "webtables"},
                {"Buttons", "buttons"},
                {"Links", "links"},
                {"Broken Links - Images", "broken"},
                {"Upload and Download", "upload-download"},
                {"Dynamic Properties", "dynamic-properties"}
        };
    }

    @Test(dataProvider = "menuItemsAndUrls")
    public void testMenuItemNavigation(String menuItem, String urlSuffix) {
        elementsPageActions.clickMenuItemWithAdAndScrollHandling(menuItem);
        Assert.assertTrue(
                elementsPageActions.getCurrentUrl().endsWith("/" + urlSuffix),
                menuItem + " should navigate to URL ending with /" + urlSuffix
        );
        // Go back to Elements page for the next test
        // new LandingPageActions(getDriver()).clickElementsCard(); // This line is removed
    }
}
