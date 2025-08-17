package gui.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // ===== Driver Management =====
    /**
     * Sets the WebDriver instance for the current thread.
     * <p>
     * Use this method at the start of your test or before using any SeleniumUtil methods that require a driver.
     * Example: SeleniumUtil.setDriver(driver);
     *
     * @param webDriver The WebDriver instance to use for this thread (e.g., ChromeDriver, FirefoxDriver).
     */
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    /**
     * Gets the WebDriver instance for the current thread.
     * <p>
     * Use this method to access the driver in your utility or page methods. It returns the driver previously set with setDriver().
     *
     * @return The WebDriver instance for this thread, or null if not set.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    // ===== Element Find/Wait =====
    /**
     * Finds a web element by locator.
     * <p>
     * Use this method to get a WebElement using a Selenium By locator (e.g., By.id, By.xpath).
     * Returns null if the element is not found.
     *
     * @param by The Selenium By locator (e.g., By.id("username"), By.xpath("//button"))
     * @return The found WebElement, or null if not found.
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
     * Waits for a web element to become visible on the page within the default timeout (10 seconds).
     * <p>
     * Use this when you expect an element to appear after some delay (e.g., after navigation or AJAX call).
     * Returns the visible WebElement, or null if not visible in time.
     *
     * @param by The locator (By) of the element to wait for.
     * @return The visible WebElement if found within the timeout, or null if not visible in time.
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
     * Waits for an element to be clickable (visible and enabled) within the default timeout (10 seconds).
     * <p>
     * Use this before clicking a button or link that may take time to become interactable.
     * Returns the clickable WebElement, or null if not clickable in time.
     *
     * @param by The locator (By) of the element to wait for.
     * @return The clickable WebElement if found, or null if not clickable in time.
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
     * Waits for an element to disappear (become invisible or removed from DOM) within the default timeout (10 seconds).
     * <p>
     * Use this to wait for loading spinners, overlays, or popups to disappear before proceeding.
     *
     * @param by The locator (By) of the element to wait for disappearance.
     */
    public static void waitForDisappear(By by) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Waits for an element to become visible with a custom timeout (in seconds).
     * <p>
     * Use this when you need a longer or shorter wait than the default. Returns the visible WebElement, or null if not visible in time.
     *
     * @param by The locator (By) of the element to wait for.
     * @param timeoutSeconds How many seconds to wait before timing out.
     * @return The visible WebElement if found, or null if not visible in time.
     */
    public static WebElement waitForElementVisibleWithTimeout(By by, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            logger.warn("Element not visible after {} seconds: {}", timeoutSeconds, by);
            return null;
        }
    }

    /**
     * Waits until the element is enabled (not disabled) within the given timeout (in seconds).
     * <p>
     * Use this for buttons or inputs that become enabled after some event.
     *
     * @param by The locator (By) of the element to check.
     * @param timeoutSeconds How many seconds to wait before timing out.
     * @return true if enabled within timeout, false otherwise.
     */
    public static boolean waitForElementEnabled(By by, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            return wait.until(driver -> {
                WebElement el = driver.findElement(by);
                return el.isEnabled();
            });
        } catch (TimeoutException e) {
            logger.warn("Element not enabled after {} seconds: {}", timeoutSeconds, by);
            return false;
        }
    }

    /**
     * Waits until the element's color (CSS 'color' property) changes from the initial value, within the given timeout (in seconds).
     * <p>
     * Use this for dynamic UI changes, such as progress bars or buttons that change color.
     *
     * @param by The locator (By) of the element to check.
     * @param initialColor The initial color value (e.g., 'rgba(0,0,0,1)').
     * @param timeoutSeconds How many seconds to wait before timing out.
     * @return true if color changed within timeout, false otherwise.
     */
    public static boolean waitForElementColorChange(By by, String initialColor, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
            return wait.until(driver -> {
                WebElement el = driver.findElement(by);
                String color = el.getCssValue("color");
                return !color.equals(initialColor);
            });
        } catch (TimeoutException e) {
            logger.warn("Element color did not change after {} seconds: {}", timeoutSeconds, by);
            return false;
        }
    }

    /**
     * Waits for the URL to not contain a specific fragment (substring) within the given timeout (in seconds).
     * <p>
     * Use this to wait for navigation or ad popups to finish changing the URL.
     *
     * @param fragment The substring to wait for removal from the URL.
     * @param timeoutSeconds How many seconds to wait before timing out.
     */
    public static void waitForUrlDoesNotContain(String fragment, int timeoutSeconds) {
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(timeoutSeconds));
        wait.until(driver -> !driver.getCurrentUrl().contains(fragment));
    }

    /**
     * Waits for the page to load completely (document.readyState == 'complete').
     * <p>
     * Use this after navigation or refresh to ensure the page is fully loaded before interacting with elements.
     */
    public static void waitForPageLoad() {
        new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_WAIT)).until(
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Waits for a number of seconds (simple sleep). Not recommended for real waits—prefer explicit waits.
     * <p>
     * Use this only for demonstration or debugging purposes.
     *
     * @param seconds How many seconds to pause the test.
     */
    public static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ===== Element Actions (Click, Type, Hover, Scroll, etc.) =====
    /**
     * Clicks an element after waiting for it to be clickable.
     * <p>
     * Use this for clicking buttons, links, or any clickable element. Waits up to 10 seconds by default.
     *
     * @param by The locator (By) of the element to click.
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
     * <p>
     * Use this for entering text into input fields or textareas.
     *
     * @param by The locator (By) of the input element.
     * @param text The text to type into the element.
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
     * Hovers the mouse over an element (shows tooltips or triggers hover effects).
     * <p>
     * Use this for menu items, tooltips, or any element with hover behavior.
     *
     * @param by The locator (By) of the element to hover over.
     */
    public static void hover(By by) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Actions(getDriver()).moveToElement(element).perform();
        }
    }

    /**
     * Scrolls the page to bring the element into view.
     * <p>
     * Use this for elements that are not visible in the current viewport.
     *
     * @param by The locator (By) of the element to scroll to.
     */
    public static void scrollTo(By by) {
        WebElement element = find(by);
        if (element != null) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        }
    }

    /**
     * Performs a double-click on the specified element.
     * <p>
     * Use this for elements that require a double-click action (e.g., editing a table cell).
     *
     * @param by The locator (By) of the element to double-click.
     */
    public static void doubleClick(By by) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Actions(getDriver()).doubleClick(element).perform();
        }
    }

    /**
     * Performs a right-click (context click) on the specified element.
     * <p>
     * Use this for context menus or elements that respond to right-click.
     *
     * @param by The locator (By) of the element to right-click.
     */
    public static void rightClick(By by) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Actions(getDriver()).contextClick(element).perform();
        }
    }

    // ===== Dropdowns & Selects =====
    /**
     * Selects an option by visible text from a dropdown (select element).
     * <p>
     * Use this to choose an option in a dropdown by what the user sees (e.g., "Option 1").
     *
     * @param by The locator (By) for the select element.
     * @param text The visible text of the option to select.
     * Example: SeleniumUtil.selectByText(By.id("dropdown"), "Option 1");
     */
    public static void selectByText(By by, String text) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Select(element).selectByVisibleText(text);
        }
    }

    /**
     * Selects an option by value from a dropdown (select element).
     * <p>
     * Use this to choose an option in a dropdown by its value attribute (e.g., value="1").
     *
     * @param by The locator (By) for the select element.
     * @param value The value attribute of the option to select.
     * Example: SeleniumUtil.selectByValue(By.id("dropdown"), "1");
     */
    public static void selectByValue(By by, String value) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            new Select(element).selectByValue(value);
        }
    }

    /**
     * Gets the visible text of the selected option in a dropdown.
     * <p>
     * Use this to verify which option is currently selected in a dropdown.
     *
     * @param by The locator (By) for the select element.
     * @return The visible text of the selected option, or empty string if not found.
     */
    public static String getSelectedOptionText(By by) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            Select select = new Select(element);
            WebElement selected = select.getFirstSelectedOption();
            return selected != null ? selected.getText() : "";
        }
        return "";
    }

    /**
     * Gets the value attribute of the selected option in a dropdown.
     * <p>
     * Use this to get the value (not the visible text) of the selected option.
     *
     * @param by The locator (By) for the select element.
     * @return The value of the selected option, or empty string if not found.
     */
    public static String getSelectedOptionValue(By by) {
        WebElement element = waitForVisible(by);
        if (element != null) {
            Select select = new Select(element);
            WebElement selected = select.getFirstSelectedOption();
            return selected != null ? selected.getAttribute("value") : "";
        }
        return "";
    }

    /**
     * Gets all visible option texts in a dropdown.
     * <p>
     * Use this to get a list of all options a user can see in a dropdown.
     *
     * @param by The locator (By) for the select element.
     * @return List of visible option texts.
     */
    public static List<String> getAllDropdownOptions(By by) {
        List<String> options = new ArrayList<>();
        WebElement element = waitForVisible(by);
        if (element != null) {
            Select select = new Select(element);
            for (WebElement option : select.getOptions()) {
                options.add(option.getText());
            }
        }
        return options;
    }

    // ===== Checkboxes, Radio Buttons, Buttons =====
    /**
     * Checks if a checkbox is checked by inspecting its class or attribute.
     * <p>
     * Use this to verify if a checkbox is selected (ticked).
     *
     * @param by The locator (By) for the checkbox element.
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
     * <p>
     * Use this for checkboxes that can be in an "indeterminate" state (partially checked).
     *
     * @param by The locator (By) for the checkbox element.
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
     * Checks if a folder (tree node) is expanded by inspecting its icon or attribute.
     * <p>
     * Use this for tree views or folder structures to check if a node is open.
     *
     * @param by The locator (By) for the folder expand icon.
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
     * Checks if a folder (tree node) is collapsed by inspecting its icon or attribute.
     * <p>
     * Use this for tree views or folder structures to check if a node is closed.
     *
     * @param by The locator (By) for the folder collapse icon.
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
     * Gets the names (text) of all checked checkboxes under a given locator.
     * <p>
     * Use this to get a list of all selected items in a group of checkboxes.
     *
     * @param by The locator (By) for all checked checkboxes.
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
     * Gets the names (text) of all partially checked checkboxes under a given locator.
     * <p>
     * Use this to get a list of all partially selected items in a group of checkboxes.
     *
     * @param by The locator (By) for all partially checked checkboxes.
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

    /**
     * Selects a radio button if it is not already selected.
     * <p>
     * Use this to select a radio button (e.g., gender, options) by its locator.
     *
     * @param by The locator (By) for the radio button input element.
     */
    public static void selectRadioButton(By by) {
        WebElement radio = waitForVisible(by);
        if (radio != null && !radio.isSelected()) {
            radio.click();
        }
    }

    /**
     * Checks if a radio button is selected.
     * <p>
     * Use this to verify if a radio button is currently selected.
     *
     * @param by The locator (By) for the radio button input element.
     * @return true if selected, false otherwise.
     */
    public static boolean isRadioButtonSelected(By by) {
        WebElement radio = find(by);
        return radio != null && radio.isSelected();
    }

    // ===== Tables & Web Data =====
    /**
     * Returns the number of rows in the table body (tbody).
     * <p>
     * Use this to count how many data rows are present in a table.
     *
     * @param tableBy The locator (By) for the table element.
     * @return Number of rows in tbody.
     */
    public static int getTableRowCount(By tableBy) {
        WebElement table = waitForVisible(tableBy);
        if (table != null) {
            return table.findElements(By.cssSelector("tbody tr")).size();
        }
        return 0;
    }

    /**
     * Returns the number of columns in the first row of the table body.
     * <p>
     * Use this to count how many columns are present in a table.
     *
     * @param tableBy The locator (By) for the table element.
     * @return Number of columns.
     */
    public static int getTableColumnCount(By tableBy) {
        WebElement table = waitForVisible(tableBy);
        if (table != null) {
            WebElement firstRow = table.findElement(By.cssSelector("tbody tr"));
            return firstRow.findElements(By.cssSelector("td")).size();
        }
        return 0;
    }

    /**
     * Returns the text of a specific cell (1-based index).
     * <p>
     * Use this to get the value of a cell in a table by row and column number.
     *
     * @param tableBy The locator (By) for the table element.
     * @param row 1-based row index (first row is 1).
     * @param col 1-based column index (first column is 1).
     * @return Cell text, or empty string if not found.
     */
    public static String getCellText(By tableBy, int row, int col) {
        WebElement table = waitForVisible(tableBy);
        if (table != null) {
            try {
                WebElement cell = table.findElement(By.cssSelector("tbody tr:nth-child(" + row + ") td:nth-child(" + col + ")"));
                return cell.getText();
            } catch (NoSuchElementException ignored) {}
        }
        return "";
    }

    /**
     * Clicks the edit button in the specified row (assumes a button with class 'edit' or similar).
     * <p>
     * Use this to start editing a row in a table (if supported by the UI).
     *
     * @param tableBy The locator (By) for the table element.
     * @param row 1-based row index (first row is 1).
     */
    public static void clickEditButtonInRow(By tableBy, int row) {
        WebElement table = waitForVisible(tableBy);
        if (table != null) {
            try {
                WebElement editBtn = table.findElement(By.cssSelector("tbody tr:nth-child(" + row + ") .edit"));
                editBtn.click();
            } catch (NoSuchElementException ignored) {}
        }
    }

    /**
     * Double-clicks a cell and types a new value (for inline editing tables).
     * <p>
     * Use this to update a cell value directly in the table (if supported by the UI).
     *
     * @param tableBy The locator (By) for the table element.
     * @param row 1-based row index (first row is 1).
     * @param col 1-based column index (first column is 1).
     * @param newValue The new value to enter.
     */
    public static void updateCellValue(By tableBy, int row, int col, String newValue) {
        WebElement table = waitForVisible(tableBy);
        if (table != null) {
            try {
                WebElement cell = table.findElement(By.cssSelector("tbody tr:nth-child(" + row + ") td:nth-child(" + col + ")"));
                new Actions(getDriver()).doubleClick(cell).perform();
                cell.clear();
                cell.sendKeys(newValue);
                cell.sendKeys(Keys.ENTER);
            } catch (NoSuchElementException ignored) {}
        }
    }

    /**
     * Verifies that a cell contains the expected value.
     * <p>
     * Use this to check if a table cell matches the expected value.
     *
     * @param tableBy The locator (By) for the table element.
     * @param row 1-based row index (first row is 1).
     * @param col 1-based column index (first column is 1).
     * @param expected The expected value.
     * @return true if the cell text matches expected, false otherwise.
     */
    public static boolean verifyCellValue(By tableBy, int row, int col, String expected) {
        String actual = getCellText(tableBy, row, col);
        return actual.equals(expected);
    }

    // ===== Links =====
    /**
     * Clicks a link by its visible text.
     * <p>
     * Use this to click on a hyperlink (anchor tag) by the text the user sees.
     *
     * @param linkText The visible text of the link (e.g., "Home").
     * Example: SeleniumUtil.clickLinkByText("Login");
     */
    public static void clickLinkByText(String linkText) {
        WebElement link = waitForVisible(By.linkText(linkText));
        if (link != null) {
            link.click();
        }
    }

    /**
     * Verifies that a link's href attribute matches the expected value.
     * <p>
     * Use this to check if a link points to the correct URL.
     *
     * @param by The locator (By) for the link element.
     * @param expectedHref The expected href value (URL).
     * @return true if the href matches, false otherwise.
     */
    public static boolean verifyLinkHref(By by, String expectedHref) {
        String actualHref = getAttribute(by, "href");
        return actualHref.equals(expectedHref);
    }

    // ===== File Upload/Download =====
    /**
     * Uploads a file by sending the file path to an <input type='file'> element.
     * <p>
     * Use this to automate file uploads in forms. The file input must be visible and enabled.
     *
     * @param by The locator (By) for the file input element.
     * @param filePath The absolute path to the file to upload.
     * Example: SeleniumUtil.uploadFile(By.id("upload"), "/path/to/file.txt");
     */
    public static void uploadFile(By by, String filePath) {
        WebElement fileInput = waitForVisible(by);
        if (fileInput != null) {
            fileInput.sendKeys(filePath);
        }
    }

    /**
     * Checks if a file with the given name exists in the specified download directory.
     * <p>
     * Use this to verify that a file was downloaded successfully.
     *
     * @param downloadDir The directory where files are downloaded.
     * @param fileName The name of the file to check for.
     * @return true if the file exists, false otherwise.
     */
    public static boolean isFileDownloaded(String downloadDir, String fileName) {
        java.io.File file = new java.io.File(downloadDir, fileName);
        return file.exists();
    }

    // ===== Form Handling =====
    /**
     * Fills a form by sending values to each field in order.
     * <p>
     * Use this to enter data into multiple form fields at once. The arrays must be the same length.
     *
     * @param fieldBys Array of locators (By[]) for the form fields.
     * @param values Array of values to enter (must match fieldBys length).
     * Example: SeleniumUtil.fillForm(new By[]{By.id("name"), By.id("email")}, new String[]{"John", "john@email.com"});
     */
    public static void fillForm(By[] fieldBys, String[] values) {
        if (fieldBys.length != values.length) {
            throw new IllegalArgumentException("Field and value array lengths do not match");
        }
        for (int i = 0; i < fieldBys.length; i++) {
            type(fieldBys[i], values[i]);
        }
    }

    /**
     * Clicks the submit button to submit a form.
     * <p>
     * Use this to submit a form after filling in the fields.
     *
     * @param submitButtonBy The locator (By) for the submit button.
     * Example: SeleniumUtil.submitForm(By.id("submit"));
     */
    public static void submitForm(By submitButtonBy) {
        click(submitButtonBy);
    }

    /**
     * Clears all fields in the form.
     * <p>
     * Use this to empty all input fields in a form before entering new data.
     *
     * @param fieldBys Array of locators (By[]) for the form fields.
     */
    public static void clearForm(By[] fieldBys) {
        for (By by : fieldBys) {
            WebElement field = find(by);
            if (field != null) {
                field.clear();
            }
        }
    }

    /**
     * Checks if a form field is empty.
     * <p>
     * Use this to verify that a field has no value (e.g., after reset or reload).
     *
     * @param by The locator (By) for the field.
     * @return true if the field is empty, false otherwise.
     */
    public static boolean isFormFieldEmpty(By by) {
        WebElement field = find(by);
        return field != null && field.getAttribute("value").isEmpty();
    }

    /**
     * Reads the first row of a CSV file and returns the values as a String array.
     * <p>
     * Use this for data-driven testing with CSV files.
     *
     * @param csvPath The path to the CSV file.
     * @return String[] of values, or null if error.
     */
    public static String[] readFirstRowFromCSV(String csvPath) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(csvPath))) {
            String line = br.readLine();
            if (line != null) {
                return line.split(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads the first row of the first sheet in an Excel file and returns the values as a String array.
     * <p>
     * Use this for data-driven testing with Excel files (XLS/XLSX).
     *
     * @param excelPath The path to the Excel file.
     * @return String[] of values, or null if error.
     */
    public static String[] readFirstRowFromExcel(String excelPath) {
        try (org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(new java.io.File(excelPath))) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);
            if (row != null) {
                String[] values = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(i);
                    values[i] = cell != null ? cell.toString() : "";
                }
                return values;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== Alerts, Windows, Frames =====
    /**
     * Accepts an alert dialog (clicks OK/Yes on a popup alert).
     * <p>
     * Use this when a JavaScript alert, confirm, or prompt appears and you want to accept it.
     * Example: SeleniumUtil.acceptAlert();
     */
    public static void acceptAlert() {
        getDriver().switchTo().alert().accept();
    }

    /**
     * Dismisses an alert dialog (clicks Cancel/No on a popup alert).
     * <p>
     * Use this when a JavaScript alert, confirm, or prompt appears and you want to dismiss it.
     * Example: SeleniumUtil.dismissAlert();
     */
    public static void dismissAlert() {
        getDriver().switchTo().alert().dismiss();
    }

    /**
     * Gets the text from an alert dialog.
     * <p>
     * Use this to read the message shown in a JavaScript alert, confirm, or prompt.
     * @return The alert text.
     * Example: String msg = SeleniumUtil.getAlertText();
     */
    public static String getAlertText() {
        return getDriver().switchTo().alert().getText();
    }

    /**
     * Switches to a frame by its index (0-based).
     * <p>
     * Use this to interact with elements inside an iframe by its order on the page.
     * @param index The 0-based index of the frame (first frame is 0).
     * Example: SeleniumUtil.switchToFrameByIndex(1);
     */
    public static void switchToFrameByIndex(int index) {
        getDriver().switchTo().frame(index);
    }

    /**
     * Switches to a frame by its name or id attribute.
     * <p>
     * Use this to interact with elements inside an iframe by its name or id.
     * @param nameOrId The name or id of the frame.
     * Example: SeleniumUtil.switchToFrameByNameOrId("myFrame");
     */
    public static void switchToFrameByNameOrId(String nameOrId) {
        getDriver().switchTo().frame(nameOrId);
    }

    /**
     * Switches back to the main document from a frame.
     * <p>
     * Use this after working inside a frame to return to the main page.
     * Example: SeleniumUtil.switchToDefaultContent();
     */
    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    /**
     * Switches to a newly opened browser window or tab.
     * <p>
     * Use this after clicking a link or button that opens a new window/tab.
     * Example: SeleniumUtil.switchToNewWindow();
     */
    public static void switchToNewWindow() {
        String current = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(current)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
    }

    /**
     * Closes the current window and switches back to the first window.
     * <p>
     * Use this to close popups or child windows and return to the main window.
     * Example: SeleniumUtil.closeCurrentWindowAndSwitchBack();
     */
    public static void closeCurrentWindowAndSwitchBack() {
        String first = getDriver().getWindowHandles().iterator().next();
        getDriver().close();
        getDriver().switchTo().window(first);
    }

    // ===== UI Widgets (Tabs, Accordions, Sliders, Progress Bars, Tooltips, Menus, Drag/Drop, Resize, Selectable, Sortable) =====
    /**
     * Clicks a tab by its visible text within a tab container.
     * <p>
     * Use this to select a tab (e.g., "Profile", "Settings") in a tabbed UI.
     * @param tabContainerBy The locator (By) for the tab container.
     * @param tabText The visible text of the tab to select.
     * Example: SeleniumUtil.selectTabByText(By.id("tabs"), "Profile");
     */
    public static void selectTabByText(By tabContainerBy, String tabText) {
        WebElement tab = waitForVisible(tabContainerBy);
        if (tab != null) {
            WebElement item = tab.findElement(By.xpath(".//*[text()='" + tabText + "']"));
            item.click();
        }
    }

    /**
     * Returns true if the tab is selected (active).
     * <p>
     * Use this to check if a tab is currently active/selected.
     * @param tabBy The locator (By) for the tab element.
     * @return true if the tab is selected, false otherwise.
     */
    public static boolean isTabSelected(By tabBy) {
        WebElement tab = find(tabBy);
        return tab != null && tab.isSelected();
    }

    /**
     * Expands an accordion section by its visible text.
     * <p>
     * Use this to open a section in an accordion UI (e.g., FAQ, collapsible panels).
     * @param accordionBy The locator (By) for the accordion container.
     * @param sectionText The visible text of the section to expand.
     * Example: SeleniumUtil.expandAccordionSection(By.id("accordion"), "Section 1");
     */
    public static void expandAccordionSection(By accordionBy, String sectionText) {
        WebElement accordion = waitForVisible(accordionBy);
        if (accordion != null) {
            WebElement section = accordion.findElement(By.xpath(".//*[text()='" + sectionText + "']"));
            new Actions(getDriver()).moveToElement(section).perform();
            // Wait for section to expand (assume class change or attribute change)
            // This is a placeholder, actual implementation might involve waiting for a specific class
            // or checking if the section is visible and not collapsed.
        }
    }

    /**
     * Returns true if the accordion section is expanded.
     * <p>
     * Use this to check if a section in an accordion is open.
     * @param sectionBy The locator (By) for the accordion section.
     * @return true if expanded, false otherwise.
     */
    public static boolean isAccordionSectionExpanded(By sectionBy) {
        WebElement section = find(sectionBy);
        if (section != null) {
            String clazz = section.getAttribute("class");
            // TODO: Update class name as per actual expanded state in demoqa
            return clazz != null && clazz.contains("expanded");
        }
        return false;
    }

    /**
     * Sets the slider to a specific value using Actions.
     * <p>
     * Use this to move a slider (range input) to a desired value.
     * @param sliderBy The locator (By) for the slider element.
     * @param value The value to set (may require calibration for pixel offset).
     * Example: SeleniumUtil.setSliderValue(By.id("slider"), 50);
     */
    public static void setSliderValue(By sliderBy, int value) {
        WebElement slider = waitForVisible(sliderBy);
        if (slider != null) {
            // This is a placeholder. Actual implementation depends on the slider's HTML structure.
            // It might involve moving the slider thumb to a specific position or using a range input.
            // For a simple example, we'll just click and hope for the best.
            new Actions(getDriver()).clickAndHold(slider).moveByOffset(value, 0).release().perform();
        }
    }

    /**
     * Gets the current value of the slider (from attribute or text).
     * <p>
     * Use this to read the value of a slider (range input).
     * @param sliderBy The locator (By) for the slider element.
     * @return The slider value as a string.
     */
    public static String getSliderValue(By sliderBy) {
        WebElement slider = find(sliderBy);
        if (slider != null) {
            String val = slider.getAttribute("value");
            if (val == null || val.isEmpty()) {
                val = slider.getText();
            }
            return val;
        }
        return "";
    }

    /**
     * Returns the current value of a progress bar.
     * <p>
     * Use this to read the progress (e.g., 0-100) of a progress bar UI.
     * @param progressBarBy The locator (By) for the progress bar element.
     * @return The progress value as a string.
     */
    public static String getProgressBarValue(By progressBarBy) {
        WebElement bar = waitForVisible(progressBarBy);
        if (bar != null) {
            String val = bar.getAttribute("aria-valuenow");
            return val != null ? val : bar.getText();
        }
        return "";
    }

    /**
     * Waits until the progress bar reaches a value or timeout.
     * <p>
     * Use this to wait for a progress bar to complete or reach a certain value.
     * @param progressBarBy The locator (By) for the progress bar element.
     * @param targetValue The value to wait for (e.g., 100).
     * @param timeoutSeconds How many seconds to wait before timing out.
     * @return true if the value is reached, false otherwise.
     */
    public static boolean waitForProgressBarToReach(By progressBarBy, int targetValue, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        return wait.until(driver -> {
            WebElement bar = driver.findElement(progressBarBy);
            String val = bar.getAttribute("aria-valuenow");
            try {
                return val != null && Integer.parseInt(val) >= targetValue;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Hovers over an element and returns the tooltip text.
     * <p>
     * Use this to get the tooltip shown when hovering over an element.
     * @param elementBy The locator (By) for the element with a tooltip.
     * @return The tooltip text, or empty string if not found.
     */
    public static String getTooltipText(By elementBy) {
        WebElement element = waitForVisible(elementBy);
        if (element != null) {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).perform();
            // Wait for tooltip to appear (assume title attribute or aria-label)
            String tooltip = element.getAttribute("title");
            if (tooltip == null || tooltip.isEmpty()) {
                tooltip = element.getAttribute("aria-label");
            }
            return tooltip != null ? tooltip : "";
        }
        return "";
    }

    /**
     * Clicks a menu item by visible text within a menu container.
     * <p>
     * Use this to select an item from a dropdown or context menu.
     * @param menuBy The locator (By) for the menu container.
     * @param itemText The visible text of the menu item to select.
     * Example: SeleniumUtil.selectMenuItemByText(By.id("menu"), "Settings");
     */
    public static void selectMenuItemByText(By menuBy, String itemText) {
        WebElement menu = waitForVisible(menuBy);
        if (menu != null) {
            WebElement item = menu.findElement(By.xpath(".//*[text()='" + itemText + "']"));
            item.click();
        }
    }

    /**
     * Drags an element to a target location (for drag-and-drop UIs).
     * <p>
     * Use this to move an element (e.g., card, list item) to a new position.
     * @param sourceBy The locator (By) for the element to drag.
     * @param targetBy The locator (By) for the drop target.
     * Example: SeleniumUtil.dragAndDrop(By.id("item1"), By.id("target"));
     */
    public static void dragAndDrop(By sourceBy, By targetBy) {
        WebElement source = waitForVisible(sourceBy);
        WebElement target = waitForVisible(targetBy);
        if (source != null && target != null) {
            Actions actions = new Actions(getDriver());
            actions.dragAndDrop(source, target).perform();
        }
    }

    /**
     * Resizes an element by the given offset (for resizable UIs).
     * <p>
     * Use this to resize a UI element (e.g., window, panel) by dragging its edge or corner.
     * @param resizableBy The locator (By) for the resizable element.
     * @param xOffset Horizontal pixels to drag.
     * @param yOffset Vertical pixels to drag.
     * Example: SeleniumUtil.resizeElement(By.id("panel"), 50, 0);
     */
    public static void resizeElement(By resizableBy, int xOffset, int yOffset) {
        WebElement resizable = waitForVisible(resizableBy);
        if (resizable != null) {
            Actions actions = new Actions(getDriver());
            actions.clickAndHold(resizable).moveByOffset(xOffset, yOffset).release().perform();
        }
    }

    /**
     * Selects an item by text in a selectable list.
     * <p>
     * Use this to select an item in a selectable or multi-select list.
     * @param containerBy The locator (By) for the list container.
     * @param itemText The visible text of the item to select.
     * Example: SeleniumUtil.selectItemByText(By.id("list"), "Item 2");
     */
    public static void selectItemByText(By containerBy, String itemText) {
        WebElement container = waitForVisible(containerBy);
        if (container != null) {
            WebElement item = container.findElement(By.xpath(".//*[text()='" + itemText + "']"));
            item.click();
        }
    }

    /**
     * Drags a sortable item to a new position (for sortable lists/grids).
     * <p>
     * Use this to reorder items in a sortable UI.
     * @param itemBy The locator (By) for the item to move.
     * @param targetBy The locator (By) for the new position.
     * Example: SeleniumUtil.sortItem(By.id("item1"), By.id("item3"));
     */
    public static void sortItem(By itemBy, By targetBy) {
        dragAndDrop(itemBy, targetBy);
    }

    /**
     * Checks if a modal dialog is visible.
     * <p>
     * Use this to verify if a modal dialog (popup) is currently shown.
     * @param dialogBy The locator (By) for the modal dialog.
     * @return true if visible, false otherwise.
     */
    public static boolean isModalDialogVisible(By dialogBy) {
        WebElement dialog = waitForVisible(dialogBy);
        return dialog != null && dialog.isDisplayed();
    }

    /**
     * Clicks the close button of a modal dialog.
     * <p>
     * Use this to close a modal dialog (popup) by clicking its close button.
     * @param closeBtnBy The locator (By) for the close button.
     * Example: SeleniumUtil.closeModalDialog(By.className("close"));
     */
    public static void closeModalDialog(By closeBtnBy) {
        WebElement closeBtn = waitForVisible(closeBtnBy);
        if (closeBtn != null) {
            closeBtn.click();
        }
    }

    // ===== Broken Links/Images =====
    /**
     * Checks all <a> links on the current page for broken URLs (HTTP status >= 400).
     * <p>
     * Use this to find broken links on a web page for validation or reporting.
     * @return List of broken link URLs (with status or error message).
     * Example: List<String> broken = SeleniumUtil.checkBrokenLinks();
     */
    public static List<String> checkBrokenLinks() {
        List<String> brokenLinks = new ArrayList<>();
        List<WebElement> links = getDriver().findElements(By.tagName("a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url != null && !url.trim().isEmpty() && !url.startsWith("javascript")) {
                try {
                    java.net.HttpURLConnection connection = (java.net.HttpURLConnection) java.net.URI.create(url).toURL().openConnection();
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code >= 400) {
                        brokenLinks.add(url + " (Status: " + code + ")");
                    }
                } catch (Exception e) {
                    brokenLinks.add(url + " (Exception: " + e.getMessage() + ")");
                }
            }
        }
        return brokenLinks;
    }

    /**
     * Checks all <img> images on the current page for broken sources (HTTP status >= 400).
     * <p>
     * Use this to find broken images on a web page for validation or reporting.
     * @return List of broken image URLs (with status or error message).
     * Example: List<String> broken = SeleniumUtil.checkBrokenImages();
     */
    public static List<String> checkBrokenImages() {
        List<String> brokenImages = new ArrayList<>();
        List<WebElement> images = getDriver().findElements(By.tagName("img"));
        for (WebElement img : images) {
            String url = img.getAttribute("src");
            if (url != null && !url.trim().isEmpty()) {
                try {
                    java.net.HttpURLConnection connection = (java.net.HttpURLConnection) java.net.URI.create(url).toURL().openConnection();
                    connection.setRequestMethod("HEAD");
                    connection.connect();
                    int code = connection.getResponseCode();
                    if (code >= 400) {
                        brokenImages.add(url + " (Status: " + code + ")");
                    }
                } catch (Exception e) {
                    brokenImages.add(url + " (Exception: " + e.getMessage() + ")");
                }
            }
        }
        return brokenImages;
    }

    // ===== Screenshots & Utilities =====
    /**
     * Takes a screenshot and saves it to the given path.
     * <p>
     * Use this to capture the current browser view for debugging or reporting.
     * @param path The file path to save the screenshot (e.g., "screenshots/test.png").
     * Example: SeleniumUtil.screenshot("screenshots/home.png");
     */
    public static void screenshot(String path) {
        try {
            ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            org.apache.commons.io.FileUtils.copyFile(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE), new java.io.File(path));
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", path, e);
        }
    }

    /**
     * Generates a random screenshot name with a timestamp.
     * <p>
     * Use this to create unique screenshot filenames.
     * @return A string like "screenshot_20240101_123456".
     * Example: String name = SeleniumUtil.generateRandomNameWithTimestamp();
     */
    public static String generateRandomNameWithTimestamp() {
        return "screenshot_" + System.currentTimeMillis() + ".png";
    }

    /**
     * Captures a screenshot and saves it to the given path (alternative method).
     * <p>
     * Use this for compatibility with some listeners or reporting tools.
     * @param pathToStore The file path to save the screenshot.
     * Example: SeleniumUtil.captureScreenShot("screenshots/error.png");
     */
    public static void captureScreenShot(String pathToStore) {
        screenshot(pathToStore);
    }

    /**
     * Attempts to close any known pop-ups or ads by trying a list of common close button locators.
     * <p>
     * Use this to automatically dismiss popups or overlays that may block test actions.
     * Example: SeleniumUtil.closeKnownPopups();
     */
    public static void closeKnownPopups() {
        // Common close button locators (example)
        List<By> closeButtons = new ArrayList<>();
        closeButtons.add(By.xpath("//button[contains(text(), 'Close')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Dismiss')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Cancel')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'No')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'OK')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Yes')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Continue')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Proceed')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Accept')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Reject')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Decline')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Deny')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Ignore')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Skip')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Next')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Previous')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Back')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Finish')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Complete')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Confirm')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Accept All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Reject All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Decline All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Deny All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Ignore All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Skip All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Next All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Previous All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Back All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Finish All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Complete All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Confirm All')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Accept All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Reject All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Decline All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Deny All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Ignore All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Skip All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Next All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Previous All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Back All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Finish All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Complete All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Confirm All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Accept All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Reject All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Decline All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Deny All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Ignore All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Skip All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Next All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Previous All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Back All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Finish All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Complete All Cookies')]"));
        closeButtons.add(By.xpath("//button[contains(text(), 'Confirm All Cookies')]"));

        for (By closeButton : closeButtons) {
            WebElement button = find(closeButton);
            if (button != null) {
                try {
                    button.click();
                    logger.info("Closed popup/ad using locator: {}", closeButton);
                    return; // Exit after closing one
                } catch (Exception e) {
                    logger.debug("Could not close popup/ad with locator: {}", closeButton, e);
                }
            }
        }
        logger.info("No known popup/ad found to close.");
    }

    // ===== Miscellaneous/Other =====
    /**
     * Gets the text of an element.
     * <p>
     * Use this to read the visible text from any element (e.g., label, div, span).
     * @param by The locator (By) for the element.
     * @return The text content, or empty string if not found.
     */
    public static String getText(By by) {
        WebElement element = find(by);
        return element != null ? element.getText() : "";
    }

    /**
     * Gets an attribute value from an element.
     * <p>
     * Use this to read any attribute (e.g., href, value, class) from an element.
     * @param by The locator (By) for the element.
     * @param attribute The attribute name (e.g., "href").
     * @return The attribute value, or empty string if not found.
     */
    public static String getAttribute(By by, String attribute) {
        WebElement element = find(by);
        return element != null ? element.getAttribute(attribute) : "";
    }

    /**
     * Navigates to a URL in the current browser window.
     * <p>
     * Use this to open a web page by its address.
     * @param url The URL to navigate to (e.g., "https://demoqa.com").
     * Example: SeleniumUtil.goTo("https://demoqa.com");
     */
    public static void goTo(String url) {
        getDriver().get(url);
    }

    /**
     * Gets the current page title.
     * <p>
     * Use this to verify the title of the loaded web page.
     * @return The page title.
     * Example: String title = SeleniumUtil.getTitle();
     */
    public static String getTitle() {
        return getDriver().getTitle();
    }

    /**
     * Gets the current URL of the browser.
     * <p>
     * Use this to verify navigation or redirection.
     * @return The current URL.
     * Example: String url = SeleniumUtil.getUrl();
     */
    public static String getUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Maximizes the browser window.
     * <p>
     * Use this to ensure the browser is in full-screen mode for consistent element visibility.
     * Example: SeleniumUtil.maximize();
     */
    public static void maximize() {
        getDriver().manage().window().maximize();
    }

    /**
     * Deletes all cookies in the browser.
     * <p>
     * Use this to clear session or authentication data between tests.
     * Example: SeleniumUtil.clearCookies();
     */
    public static void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Adds a cookie to the browser session.
     * <p>
     * Use this to set authentication or custom cookies before loading a page.
     * @param name The cookie name.
     * @param value The cookie value.
     * Example: SeleniumUtil.addCookie("token", "abc123");
     */
    public static void addCookie(String name, String value) {
        Cookie cookie = new Cookie.Builder(name, value).build();
        getDriver().manage().addCookie(cookie);
    }
}
