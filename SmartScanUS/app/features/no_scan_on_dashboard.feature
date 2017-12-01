

Feature:   As an Operator I shouldn't be able to scan without making a selection first.

    Scenario: Error dialog on the dashboard for false scan

      Given I am on the Login Screen
      When I press the menu key
      And I select "Reset data" from the menu
      Then I wait

      When I enter "warehousetw" for username
      And I enter "Dhl@123456" for password
      Then I press "Login" 
      Then I wait

      When I scan transport 1 barcode
      Then I should see "Please select"
      Then I press "OK"
