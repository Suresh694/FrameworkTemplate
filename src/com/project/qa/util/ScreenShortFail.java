package com.project.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;

import com.project.qa.Base.TestBase;

public class ScreenShortFail {

	public ScreenShortFail(WebDriver driver) {
		TestBase.driver=driver;
		// TODO Auto-generated constructor stub
	}
	
	private static final DateFormat dateformat=new SimpleDateFormat("yyyy_MM_dd");
	
	public static String captureScreenFail(WebDriver driver, String screenName) {
		try {
			File src=((TakesScreenshot)TestBase.driver).getScreenshotAs(OutputType.FILE);
			
			String dest=System.getProperty("user,dir")+"//Test-ScreenShots-Fail//"+screenName+".png";
			File target=new File(dest);
			FileUtils.copyFile(src, target);
			return dest;
			
		} catch (WebDriverException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenName;
	}
	
	public static String generateFileName(ITestResult result) {
		Date date=new Date();
		String fileName=result.getName()+"_"+dateformat.format(date);
		return fileName;
	}
	

}
