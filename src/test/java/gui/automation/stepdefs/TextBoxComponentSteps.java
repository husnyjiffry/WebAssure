package gui.automation.stepdefs;

import gui.automation.actions.TextBoxActions;
import gui.automation.utils.SeleniumUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class TextBoxComponentSteps {
    private TextBoxActions textBoxActions;
    private static final String PAGE_URL = "https://demoqa.com/text-box";

    @Given("I am on the text box page")
    public void i_am_on_the_text_box_page() {
        textBoxActions = new TextBoxActions(SeleniumUtil.getDriver());
        textBoxActions.navigateTo(PAGE_URL);
    }

    @Then("the page title should be visible")
    public void page_title_should_be_visible() {
        Assert.assertTrue(textBoxActions.isPageTitleVisible());
    }

    @Then("the page title should be {string}")
    public void page_title_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getPageTitleText(), expected);
    }

    @Then("the Full Name label should be visible")
    public void full_name_label_should_be_visible() {
        Assert.assertTrue(textBoxActions.isFullNameLabelVisible());
    }

    @Then("the Full Name label text should be {string}")
    public void full_name_label_text_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getFullNameLabelText(), expected);
    }

    @Then("the Full Name field should be visible")
    public void full_name_field_should_be_visible() {
        Assert.assertTrue(textBoxActions.isFullNameFieldVisible());
    }

    @Then("the Full Name placeholder should be {string}")
    public void full_name_placeholder_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getFullNamePlaceholder(), expected);
    }

    @Then("the Email label should be visible")
    public void email_label_should_be_visible() {
        Assert.assertTrue(textBoxActions.isEmailLabelVisible());
    }

    @Then("the Email label text should be {string}")
    public void email_label_text_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getEmailLabelText(), expected);
    }

    @Then("the Email field should be visible")
    public void email_field_should_be_visible() {
        Assert.assertTrue(textBoxActions.isEmailFieldVisible());
    }

    @Then("the Email placeholder should be {string}")
    public void email_placeholder_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getEmailPlaceholder(), expected);
    }

    @Then("the Current Address label should be visible")
    public void current_address_label_should_be_visible() {
        Assert.assertTrue(textBoxActions.isCurrentAddressLabelVisible());
    }

    @Then("the Current Address label text should be {string}")
    public void current_address_label_text_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getCurrentAddressLabelText(), expected);
    }

    @Then("the Current Address field should be visible")
    public void current_address_field_should_be_visible() {
        Assert.assertTrue(textBoxActions.isCurrentAddressFieldVisible());
    }

    @Then("the Current Address placeholder should be {string}")
    public void current_address_placeholder_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getCurrentAddressPlaceholder(), expected);
    }

    @Then("the Permanent Address label should be visible")
    public void permanent_address_label_should_be_visible() {
        Assert.assertTrue(textBoxActions.isPermanentAddressLabelVisible());
    }

    @Then("the Permanent Address label text should be {string}")
    public void permanent_address_label_text_should_be(String expected) {
        Assert.assertEquals(textBoxActions.getPermanentAddressLabelText(), expected);
    }

    @Then("the Permanent Address field should be visible")
    public void permanent_address_field_should_be_visible() {
        Assert.assertTrue(textBoxActions.isPermanentAddressFieldVisible());
    }

    @Then("the Submit button should be visible")
    public void submit_button_should_be_visible() {
        Assert.assertTrue(textBoxActions.isSubmitButtonVisible());
    }

    @When("I fill the form with name {string}, email {string}, current address {string}, permanent address {string}")
    public void fill_form(String name, String email, String currentAddress, String permanentAddress) {
        textBoxActions.fillForm(name, email, currentAddress, permanentAddress);
    }

    @When("I click the submit button")
    public void click_submit_button() {
        textBoxActions.clickSubmit();
    }

    @Then("the output should contain name {string}, email {string}, current address {string}, permanent address {string}")
    public void output_should_contain(String name, String email, String currentAddress, String permanentAddress) {
        String outputName = textBoxActions.getSubmittedNameOutput();
        String outputEmail = textBoxActions.getSubmittedEmailOutput();
        String outputCurrentAddress = textBoxActions.getSubmittedCurrentAddressOutput();
        String outputPermanentAddress = textBoxActions.getSubmittedPermanentAddressOutput();
        Assert.assertTrue(outputName.contains(name));
        Assert.assertTrue(outputEmail.contains(email));
        Assert.assertTrue(outputCurrentAddress.contains(currentAddress));
        Assert.assertTrue(outputPermanentAddress.contains(permanentAddress));
    }

    @When("I reload the page")
    public void reload_the_page() {
        textBoxActions.refreshPage();
    }

    @Then("all fields and output should be empty")
    public void all_fields_and_output_should_be_empty() {
        Assert.assertTrue(textBoxActions.isFullNameEmpty());
        Assert.assertTrue(textBoxActions.isEmailEmpty());
        Assert.assertTrue(textBoxActions.isCurrentAddressEmpty());
        Assert.assertTrue(textBoxActions.isPermanentAddressEmpty());
        Assert.assertTrue(textBoxActions.isOutputEmpty());
    }
}
