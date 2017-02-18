Feature: Search top five brands and shop

In order to shop from top five brands
As an user
I want to search top five brands and add item to cart



Scenario: Search and shop from top five brands
When I go to "Website" on "Browser"
Given I am logged in "MyAccount" 
And I point to "Departments"
Then I move to "clothing&Shoes"
Then I click on "Mens"
Then I get to "MenFashionPage"
Then I click on "ShoesInSideMenu"
Then I select top five brands
| Brand1 |
| Brand2 |
| Brand3 |
| Brand4 |
| Brand5 |
Then I choose "Item"
Then I get to "ItemPage"
Then I pick "Size"
Then I click on "AddToCart"
Then I get to "CartPage"
Then I verify "ItemAdded" in cart
Then I click on "ProceedButton"
