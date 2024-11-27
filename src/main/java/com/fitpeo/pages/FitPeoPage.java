package com.fitpeo.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FitPeoPage {
	WebDriver driver;

	private JavascriptExecutor js;

	private static final Logger logger = Logger.getLogger(FitPeoPage.class.getName());
	private WebDriverWait wait;

	@FindBy(xpath = "//div[contains(text(),'Revenue Calculator')]")
	private WebElement btnRevenueCalculator;

	@FindBy(xpath = "//div[@class='MuiBox-root css-j7qwjs']")
	private WebElement RevenueCalculatorSlider;

	@FindBy(xpath = "//input[@type='number']")
	private WebElement txtPatients;

	@FindBy(xpath = "//input[contains(@class,'PrivateSwitchBase-input')]")
	private List<WebElement> chkCPTCodes;

	@FindBys({
	    @FindBy(xpath = "//p[contains(@class, 'MuiTypography-root') and contains(@class, 'MuiTypography-body1') and contains(text(), 'CPT-')]")
	})
	private List<WebElement> lblCPTCodes;
	
	public FitPeoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickOnRevenueCalculatorBtn() throws InterruptedException {
		logger.info("Starting of clickOnRevenueCalculatorBtn method");

		// Add wait for btnRevenueCalculator to be clickable
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(btnRevenueCalculator));

		this.btnRevenueCalculator.click();

		logger.info("Ending of clickOnRevenueCalculatorBtn method");
	}

	public void enterPatientNumber(String patients) throws InterruptedException {
		logger.info("Starting of enterPatientNumber method");
		Thread.sleep(5000);
		this.txtPatients.sendKeys(Keys.CONTROL + "A", Keys.DELETE);
		this.txtPatients.sendKeys(patients);
		logger.info("Ending of enterPatientNumber method");
	}

	public void selectAllCPTCodes() throws InterruptedException {
		logger.info("Starting of selectAllCPTCodes method");
		
		ArrayList<String> codeList = new ArrayList<>();
		codeList.add("CPT-99091");
		codeList.add("CPT-99453");
		codeList.add("CPT-99454");
		codeList.add("CPT-99474");
		Thread.sleep(5000);
		scrollDown(100);
		for (int j = 0; j < lblCPTCodes.size(); j++) {
			for (String lstCodeValue : codeList) {
				if (lblCPTCodes.get(j).getText().equalsIgnoreCase(lstCodeValue)) {
					for (int i = 0; i < chkCPTCodes.size(); i++) {
						if(i==j) {
						try {
							chkCPTCodes.get(i).click();
						} catch (Exception e) {
							clickOnWebElement(chkCPTCodes.get(i));
						}}
					}
				}
			}
		}
		logger.info("Ending of selectAllCPTCodes method");
	}

	public void clickOnWebElement(WebElement webelement) {
		logger.info("Starting of clickOnWebElement method");

		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		jsExec.executeScript("arguments[0].click();", webelement);

		logger.info("Ending of clickOnWebElement method");
	}

	public void scrollDown(int scroll) {
		logger.info("Starting of scrollDown method");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, " + scroll + ")");

		logger.info("Ending of scrollDown method");
	}
}
