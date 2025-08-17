package gui.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Selenium WebDriver actions.
 * All Selenium-specific logic is encapsulated here for easy framework migration.
 */
public class SeleniumUtil {
    private static final int DEFAULT_WAIT = 10;
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Sets the WebDriver instance for the current thread.
     */
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    /**
     * Gets the WebDriver instance for the current thread.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Finds a web element by locator.
     */
    public static WebElement find(By by) {
        try {
            return getDriver().findElement(by);
        } catch (NoSuchElementException e) {
            logger.warn("Element not found: {}", by, e);
            return null;
        }
    }

    /**
     * Clicks an element after waiting for it to be clickable.
     */
    public static void click(By by) {
        WebElement element = waitForClickable(by);
        if (element != null) {
            element.click();
        } else {
            logger.error("Unable to click element: {}", by);
        }
    }

    /**
     * Types text into an element after waiting for it to be visible.
     */
    public static void type(By by, String text) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            element.clear();
            element.sendKeys(text);
        } else {
            logger.error("Unable to type in element: {}", by);
        }
    }

    /**
     * Waits for a web element to become visible on the page within the default timeout.
     *
     * This method is useful when you expect an element to appear after some delay (e.g., after navigation or an AJAX call).
     * It will repeatedly check for the element's visibility until the timeout is reached.
     *
     * @param by The locator (By) of the element to wait for.
     * @return The visible WebElement if found within the timeout, or null if the element is not visible in time.
     *
     * Example usage:
     *   WebElement button = SeleniumUtil.waitForVisible(By.id("submit"));
     *   if (button != null) {
     *       button.click();
     *   }
     */
    public static WebElement waitForVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            logger.warn("Element not visible after {} seconds: {}", DEFAULT_WAIT, by);
            return null;
        }
    }

    /**
     * Waits for an element to be clickable.
     */
    public static WebElement waitForClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (TimeoutException e) {
            logger.warn("Element not clickable after {} seconds: {}", DEFAULT_WAIT, by);
            return null;
        }
    }

    /**
     * Gets the text of an element.
     */
    public static String getText(By by) {
        WebElement element = find(by);
        return element != null ? element.getText() : "";
    }

    /**
     * Gets an attribute value from an element.
     */
    public static String getAttribute(By by, String attribute) {
        WebElement element = find(by);
        return element != null ? element.getAttribute(attribute) : "";
    }

    /**
     * Selects an option by visible text from a dropdown.
     */
    public static void selectByText(By by, String text) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Select(element).selectByVisibleText(text);
        }
    }

    /**
     * Selects an option by value from a dropdown.
     */
    public static void selectByValue(By by, String value) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Select(element).selectByValue(value);
        }
    }

    /**
     * Navigates to a URL.
     */
    public static void goTo(String url) {
        getDriver().get(url);
    }

    /**
     * Takes a screenshot and saves it to the given path.
     */
    public static void screenshot(String path) {
        try {
            TakesScreenshot ts = (TakesScreenshot) getDriver();
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException | WebDriverException e) {
            logger.error("Screenshot failed: {}", e.getMessage());
        }
    }

    /**
     * Waits for the page to load completely.
     */
    public static void waitForPageLoad() {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT)).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Hovers over an element.
     */
    public static void hover(By by) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Actions(getDriver()).moveToElement(element).perform();
        }
    }

    /**
     * Scrolls to an element.
     */
    public static void scrollTo(By by) {
        WebElement element = find(by);
        if (element != null) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        }
    }

    /**
     * Accepts an alert dialog.
     */
    public static void acceptAlert() {
        try {
            getDriver().switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {
        }
    }

    /**
     * Dismisses an alert dialog.
     */
    public static void dismissAlert() {
        try {
            getDriver().switchTo().alert().dismiss();
        } catch (NoAlertPresentException ignored) {
        }
    }

    /**
     * Gets the current page title.
     */
    public static String getTitle() {
        return getDriver().getTitle();
    }

    /**
     * Gets the current URL.
     */
    public static String getUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Maximizes the browser window.
     */
    public static void maximize() {
        getDriver().manage().window().maximize();
    }

    /**
     * Deletes all cookies.
     */
    public static void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Adds a cookie.
     */
    public static void addCookie(String name, String value) {
        getDriver().manage().addCookie(new Cookie(name, value));
    }

    /**
     * Waits for an element to disappear.
     */
    public static void waitForDisappear(By by) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Waits for a number of seconds (not recommended for real waits).
     */
    public static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Attempts to close any known pop-ups or ads by trying a list of common close button locators.
     */
    public static void closeKnownPopups() {
        By[] closeLocators = new By[]{
                By.xpath("//button[normalize-space()='Close']"),
                By.xpath("//button[contains(@class,'close')]"),
                By.xpath("//div[contains(@class,'close') or contains(@class,'Close') or contains(@class,'modal-close') or contains(@class,'popup-close')]"),
                By.cssSelector(".close, .close-btn, .close-button, .modal-close, .popup-close"),
                By.xpath("//span[text()='×']"),
                By.xpath("//button[@aria-label='Close']")
        };
        for (By locator : closeLocators) {
            try {
                WebElement closeBtn = waitForVisible(locator);
                if (closeBtn != null && closeBtn.isDisplayed()) {
                    closeBtn.click();
                    logger.info("Closed pop-up/ad using locator: {}", locator);
                    // Optionally, break after first close or continue to close multiple
                }
            } catch (Exception ignored) {
                // Ignore if not present
            }
        }
    }

    /**
     * Generates a random screenshot name with a timestamp.
     */
    public static String generateRandomNameWithTimestamp() {
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        return "screenshot_" + timeStamp;
    }

    /**
     * Captures a screenshot and saves it to the given path.
     */
    public static void captureScreenShot(String pathToStore) {
        try {
            TakesScreenshot screenShot = (TakesScreenshot) getDriver();
            java.io.File source = screenShot.getScreenshotAs(OutputType.FILE);
            java.io.File destination = new java.io.File(pathToStore);
            destination.getParentFile().mkdirs();
            java.nio.file.Files.copy(source.toPath(), destination.toPath());
        } catch (Exception e) {
            logger.error("An error occurred while saving the screenshot: {}", e.getMessage());
        }
    }

    public static void waitForUrlDoesNotContain(String fragment, int timeoutSeconds) {
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(timeoutSeconds));
        wait.until(driver -> !driver.getCurrentUrl().contains(fragment));
    }

    /**
     * Checks if a checkbox is checked by inspecting its class or attribute.
     * @param by The locator for the checkbox.
     * @return true if checked, false otherwise.
     */
    public static boolean isCheckboxChecked(By by) {
        WebElement element = find(by);
        if (element != null) {
            String clazz = element.getAttribute("class");
            // TODO: Update class name as per actual checked state in demoqa
            return clazz != null && clazz.contains("checked");
        }
        return false;
    }

    /**
     * Checks if a checkbox is partially checked (indeterminate state).
     * @param by The locator for the checkbox.
     * @return true if partially checked, false otherwise.
     */
    public static boolean isCheckboxPartiallyChecked(By by) {
        WebElement element = find(by);
        if (element != null) {
            String clazz = element.getAttribute("class");
            // TODO: Update class name as per actual partial state in demoqa
            return clazz != null && clazz.contains("indeterminate");
        }
        return false;
    }

    /**
     * Checks if a folder is expanded by inspecting its icon or attribute.
     * @param by The locator for the folder expand icon.
     * @return true if expanded, false otherwise.
     */
    public static boolean isFolderExpanded(By by) {
        WebElement element = find(by);
        if (element != null) {
            String clazz = element.getAttribute("class");
            // TODO: Update class name as per actual expanded state in demoqa
            return clazz != null && clazz.contains("expanded");
        }
        return false;
    }

    /**
     * Checks if a folder is collapsed by inspecting its icon or attribute.
     * @param by The locator for the folder collapse icon.
     * @return true if collapsed, false otherwise.
     */
    public static boolean isFolderCollapsed(By by) {
        WebElement element = find(by);
        if (element != null) {
            String clazz = element.getAttribute("class");
            // TODO: Update class name as per actual collapsed state in demoqa
            return clazz != null && clazz.contains("collapsed");
        }
        return false;
    }

    /**
     * Gets the names of all checked checkboxes under a given locator.
     * @param by The locator for all checked checkboxes.
     * @return List of names (text) of checked checkboxes.
     */
    public static List<String> getAllCheckedCheckboxNames(By by) {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = getDriver().findElements(by);
        for (WebElement el : elements) {
            names.add(el.getText());
        }
        return names;
    }

    /**
     * Gets the names of all partially checked checkboxes under a given locator.
     * @param by The locator for all partially checked checkboxes.
     * @return List of names (text) of partially checked checkboxes.
     */
    public static List<String> getAllPartiallyCheckedCheckboxNames(By by) {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = getDriver().findElements(by);
        for (WebElement el : elements) {
            names.add(el.getText());
        }
        return names;
    }
}
