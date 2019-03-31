package vk_com;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class VkTestConfigurationsParser {

	public List<VkTestConfiguration> parse(Document document) throws FileNotFoundException, XMLStreamException {
		NodeList nodeList = document.getElementsByTagName("TestData");
		List<VkTestConfiguration> vkConfiguration = new ArrayList<VkTestConfiguration>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			vkConfiguration.add(getVkConfiguration(nodeList.item(i)));
		}
		return vkConfiguration;
	}

	private static VkTestConfiguration getVkConfiguration(Node node) {
		VkTestConfiguration vkTestConfiguration = new VkTestConfiguration();
		Element element = (Element) node;
		vkTestConfiguration.setAccessToken(getTagValue("accessToken", element));
		vkTestConfiguration.setOwnerId(getTagValue("ownerId", element));
		vkTestConfiguration.setVersion(getTagValue("version", element));
		return vkTestConfiguration;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodeList.item(0);
		return node.getNodeValue();
	}
}
