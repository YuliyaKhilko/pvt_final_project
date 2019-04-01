package mail_ru;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mail_ru.data.TestData;
import mail_ru.data.TestDataDAO;
import mail_ru.pages.AddressBookPage;
import mail_ru.pages.CreateEmailPage;
import mail_ru.pages.InboxFolder;
import mail_ru.pages.LoginPage;
import mail_ru.pages.PrepareDataFacade;
import mail_ru.pages.SendEmailPage;
import mail_ru.pages.SpamFolder;
import mail_ru.webdriver.WebDriverSingleton;
import ru.yandex.qatools.allure.annotations.Attachment;
import tools.ScreenshotMaker;

public class MailRuSteps {

	private WebDriver driver;
	private LoginPage loginPage;
	private InboxFolder inboxFolder;
	private CreateEmailPage createEmailPage;
	private SpamFolder spamFolder;
	private AddressBookPage addressBookPage;
	private SendEmailPage sendEmailPage;
	private PrepareDataFacade prepareDataFacade;
	private static TestData testData;
	private String emailSubject;
	private String groupName;
	private Boolean isEmailSentMessageDisplayed;
	private ScreenshotMaker screenshotMaker;

	public MailRuSteps() {

		TestDataDAO testDataDAO = new TestDataDAO();
		testDataDAO.getConnection();
		testData = testDataDAO.select().get(0);
		driver = WebDriverSingleton.getDriver();
		loginPage = new LoginPage(driver);
		inboxFolder = new InboxFolder(driver);
		createEmailPage = new CreateEmailPage(driver);
		sendEmailPage = new SendEmailPage(driver);
		spamFolder = new SpamFolder(driver);
		addressBookPage = new AddressBookPage(driver);
		prepareDataFacade = new PrepareDataFacade(driver);
		screenshotMaker = new ScreenshotMaker();
	}

	@Before
	public void setUp() {
		driver.manage().window().maximize();
		prepareDataFacade.emptyFolders();
	}

	@AfterStep
	public void afterStep() {
		screenshotMaker.makeScreenshot();
	}

	@After
	public void tearDown() {
		loginPage.logout();
		WebDriverSingleton.closeWebBrowser();
	}
//	@Attachment(value = "Page screenshot", type = "image/png")
//	public byte[] saveScreenshot(byte[] screenShot) {
//	    return screenShot;
//	}

	@When("^I create a new email$")
	public void createEmail() {
		createEmailPage.createNewEmail(testData.getEmail(), testData.getEmailSubject(), testData.getEmailBody());
	}

	@And("^I send email to myself$")
	public void sendEmail() {
		emailSubject = prepareDataFacade.sendEmailToInbox();
	}

	@And("^I open Inbox folder$")
	public void openInbox() {
		inboxFolder.openInboxFolder();
	}

	@Then("^I see the folder is not empty$")
	public void isFolderNotEmpty() {
		inboxFolder.ensureHasEmailBySubject(emailSubject);
		Assert.assertTrue(inboxFolder.getListOfEmails().size() > 0);
	}

	@And("^I Save email$")
	public void saveEmail() {
		createEmailPage.saveDraft();
	}

	@Then("^I see the message that email was saved to drafts$")
	public void isDraftSavedMessageDisplayed() {
		Assert.assertTrue(createEmailPage.isDraftSaved());
	}

	@When("^I move email from Inbox to Spam$")
	public void moveEmailToSpam() {
		inboxFolder.openInboxFolder();
		inboxFolder.moveFirstEmailToSpam();
		spamFolder.openSpamFolder();
		spamFolder.ensureHasEmailBySubject(emailSubject);
	}

	@Then("^I see Spam is not empty$")
	public void emailIsDisplayedInSpam() {
		Assert.assertTrue((inboxFolder.getListOfEmails()).size() > 0);
	}

	@When("^I create contacts group$")
	public void createContactsGroup() {
		groupName = testData.getGroupName() + System.currentTimeMillis();
		addressBookPage.openAddressBook();
		addressBookPage.createContactsGroup(groupName);
		addressBookPage.addContactsToGroup(groupName, testData.getName(), testData.getEmail());
	}

	@And("^I Send new email to group$")
	public void sendEmailToContactsGroup() {
		isEmailSentMessageDisplayed = sendEmailPage.sendNewEmail(groupName, testData.getEmailSubject(),
				testData.getEmailBody());
	}

	@Then("^I see message that email is sent$")
	public void isEmailSentMessageDisplayed() {
		Assert.assertTrue(isEmailSentMessageDisplayed);
	}

	@When("^I send (\\d+) emails to my Inbox$")
	public void sendMultipleEmailsToInbox(int emailsToSendNumber) {
		for (int i = 0; i < emailsToSendNumber; i++) {
			prepareDataFacade.sendEmailToInbox();
		}
	}

	@And("^I mark (\\d+) emails with flag$")
	public void markEmailsWithFlag(int emailsToBeMarkedNumber) {
		inboxFolder.openInboxFolder();
		inboxFolder.markEmailWithFlag(emailsToBeMarkedNumber);
	}

	@Then("^I see (\\d+) emails are displayed with filled flag icon$")
	public void checkThatEmailsAreMarkedWithFlag(int flaggedEmailsNumber) {
		Assert.assertEquals(inboxFolder.getNumberOfMarkedWithFlagEmails(), flaggedEmailsNumber);
	}

	@And("^I select all emails and unflag them$")
	public void unflagAllEmails() {
		inboxFolder.unflagAllInboxEmails();
	}

	@Then("^I see that there no emails marked with flag$")
	public void checkNoFlaggedEmails() {
		Assert.assertTrue(inboxFolder.getNumberOfMarkedWithFlagEmails() == 0);
	}

	@And("^I remove all emails from inbox$")
	public void emptyInbox() {
		inboxFolder.emptyInboxFolder();
	}

	@Then("^I see Inbox is empty$")
	public void CheckThatInboxIsEmpty() {
		inboxFolder.openInboxFolder();
		Assert.assertEquals((inboxFolder.getListOfEmails()).size(), 0);
	}
}
