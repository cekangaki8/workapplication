
Given /^I am on the Login Screen$/ do
    wait_for_activity("LoginActivity")
#    wait_for(180) {
#        element_exists("edittext id:'etLoginUser'")
#        element_exists("edittext id:'etLoginPass'")}
end

When /^I scan (\d+) barcode$/  do |barcode|
    clear_preferences("scanner")
    set_preferences("scanner",{:status => barcode })
end

When /^I scan transport (\d+) barcode$/  do |barcode|
    clear_preferences("scanner")
    set_preferences("scanner",{:status => barcode })
end

Then /^I scan receptacle (\d+)$/  do |barcode|
    clear_preferences("scanner")
    set_preferences("scanner",{:status => barcode })
end

When /^I get Preferences$/  do
    preferences = get_preferences("scanner")
    puts preferences
end

When /^I enter "([^\"]*)" for username$/ do |text|
  enter_text("android.widget.EditText id:'etLoginUser'", text)
end

When /^I enter "([^\"]*)" for password$/ do |text|
  enter_text("android.widget.EditText id:'etLoginPass'", text)
end
