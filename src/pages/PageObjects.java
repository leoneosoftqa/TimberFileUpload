package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObjects {
	WebDriver driver;
	Robot robot;

	@FindBy(id = "user")
	public WebElement userName;
	
	@FindBy(id = "password")
	public WebElement password;
	
	@FindBy(xpath = "//input[@type='submit']")
	public WebElement submit;
	
	@FindBy(xpath = "//a[.='Files']")
	public WebElement files;
	
	@FindBy(xpath = "//a[.='Dashboard']")
	public WebElement dashBoard;
	
	@FindBy(xpath = "//a[.='Add Files']")
	public WebElement addFiles;
	
	@FindBy(xpath = "//a[.='Add Video']")
	public WebElement addVideo;
	
	@FindBy(xpath = "//form[contains(@class,'dz-clickable')]")
	public WebElement fileUploadArea;
	
	@FindBy(xpath = "//input[@type='file']")
	public WebElement fileUpload;
	
	@FindBy(xpath = "//*[@id='file_manager_dropzone']/div/div/div/span[.='file.jpg']")
	public WebElement imageUploadblock;
	
	@FindBy(xpath = "//*[@id='file_manager_dropzone']/div/div/div/span[.='file.pdf']")
	public WebElement pdfUploadblock;
	
	@FindBy(xpath = "//footer/a[contains(@class,'disabled')]")
	public WebElement disabledContinue;
	
	@FindBy(xpath = "//span[@data-dz-errormessage]")
	public WebElement errorMessage;
	
	@FindBy(xpath = "//footer/a[not(contains(@class,'disabled'))]")
	public WebElement continueBtn;
	
	@FindBy(xpath = "//*[@value='Update File']")
	public WebElement updateFile;
	
	@FindBy(xpath = "//section//h3[contains(.,'File Manager')]/following-sibling::p[contains(.,'Uploaded Files')]")
	public WebElement pdfUploadSuccess;

	@FindBy(xpath = "//section//h3[contains(.,'File Manager')]/following-sibling::p[contains(.,'Updated Files')]")
	public WebElement pdfUpdateSuccess;
	
	@FindBy(xpath = "//a[@class='icon_delete']")
	public WebElement deleteEntry;

	@FindBy(xpath = "//input[@value='Delete File']")
	public WebElement deleteConfirm;
	
	public PageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void login(String user, String pass) {
		userName.sendKeys(user);
		password.sendKeys(pass);
		submit.click();
	}
	
	public WebElement makeFileUploadAvailable() {
		JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("return document.querySelector('input[type=file]').removeAttribute('style')");
		return fileUpload;
	}
	
	public void uploadFileUsingRobot(String filepath){
		try {
		robot = new Robot();
		fileUploadArea.click();
		StringSelection str = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {}
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		}
		catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void cleanpup() {
		deleteEntry.click();
		deleteConfirm.click();
	}
}
