package gui.automation.actions;

import gui.automation.pages.CheckBoxPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Actions class for the Check Box page.
 * Encapsulates user workflows and business logic for interacting with checkboxes and folders.
 */
public class CheckBoxActions extends BaseActions {
    private static final Logger logger = LoggerFactory.getLogger(CheckBoxActions.class);
    private final CheckBoxPage checkBoxPage;

    public CheckBoxActions(WebDriver driver) {
        super(driver);
        this.checkBoxPage = new CheckBoxPage(driver);
    }

    // -------------------- Page Title Verification --------------------
    public boolean isPageTitleVisible() {
        logger.info("Verifying Check Box page title is visible");
        return checkBoxPage.isPageTitleVisible();
    }
    public String getPageTitleText() {
        logger.info("Getting Check Box page title text");
        return checkBoxPage.getPageTitleText();
    }

    // -------------------- Expand/Collapse Actions --------------------
    public void clickExpandAll() {
        logger.info("Clicking Expand All button");
        checkBoxPage.clickExpandAll();
    }
    public void clickCollapseAll() {
        logger.info("Clicking Collapse All button");
        checkBoxPage.clickCollapseAll();
    }
    public void expandFolder(String folderName) {
        logger.info("Expanding folder: {}", folderName);
        checkBoxPage.expandFolder(folderName);
    }
    public void collapseFolder(String folderName) {
        logger.info("Collapsing folder: {}", folderName);
        checkBoxPage.collapseFolder(folderName);
    }
    public boolean isFolderExpanded(String folderName) {
        logger.info("Checking if folder '{}' is expanded", folderName);
        return checkBoxPage.isFolderExpanded(folderName);
    }
    public boolean isFolderCollapsed(String folderName) {
        logger.info("Checking if folder '{}' is collapsed", folderName);
        return checkBoxPage.isFolderCollapsed(folderName);
    }

    // -------------------- Checkbox Actions --------------------
    public void clickCheckbox(String name) {
        logger.info("Clicking checkbox: {}", name);
        checkBoxPage.clickCheckbox(name);
    }
    public boolean isCheckboxChecked(String name) {
        logger.info("Checking if checkbox '{}' is checked", name);
        return checkBoxPage.isCheckboxChecked(name);
    }
    public boolean isCheckboxPartiallyChecked(String name) {
        logger.info("Checking if checkbox '{}' is partially checked", name);
        return checkBoxPage.isCheckboxPartiallyChecked(name);
    }
    public boolean isCheckboxVisible(String name) {
        logger.info("Checking if checkbox '{}' is visible", name);
        return checkBoxPage.isCheckboxVisible(name);
    }
    public List<String> getAllCheckedCheckboxNames() {
        logger.info("Getting all checked checkbox names");
        return checkBoxPage.getAllCheckedCheckboxNames();
    }
    public List<String> getAllPartiallyCheckedCheckboxNames() {
        logger.info("Getting all partially checked checkbox names");
        return checkBoxPage.getAllPartiallyCheckedCheckboxNames();
    }

    // -------------------- Utility Actions --------------------
    public void expandAllFolders() {
        logger.info("Expanding all folders");
        checkBoxPage.expandAllFolders();
    }
    public void collapseAllFolders() {
        logger.info("Collapsing all folders");
        checkBoxPage.collapseAllFolders();
    }

    // -------------------- Button Visibility Methods --------------------
    public boolean isExpandAllButtonVisible() {
        return checkBoxPage.isExpandAllButtonVisible();
    }
    public boolean isCollapseAllButtonVisible() {
        return checkBoxPage.isCollapseAllButtonVisible();
    }

    public boolean isHomeNodeExpanded() {
        return checkBoxPage.isHomeNodeExpanded();
    }
    public boolean isHomeNodeCollapsed() {
        return checkBoxPage.isHomeNodeCollapsed();
    }

    public boolean isHomeCheckboxUnchecked() {
        return checkBoxPage.isHomeCheckboxUnchecked();
    }
    public void clickHomeExpandIcon() {
        checkBoxPage.clickHomeExpandIcon();
    }
    public boolean isSubfolderVisible(String name) {
        return checkBoxPage.isSubfolderVisible(name);
    }

    public boolean isHomeExpandIconVisible() {
        return checkBoxPage.isHomeExpandIconVisible();
    }
    public boolean isHomeCheckboxVisible() {
        return checkBoxPage.isHomeCheckboxVisible();
    }
    public boolean isHomeFolderIconVisible() {
        return checkBoxPage.isHomeFolderIconVisible();
    }
}
