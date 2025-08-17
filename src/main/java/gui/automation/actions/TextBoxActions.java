package gui.automation.actions;

import gui.automation.pages.TextBoxPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

public class TextBoxActions extends BaseActions {
    private static final Logger logger = LoggerFactory.getLogger(TextBoxActions.class);
    private final TextBoxPage textBoxPage;

    public TextBoxActions(WebDriver driver) {
        super(driver);
        this.textBoxPage = new TextBoxPage(driver);
    }

    // ----------------------
    // Navigation Methods
    // ----------------------
    // (Inherit navigation methods from BaseActions)

    // ----------------------
    // Input Methods
    // ----------------------
    public void enterFullName(String name) {
        logger.info("Entering Full Name: {}", name);
        textBoxPage.setFullName(name);
    }
    public void enterEmail(String email) {
        logger.info("Entering Email: {}", email);
        textBoxPage.setEmail(email);
    }
    public void enterCurrentAddress(String address) {
        logger.info("Entering Current Address: {}", address);
        textBoxPage.setCurrentAddress(address);
    }
    public void enterPermanentAddress(String address) {
        logger.info("Entering Permanent Address: {}", address);
        textBoxPage.setPermanentAddress(address);
    }

    // ----------------------
    // Action/Click Methods
    // ----------------------
    public void clickSubmit() {
        logger.info("Clicking Submit button");
        textBoxPage.clickSubmit();
    }
    public void fillForm(String name, String email, String currentAddress, String permanentAddress) {
        logger.info("Filling form with: Name={}, Email={}, CurrentAddress={}, PermanentAddress={}", name, email, currentAddress, permanentAddress);
        enterFullName(name);
        enterEmail(email);
        enterCurrentAddress(currentAddress);
        enterPermanentAddress(permanentAddress);
        clickSubmit();
    }

    // ----------------------
    // Output Retrieval Methods
    // ----------------------
    public String getSubmittedNameOutput() {
        String value = textBoxPage.getSubmittedNameOutput();
        logger.info("Output Name after submit: {}", value);
        return value;
    }
    public String getSubmittedEmailOutput() {
        String value = textBoxPage.getSubmittedEmailOutput();
        logger.info("Output Email after submit: {}", value);
        return value;
    }
    public String getSubmittedCurrentAddressOutput() {
        String value = textBoxPage.getSubmittedCurrentAddressOutput();
        logger.info("Output Current Address after submit: {}", value);
        return value;
    }
    public String getSubmittedPermanentAddressOutput() {
        String value = textBoxPage.getSubmittedPermanentAddressOutput();
        logger.info("Output Permanent Address after submit: {}", value);
        return value;
    }

    // ----------------------
    // Visibility, Label, Placeholder, and Empty Check Methods
    // ----------------------
    public boolean isPageTitleVisible() { return textBoxPage.isPageTitleVisible(); }
    public String getPageTitleText() { return textBoxPage.getPageTitleText(); }
    public boolean isFullNameLabelVisible() { return textBoxPage.isFullNameLabelVisible(); }
    public String getFullNameLabelText() { return textBoxPage.getFullNameLabelText(); }
    public boolean isFullNameFieldVisible() { return textBoxPage.isFullNameVisible(); }
    public String getFullNamePlaceholder() { return textBoxPage.getFullNamePlaceholder(); }
    public boolean isEmailLabelVisible() { return textBoxPage.isEmailLabelVisible(); }
    public String getEmailLabelText() { return textBoxPage.getEmailLabelText(); }
    public boolean isEmailFieldVisible() { return textBoxPage.isEmailVisible(); }
    public String getEmailPlaceholder() { return textBoxPage.getEmailPlaceholder(); }
    public boolean isCurrentAddressLabelVisible() { return textBoxPage.isCurrentAddressLabelVisible(); }
    public String getCurrentAddressLabelText() { return textBoxPage.getCurrentAddressLabelText(); }
    public boolean isCurrentAddressFieldVisible() { return textBoxPage.isCurrentAddressVisible(); }
    public String getCurrentAddressPlaceholder() { return textBoxPage.getCurrentAddressPlaceholder(); }
    public boolean isPermanentAddressLabelVisible() { return textBoxPage.isPermanentAddressLabelVisible(); }
    public String getPermanentAddressLabelText() { return textBoxPage.getPermanentAddressLabelText(); }
    public boolean isPermanentAddressFieldVisible() { return textBoxPage.isPermanentAddressVisible(); }
    public String getPermanentAddressPlaceholder() { return textBoxPage.getPermanentAddressPlaceholder(); }
    public boolean isSubmitButtonVisible() { return textBoxPage.isSubmitButtonVisible(); }
    public boolean isFullNameEmpty() { return textBoxPage.isFullNameEmpty(); }
    public boolean isEmailEmpty() { return textBoxPage.isEmailEmpty(); }
    public boolean isCurrentAddressEmpty() { return textBoxPage.isCurrentAddressEmpty(); }
    public boolean isPermanentAddressEmpty() { return textBoxPage.isPermanentAddressEmpty(); }
    public boolean isOutputEmpty() { return textBoxPage.isOutputEmpty(); }
}
