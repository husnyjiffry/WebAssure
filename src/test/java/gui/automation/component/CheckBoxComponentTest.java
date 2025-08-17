package gui.automation.component;

import gui.automation.actions.CheckBoxActions;
import gui.automation.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckBoxComponentTest extends BaseTest {
    private CheckBoxActions checkBoxActions;
    private static final String CHECKBOX_PAGE_URL = "https://demoqa.com/checkbox";

    @BeforeMethod
    public void setUpActions() {
        checkBoxActions = new CheckBoxActions(getDriver());
        getDriver().get(CHECKBOX_PAGE_URL);
    }

    // 1. Check Box h1
    @Test
    public void testCheckBoxTitleVisible() {
        Assert.assertTrue(checkBoxActions.isPageTitleVisible(), "Check Box page title (h1) should be visible");
    }

    // 2. > icon (expand/collapse button for Home)
    @Test
    public void testHomeExpandIconVisible() {
        Assert.assertTrue(checkBoxActions.isHomeExpandIconVisible(), "> icon (expand/collapse) for Home should be visible");
    }

    // 3. Home checkbox
    @Test
    public void testHomeCheckboxVisible() {
        Assert.assertTrue(checkBoxActions.isHomeCheckboxVisible(), "Home checkbox should be visible");
    }

    // 4. Home folder icon
    @Test
    public void testHomeFolderIconVisible() {
        Assert.assertTrue(checkBoxActions.isHomeFolderIconVisible(), "Home folder icon should be visible");
    }

    // 5. Expand all icon
    @Test
    public void testExpandAllButtonVisible() {
        Assert.assertTrue(checkBoxActions.isExpandAllButtonVisible(), "Expand All button should be visible");
    }

    // 6. Collapse all icon
    @Test
    public void testCollapseAllButtonVisible() {
        Assert.assertTrue(checkBoxActions.isCollapseAllButtonVisible(), "Collapse All button should be visible");
    }

    // --- Commenting out other tests for now ---
    /*
    @Test
    public void testExpandAllAndCollapseAll_VerifyHomeNode() throws InterruptedException { ... }
    @Test
    public void testExpandHomeAndVerifySubfolders() throws InterruptedException { ... }
    // ... other tests ...
    */
}
