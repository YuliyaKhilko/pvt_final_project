package vk_com;

import org.apache.log4j.Logger;
import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VkApiSteps {
	private static final Logger logger = Logger.getLogger(VkApiSteps.class);
	private VkApi vkApi = new VkApi();
	private Response response;
	private String postId;
	
	@When("^I add new post to wall using api$")
	public void addWallPost() {
		WallPostResponse wallPostResponse = vkApi.addWallPost();
		response = wallPostResponse;
		postId = wallPostResponse.getPostId();
		logger.info("Added post: post id = "+ postId);
	}

	@Then("^I get response code 200$")
	public void checkResponse() {
		Assert.assertEquals(200, response.statusCode);
	}

	@And("^I edit wall post using api$")
	public void EditWallPost() {
		response = vkApi.editWallPost(postId);
		logger.info("Edited post: post id = "+ postId);
	}

	@And("^I delete wall post using api$")
	public void DeleteWallPost() {
		response = vkApi.deleteWallPost(postId);
		logger.info("Deleted post: post id = "+ postId);
	}
}
