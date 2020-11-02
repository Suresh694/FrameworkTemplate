package com.project.qa.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.project.qa.util.TestUtil;

public class TestBase {	
	private static Properties prop;
	private static String currentDir=System.getProperty("user.dir");
	private static String configFile="/src/com/project/qa/config/config.properties";
	private static String username;
	private static String password;
	public static WebDriver driver;
	public static String inputfileLocation;
	public static ExtentHtmlReporter  htmlReporter;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;

	public TestBase() throws FileNotFoundException {
		prop=new Properties();
		FileInputStream fis=new FileInputStream(currentDir+configFile);
		try {
			prop.load(fis);
		} catch (IOException e) {
			System.err.println("The File is not Present at Given Location.Please Check the filePath Again!!");		
			e.printStackTrace();
		}		
	}
	
	public void initialization() {
		inputfileLocation=currentDir+prop.getProperty("filePath");
		username=prop.getProperty("username"); //Username should be defined in the config.properties file
		password=prop.getProperty("password");	//password should be defined in the config.properties file
		String browser=prop.getProperty("browser");
		if (browser.equals("Chrome")) {
			String chromedriverinConfig=prop.getProperty("browser");
			System.setProperty("webdriver.chrome.driver", currentDir+chromedriverinConfig);
			driver=new ChromeDriver();
		}
		if (browser.equals("Firefox")) {
			String firefoxDriver=prop.getProperty("browser");
			System.setProperty("webdriver.gecko.driver", currentDir+firefoxDriver);
			driver=new FirefoxDriver();
		}
		if (browser.equals("InternetExplorer")) {
			String InternetExplorerDriver=prop.getProperty("browser");
			System.setProperty("webdriver.ie.driver", currentDir+InternetExplorerDriver);
			driver=new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));
	}
	
	public static void setExtentReport() {
		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Functional TestCases");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extentReports=new ExtentReports();
		extentReports.attachReporter(htmlReporter);
		extentReports.setSystemInfo("TesterName", "Suresh Das");
		extentReports.setSystemInfo("HostName", "LocalHost");
		extentReports.setSystemInfo("OS", "Windows");
		extentReports.setSystemInfo("Browser", "Chrome");
	}
	
	public static void endReport() {
		extentReports.flush();
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

}
