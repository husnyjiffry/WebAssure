# WebAssure UI Automation Framework

[![Build Status](https://img.shields.io/badge/build-manual-blue)](https://github.com/husnyjiffry/WebAssure/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)](https://adoptopenjdk.net/)

## Vision

**Empowering the QA Community and Future Automation Engineers**

My vision for WebAssure is to:
- Support the global QA community, automation enthusiasts, and fresh graduates aspiring to work in Software Engineer in Test (SET) roles.
- Make automation accessible, practical, and business-friendly for everyone, regardless of background or experience.
- Empower others to learn, share, and grow in the field of test automation through open-source collaboration.
- Foster a culture of continuous learning, knowledge sharing, and support for anyone passionate about quality engineering.

Whether you are a beginner, a student, or a seasoned professional, this framework is for you. Let's build a stronger, more inclusive automation community—together!

---

## Table of Contents
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [How to Run Tests](#how-to-run-tests)
- [Test Styles Supported: TestNG & BDD (Cucumber)](#test-styles-supported-testng--bdd-cucumber)
- [Adding/Updating ChromeDriver](#addingupdating-chromedriver)
- [Framework Design](#framework-design)
- [How to Add New Tests](#how-to-add-new-tests)
- [SeleniumUtil: Why and How](#seleniumutil-why-and-how)
- [Parallel Execution](#parallel-execution)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

---

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/husnyjiffry/WebAssure
   cd WebAssure
   ```

2. **Install dependencies:**
   ```sh
   mvn clean install
   ```

---

## Prerequisites
- Java 11+ (recommended: Java 17 or newer)
- Maven 3.6+
- Chrome browser (version matching your chromedriver)
- (Optional) IntelliJ IDEA or VSCode for development

---

## Project Structure

```
WebAssure/
├── src/
│   ├── main/
│   │   ├── java/gui/automation/pages/      # Page Objects
│   │   ├── java/gui/automation/actions/    # Actions (user flows)
│   │   ├── java/gui/automation/utils/      # Utilities (SeleniumUtil, ConfigUtils, etc.)
│   ├── test/
│   │   ├── java/gui/automation/component/  # Component/UI tests
│   │   ├── java/gui/automation/e2e/        # E2E tests (optional)
│   │   ├── java/gui/automation/stepdefs/   # Cucumber step definitions
│   │   ├── java/gui/automation/runner/     # Cucumber test runners
│   ├── resources/
│   │   ├── features/                      # Cucumber feature files
│   │   ├── config.properties               # Config file
│   │   ├── driver/chrome/chromedriver      # ChromeDriver binary
│   │   └── testng.xml                      # TestNG suite config
├── pom.xml                                 # Maven dependencies
└── README.md                               # This documentation
```

---

## Setup & Installation

1. **Download the correct ChromeDriver:**
   - Visit [ChromeDriver downloads](https://chromedriver.chromium.org/downloads)
   - Download the version matching your Chrome browser and OS (e.g., mac-arm64 for Apple Silicon)
   - Place the binary in `src/main/resources/driver/chrome/`
   - Make it executable:
     ```sh
     chmod +x src/main/resources/driver/chrome/chromedriver
     ```
   - **macOS Users:**
     - If you see a message like "chromedriver cannot be opened because the developer cannot be verified" or the binary is blocked by Gatekeeper:
       1. Open **System Settings** > **Privacy & Security**.
       2. Scroll down to the "Security" section.
       3. You should see a message about "chromedriver" being blocked. Click **Allow Anyway**.
       4. Try running your tests again. The first time, you may be prompted to confirm opening the file—click **Open**.
     - This step is required only once per new chromedriver binary.

2. **Configure properties:**
   - Edit `src/main/resources/config.properties` for base URL, browser, etc.
   - To run tests without opening a visible browser window (headless mode), set `headless=true` in `src/main/resources/config.properties`. This is useful for CI/CD or running on servers.

**Example config.properties:**

| Property         | Description                                      | Example Value                |
|------------------|--------------------------------------------------|------------------------------|
| base.url         | The base URL for your tests                      | https://demoqa.com/          |
| browser          | Browser to use (chrome, firefox, etc.)           | chrome                       |
| timeout.seconds  | Default wait timeout for elements (in seconds)   | 10                           |
| use.bundled.driver | Use bundled chromedriver binary (true/false)   | true                         |
| screenshot.dir   | Directory to save screenshots                    | src/test/resources/screenshots |
| headless         | Run browser in headless mode (true/false)        | true                         |

---

## How to Run Tests

- **All tests:**
  ```sh
  mvn clean test
  ```
- **Specific test class:**
  ```sh
  mvn clean test -Dtest=gui.automation.component.ElementsPageComponentTest
  ```
- **Parallel execution:**  
  Tests run in parallel by default (see `testng.xml`).

---

## Test Styles Supported: TestNG & BDD (Cucumber)

> **Note for Beginners:**
> 
> The test classes and feature files included in this framework are **example tests**. You can use them as templates or references when writing your own tests. They demonstrate best practices for both TestNG and BDD styles.

This framework supports **two ways to write and run tests**:

### 1. TestNG (Classic Java)
> **Example Tests Provided:**
> 
> All test classes in `src/test/java/gui/automation/component/` are provided as examples. Feel free to copy, modify, or use them as a starting point for your own UI tests.
- **What is it?**
  - Write tests as Java methods using the TestNG framework (like JUnit, but more powerful for UI automation).
  - Good for technical users who prefer code-based tests.
- **Where?**
  - Test classes: `src/test/java/gui/automation/component/` (e.g., `LandingPageComponentTest.java`)
- **How to run?**
  - All TestNG tests:
    ```sh
    mvn clean test
    ```
  - Specific test class:
    ```sh
    mvn clean test -Dtest=gui.automation.component.LandingPageComponentTest
    ```
- **Example:**
  ```java
  @Test
  public void testBannerVisible() {
      Assert.assertTrue(landingPageActions.isBannerVisible(), "Banner should be visible");
  }
  ```

### 2. BDD (Cucumber)
> **Example Feature Files Provided:**
> 
> All feature files in `src/test/resources/features/` and their step definitions are provided as examples. You can use these as a reference for writing your own business-readable scenarios.
- **What is it?**
  - Write tests in plain English using Gherkin syntax (Given/When/Then).
  - Good for business users, product owners, or anyone who prefers readable scenarios.
- **Where?**
  - Feature files: `src/test/resources/features/` (e.g., `LandingPageComponent.feature`)
  - Step definitions: `src/test/java/gui/automation/stepdefs/`
  - Runner: `src/test/java/gui/automation/runner/CucumberTestRunner.java`
- **How to run?**
  - All BDD tests:
    ```sh
    mvn clean test -Dtest=gui.automation.runner.CucumberTestRunner
    ```
- **Example:**
  ```gherkin
  Scenario: Banner is visible
    Given I am on the landing page
    Then the banner should be visible
  ```

### When to use which?
- **TestNG:**
  - For technical, code-centric tests, or when you want to use advanced Java/TestNG features.
- **BDD (Cucumber):**
  - For business-readable, collaborative scenarios, or when you want to share test intent with non-developers.

**You can mix and match both styles in this framework!**

---

## Adding/Updating ChromeDriver
- Download the correct version for your Chrome browser and OS.
- Place it in `src/main/resources/driver/chrome/`.
- Make it executable:
  ```sh
  chmod +x src/main/resources/driver/chrome/chromedriver
  ```
- **macOS Gatekeeper Note:**
  - If the binary is blocked, follow the steps in the [Setup & Installation](#setup--installation) section to allow it in System Settings > Privacy & Security.

---

## Framework Design

- **Page Objects:**  
  Encapsulate locators and page-specific actions (e.g., `LandingPage`, `ElementsPage`).
- **Actions:**  
  Encapsulate user flows and business logic, calling page object methods (e.g., `LandingPageActions`).
- **Tests:**  
  Only interact with the actions layer for clean, readable, and maintainable tests.
- **SeleniumUtil:**  
  Centralizes all direct WebDriver/Selenium interactions, making the framework easy to update or migrate.
- **BaseActions:**  
  Contains shared browser-level actions (getCurrentUrl, goBack, refreshPage) for use in all actions classes.

---

## How to Add New Tests

> **Tip:**
> 
> The included example tests (both TestNG and BDD) are meant to help you get started. Use them as a guide for structure, naming, and best practices when adding your own tests.

1. **Create/Update a Page Object** for the new page/section.
2. **Add/Update an Actions class** for user flows.
3. **Write a test class** in `component/` or `e2e/` using the actions layer.
4. **Add the test class to `testng.xml`** if needed.

---

## SeleniumUtil: Why and How

- **Purpose:**  
  Encapsulates all direct Selenium/WebDriver calls (click, type, waits, screenshots, etc.).
- **Benefit:**  
  If you ever switch to a different automation tool, you only need to update this class.

---

## Extending the Framework

- **Add new utility methods:**
  - Add to `SeleniumUtil.java` for new types of interactions or waits.
- **Add new Page Objects:**
  - Create a new class in `src/main/java/gui/automation/pages/` for each new page or component.
- **Add new Actions classes:**
  - Create a new class in `src/main/java/gui/automation/actions/` for user flows or business logic.
- **Support more browsers:**
  - Extend `DriverUtils.java` to add support for Firefox, Edge, etc. (see the Chrome example).
- **Add new test types:**
  - Add new test classes in `component/` or `e2e/` as needed.

---

## What Can You Automate With This Framework?

This framework is designed to automate a wide variety of web UI scenarios, from simple form filling to advanced interactions like drag-and-drop, popups, and dynamic content. You can use it for both business-readable BDD (Cucumber) and technical TestNG tests.

**Supported Automation Types (with SeleniumUtil method groups):**

- **Basic Interactions:**
  - Click any button, link, or element (`click`, `clickLinkByText`, `selectMenuItemByText`)
  - Type into input fields (`type`)
  - Hover, double-click, right-click (`hover`, `doubleClick`, `rightClick`)
  - Scroll to elements (`scrollTo`)
- **Dropdowns & Selects:**
  - Select by visible text or value (`selectByText`, `selectByValue`)
  - Get selected option or all options (`getSelectedOptionText`, `getAllDropdownOptions`)
- **Checkboxes, Radio Buttons, Buttons:**
  - Check/uncheck, verify state (`isCheckboxChecked`, `selectRadioButton`, `isRadioButtonSelected`)
  - Work with tree/folder structures (`isFolderExpanded`, `isFolderCollapsed`)
- **Tables & Web Data:**
  - Read/write table cells, verify values (`getCellText`, `updateCellValue`, `verifyCellValue`)
  - Count rows/columns (`getTableRowCount`, `getTableColumnCount`)
- **Links:**
  - Click and verify links (`clickLinkByText`, `verifyLinkHref`)
- **File Upload/Download:**
  - Upload files (`uploadFile`)
  - Verify downloads (`isFileDownloaded`)
- **Form Handling:**
  - Fill, submit, clear forms (`fillForm`, `submitForm`, `clearForm`)
  - Data-driven from CSV/Excel (`readFirstRowFromCSV`, `readFirstRowFromExcel`)
- **Alerts, Windows, Frames:**
  - Handle popups, alerts, and browser windows/tabs (`acceptAlert`, `dismissAlert`, `switchToNewWindow`, `closeCurrentWindowAndSwitchBack`)
  - Work with iframes (`switchToFrameByIndex`, `switchToFrameByNameOrId`, `switchToDefaultContent`)
- **UI Widgets:**
  - Tabs, accordions, sliders, progress bars, tooltips, menus, drag-and-drop, resizable, selectable, sortable (`selectTabByText`, `expandAccordionSection`, `setSliderValue`, `dragAndDrop`, etc.)
  - Modal dialogs (`isModalDialogVisible`, `closeModalDialog`)
- **Broken Links/Images:**
  - Check for broken links and images on any page (`checkBrokenLinks`, `checkBrokenImages`)
- **Screenshots & Utilities:**
  - Capture screenshots, generate unique names (`screenshot`, `generateRandomNameWithTimestamp`)
  - Automatically close popups/ads (`closeKnownPopups`)
- **Miscellaneous:**
  - Get/set cookies, maximize window, wait for elements, custom waits, etc.
  - Run tests in headless mode for faster, UI-less execution (`headless` property in config).

> **Every method in SeleniumUtil is documented for beginners:**
> - What it does, what parameters it needs, and when to use it (with examples).
> - See the source code for grouped, easy-to-navigate methods.

---

## Parallel Execution

<!--
    Beginner Tip:
    The 'parallel="methods"' attribute tells TestNG to run test methods in parallel.
    The 'thread-count="4"' attribute sets the maximum number of test methods that can run at the same time.
    This makes your tests run faster by using multiple browser sessions in parallel.
    You can change the thread-count to control how many tests run at once.
-->
- Enabled by default in `testng.xml` with 4 threads.
- Each test runs in its own browser session for speed and isolation.

---

## Viewing Test Reports

- **TestNG Reports:**
  - After running tests, see `target/surefire-reports/` for HTML and text reports.
- **Cucumber Reports:**
  - See console output for scenario results. (Add plugins for HTML reports if needed.)
- **Screenshots on Failure:**
  - Screenshots are saved to the directory specified in `config.properties` (e.g., `

## Changelog

- 2025-08-17: Initial public release.
- _Add version notes here as you update the framework._

---