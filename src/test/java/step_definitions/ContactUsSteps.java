package step_definitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactUsSteps {

    private static final String ERROR_PREFIX = "Error: ";

    private final BrowserManager browserManager;
    private Page page;

    public ContactUsSteps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Given("I go to the WebDriverUniversity website")
    public void i_go_to_the_webdriveruniversity_website() {
        page = browserManager.getPage();
        page.navigate(ConfigReader.get(ConfigKeys.BASE_URL));
    }

    @Given("I navigate to the Contact Us page")
    public void i_navigate_to_the_contact_us_page() {
        Page newTab = page.context().waitForPage(() ->
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Contact Us Form")).click());
        newTab.waitForLoadState();
        page = newTab;
        browserManager.setPage(page);
    }

    @When("I enter {string} as the first name")
    public void i_enter_as_the_first_name(String firstName) {
        page.locator("input[name='first_name']").fill(firstName);
    }

    @And("I enter {string} as the last name")
    public void i_enter_as_the_last_name(String lastName) {
        page.locator("input[name='last_name']").fill(lastName);
    }

    @And("I enter {string} as the email")
    public void i_enter_as_the_email(String email) {
        page.locator("input[name='email']").fill(email);
    }

    @And("I enter {string} as the message")
    public void i_enter_as_the_message(String message) {
        page.locator("textarea[name='message']").fill(message);
    }

    @And("I click the Submit button")
    public void i_click_the_submit_button() {
        page.locator("input.contact_button[type='submit']").click();
    }

    @Then("I should see the confirmation message {string}")
    public void i_should_see_the_confirmation_message(String expectedMessage) {
        assertTrue(page.locator("#contact_reply h1").textContent().contains(expectedMessage));
    }

    @Then("I should see the error {string}")
    public void i_should_see_the_error(String expectedErrors) {
        String bodyText = page.locator("body").textContent();
        for (String error : expectedErrors.split(",\\s*")) {
            assertTrue(bodyText.contains(ERROR_PREFIX + error), "Expected error not found: " + error);
        }
    }
}
