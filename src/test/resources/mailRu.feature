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
 
 Scenario Outline: Mark inbox emails with flag
 When I send <sendEmailsNumber> emails to my Inbox
 And I mark <toBeMarkedEmailsNumber> emails with flag
 Then I see <markedEmailsNumber> emails are displayed with filled flag icon
 Examples:
 |sendEmailsNumber|toBeMarkedEmailsNumber|markedEmailsNumber|
 |2|1|1|
 
 Scenario Outline: User unflags all emails
 When I send <sendEmailsNumber> emails to my Inbox
 And I mark <toBeMarkedEmailsNumber> emails with flag
 And I select all emails and unflag them
 Then I see that there no emails marked with flag
 Examples:
 |sendEmailsNumber|toBeMarkedEmailsNumber|
 |3|2|
 
 Scenario: Remove all emails from Inbox
 When I send email to myself
 And I remove all emails from inbox
 Then I see Inbox is empty

# Scenario: User moves email from Spam to Inbox
# When I create a new email 
# And I send email
# And I move email to spam
# And I open Spam folder
# And I select email and move it to Inbox
# Then I open Inbox folder and see the email from Spam
# 
# Scenario: Create group of people
# When I create contacts group
# Then I see group is added to the list of groups
# 

# 


 
	