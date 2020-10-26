package com.project.qa.testCases;

import java.io.FileNotFoundException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.project.qa.Base.TestBase;
import com.project.qa.pages.HomePage;

public class HomePage_Test extends TestBase{
	HomePage homePage;
	public HomePage_Test() throws FileNotFoundException {
		super();
	}
	
	@BeforeTest
	public void beforeAllTest() {
		/*Write the code which will be used for all the Test Cases for once*/
	}
	
	@BeforeMethod
	public void setup() throws FileNotFoundException {
		/*Initializing the driver and set the Properties of Browser*/
		initialization();
		homePage=new HomePage();
	}
	
	@Test
	public void setUsername() {
		/*Write the Test Cases accordingly to your Scenarios*/
	}
	
	@AfterMethod
	public void closeBrowser() {
		driver.quit();
	}
	
	@AfterTest
	public void sendEmail() {
		/*Write the code for sending report through mail*/
	}

}
