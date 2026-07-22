package test.playwright_automation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ScreenSizeTest
{
    @Test
    void windowOpensMaximizedToScreenSize()
    {
        try(Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
					.setHeadless(false)
					.setArgs(java.util.List.of("--start-maximized")));
			BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));

			Page page = context.newPage();
			page.navigate("https://webdriveruniversity.com");
			page.waitForTimeout(2000);
			System.out.println("Page title: " + page.title());

			int actualWidth = ((Number) page.evaluate("window.innerWidth")).intValue();
			int actualHeight = ((Number) page.evaluate("window.innerHeight")).intValue();
			assertTrue(actualWidth > 0);
			assertTrue(actualHeight > 0);

			page.waitForTimeout(2000);
			page.close();
			context.close();
			browser.close();
		}
    }
}
