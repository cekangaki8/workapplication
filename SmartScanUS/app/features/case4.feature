Feature:   As an Operator if during outbound scan if the weight limits exceeds I should see corresponding dialog error.

    Scenario: Outbound scan case 4 (413)

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

      When I scan receptacle 22
      Then I should see "1 Pcs"

      When I scan receptacle 23
      Then I should see "2 Pcs"

      When I scan receptacle 24
      Then I should see "3 Pcs"

      When I scan receptacle 25
      Then I should see "4 Pcs"

      When I scan receptacle 25
      Then I should see "Start new Transport"
      Then I press "Cancel"
