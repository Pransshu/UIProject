package com.PageObjects;

import com.Base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends TestBase {
	
	private static final Logger logger = Logger.getLogger(LoginPage.class);

	JavascriptExecutor js = ((JavascriptExecutor) driver);

	@FindBy(xpath = "//input[@name=\"uid\"]")
	public WebElement username;

	
	// Enter Account Name
		public String enterUsername(String username) {
			logger.debug("Entering the Usermame");
			test.log(LogStatus.INFO, "Entering the username");
			
			System.out.println("Entered username is : " + username);
			waitForElement(username, 10).clear();
			username.sendKeys(username);
			return username;
		}


}
