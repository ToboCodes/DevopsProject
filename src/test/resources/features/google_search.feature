Feature: Google Search
  As a web user
  I want to search Google
  So that I can find information online

  Scenario: Searching for OpenAI
    Given I am on the Google search page
    When I search for "OpenAI"
    Then the page title should contain "OpenAI"
