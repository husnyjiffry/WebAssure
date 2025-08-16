# WebAssure UI Automation Framework

A beginner-friendly, scalable Selenium/TestNG UI automation framework for demoqa.com and similar web apps.

---

## Table of Contents
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [How to Run Tests](#how-to-run-tests)
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
│   ├── resources/
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

## Troubleshooting

- **ElementClickInterceptedException:**  
  The framework automatically handles popups and scrolling. If issues persist, check for new overlays or update SeleniumUtil.
- **ChromeDriver errors:**  
  Ensure the binary matches your Chrome version and is executable.
  - **macOS Gatekeeper:** If you see a security warning, follow the steps in [Setup & Installation](#setup--installation) to allow the binary.
- **Ad popups or overlays:**  
  The framework includes logic to close popups and scroll elements into view before clicking.

---

## Contributing

- Fork the repo, create a branch, and submit a pull request.
- Please add/modify tests and update documentation as needed.

---

## License

This project is licensed under the MIT License.

You are free to use this framework for any purpose, including commercial and non-commercial projects, as long as you retain the copyright notice.

See the [LICENSE](LICENSE) file for details.
