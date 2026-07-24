package step_definitions;

import java.util.Arrays;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ScreenshotType;

public class BrowserManager {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public Page launch() {
        String browserName = ConfigReader.get(ConfigKeys.BROWSER).toLowerCase();
        if (browserName.equals("chrome")) {
            browserName = "chromium";
        }
        playwright = Playwright.create();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(ConfigReader.getBoolean(ConfigKeys.HEADLESS));

        switch (browserName) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "chromium":
                browser = playwright.chromium().launch(options.setArgs(Arrays.asList("--start-maximized")));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
        return page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public byte[] captureScreenshot() {
        if (page == null || page.isClosed()) {
            return null;
        }
        return page.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
    }

    public void close() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
