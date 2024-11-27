package com.fitpeo.test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.fitpeo.pages.FitPeoPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FitPeoTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private FitPeoPage fitpeoPage = null;

	@BeforeClass
	public void setUp() {
		try {
			WebDriverManager.chromedriver();
			driver = new ChromeDriver();
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			driver.manage().window().maximize();
		} catch (Exception e) {
			System.err.println("Error in setUp: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	@Parameters({ "siteURL" })
	public void navigatingToUrl(String siteURL) {
		try {
			// Navigate to FitPeo homepage
			driver.get(siteURL);
		} catch (Exception e) {
			System.err.println("Error in navigatingToUrl: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	public void clickOnRevenueCalculatorTab() {
		try {
			this.fitpeoPage = new FitPeoPage(driver);
			this.fitpeoPage.clickOnRevenueCalculatorBtn();

		} catch (InterruptedException e) {
			System.err.println("Error in clickOnRevenueCalculatorTab: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error in clickOnRevenueCalculatorTab: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void ScrollDowntotheSlidersection() {
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement slider = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@aria-orientation='horizontal']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", slider);

		} catch (Exception e) {
			System.err.println("Error in ScrollDowntotheSlidersection: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 4)
	public void adjusttheSlider() {
		try {
			WebElement slider = driver.findElement(By.xpath("//input[@aria-orientation='horizontal']"));
			Actions actions = new Actions(driver);
			actions.clickAndHold(slider).moveByOffset(94, 0).release().perform();

			// Verify the text field value is updated to 820
			WebElement textField = driver.findElement(By.cssSelector("input[type='number']"));
			Assert.assertEquals(textField.getAttribute("value"), "820", "Text field value should be 820");
		} catch (Exception e) {
			System.err.println("Error in adjusttheSlider: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 5)
	public void UpdatetheTextField() {
		try {
			this.fitpeoPage.enterPatientNumber("560");

			// Verify the slider field value is updated to 560
			WebElement textField = driver.findElement(By.xpath("//input[@type='range']"));
			Assert.assertEquals(textField.getAttribute("value"), "560", "Text field value should be 560");
		} catch (Exception e) {
			System.err.println("Error in UpdatetheTextField: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 6)
	public void selectCPTCodes() {
		try {
			this.fitpeoPage.selectAllCPTCodes();

			this.fitpeoPage.enterPatientNumber("820");

			this.fitpeoPage.scrollDown(300);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement textField = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//p[contains(text(),'Total Recurring Reimbursement for all Patients Per Month:')]/p")));

			Assert.assertEquals(textField.getText(), "$110700", "Text field value should be $110700");
		} catch (Exception e) {
			System.err.println("Error in selectCPTCodes: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@AfterClass
	public void tearDown() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			System.err.println("Error in tearDown: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
