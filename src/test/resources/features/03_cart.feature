Feature: Cart

  Scenario: Add product to cart
    Given I am on the login page
    Given I am logged in
    When I add "Sauce Labs Backpack" to cart
    And I open the cart
    Then I should see "Sauce Labs Backpack" in the cart

  Scenario: Remove product from cart
    Given I am on the login page
    Given I am logged in
    When I add "Sauce Labs Bike Light" to cart
    And I remove "Sauce Labs Bike Light" from cart
    And I open the cart
    Then the cart should be empty


  Scenario: Checkout and complete order
    Given I am on the login page
    Given I am logged in
    When I add "Sauce Labs Backpack" to cart
    Then I open the cart
    Then I should see "Sauce Labs Backpack" in the cart
    When I proceed to checkout
    When I enter random customer information
    When I click continue on checkout page
    When I click finish on checkout page
    Then I should see the success message
