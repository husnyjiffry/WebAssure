package gui.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gui.automation.utils.ConfigUtils;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.Duration;

public class DriverUtils {
    private static final Logger logger = LoggerFactory.getLogger(DriverUtils.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Returns a WebDriver instance for the specified browser and navigates to the given URL.
     * Supported browsers: "chrome", "firefox".
     *
     * @param browser Browser name (chrome/firefox)
     * @param url     URL to navigate to
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browser, String url) {
        WebDriver webDriver;
        switch (browser.toLowerCase()) {
            case "chrome":
                webDriver = createChromeDriver(url);
                break;
            case "firefox":
                webDriver = createFirefoxDriver(url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.set(webDriver);
        return webDriver;
    }

    /**
     * Gets the WebDriver instance for the current thread.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits the WebDriver instance for the current thread and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }

    private static WebDriver createChromeDriver(String url) {
        try {
            boolean useBundled = Boolean.parseBoolean(ConfigUtils.get("use.bundled.driver"));
            if (useBundled) {
                String driverPath = "src/main/resources/driver/chrome/chromedriver";
                if (Files.exists(Paths.get(driverPath))) {
                    System.setProperty("webdriver.chrome.driver", driverPath);
                } else {
                    throw new RuntimeException("Bundled ChromeDriver not found at: " + driverPath);
                }
            } else {
                WebDriverManager.chromedriver().setup();
            }
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-plugins");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-features=VizDisplayCompositor");
            // Removed non-standard flags: --disable-images, --disable-javascript

            String headless = ConfigUtils.get("headless");
            if (headless != null && headless.equalsIgnoreCase("true")) {
                options.addArguments("--headless=new"); // For Chrome 109+; use --headless for older
                logger.info("Running in headless mode");
            } else {
                logger.info("Running in headed (UI) mode");
            }

            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get(url);
            logger.info("Successfully created Chrome driver and navigated to: {}", url);
            return driver;
        } catch (Exception e) {
            logger.error("Failed to create Chrome driver: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create Chrome driver", e);
        }
    }

    private static WebDriver createFirefoxDriver(String url) {
        try {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            // Add Firefox-specific options here if needed
            WebDriver driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            driver.get(url);
            logger.info("Successfully created Firefox driver and navigated to: {}", url);
            return driver;
        } catch (Exception e) {
            logger.error("Failed to create Firefox driver: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create Firefox driver", e);
        }
    }

    static {
        // JVM shutdown hook for cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("JVM shutting down, cleaning up WebDriver instances...");
            quitDriver();
            cleanupChromeProcesses();
        }));
    }

    private static void cleanupChromeProcesses() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                ProcessBuilder pb = new ProcessBuilder("pkill", "-f", "chromedriver");
                pb.start().waitFor();
                // Removed killing all Google Chrome processes to avoid closing user's main browser
                logger.info("chromedriver processes cleaned up");
            }
        } catch (Exception e) {
            logger.warn("Failed to cleanup Chrome processes: {}", e.getMessage());
        }
    }
}
