

Feature:   As an Operator I should be able to perform outbound scan and I should see the increase in weight and no of receptacels

    Scenario: Outbound scan case 1 (200)

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
      When I scan transport 3 barcode
      Then I should see "GBFEMA0001504165"
      Then I wait

      When I scan receptacle 11
      Then I should see "1 Pcs"
      When I scan receptacle 12
      Then I should see "2 Pcs"
      
      When I go back
      Then I press "Log out"
      Then I press "Yes, Log out"
      Then I wait
