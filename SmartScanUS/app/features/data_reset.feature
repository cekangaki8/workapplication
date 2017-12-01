Feature:   During the tests the test runner should be able to reset the test data.

    Scenario: Data reset from menu

      Given I am on the Login Screen
      When I press the menu key
      And I select "Reset data" from the menu
      Then I should see "Data reset successful"
