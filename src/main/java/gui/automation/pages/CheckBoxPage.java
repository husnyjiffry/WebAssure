package gui.automation.pages;

import gui.automation.utils.SeleniumUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the 'Check Box' page on demoqa.com.
 * Provides generalized methods to interact with checkboxes, folders, and expand/collapse controls.
 */
public class CheckBoxPage extends BasePage {
    // Locators (to be filled in by user)
    private static final By pageTitle = By.xpath("//h1[contains(@class,'text-center') and text()='Check Box']");
    private static final By expandAllButton = By.xpath("//button[contains(@class,'rct-option-expand-all')]");
    private static final By collapseAllButton = By.xpath("//button[contains(@class,'rct-option-collapse-all')]");
    private static final By checkedCheckboxes = By.xpath(""); // TODO: Fill XPath for all checked checkboxes
    private static final By partiallyCheckedCheckboxes = By.xpath(""); // TODO: Fill XPath for all partially checked checkboxes
    private static final By homeNodeLi = By.xpath("//span[@class='rct-title' and text()='Home']/ancestor::li[contains(@class,'rct-node-parent')]");
    private static final By homeCheckboxIcon = By.xpath("//label[span[@class='rct-title' and text()='Home']]/span[@class='rct-checkbox']//*[name()='svg']");
    private static final By homeExpandButton = By.xpath("//span[@class='rct-title' and text()='Home']/ancestor::li//button[@aria-label='Toggle']");
    private static final By homeFolderIcon = By.xpath("//label[span[@class='rct-title' and text()='Home']]/span[@class='rct-node-icon']");
    private static final By homeCheckbox = By.xpath("//label[span[@class='rct-title' and text()='Home']]/span[@class='rct-checkbox']");

    private static By folderByName(String name) {
        // Returns the span with class 'rct-title' and the given folder name
        return By.xpath("//span[@class='rct-title' and text()='" + name + "']");
    }
    private static By checkboxByName(String name) {
        // Finds the checkbox span preceding the folder title
        return By.xpath("//span[@class='rct-title' and text()='" + name + "']/preceding-sibling::span[@class='rct-checkbox']");
    }
    private static By expandIconByName(String name) {
        // Finds the expand/collapse icon preceding the folder title
        return By.xpath("//span[@class='rct-title' and text()='" + name + "']/preceding-sibling::span[contains(@class,'rct-node-icon')]");
    }
    private static By collapseIconByName(String name) {
        // For this tree, expand and collapse use the same icon, so reuse expandIconByName
        return expandIconByName(name);
    }
    private static By subfolderByName(String name) {
        return By.xpath("//span[@class='rct-title' and text()='" + name + "']");
    }

    public CheckBoxPage(WebDriver driver) {
        super(driver);
    }

    // -------------------- Page Title Methods --------------------
    public boolean isPageTitleVisible() {
        return SeleniumUtil.waitForVisible(pageTitle) != null;
    }
    public String getPageTitleText() {
        return SeleniumUtil.getText(pageTitle);
    }

    // -------------------- Expand/Collapse Methods --------------------
    public void clickExpandAll() {
        SeleniumUtil.click(expandAllButton);
    }
    public void clickCollapseAll() {
        SeleniumUtil.click(collapseAllButton);
    }
    public void expandFolder(String folderName) {
        SeleniumUtil.click(expandIconByName(folderName));
    }
    public void collapseFolder(String folderName) {
        SeleniumUtil.click(collapseIconByName(folderName));
    }
    public boolean isFolderExpanded(String folderName) {
        return SeleniumUtil.isFolderExpanded(expandIconByName(folderName));
    }
    public boolean isFolderCollapsed(String folderName) {
        return SeleniumUtil.isFolderCollapsed(collapseIconByName(folderName));
    }

    // -------------------- Checkbox Methods --------------------
    public void clickCheckbox(String name) {
        SeleniumUtil.click(checkboxByName(name));
    }
    public boolean isCheckboxChecked(String name) {
        return SeleniumUtil.isCheckboxChecked(checkboxByName(name));
    }
    public boolean isCheckboxPartiallyChecked(String name) {
        return SeleniumUtil.isCheckboxPartiallyChecked(checkboxByName(name));
    }
    public boolean isCheckboxVisible(String name) {
        return SeleniumUtil.waitForVisible(checkboxByName(name)) != null;
    }
    public List<String> getAllCheckedCheckboxNames() {
        return SeleniumUtil.getAllCheckedCheckboxNames(checkedCheckboxes);
    }
    public List<String> getAllPartiallyCheckedCheckboxNames() {
        return SeleniumUtil.getAllPartiallyCheckedCheckboxNames(partiallyCheckedCheckboxes);
    }

    // -------------------- Button Visibility Methods --------------------
    public boolean isExpandAllButtonVisible() {
        return SeleniumUtil.waitForVisible(expandAllButton) != null;
    }
    public boolean isCollapseAllButtonVisible() {
        return SeleniumUtil.waitForVisible(collapseAllButton) != null;
    }

    // -------------------- Utility Methods --------------------
    public void expandAllFolders() {
        clickExpandAll();
    }
    public void collapseAllFolders() {
        clickCollapseAll();
    }

    public boolean isHomeNodeExpanded() {
        String clazz = SeleniumUtil.getAttribute(homeNodeLi, "class");
        return clazz != null && clazz.contains("rct-node-expanded");
    }
    public boolean isHomeNodeCollapsed() {
        String clazz = SeleniumUtil.getAttribute(homeNodeLi, "class");
        return clazz != null && clazz.contains("rct-node-collapsed");
    }

    public boolean isHomeCheckboxUnchecked() {
        String clazz = SeleniumUtil.getAttribute(homeCheckboxIcon, "class");
        return clazz != null && clazz.contains("rct-icon-uncheck");
    }
    public void clickHomeExpandIcon() {
        SeleniumUtil.click(homeExpandButton);
    }
    public boolean isSubfolderVisible(String name) {
        return SeleniumUtil.waitForVisible(subfolderByName(name)) != null;
    }

    public boolean isHomeExpandIconVisible() {
        return SeleniumUtil.waitForVisible(homeExpandButton) != null;
    }
    public boolean isHomeCheckboxVisible() {
        return SeleniumUtil.waitForVisible(homeCheckbox) != null;
    }
    public boolean isHomeFolderIconVisible() {
        return SeleniumUtil.waitForVisible(homeFolderIcon) != null;
    }
}
