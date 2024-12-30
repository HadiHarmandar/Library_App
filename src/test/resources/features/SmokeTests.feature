Feature: As a librarian I should be able to log in to the app

  @wip @ui @smoke
  Scenario: I log in with correct credentials
    Given I am on the login page
    When I logged in Library UI as "librarian"
    Then title should be "Library"
