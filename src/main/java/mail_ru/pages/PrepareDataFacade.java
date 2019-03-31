package mail_ru.pages;

import org.openqa.selenium.WebDriver;

import mail_ru.data.TestData;
import mail_ru.data.TestDataDAO;

public class PrepareDataFacade {
	private InboxFolder inboxFolder;
	private LoginPage loginPage;
	private SpamFolder spamFolder;
	private SendEmailPage sendEmailPage;
	private static TestData testdata;
	private WebDriver driver;

	public PrepareDataFacade(WebDriver driver) {
		inboxFolder = new InboxFolder(driver);
		loginPage = new LoginPage(driver);
		spamFolder = new SpamFolder(driver);
		sendEmailPage = new SendEmailPage(driver);
		this.driver = driver;
	}

	private void login() {
		TestDataDAO testDataDAO = new TestDataDAO();
		testDataDAO.getConnection();
		testdata = testDataDAO.select().get(0);
		driver.get("https://mail.ru");
		loginPage.login(testdata.getLogin(), testdata.getPassword());
	}
	
	public void emptyFolders() {
		login();
		inboxFolder.emptyInboxFolder();
		spamFolder.emptySpamFolder();
	}
	
	public String sendEmailToInbox() {
		String subject = testdata.getEmailSubject() + System.currentTimeMillis();
		sendEmailPage.sendNewEmail(testdata.getEmail(), subject, testdata.getEmailBody());
		inboxFolder.openInboxFolder();
		inboxFolder.ensureHasEmailBySubject(subject);
		return subject;
	}
}
