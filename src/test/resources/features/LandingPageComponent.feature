Feature: Landing Page Main Cards

  Scenario: All main cards are visible on the landing page
    Given I am on the landing page
    Then the "Elements" card should be visible
    And the "Forms" card should be visible
    And the "Alerts" card should be visible
    And the "Widgets" card should be visible
    And the "Interactions" card should be visible
    And the "Book Store Application" card should be visible

  Scenario: Click Elements card
    Given I am on the landing page
    When I click the Elements card
    Then the Elements page should be loaded

  Scenario: Click Forms card
    Given I am on the landing page
    When I click the Forms card
    Then the Forms page should be loaded

  Scenario: Click Alerts card
    Given I am on the landing page
    When I click the Alerts card
    Then the Alerts page should be loaded

  Scenario: Click Widgets card
    Given I am on the landing page
    When I click the Widgets card
    Then the Widgets page should be loaded

  Scenario: Click Interactions card
    Given I am on the landing page
    When I click the Interactions card
    Then the Interactions page should be loaded

  Scenario: Click Book Store card
    Given I am on the landing page
    When I click the Book Store card
    Then the Book Store page should be loaded
