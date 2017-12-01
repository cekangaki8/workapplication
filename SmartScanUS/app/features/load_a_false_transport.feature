

Feature:   As an Operator I shouldn't be able to load a not active transport.

    Scenario: Load a false transport

      Given I am on the Login Screen
      When I press the menu key
      And I select "Reset data" from the menu

      Then I wait
      When I enter "warehousetw" for username
      And I enter "Dhl@123456" for password

      Then I press "Login" 
      Then I wait
      Then I press view with id "ll_mainfrg_load_transport"
      Then I wait
      When I scan transport 11 barcode

      Then I should see "Active Transport not found"
      Then I press "OK"
