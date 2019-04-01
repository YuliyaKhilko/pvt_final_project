package tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import mail_ru.webdriver.WebDriverSingleton;


public class ScreenshotMaker {
	private WebDriver driver;
	private String folderName;
	private File dstDirectory;

	public ScreenshotMaker() {
		driver = WebDriverSingleton.getDriver();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		folderName = dateFormat.format(date);
		dstDirectory = new File("/Users/yulyakhilko/Documents/screenshots/" + folderName);
		dstDirectory.mkdir();
	}

	public void makeScreenshot() {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFileToDirectory(scrFile, dstDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public void attachScreebshot(File screenshot) {
//		Path content = Paths.get("path-to-my-attachment-contnet");
//		try (InputStream is = Files.newInputStream(content)) {
//		    Allure.addAttachemnt("My attachment", is);
//		}
//	}
  
}
