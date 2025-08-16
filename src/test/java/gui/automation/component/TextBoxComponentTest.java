package gui.automation.component;

import gui.automation.actions.TextBoxActions;
import gui.automation.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextBoxComponentTest extends BaseTest {
    private TextBoxActions textBoxActions;
    private static final String PAGE_URL = "https://demoqa.com/text-box";

    @BeforeMethod
    public void setUpActions() {
        textBoxActions = new TextBoxActions();
        textBoxActions.navigateTo(PAGE_URL);
    }

    @Test
    public void testPageTitleVisible() {
        Assert.assertTrue(textBoxActions.isPageTitleVisible(), "Page title should be visible");
        Assert.assertEquals(textBoxActions.getPageTitleText(), "Text Box", "Page title text should be 'Text Box'");
    }

    @Test
    public void testFullNameLabel() {
        Assert.assertTrue(textBoxActions.isFullNameLabelVisible(), "Full Name label should be visible");
        Assert.assertEquals(textBoxActions.getFullNameLabelText(), "Full Name", "Full Name label text should be 'Full Name'");
    }

    @Test
    public void testFullNameField() {
        Assert.assertTrue(textBoxActions.isFullNameFieldVisible(), "Full Name field should be visible");
        Assert.assertEquals(textBoxActions.getFullNamePlaceholder(), "Full Name", "Full Name placeholder should be 'Full Name'");
    }

    @Test
    public void testEmailLabel() {
        Assert.assertTrue(textBoxActions.isEmailLabelVisible(), "Email label should be visible");
        Assert.assertEquals(textBoxActions.getEmailLabelText(), "Email", "Email label text should be 'Email'");
    }

    @Test
    public void testEmailField() {
        Assert.assertTrue(textBoxActions.isEmailFieldVisible(), "Email field should be visible");
        Assert.assertEquals(textBoxActions.getEmailPlaceholder(), "name@example.com", "Email placeholder should be 'name@example.com'");
    }

    @Test
    public void testCurrentAddressLabel() {
        Assert.assertTrue(textBoxActions.isCurrentAddressLabelVisible(), "Current Address label should be visible");
        Assert.assertEquals(textBoxActions.getCurrentAddressLabelText(), "Current Address", "Current Address label text should be 'Current Address'");
    }

    @Test
    public void testCurrentAddressField() {
        Assert.assertTrue(textBoxActions.isCurrentAddressFieldVisible(), "Current Address field should be visible");
        Assert.assertEquals(textBoxActions.getCurrentAddressPlaceholder(), "Current Address", "Current Address placeholder should be 'Current Address'");
    }

    @Test
    public void testPermanentAddressLabel() {
        Assert.assertTrue(textBoxActions.isPermanentAddressLabelVisible(), "Permanent Address label should be visible");
        Assert.assertEquals(textBoxActions.getPermanentAddressLabelText(), "Permanent Address", "Permanent Address label text should be 'Permanent Address'");
    }

    @Test
    public void testPermanentAddressField() {
        Assert.assertTrue(textBoxActions.isPermanentAddressFieldVisible(), "Permanent Address field should be visible");
        // Placeholder may be empty, so just check visibility
    }

    @Test
    public void testSubmitButtonVisible() {
        Assert.assertTrue(textBoxActions.isSubmitButtonVisible(), "Submit button should be visible");
    }

    @Test
    public void testEnterDataAndSubmit() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String currentAddress = "123 Main St";
        String permanentAddress = "456 Elm St";
        textBoxActions.fillForm(name, email, currentAddress, permanentAddress);
        // Optionally, add assertions for output if needed
    }

    @Test
    public void testOutputValuesAfterSubmit() {
        String name = "Husny";
        String email = "husny@gmail.com";
        String currentAddress = "Singapore";
        String permanentAddress = "Sri Lanka";
        textBoxActions.fillForm(name, email, currentAddress, permanentAddress);
        // Output will be in the format: 'Name: Husny', etc.
        String outputName = textBoxActions.getSubmittedNameOutput();
        String outputEmail = textBoxActions.getSubmittedEmailOutput();
        String outputCurrentAddress = textBoxActions.getSubmittedCurrentAddressOutput();
        String outputPermanentAddress = textBoxActions.getSubmittedPermanentAddressOutput();
        Assert.assertTrue(outputName.contains(name), "Output name should contain the entered name");
        Assert.assertTrue(outputEmail.contains(email), "Output email should contain the entered email");
        Assert.assertTrue(outputCurrentAddress.contains(currentAddress), "Output current address should contain the entered address");
        Assert.assertTrue(outputPermanentAddress.contains(permanentAddress), "Output permanent address should contain the entered address");
    }

    @DataProvider(name = "textBoxData")
    public Object[][] textBoxData() {
        return new Object[][] {
            {"Husny", "husny@gmail.com", "Singapore", "Sri Lanka"},
            {"Alice", "alice@example.com", "New York", "USA"}
        };
    }

    @Test(dataProvider = "textBoxData")
    public void testFieldsAndOutputClearedAfterReload(String name, String email, String currentAddress, String permanentAddress) {
        // Fill and submit the form
        textBoxActions.fillForm(name, email, currentAddress, permanentAddress);
        try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
        // Assert output is present and correct after submit
        String outputName = textBoxActions.getSubmittedNameOutput();
        String outputEmail = textBoxActions.getSubmittedEmailOutput();
        String outputCurrentAddress = textBoxActions.getSubmittedCurrentAddressOutput();
        String outputPermanentAddress = textBoxActions.getSubmittedPermanentAddressOutput();
        Assert.assertTrue(outputName.contains(name), "Output name should contain the entered name after submit");
        Assert.assertTrue(outputEmail.contains(email), "Output email should contain the entered email after submit");
        Assert.assertTrue(outputCurrentAddress.contains(currentAddress), "Output current address should contain the entered address after submit");
        Assert.assertTrue(outputPermanentAddress.contains(permanentAddress), "Output permanent address should contain the entered address after submit");
        // Simulate a true browser refresh
        textBoxActions.refreshPage();
        // Assert all fields and output are empty
        Assert.assertTrue(textBoxActions.isFullNameEmpty(), "Full Name field should be empty after reload");
        Assert.assertTrue(textBoxActions.isEmailEmpty(), "Email field should be empty after reload");
        Assert.assertTrue(textBoxActions.isCurrentAddressEmpty(), "Current Address field should be empty after reload");
        Assert.assertTrue(textBoxActions.isPermanentAddressEmpty(), "Permanent Address field should be empty after reload");
        Assert.assertTrue(textBoxActions.isOutputEmpty(), "Output should be empty after reload");
    }
}
