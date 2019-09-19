Feature: User creates a new team

  Scenario: As an Authorized user, I should be able to create a new team using bookit api

    Given Authorization token is provided
    When user sends a POST request to /api/teams/team with following data:
      |campus-location | VA |
      |batch-number | 8     |
      |team-name | Turbo    |
    Then status code should be 201