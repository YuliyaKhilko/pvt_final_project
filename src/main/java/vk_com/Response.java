package vk_com;

import org.apache.http.HttpResponse;

public class Response {
	public final int statusCode;
	
	public Response(HttpResponse response) {
		this.statusCode = response.getStatusLine().getStatusCode();
	}

}
