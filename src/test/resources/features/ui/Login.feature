Feature: Login

  @closeDriver
  Scenario: Login with valid credentials
    Given I navigate to the following URL "https://www.pivotaltracker.com/signin"
    When I set a valid user email
    And I click on the Next button
    And I set a valid user password
    And I click on the SignIn button
    Then I verify that I'm in the dashboard page

  @closeDriver
  Scenario: Login with invalid credentials
    Given I navigate to the following URL "https://www.pivotaltracker.com/signin"
    When I set the following email "mail@mail.com"
    And I click on the Next button
    And I set the following password "admin000"
    And I click on the SignIn button
    Then I verify that the following error message is displayed "Invalid username/password"
