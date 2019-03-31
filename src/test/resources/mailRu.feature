Feature: mail.ru test scenarios 
 
 Scenario: User creates and sends email to himself 
 When I send email to myself
 And I open Inbox folder 
 Then I see the folder is not empty 
 
 Scenario: User starts creating a new email and then saves it to drafts 
 When I create a new email 
 And I Save email 
 Then I see the message that email was saved to drafts 
 
 Scenario: User moves email from Inbox to Spam
 When I send email to myself
 And I move email from Inbox to Spam
 Then I see Spam is not empty
 
 Scenario: User sends email to group of people
 When I create contacts group
 And I Send new email to group
 Then I see message that email is sent

# Scenario: User moves email from Spam to Inbox
# When I create a new email 
# And I send email
# And I move email to spam
# And I open Spam folder
# And I select email and move it to Inbox
# Then I open Inbox folder and see the email from Spam
# 
# Scenario: Create group of people
# When I go to Address book page
# And Create a new group
# Then I see group is added to the list of groups
# 

# 
# Scenario: Mark inbox emails with flag
# When I send 4 emails to my Inbox
# And Mark 3 emails with flag
# Then I see the selected emails are displayed with filled flag icon
# 
# Scenario: User unflags all emails
# When I send 4 emails to my Inbox
# And Mark 3 emails with flag
# And I select all emails and unflag them
# Then I see that there no emails marked with flag
# 
 
	