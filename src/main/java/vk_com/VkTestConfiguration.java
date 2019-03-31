package vk_com;

public class VkTestConfiguration {

	private String accessToken;
	private String ownerId;
	private String version;
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public String getVersion() {
		return version;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
