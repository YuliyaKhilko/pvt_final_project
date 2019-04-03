package mail_ru.pages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class Page {
	public WebDriver driver;

	@FindBy(xpath = "(//*[(@data-cache-key) and not(contains(@style,\"display: none\"))]//div[@data-group=\"selectAll\"]//div[@class=\"b-checkbox__box\"])[1]")
	WebElement selectAllCheckbox;
	
	@FindBy(xpath = ".//div[@class=\"js-item-checkbox b-datalist__item__cbx\"]/div/div[@class=\"b-checkbox__box\"]")
	List<WebElement> emailCheckboxes;

	private WebElement getEmailBySubject(String subject) {
		return driver.findElement(
				By.xpath("//*[(@data-cache-key) and not(contains(@style,\"display: none\"))]//*[@data-subject=\""
						+ subject + "\"]"));
	}

	public Page(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public void enterText(WebElement element, String text) {
		element.clear();
		element.click();
		element.sendKeys(text);
	}

	public void enterTinyMCEText(String text) {
		((JavascriptExecutor) driver).executeScript("tinyMCE.activeEditor.setContent('<p>" + text + "</p>')");
	}
	
	public void safe(Predicate<WebDriver> condition) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
				.ignoreAll(Arrays.asList(StaleElementReferenceException.class, NoSuchElementException.class,
						ElementNotVisibleException.class));

		wait.until(driver -> {
			PageFactory.initElements(driver, this);
			return condition.test(driver);
		});
	}

	public List<WebElement> getListOfEmails() {
		return emailCheckboxes;
	}

	public void selectAllInboxEmails() {
		Actions action = new Actions(driver);
        action.moveToElement(selectAllCheckbox).build().perform();
        this.safe(driver -> {
        	selectAllCheckbox.click();
        	return true;
        });
		
	}

	public void ensureHasEmailBySubject(String subject) {
		driver.navigate().refresh();
		this.safe(driver -> {
			getEmailBySubject(subject).getText();
			return true;
		});
	}
}
