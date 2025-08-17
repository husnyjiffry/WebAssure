package gui.automation.stepdefs;

import gui.automation.actions.LandingPageActions;
import gui.automation.utils.SeleniumUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.SkipException;

public class LandingPageComponentSteps {
    private LandingPageActions landingPageActions;

    @Given("I am on the landing page")
    public void i_am_on_the_landing_page() {
        landingPageActions = new LandingPageActions(SeleniumUtil.getDriver());
        SeleniumUtil.getDriver().get("https://demoqa.com/");
    }

    @Then("the {string} card should be visible")
    public void card_should_be_visible(String cardName) {
        switch (cardName) {
            case "Elements":
                Assert.assertTrue(landingPageActions.isElementsCardVisible(), "Elements card should be visible");
                break;
            case "Forms":
                Assert.assertTrue(landingPageActions.isFormsCardVisible(), "Forms card should be visible");
                break;
            case "Alerts":
                Assert.assertTrue(landingPageActions.isAlertsCardVisible(), "Alerts card should be visible");
                break;
            case "Widgets":
                Assert.assertTrue(landingPageActions.isWidgetsCardVisible(), "Widgets card should be visible");
                break;
            case "Interactions":
                Assert.assertTrue(landingPageActions.isInteractionsCardVisible(), "Interactions card should be visible");
                break;
            case "Book Store Application":
            case "Book Store":
                Assert.assertTrue(landingPageActions.isBookStoreCardVisible(), "Book Store Application card should be visible");
                break;
            default:
                throw new IllegalArgumentException("Unknown card: " + cardName);
        }
    }

    @When("I click the Elements card")
    public void i_click_the_elements_card() {
        landingPageActions.clickElementsCard();
    }

    @Then("the Elements page should be loaded")
    public void elements_page_should_be_loaded() {
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/elements");
    }

    @When("I click the Forms card")
    public void i_click_the_forms_card() {
        landingPageActions.clickFormsCard();
    }

    @Then("the Forms page should be loaded")
    public void forms_page_should_be_loaded() {
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/forms");
    }

    @When("I click the Alerts card")
    public void i_click_the_alerts_card() {
        landingPageActions.clickAlertsCard();
    }

    @Then("the Alerts page should be loaded")
    public void alerts_page_should_be_loaded() {
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/alertsWindows");
    }

    @When("I click the Widgets card")
    public void i_click_the_widgets_card() {
        landingPageActions.clickWidgetsCard();
    }

    @Then("the Widgets page should be loaded")
    public void widgets_page_should_be_loaded() {
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/widgets");
    }

    @When("I click the Interactions card")
    public void i_click_the_interactions_card() {
        landingPageActions.clickInteractionsCard();
    }

    @Then("the Interactions page should be loaded")
    public void interactions_page_should_be_loaded() {
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/interaction");
    }

    @When("I click the Book Store card")
    public void i_click_the_book_store_card() {
        landingPageActions.clickBookStoreCard();
    }

    @Then("the Book Store page should be loaded")
    public void book_store_page_should_be_loaded() {
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/books");
    }
}
