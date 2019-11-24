package test;
import pages.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.chrome.ChromeDriverService.*;
import static org.testng.Assert.*;

import java.io.File;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestcasesUsingRobotClass {
	WebDriver driver;
	PageObjects page;
	WebDriverWait wait;
//	String username = "demo@bigtreecms.org";
//	String password = "demo";
	
	@BeforeMethod
	public void setUp() {
//		System.setProperty("webdriver.chrome.driver", "");
		System.setProperty(CHROME_DRIVER_EXE_PROPERTY, System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 10);
		page = new PageObjects(driver);
	}

	@DataProvider(name = "Credentials")
    public Object[][] dataProviderMethod() {
        return new Object[][] { { "demo@bigtreecms.org", "demo" } };
    }
	
	/*
	 * 
	 * These test cases will use awt robot class to upload file  
	 * 
	 */
	
	@Test(description = "Upload a file and check success scenario", dataProvider = "Credentials", priority = 1)
	public void checkvalidInput(String username, String password) {
		driver.get("https://demo.bigtreecms.org/admin/login/");
		page.login(username, password);
		page.files.click();
		page.addFiles.click();
		page.uploadFileUsingRobot(System.getProperty("user.dir") + File.separator + "testdata"  + File.separator + "file.pdf");
		wait.until(ExpectedConditions.visibilityOf(page.pdfUploadblock));
		assertTrue(page.pdfUploadblock.isDisplayed(),"PDF upload box available");
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//footer/a[contains(@class,'disabled')]"),0));
		page.continueBtn.click();
		assertTrue(page.pdfUploadSuccess.isDisplayed(), "PDF upload status");
		page.updateFile.click();
		page.cleanpup();
	}
	
	@Test(description = "Upload a image and check failure", dataProvider = "Credentials", priority = 2)
	public void checkInvalidInput(String username, String password) {
		driver.get("https://demo.bigtreecms.org/admin/login/");
		page.login(username, password);
		page.files.click();
		page.addFiles.click();
		page.uploadFileUsingRobot(System.getProperty("user.dir") + File.separator + "testdata"  + File.separator + "file.jpg");
		wait.until(ExpectedConditions.visibilityOf(page.imageUploadblock));
		assertTrue(page.imageUploadblock.isDisplayed(),"Image upload box available");
		assertEquals("This form does not accept images.", page.errorMessage.getText());
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//footer/a[contains(@class,'disabled')]"),1));
		assertTrue(page.disabledContinue.isDisplayed());
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
