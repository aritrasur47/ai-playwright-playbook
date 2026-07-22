# Playwright Automation

Cucumber + Playwright + TestNG test suite for the WebDriverUniversity Contact Us form, with cross-browser support, externalized configuration, PicoContainer dependency injection, and Allure reporting with automatic screenshot capture on failure.

## Stack

- Java + Playwright
- Cucumber (Gherkin, TestNG runner, PicoContainer DI)
- Allure reporting

## Configuration

All environment values live in `src/test/resources/config/config.properties`:

| Key             | Description                          |
|-----------------|---------------------------------------|
| `browser`       | `chromium` or `firefox`               |
| `base.url`      | Site under test                       |
| `headless`      | Run browser headless (`true`/`false`) |
| `step.delay.ms` | Pause after each Gherkin step         |

Any key can be overridden at runtime via a JVM system property, e.g.:

```bash
java -Dbrowser=firefox -cp "$CP" org.testng.TestNG -testclass runner.ContactUsRunner
```

## Running the tests

```bash
mvn dependency:build-classpath -Dmdep.outputFile=/tmp/cp.txt -q
CP=$(cat /tmp/cp.txt):target/classes:target/test-classes
java -cp "$CP" org.testng.TestNG -testclass runner.ContactUsRunner
```

## Allure report

Generate and open the report:

```bash
mvn io.qameta.allure:allure-maven:2.12.0:serve
```

On failure, a screenshot of the page is automatically captured and attached to the failed scenario in the report (see `Hooks.java`).

### Sample: failed scenario report

![Failed scenario in Allure report](docs/screenshots/allure-failed-scenario-report.png)
