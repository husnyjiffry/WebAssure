package gui.automation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features/LandingPageComponent.feature", "src/test/resources/features/TextBoxComponent.feature"},
        glue = {"gui.automation.stepdefs"},
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class CucumberTestRunnerTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
