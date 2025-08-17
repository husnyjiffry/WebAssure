package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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

    // ----------------------
    // Visibility Check Methods
    // ----------------------
    public boolean isPageTitleVisible() {
        return SeleniumUtil.waitForVisible(pageTitle) != null;
    }

    public boolean isFullNameLabelVisible() {
        return SeleniumUtil.waitForVisible(fullNameLabel) != null;
    }

    public boolean isFullNameVisible() {
        return SeleniumUtil.waitForVisible(fullNameInput) != null;
    }

    public boolean isEmailLabelVisible() {
        return SeleniumUtil.waitForVisible(emailLabel) != null;
    }

    public boolean isEmailVisible() {
        return SeleniumUtil.waitForVisible(emailInput) != null;
    }

    public boolean isCurrentAddressLabelVisible() {
        return SeleniumUtil.waitForVisible(currentAddressLabel) != null;
    }

    public boolean isCurrentAddressVisible() {
        return SeleniumUtil.waitForVisible(currentAddressInput) != null;
    }

    public boolean isPermanentAddressLabelVisible() {
        return SeleniumUtil.waitForVisible(permanentAddressLabel) != null;
    }

    public boolean isPermanentAddressVisible() {
        return SeleniumUtil.waitForVisible(permanentAddressInput) != null;
    }

    public boolean isSubmitButtonVisible() {
        return SeleniumUtil.waitForVisible(submitButton) != null;
    }

    public TextBoxPage(WebDriver driver) {
        super(driver);
    }

    // ----------------------
    // Input Methods
    // ----------------------
    public void setFullName(String name) {
        SeleniumUtil.type(fullNameInput, name);
    }

    public void setEmail(String email) {
        SeleniumUtil.type(emailInput, email);
    }

    public void setCurrentAddress(String address) {
        SeleniumUtil.type(currentAddressInput, address);
    }

    public void setPermanentAddress(String address) {
        SeleniumUtil.type(permanentAddressInput, address);
    }

    // ----------------------
    // Click Methods
    // ----------------------
    public void clickSubmit() {
        try {
            SeleniumUtil.click(submitButton);
        } catch (ElementClickInterceptedException e1) {
            SeleniumUtil.scrollTo(submitButton);
            try {
                SeleniumUtil.click(submitButton);
            } catch (ElementClickInterceptedException e2) {
                SeleniumUtil.closeKnownPopups();
                try {
                    SeleniumUtil.waitSeconds(1);
                    SeleniumUtil.click(submitButton);
                } catch (ElementClickInterceptedException e3) {
                    JavascriptExecutor js = (JavascriptExecutor) SeleniumUtil.getDriver();
                    js.executeScript("arguments[0].click();", SeleniumUtil.waitForVisible(submitButton));
                }
            }
        }
    }

    // ----------------------
    // Output Retrieval Methods
    // ----------------------
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

    // ----------------------
    // Utility Methods
    // ----------------------
    public String getPageTitleText() {
        return SeleniumUtil.getText(pageTitle);
    }

    public String getFullNameLabelText() {
        return SeleniumUtil.getText(fullNameLabel);
    }

    public String getFullNamePlaceholder() {
        return SeleniumUtil.getAttribute(fullNameInput, "placeholder");
    }

    public String getEmailLabelText() {
        return SeleniumUtil.getText(emailLabel);
    }

    public String getEmailPlaceholder() {
        return SeleniumUtil.getAttribute(emailInput, "placeholder");
    }

    public String getCurrentAddressLabelText() {
        return SeleniumUtil.getText(currentAddressLabel);
    }

    public String getCurrentAddressPlaceholder() {
        return SeleniumUtil.getAttribute(currentAddressInput, "placeholder");
    }

    public String getPermanentAddressLabelText() {
        return SeleniumUtil.getText(permanentAddressLabel);
    }

    public String getPermanentAddressPlaceholder() {
        return SeleniumUtil.getAttribute(permanentAddressInput, "placeholder");
    }

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
        boolean nameMissingOrEmpty = SeleniumUtil.find(outputName) == null || SeleniumUtil.getText(outputName).isEmpty();
        boolean emailMissingOrEmpty = SeleniumUtil.find(outputEmail) == null || SeleniumUtil.getText(outputEmail).isEmpty();
        boolean currentAddressMissingOrEmpty = SeleniumUtil.find(outputCurrentAddress) == null || SeleniumUtil.getText(outputCurrentAddress).isEmpty();
        boolean permanentAddressMissingOrEmpty = SeleniumUtil.find(outputPermanentAddress) == null || SeleniumUtil.getText(outputPermanentAddress).isEmpty();
        return nameMissingOrEmpty && emailMissingOrEmpty && currentAddressMissingOrEmpty && permanentAddressMissingOrEmpty;
    }
}
