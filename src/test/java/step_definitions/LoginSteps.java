package step_definitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microsoft.playwright.Page;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private static final long ALERT_VISIBLE_DELAY_MS = (long) ConfigReader.getDouble(ConfigKeys.STEP_DELAY_MS);

    private final BrowserManager browserManager;
    private Page page;
    private String alertMessage;

    public LoginSteps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Given("I navigate to the Login Portal page")
    public void i_navigate_to_the_login_portal_page() {
        page = browserManager.getPage();
        page.navigate(ConfigReader.get(ConfigKeys.BASE_URL) + ConfigReader.get(ConfigKeys.LOGIN_PORTAL_PATH));
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        page.onDialog(dialog -> {
            alertMessage = dialog.message();
            try {
                Thread.sleep(ALERT_VISIBLE_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            dialog.accept();
        });
        page.locator("#text").fill(username);
        page.locator("#password").fill(password);
        page.locator("#login-button").click();
    }

    @Then("I should see the alert message {string}")
    public void i_should_see_the_alert_message(String expectedMessage) {
        assertEquals(expectedMessage, alertMessage);
    }
}
