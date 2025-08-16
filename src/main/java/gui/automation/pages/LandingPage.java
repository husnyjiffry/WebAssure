package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;

public class LandingPage extends BasePage {
    // Robust XPath locators for cards based on provided structure
    private final By elementsCard = By.xpath("//div[contains(@class,'card mt-4 top-card')]//h5[text()='Elements']/ancestor::div[contains(@class,'card mt-4 top-card')]");
    private final By formsCard = By.xpath("//div[contains(@class,'card mt-4 top-card')]//h5[text()='Forms']/ancestor::div[contains(@class,'card mt-4 top-card')]");
    private final By alertsCard = By.xpath("//div[contains(@class,'card mt-4 top-card')]//h5[text()='Alerts, Frame & Windows']/ancestor::div[contains(@class,'card mt-4 top-card')]");
    private final By widgetsCard = By.xpath("//div[contains(@class,'card mt-4 top-card')]//h5[text()='Widgets']/ancestor::div[contains(@class,'card mt-4 top-card')]");
    private final By interactionsCard = By.xpath("//div[contains(@class,'card mt-4 top-card')]//h5[text()='Interactions']/ancestor::div[contains(@class,'card mt-4 top-card')]");
    private final By bookStoreCard = By.xpath("//div[contains(@class,'card mt-4 top-card')]//h5[text()='Book Store Application']/ancestor::div[contains(@class,'card mt-4 top-card')]");
    private final By banner = By.xpath("//img[@class='banner-image' and @alt='Selenium Online Training']");
    private final By joinNowLink = By.xpath("//a[@href='https://www.toolsqa.com/selenium-training/']//img[@class='banner-image']");
    private final By logo = By.xpath("//header//a[@href='https://demoqa.com']//img[contains(@src,'Toolsq')]");
    private final By footerAd = By.xpath("//div[contains(@class,'swiper-slide')]//img");

    public boolean isElementsCardVisible() {
        return SeleniumUtil.waitForVisible(elementsCard) != null;
    }

    public boolean isFormsCardVisible() {
        return SeleniumUtil.waitForVisible(formsCard) != null;
    }

    public boolean isAlertsCardVisible() {
        return SeleniumUtil.waitForVisible(alertsCard) != null;
    }

    public boolean isWidgetsCardVisible() {
        return SeleniumUtil.waitForVisible(widgetsCard) != null;
    }

    public boolean isInteractionsCardVisible() {
        return SeleniumUtil.waitForVisible(interactionsCard) != null;
    }

    public boolean isBookStoreCardVisible() {
        return SeleniumUtil.waitForVisible(bookStoreCard) != null;
    }

    public boolean isBannerVisible() {
        return SeleniumUtil.waitForVisible(banner) != null;
    }

    public boolean isJoinNowLinkPresent() {
        return SeleniumUtil.waitForVisible(joinNowLink) != null;
    }

    public void clickJoinNowLink() {
        SeleniumUtil.click(joinNowLink);
    }

    public boolean isLogoVisible() {
        return SeleniumUtil.waitForVisible(logo) != null;
    }

    public boolean isFooterAdVisible() {
        return SeleniumUtil.waitForVisible(footerAd) != null;
    }

    public boolean isPageLoaded() {
        return isBannerVisible();
    }

    public void clickElementsCard() {
        SeleniumUtil.click(elementsCard);
        SeleniumUtil.waitSeconds(3);
    }

    public void clickFormsCard() {
        SeleniumUtil.click(formsCard);
        SeleniumUtil.waitSeconds(3);
    }

    public void clickAlertsCard() {
        SeleniumUtil.click(alertsCard);
        SeleniumUtil.waitSeconds(3);
    }

    public void clickWidgetsCard() {
        SeleniumUtil.click(widgetsCard);
        SeleniumUtil.waitSeconds(3);
    }

    public void clickInteractionsCard() {
        SeleniumUtil.click(interactionsCard);
        SeleniumUtil.waitSeconds(3);
    }

    public void clickBookStoreCard() {
        SeleniumUtil.click(bookStoreCard);
        SeleniumUtil.waitSeconds(3);
    }
}
