@login
Feature: Login Portal alert messages
  As a visitor to WebDriverUniversity.com
  I want to log in through the Login Portal
  So that I receive the correct alert message for valid and invalid credentials

  @smoke
  Scenario Outline: Verify login alert message for given credentials
    Given I navigate to the Login Portal page
    When I login with username "<username>" and password "<password>"
    Then I should see the alert message "<expectedMessage>"

    Examples:
      | username  | password     | expectedMessage       |
      | webdriver | webdriver123 | validation succeeded  |
      | webdriver | wrongpassword| validation failed     |
