Feature: Text Box Page

  Scenario: Page title is visible
    Given I am on the text box page
    Then the page title should be visible
    And the page title should be "Text Box"

  Scenario: Full Name label is visible
    Given I am on the text box page
    Then the Full Name label should be visible
    And the Full Name label text should be "Full Name"

  Scenario: Full Name field is visible
    Given I am on the text box page
    Then the Full Name field should be visible
    And the Full Name placeholder should be "Full Name"

  Scenario: Email label is visible
    Given I am on the text box page
    Then the Email label should be visible
    And the Email label text should be "Email"

  Scenario: Email field is visible
    Given I am on the text box page
    Then the Email field should be visible
    And the Email placeholder should be "name@example.com"

  Scenario: Current Address label is visible
    Given I am on the text box page
    Then the Current Address label should be visible
    And the Current Address label text should be "Current Address"

  Scenario: Current Address field is visible
    Given I am on the text box page
    Then the Current Address field should be visible
    And the Current Address placeholder should be "Current Address"

  Scenario: Permanent Address label is visible
    Given I am on the text box page
    Then the Permanent Address label should be visible
    And the Permanent Address label text should be "Permanent Address"

  Scenario: Permanent Address field is visible
    Given I am on the text box page
    Then the Permanent Address field should be visible

  Scenario: Submit button is visible
    Given I am on the text box page
    Then the Submit button should be visible

  Scenario: Enter data and submit
    Given I am on the text box page
    When I fill the form with name "John Doe", email "john.doe@example.com", current address "123 Main St", permanent address "456 Elm St"
    And I click the submit button
    Then the output should contain name "John Doe", email "john.doe@example.com", current address "123 Main St", permanent address "456 Elm St"

  Scenario: Output values after submit
    Given I am on the text box page
    When I fill the form with name "Husny", email "husny@gmail.com", current address "Singapore", permanent address "Sri Lanka"
    And I click the submit button
    Then the output should contain name "Husny", email "husny@gmail.com", current address "Singapore", permanent address "Sri Lanka"

  Scenario: Fields and output cleared after reload
    Given I am on the text box page
    When I fill the form with name "Husny", email "husny@gmail.com", current address "Singapore", permanent address "Sri Lanka"
    And I click the submit button
    And I reload the page
    Then all fields and output should be empty
