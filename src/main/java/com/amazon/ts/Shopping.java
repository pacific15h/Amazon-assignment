package com.amazon.ts;

/**************** Step definition for feature file **********************/

import java.util.List;

import org.junit.Assert;


import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Shopping {
	
	WebConnector webConnector = null;
	
	@Before
	public void setup() {
	webConnector = WebConnector.getInstance();
	}
	
	@When("^I go to \"([^\"]*)\" on \"([^\"]*)\"$")
	public void I_GO_TO(String url, String browser){
		System.out.println(url);
		webConnector.openBrowser(browser);
		webConnector.navigate(url);
		
	}
	
	
	@Given("^I am logged in \"([^\"]*)\"$")
	public void I_Logged_In(String element){
		
		if (!webConnector.isElementPresent(element)) {
			webConnector.doLogin(webConnector.config.getProperty("UserName"), webConnector.config.getProperty("Password"));
		}
		Assert.assertTrue(webConnector.isElementPresent(element));
		}
	
	@And("^I point to \"([^\"]*)\"$")
	public void I_Point_to(String element){
		
		webConnector.mouseMoveTo(element);
		System.out.println(element);
		}
	
	@Then("^I move to \"([^\"]*)\"$")
	public void I_Move_To(String element){
		webConnector.mouseMoveTo(element);
		
		System.out.println(element);
		}
	
	@Then("^I click on \"([^\"]*)\"$")
	public void I_Click_On(String element){
		webConnector.Click(element);
		
		System.out.println(element);
		}
	
	@Then("^I get to \"([^\"]*)\"$")
	public void I_Get_To(String element){
		
		Assert.assertTrue(webConnector.verifyPageTitle(element));
		
		System.out.println(element);
		}
	
	@Then("^I select top five brands$")
	public void I_Select(DataTable dt){
		
		List <String> elements = dt.asList(String.class);
		
		for(String element : elements) {
			webConnector.Click(element);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(element);
		}
	}
	
	@Then("^I choose \"([^\"]*)\"$")
	public void I_Choose(String element){
		webConnector.Click(element);
		
		System.out.println(element);
		}
	
	@Then("^I pick \"([^\"]*)\"$")
	public void I_Pick_Size(String element){
		
		String sizeDropdown = webConnector.OR.getProperty("SizeDropdown");
		webConnector.dropdownSelect(sizeDropdown, element);
		
		System.out.println(element);
		}
	
	
	@Then("^I verify \"([^\"]*)\" in cart$")
	public void I_Verify_Item(String element){
		webConnector.isElementPresent(element);
		
		System.out.println(element);
		}
	
	@After
	public void teardown() {
		webConnector.quit();
	}

	




}
