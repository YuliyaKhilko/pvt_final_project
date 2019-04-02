package mail_ru.pages;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InboxFolder extends Page {

	private static final int TIMEOUT = 10;

	@FindBy(xpath = "(.//a[@href=\"/messages/inbox/\"])[1]")
	WebElement linkToInboxFolder;

	@FindBy(xpath = "(.//div[@class=\"js-item-checkbox b-datalist__item__cbx\"]/div/div[@class=\"b-checkbox__box\"])[1]")
	WebElement firstEmailCheckbox;

	@FindBy(xpath = "(.//div[@data-name=\"spam\"])[1]")
	WebElement moveToSpamButton;

	@FindBy(xpath = ".//button[@class=\"btn b-spam-confirm__btn\"]")
	WebElement confirmMoveToSpam;

	@FindBy(xpath = "(.//div[@data-group=\"letters-more\"])[1]")
	WebElement moreDropdown;

	@FindBy(xpath = ".//a[@data-name=\"flag_no\"]")
	WebElement unflagAll;

	@FindBy(xpath = "(.//div[@data-name=\"remove\"])[1]")
	WebElement deleteButton;

	@FindBy(xpath = "(.//span[@class=\"b-datalist__empty__text\"])[1]")
	WebElement emptyInboxLabel;
	
	@FindBy(xpath = "//div[@class=\"js-item-checkbox b-datalist__item__cbx\"]")
		    List<WebElement> emailCheckboxes;
	
	@FindBy(xpath = ".//div[@data-act=\"flag\" and contains(@class, \"b-flag_yes\")]")
	List<WebElement> flaggedEmails;
	
	@FindBy (xpath = ".//div[@data-act=\"flag\" and not(contains(@class, \"b-flag_yes\"))]")
	List<WebElement> notFlaggedEmails;
	
	
	public InboxFolder(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void openInboxFolder() {
		this.safe(driver -> {
			this.linkToInboxFolder.click();
			return true;
		});

		new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.urlContains("inbox"));
	}

	public void moveFirstEmailToSpam() {
		Actions action = new Actions(driver);
        action.moveToElement(emailCheckboxes.get(0)).build().perform();
        emailCheckboxes.get(0).click();
		moveToSpamButton.click();
		try{
			new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(confirmMoveToSpam));
			confirmMoveToSpam.click();
		} catch(NoSuchElementException ex) {
			ex.printStackTrace();
		}
		
	}

	public void markEmailWithFlag(int numberofEmailsToBeMarked) {

		driver.navigate().refresh();
		for (int i = 0; i <= numberofEmailsToBeMarked - 1; i++) {
			notFlaggedEmails.get(i).click();
		}
	}

	public int getNumberOfFlaggedEmails() {
		return flaggedEmails.size();
	}

	public void unflagAllInboxEmails() {
		selectAllInboxEmails();
		moreDropdown.click();
		unflagAll.click();
	}

	public void deleteAllEmailsFromInbox() {
		openInboxFolder();
		selectAllInboxEmails();
		deleteButton.click();
		new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.invisibilityOf(emptyInboxLabel));
	}

}
