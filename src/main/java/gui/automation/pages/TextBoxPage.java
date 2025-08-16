package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;

public class TextBoxPage extends BasePage {
    private final By pageTitle = By.xpath("//h1[contains(@class,'text-center') and text()='Text Box']");
    // Use robust XPath for Full Name label and input
    private final By fullNameLabel = By.xpath("//div[@id='userName-wrapper']//label[@id='userName-label']");
    private final By fullNameInput = By.xpath("//div[@id='userName-wrapper']//input[@id='userName']");
    private final By emailLabel = By.xpath("//div[@id='userEmail-wrapper']//label[@id='userEmail-label']");
    private final By emailInput = By.xpath("//div[@id='userEmail-wrapper']//input[@id='userEmail']");
    private final By currentAddressLabel = By.xpath("//div[@id='currentAddress-wrapper']//label[@id='currentAddress-label']");
    private final By currentAddressInput = By.xpath("//div[@id='currentAddress-wrapper']//textarea[@id='currentAddress']");
    private final By permanentAddressLabel = By.xpath("//div[@id='permanentAddress-wrapper']//label[@id='permanentAddress-label']");
    private final By permanentAddressInput = By.xpath("//div[@id='permanentAddress-wrapper']//textarea[@id='permanentAddress']");
    // Robust XPath for submit button
    private final By submitButton = By.xpath("//form[@id='userForm']//button[@id='submit']");

    // Output locators after submit
    private final By outputName = By.xpath("//p[@id='name']");
    private final By outputEmail = By.xpath("//p[@id='email']");
    private final By outputCurrentAddress = By.xpath("//p[@id='currentAddress']");
    private final By outputPermanentAddress = By.xpath("//p[@id='permanentAddress']");

    // Page title
    public boolean isPageTitleVisible() {
        return SeleniumUtil.waitForVisible(pageTitle) != null;
    }
    public String getPageTitleText() {
        return SeleniumUtil.getText(pageTitle);
    }

    // Full Name
    public boolean isFullNameLabelVisible() {
        return SeleniumUtil.waitForVisible(fullNameLabel) != null;
    }
    public String getFullNameLabelText() {
        return SeleniumUtil.getText(fullNameLabel);
    }
    public boolean isFullNameVisible() {
        return SeleniumUtil.waitForVisible(fullNameInput) != null;
    }
    public String getFullNamePlaceholder() {
        return SeleniumUtil.getAttribute(fullNameInput, "placeholder");
    }
    public void setFullName(String name) {
        SeleniumUtil.type(fullNameInput, name);
    }

    // Email
    public boolean isEmailLabelVisible() {
        return SeleniumUtil.waitForVisible(emailLabel) != null;
    }
    public String getEmailLabelText() {
        return SeleniumUtil.getText(emailLabel);
    }
    public boolean isEmailVisible() {
        return SeleniumUtil.waitForVisible(emailInput) != null;
    }
    public String getEmailPlaceholder() {
        return SeleniumUtil.getAttribute(emailInput, "placeholder");
    }
    public void setEmail(String email) {
        SeleniumUtil.type(emailInput, email);
    }

    // Current Address
    public boolean isCurrentAddressLabelVisible() {
        return SeleniumUtil.waitForVisible(currentAddressLabel) != null;
    }
    public String getCurrentAddressLabelText() {
        return SeleniumUtil.getText(currentAddressLabel);
    }
    public boolean isCurrentAddressVisible() {
        return SeleniumUtil.waitForVisible(currentAddressInput) != null;
    }
    public String getCurrentAddressPlaceholder() {
        return SeleniumUtil.getAttribute(currentAddressInput, "placeholder");
    }
    public void setCurrentAddress(String address) {
        SeleniumUtil.type(currentAddressInput, address);
    }

    // Permanent Address (using robust XPath)
    public boolean isPermanentAddressLabelVisible() {
        return SeleniumUtil.waitForVisible(permanentAddressLabel) != null;
    }
    public String getPermanentAddressLabelText() {
        return SeleniumUtil.getText(permanentAddressLabel);
    }
    public boolean isPermanentAddressVisible() {
        return SeleniumUtil.waitForVisible(permanentAddressInput) != null;
    }
    public String getPermanentAddressPlaceholder() {
        return SeleniumUtil.getAttribute(permanentAddressInput, "placeholder");
    }
    public void setPermanentAddress(String address) {
        SeleniumUtil.type(permanentAddressInput, address);
    }

    // Submit
    public boolean isSubmitButtonVisible() {
        return SeleniumUtil.waitForVisible(submitButton) != null;
    }
    public void clickSubmit() {
        try {
            SeleniumUtil.click(submitButton);
        } catch (ElementClickInterceptedException e1) {
            // Try scrolling into view and retry
            SeleniumUtil.scrollTo(submitButton);
            try {
                SeleniumUtil.click(submitButton);
            } catch (ElementClickInterceptedException e2) {
                // Try to close known popups/ads and retry
                SeleniumUtil.closeKnownPopups();
                try {
                    SeleniumUtil.waitSeconds(1);
                    SeleniumUtil.click(submitButton);
                } catch (ElementClickInterceptedException e3) {
                    // Fallback: try JavaScript click
                    JavascriptExecutor js = (JavascriptExecutor) SeleniumUtil.getDriver();
                    js.executeScript("arguments[0].click();", SeleniumUtil.waitForVisible(submitButton));
                }
            }
        }
    }

    // Output getters (renamed for clarity)
    public String getSubmittedNameOutput() {
        return SeleniumUtil.getText(outputName);
    }
    public String getSubmittedEmailOutput() {
        return SeleniumUtil.getText(outputEmail);
    }
    public String getSubmittedCurrentAddressOutput() {
        return SeleniumUtil.getText(outputCurrentAddress);
    }
    public String getSubmittedPermanentAddressOutput() {
        return SeleniumUtil.getText(outputPermanentAddress);
    }

    // Methods to check if fields are empty
    public boolean isFullNameEmpty() {
        return SeleniumUtil.getAttribute(fullNameInput, "value").isEmpty();
    }
    public boolean isEmailEmpty() {
        return SeleniumUtil.getAttribute(emailInput, "value").isEmpty();
    }
    public boolean isCurrentAddressEmpty() {
        return SeleniumUtil.getAttribute(currentAddressInput, "value").isEmpty();
    }
    public boolean isPermanentAddressEmpty() {
        return SeleniumUtil.getAttribute(permanentAddressInput, "value").isEmpty();
    }
    public boolean isOutputEmpty() {
        // Output is empty if the outputName, outputEmail, outputCurrentAddress, and outputPermanentAddress are all empty or not displayed
        return SeleniumUtil.getText(outputName).isEmpty() &&
               SeleniumUtil.getText(outputEmail).isEmpty() &&
               SeleniumUtil.getText(outputCurrentAddress).isEmpty() &&
               SeleniumUtil.getText(outputPermanentAddress).isEmpty();
    }
}
