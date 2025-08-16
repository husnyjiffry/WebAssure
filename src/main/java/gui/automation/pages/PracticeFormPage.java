package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;

/**
 * Page Object for the Practice Form page of demoqa.com.
 */
public class PracticeFormPage {
    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By submitButton = By.id("submit");

    public void fillForm(String firstName, String lastName, String email) {
        SeleniumUtil.type(firstNameInput, firstName);
        SeleniumUtil.type(lastNameInput, lastName);
        SeleniumUtil.type(emailInput, email);
    }

    public void submitForm() {
        SeleniumUtil.click(submitButton);
    }
}
