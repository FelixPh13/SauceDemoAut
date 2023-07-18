Feature: Login

  Scenario: Successful login
    Given I am on the login page
    When I enter username "standard_user" and password "secret_sauce"
    And I click on the login button
    Then I should be redirected to the inventory page

  Scenario: Failed login with invalid username
    Given I am on the login page
    When I enter username "invalid_user" and password "secret_sauce"
    And I click on the login button
    Then I should see the error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Failed login with invalid password
    Given I am on the login page
    When I enter username "standard_user" and password "invalid_password"
    And I click on the login button
    Then I should see the error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Failed login with missing username
    Given I am on the login page
    When I enter username "" and password "secret_sauce"
    And I click on the login button
    Then I should see the error message "Epic sadface: Username is required"

  Scenario: Failed login with missing password
    Given I am on the login page
    When I enter username "standard_user" and password ""
    And I click on the login button
    Then I should see the error message "Epic sadface: Password is required"

  Scenario: Failed login with missing password and username
    Given I am on the login page
    When I enter username "" and password ""
    And I click on the login button
    Then I should see the error message "Epic sadface: Username is required"

  Scenario: Failed login with locked out user
    Given I am on the login page
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click on the login button
    Then I should see the error message "Epic sadface: Sorry, this user has been locked out."

  Scenario: Login with problem user
    Given I am on the login page
    When I enter username "problem_user" and password "secret_sauce"
    And I click on the login button
    Then all product images should be replaced with the incorrect image

  Scenario: Login with performance glitch user
    Given I am on the login page
    When I enter username "performance_glitch_user" and password "secret_sauce"
    And I click on the login button
    Then the page should not load within 3000 milliseconds

