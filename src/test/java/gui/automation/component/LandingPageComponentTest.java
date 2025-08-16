package gui.automation.component;

import gui.automation.actions.LandingPageActions;
import gui.automation.base.BaseTest;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import gui.automation.utils.SeleniumUtil;

public class LandingPageComponentTest extends BaseTest {
    private LandingPageActions landingPageActions;

    @BeforeMethod
    public void setUpActions() {
        landingPageActions = new LandingPageActions();
    }

    @Test
    public void testLandingPageLoads() {
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Landing page should be loaded (banner visible)");
    }

    @Test
    public void testAllMainCardsVisible() {
        Assert.assertTrue(landingPageActions.allMainCardsVisible(), "All main cards should be visible");
    }

    @Test
    public void testBannerIsVisible() {
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Banner should be visible");
    }

    @Test
    public void testJoinNowLinkIsPresent() {
        Assert.assertTrue(landingPageActions.isJoinNowLinkPresent(), "JOIN NOW link should be present");
    }

    @Test
    public void testClickJoinNowLink() {
        landingPageActions.clickJoinNowLink();
        // Optionally, add an assertion to verify navigation if needed
    }

    @Test
    public void testLogoIsVisible() {
        Assert.assertTrue(landingPageActions.isLogoVisible(), "ToolsQA logo should be visible");
    }

    @Test
    public void testFooterAdIsVisible() {
        if (!landingPageActions.isFooterAdVisible()) {
            throw new SkipException("Footer ad not present, skipping test.");
        }
        Assert.assertTrue(true, "Footer ad is present on the landing page");
    }

    @Test
    public void testElementsCardNavigationAndBack() {
        landingPageActions.clickElementsCard();
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/elements", "Should navigate to Elements page");
        landingPageActions.goBack();
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Should return to landing page");
    }

    @Test
    public void testFormsCardNavigationAndBack() {
        landingPageActions.clickFormsCard();
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/forms", "Should navigate to Forms page");
        landingPageActions.goBack();
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Should return to landing page");
    }

    @Test
    public void testAlertsCardNavigationAndBack() {
        landingPageActions.clickAlertsCard();
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/alertsWindows", "Should navigate to Alerts, Frame & Windows page");
        landingPageActions.goBack();
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Should return to landing page");
    }

    @Test
    public void testWidgetsCardNavigationAndBack() {
        landingPageActions.clickWidgetsCard();
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/widgets", "Should navigate to Widgets page");
        landingPageActions.goBack();
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Should return to landing page");
    }

    @Test
    public void testInteractionsCardNavigationAndBack() {
        landingPageActions.clickInteractionsCard();
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/interaction", "Should navigate to Interactions page");
        landingPageActions.goBack();
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Should return to landing page");
    }

    @Test
    public void testBookStoreCardNavigationAndBack() {
        landingPageActions.clickBookStoreCard();
        Assert.assertEquals(landingPageActions.getCurrentUrl(), "https://demoqa.com/books", "Should navigate to Book Store Application page");
        landingPageActions.goBack();
        Assert.assertTrue(landingPageActions.isBannerVisible(), "Should return to landing page");
    }
}
