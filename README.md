# Playwright Automation

![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Playwright](https://img.shields.io/badge/Playwright-1.61.0-2EAD33?style=for-the-badge&logo=playwright&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-7.34.4-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-runner-F7941E?style=for-the-badge&logoColor=white)
![Allure](https://img.shields.io/badge/Allure-Report-FF3E00?style=for-the-badge&logo=qameta&logoColor=white)
![Datafaker](https://img.shields.io/badge/Datafaker-random%20data-6E56CF?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

## Overview

This repository contains an automated end-to-end test suite built with Playwright and Cucumber, executed via a TestNG runner. The framework is designed around configurability, maintainability, and clear, evidence-backed reporting.

Key capabilities:

- **Cross-browser execution** — Chromium and Firefox, selectable at runtime.
- **Externalized configuration** — browser, base URL, headless mode, and timing are driven entirely from a properties file and may be overridden via JVM system properties.
- **Dependency injection** — step definition and hook classes are wired together via Cucumber's PicoContainer integration, avoiding static shared state between scenarios.
- **Allure reporting** — every scenario is reported with a full step timeline; failed scenarios automatically capture and attach a screenshot of the application state at the point of failure.
- **Randomized test data** — the successful-submission scenario uses [Datafaker](https://www.datafaker.net/) to generate random names, emails, and messages at runtime, rather than relying on hardcoded values.

## Configuration

All environment values are defined in `src/test/resources/config/config.properties`:

| Key             | Description                            |
|-----------------|-----------------------------------------|
| `browser`       | Target browser (`chromium` or `firefox`) |
| `base.url`      | Base URL of the application under test  |
| `headless`      | Whether to run headless (`true`/`false`) |
| `step.delay.ms` | Pause applied after each Gherkin step   |

Any key may be overridden at runtime via a JVM system property without modifying the properties file, for example:

```bash
java -Dbrowser=firefox -cp "$CP" org.testng.TestNG -testclass runner.ContactUsRunner
```

## Running the Suite

```bash
mvn dependency:build-classpath -Dmdep.outputFile=/tmp/cp.txt -q
CP=$(cat /tmp/cp.txt):target/classes:target/test-classes
java -cp "$CP" org.testng.TestNG -testclass runner.ContactUsRunner
```

## Reporting

Generate and view the Allure report:

```bash
mvn io.qameta.allure:allure-maven:2.12.0:serve
```

When a scenario fails, a screenshot of the current page state is automatically captured and attached to that scenario's entry in the report (see `Hooks.java`), providing immediate visual context for the failure without needing to re-run the test.

### Sample Report — Failed Scenario

![Failed scenario in Allure report](docs/screenshots/allure-failed-scenario-report.png)

## License

This project is licensed under the [MIT License](LICENSE).
