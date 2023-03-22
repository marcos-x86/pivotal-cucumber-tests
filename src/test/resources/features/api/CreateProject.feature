Feature: Create project

  @deleteProjectPostCondition
  Scenario: Create project using a POST request
    Given I create a new request
    And I set the following request body
    """
    {
      "name": "Automation"
    }
    """
    When I send a POST request to the endpoint "projects"
    Then I verify that the response status code is 200
    And I verify that the body response matches the schema "postProjectSchema.json"

  Scenario: Create project without a name
    Given I create a new request
    And I set the following request body
    """
    {
      "name": ""
    }
    """
    When I send a POST request to the endpoint "projects"
    Then I verify that the response status code is 400
