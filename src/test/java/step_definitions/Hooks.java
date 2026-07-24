package step_definitions;

import java.io.ByteArrayInputStream;

import com.microsoft.playwright.Page;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class Hooks {

    private static final double STEP_DELAY_MS = ConfigReader.getDouble(ConfigKeys.STEP_DELAY_MS);

    private final BrowserManager browserManager;

    public Hooks(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Before
    public void setUp() {
        browserManager.launch();
    }

    @AfterStep
    public void pauseAfterStep() {
        Page page = browserManager.getPage();
        if (page != null && !page.isClosed()) {
            page.waitForTimeout(STEP_DELAY_MS);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            attachFailureScreenshot(scenario);
        }
        browserManager.close();
    }

    private void attachFailureScreenshot(Scenario scenario) {
        byte[] screenshot = browserManager.captureScreenshot();
        if (screenshot == null) {
            return;
        }
        Allure.addAttachment(scenario.getName() + "-failure", "image/png",
                new ByteArrayInputStream(screenshot), "png");
    }
}
