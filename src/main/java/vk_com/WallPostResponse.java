package vk_com;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class WallPostResponse extends Response {
	private String postId;

	public String getPostId() {
		return postId;
	}

	public WallPostResponse(HttpResponse response) {
		super(response);
		try {
			postId = EntityUtils.toString(response.getEntity()).replaceAll("[^0-9]", "");
		} catch (ParseException e) {
			e.printStackTrace();
			postId = null;
		} catch (IOException e) {
			e.printStackTrace();
			postId = null;
		}
	}

}
