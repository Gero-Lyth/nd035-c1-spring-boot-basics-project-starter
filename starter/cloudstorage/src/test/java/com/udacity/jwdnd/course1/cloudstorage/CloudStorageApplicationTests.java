package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		//Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * <p>
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * <a href="https://review.udacity.com/#!/rubrics/2724/view">Udacity rubric</a>
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * <p>
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * <p>
	 * Read more about custom error pages at: 
	 * <a href="https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page">here</a>
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Tests","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * <p>
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * <p>
	 * Read more about file size limits here: 
	 * <a href="https://spring.io/guides/gs/uploading-files/">spring.io/guides/gs/uploading-files</a> under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testEmptyDownload() {
		String userName = "EmptyDownload";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		driver.get("http://localhost:" + this.port + "/download/-1");
	}
	@Test
	public void testNoteSubmit(){
		String userName = "suabmitNote";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		WebElement newButton = driver.findElement(By.id("new-note"));
		newButton.click();
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("this is a title");
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.sendKeys("this si the thingsa");
		desc.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is a title"));
	}

	@Test
	public void testNoteSecure(){
		String userName = "NoteSecure";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		WebElement newButton = driver.findElement(By.id("new-note"));
		newButton.click();
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("this is a title");
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.sendKeys("this si the thingsa");
		desc.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is a title"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutDiv")));
		WebElement logout = driver.findElement(By.id("logoutBtn"));
		logout.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
		doMockSignUp("Second","Test","Hecker","123");
		doLogIn("Hecker", "123");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		((JavascriptExecutor) driver).executeScript("showNoteModal(1,'hecked','dsadasdsd');");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		driver.findElement(By.id("note-description")).submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assertions.assertFalse(driver.getPageSource().contains("hecked"));
	}

	@Test
	public void testNoteBeegSubmit(){
		String userName = "suabmitBeeegNote";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 9);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		((JavascriptExecutor) driver).executeScript("showNoteModal(null,'heckedsomuchhahahahahahahahahahahahahahahahahaha','dsadasdsd');");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.sendKeys("this si the thingsa");
		desc.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assertions.assertFalse(driver.getPageSource().contains("heckedsomuchhahahahahahahahahahahahahahahahahaha"));
	}
	@Test
	public void testNoteUpdate(){
		String userName = "Note Updatington";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		WebElement newButton = driver.findElement(By.id("new-note"));
		newButton.click();
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("this is a title");
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.sendKeys("this si the thingsa");
		desc.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is a title"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		WebElement editButton = driver.findElement(By.className("edit-note"));
		editButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.clear();
		noteTitle.sendKeys("this is a different");
		noteTitle.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is a different"));
	}
	@Test
	public void testNoteWrongUpdate(){
		String userName = "Note Wrongdatington";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-notes-tab"));
		tabButton.click();
		((JavascriptExecutor) driver).executeScript("showNoteModal(-3);");
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys("this is a title");
		WebElement desc = driver.findElement(By.id("note-description"));
		desc.sendKeys("this si the thingsa");
		desc.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assertions.assertTrue(driver.getPageSource().contains("Note with ID -3"));
	}
	@Test
	public void testCredSubmit(){
		String userName = "Credington McSubmit";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-credentials-tab"));
		tabButton.click();
		WebElement newButton = driver.findElement(By.id("new-cred"));
		newButton.click();
		WebElement credurl = driver.findElement(By.id("credential-url"));
		credurl.sendKeys("this is url");
		WebElement username = driver.findElement(By.id("credential-username"));
		username.sendKeys("this si the thingsa");
		WebElement pw = driver.findElement(By.id("credential-password"));
		pw.sendKeys("passsss wore");
		pw.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is url"));
	}
	@Test
	public void testCredWrongUpdate(){
		String userName = "Credington Negative";
		String password = "123";
		// Create a test account
		doMockSignUp("Hecker","InDaCode",userName,password);
		doLogIn(userName, password);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-credentials-tab"));
		tabButton.click();
		((JavascriptExecutor) driver).executeScript("showCredentialModal(-1);");
		WebElement credurl = driver.findElement(By.id("credential-url"));
		credurl.sendKeys("this is url");
		WebElement username = driver.findElement(By.id("credential-username"));
		username.sendKeys("this si the thingsa");
		WebElement pw = driver.findElement(By.id("credential-password"));
		pw.sendKeys("passsss wore");
		pw.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
		Assertions.assertTrue(driver.getPageSource().contains("Credential ID -1 not found"));
	}
	@Test
	public void testCredUpdate(){
		String userName = "Credington updatieee";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-credentials-tab"));
		tabButton.click();
		WebElement newButton = driver.findElement(By.id("new-cred"));
		newButton.click();
		WebElement credurl = driver.findElement(By.id("credential-url"));
		credurl.sendKeys("this is url");
		WebElement username = driver.findElement(By.id("credential-username"));
		username.sendKeys("this si the thingsa");
		WebElement pw = driver.findElement(By.id("credential-password"));
		pw.sendKeys("passsss wore");
		pw.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is url"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		tabButton = driver.findElement(By.id("nav-credentials-tab"));
		tabButton.click();
		WebElement editButton = driver.findElement(By.className("edit-cred"));
		editButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		pw = driver.findElement(By.id("credential-password"));
		pw.clear();
		pw.sendKeys("this is a different");
		pw.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		Assertions.assertTrue(driver.getPageSource().contains("this is a different"));
	}
	@Test
	public void testCredDelete(){
		String userName = "DELEEEETEEEED";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement tabButton = driver.findElement(By.id("nav-credentials-tab"));
		tabButton.click();
		WebElement newButton = driver.findElement(By.id("new-cred"));
		newButton.click();
		WebElement credurl = driver.findElement(By.id("credential-url"));
		credurl.sendKeys("this is url");
		WebElement username = driver.findElement(By.id("credential-username"));
		username.sendKeys("this si the thingsa");
		WebElement pw = driver.findElement(By.id("credential-password"));
		pw.sendKeys("passsss wore22");
		pw.submit();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("this is url"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		tabButton = driver.findElement(By.id("nav-credentials-tab"));
		tabButton.click();
		WebElement deleteButton = driver.findElement(By.className("delete-cred"));
		deleteButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		Assertions.assertTrue(driver.getPageSource().contains("delete"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		Assertions.assertFalse(driver.getPageSource().contains("passsss wore22"));
	}

	@Test
	public void testUploadDelete() {
		String userName = "DELEEEETEEEED 2";
		String password = "123";
		// Create a test account
		doMockSignUp("Primary","Test",userName,password);
		doLogIn(userName, password);

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileDeleteButton = driver.findElement(By.className("delete-file"));
		fileDeleteButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
	}
}
