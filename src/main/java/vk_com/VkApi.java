package vk_com;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class VkApi {
	private HttpClient client;
	private String accessToken;
	private String ownerId;
	private String version;
	private static final String VKCONFIGURATIONS_XML = "VkTestConfigurations.xml";

	public VkApi() {
		client = HttpClientBuilder.create().build();
		try {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(VKCONFIGURATIONS_XML);
		List<VkTestConfiguration> vkConfigurations = new VkTestConfigurationsParser().parse(document);
		VkTestConfiguration vkTestConfiguration = vkConfigurations.get(0);
		accessToken = vkTestConfiguration.getAccessToken();
		ownerId = vkTestConfiguration.getOwnerId();
		version = vkTestConfiguration.getVersion();
		} catch (ParserConfigurationException | SAXException | IOException | XMLStreamException e) {
			e.printStackTrace();
		} 
	}

	public WallPostResponse addWallPost() {
		try {
			String message = "test";
			URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.post?");
			builder.setParameter("access_token", accessToken).setParameter("owner_id", ownerId)
					.setParameter("message", message).setParameter("v", version);
			HttpPost request = new HttpPost(builder.build());
			return new WallPostResponse(client.execute(request));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Response editWallPost(String postId) {
		try {
			String message = "test_edited";
			URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.edit?");
			builder.setParameter("access_token", accessToken).setParameter("owner_id", ownerId)
					.setParameter("post_id", postId).setParameter("message", message).setParameter("v", version);
			HttpGet request = new HttpGet(builder.build());
			return new Response(client.execute(request));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Response deleteWallPost(String postId) {
		try {
			URIBuilder builder = new URIBuilder("https://api.vk.com/method/wall.delete?");
			builder.setParameter("access_token", accessToken).setParameter("owner_id", ownerId)
					.setParameter("post_id", postId).setParameter("v", version);
			HttpGet request = new HttpGet(builder.build());
			return new Response(client.execute(request));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

}
