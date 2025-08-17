package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;
import org.openqa.selenium.WebDriver;

public class LandingPage extends BasePage {
    // XPath locators for cards on the Landing Page

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

    // ----------------------
    // Visibility Check Methods
    // ----------------------

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

    public boolean isLogoVisible() {
        return SeleniumUtil.waitForVisible(logo) != null;
    }

    public boolean isFooterAdVisible() {
        return SeleniumUtil.waitForVisible(footerAd) != null;
    }

    public boolean isPageLoaded() {
        return isBannerVisible();
    }

    // ----------------------
    // Click Methods
    // ----------------------

    public void clickJoinNowLink() {
        SeleniumUtil.click(joinNowLink);
    }

    public void clickElementsCard() {
        SeleniumUtil.click(elementsCard);
    }

    public void clickFormsCard() {
        SeleniumUtil.click(formsCard);
    }

    public void clickAlertsCard() {
        SeleniumUtil.click(alertsCard);
    }

    public void clickWidgetsCard() {
        SeleniumUtil.click(widgetsCard);
    }

    public void clickInteractionsCard() {
        SeleniumUtil.click(interactionsCard);
    }

    public void clickBookStoreCard() {
        SeleniumUtil.click(bookStoreCard);
    }

    /**
     * Constructor for the LandingPage.
     * Receives a WebDriver instance and passes it to the BasePage,
     * allowing this page object to interact with the browser.
     */
    public LandingPage(WebDriver driver) {
        super(driver);
    }
}
