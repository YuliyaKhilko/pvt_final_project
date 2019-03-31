Feature: VK wall posts API tests

Scenario: User adds new post to wall 
When I add new post to wall using api
Then I get response code 200

Scenario: User edits wall post
When I add new post to wall using api
And I edit wall post using api
Then I get response code 200

Scenario: User deletes wall post
When I add new post to wall using api
And I delete wall post using api
Then I get response code 200 