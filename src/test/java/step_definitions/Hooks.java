package step_definitions;

import java.io.ByteArrayInputStream;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;

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
            captureScreenshot(scenario);
        }
        browserManager.close();
    }

    private void captureScreenshot(Scenario scenario) {
        Page page = browserManager.getPage();
        if (page == null || page.isClosed()) {
            return;
        }
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
        Allure.addAttachment(scenario.getName() + "-failure", "image/png",
                new ByteArrayInputStream(screenshot), "png");
    }
}
