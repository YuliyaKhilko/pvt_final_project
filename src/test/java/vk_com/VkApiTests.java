package vk_com;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith (Cucumber.class)
@CucumberOptions(features = {"src/test/resources/vk.feature"})
public class VkApiTests {

}
