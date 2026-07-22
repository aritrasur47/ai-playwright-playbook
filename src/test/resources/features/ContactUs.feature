Feature: Contact Us form
  As a visitor to WebDriverUniversity.com
  I want to submit the Contact Us form
  So that I can send a message to the site

  Background:
    Given I go to the WebDriverUniversity website
    And I navigate to the Contact Us page

  Scenario: Successful submission with valid details
    When I enter "Jordan" as the first name
    And I enter "Rivers" as the last name
    And I enter "jordan.rivers@example.com" as the email
    And I enter "Hello, I would like more information." as the message
    And I click the Submit button
    Then I should see the confirmation message "Thank You for your Message!"

  Scenario: Submission fails against an incorrect expected confirmation message
    When I enter "Jordan" as the first name
    And I enter "Rivers" as the last name
    And I enter "jordan.rivers@example.com" as the email
    And I enter "Hello, I would like more information." as the message
    And I click the Submit button
    Then I should see the confirmation message "This message will never appear"

  Scenario Outline: Submitting the form with invalid details
    When I enter "<first_name>" as the first name
    And I enter "<last_name>" as the last name
    And I enter "<email>" as the email
    And I enter "<message>" as the message
    And I click the Submit button
    Then I should see the error "<error>"

    Examples:
      | first_name | last_name | email                | message | error                                          |
      |            |           |                      |         | all fields are required, Invalid email address |
      | Alex       | Carter    | plainaddress         | Hi      | Invalid email address                          |
      |            | Carter    | valid@example.com    | Hi      | all fields are required                        |
