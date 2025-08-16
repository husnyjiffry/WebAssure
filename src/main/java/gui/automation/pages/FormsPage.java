package gui.automation.pages;

import org.openqa.selenium.By;
import gui.automation.utils.SeleniumUtil;

/**
 * Page Object for the Forms section of demoqa.com.
 */
public class FormsPage {
    private final By practiceFormMenu = By.id("item-0");

    public void goToPracticeForm() {
        SeleniumUtil.click(practiceFormMenu);
    }
}
