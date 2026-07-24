package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "step_definitions",
        plugin = {"pretty", "html:target/cucumber-reports/ContactUs.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        monochrome = true,
        tags = "not @demo-failure"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
