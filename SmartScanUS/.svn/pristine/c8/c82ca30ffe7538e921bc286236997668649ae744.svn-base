
Feature:   As an operator I should be able to log in and out with my credentials

   Scenario Outline: Login and Log out

      Given I am on the Login Screen
      When I enter "<User>" for username
      And I enter "<Pass>" for password

      Then I press "Login" 
      Then I wait
      Then I should see "Load Transport"
      And I should see "Quarantine"

      Then I press "Log out"
      Then I press "Yes, Log out"
      Then I wait
      Then I should see "Closing Application!"

  Examples:
  | User | Pass |
  | warehousetw | Dhl@123456 |