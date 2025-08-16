package gui.automation.actions;

import gui.automation.pages.LandingPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LandingPageActions extends BaseActions {
    private static final Logger logger = LoggerFactory.getLogger(LandingPageActions.class);
    private final LandingPage landingPage = new LandingPage();

    public boolean allMainCardsVisible() {
        logger.info("Checking visibility of all main cards on the landing page");
        return landingPage.isElementsCardVisible() &&
                landingPage.isFormsCardVisible() &&
                landingPage.isAlertsCardVisible() &&
                landingPage.isWidgetsCardVisible() &&
                landingPage.isInteractionsCardVisible() &&
                landingPage.isBookStoreCardVisible();
    }

    public boolean isBannerVisible() {
        logger.info("Checking if banner is visible");
        return landingPage.isBannerVisible();
    }
    public boolean isJoinNowLinkPresent() {
        logger.info("Checking if Join Now link is present");
        return landingPage.isJoinNowLinkPresent();
    }
    public void clickJoinNowLink() {
        logger.info("Clicking Join Now link");
        landingPage.clickJoinNowLink();
    }
    public boolean isLogoVisible() {
        logger.info("Checking if logo is visible");
        return landingPage.isLogoVisible();
    }
    public boolean isFooterAdVisible() {
        logger.info("Checking if footer ad is visible");
        return landingPage.isFooterAdVisible();
    }
    public void clickElementsCard() {
        logger.info("Clicking Elements card");
        landingPage.clickElementsCard();
    }
    public void clickFormsCard() {
        logger.info("Clicking Forms card");
        landingPage.clickFormsCard();
    }
    public void clickAlertsCard() {
        logger.info("Clicking Alerts card");
        landingPage.clickAlertsCard();
    }
    public void clickWidgetsCard() {
        logger.info("Clicking Widgets card");
        landingPage.clickWidgetsCard();
    }
    public void clickInteractionsCard() {
        logger.info("Clicking Interactions card");
        landingPage.clickInteractionsCard();
    }
    public void clickBookStoreCard() {
        logger.info("Clicking Book Store card");
        landingPage.clickBookStoreCard();
    }
}
