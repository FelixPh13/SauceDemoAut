Feature: Inventory

  Scenario: Filter products A-Z
    Given I am on the login page
    Given I am logged in
    When I select the "Name (A to Z)" filter
    Then The products should be sorted by "Name (A to Z)"

  Scenario: Filter products Z-A
    Given I am on the login page
    Given I am logged in
    When I select the "Name (Z to A)" filter
    Then The products should be sorted by "Name (Z to A)"

  Scenario: Filter products by price low to high
    Given I am on the login page
    Given I am logged in
    When I select the "Price (low to high)" filter
    Then The products should be sorted by "Price (low to high)"

  Scenario: Filter products by price high to low
    Given I am on the login page
    Given I am logged in
    When I select the "Price (high to low)" filter
    Then The products should be sorted by "Price (high to low)"



