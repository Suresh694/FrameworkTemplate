/**
 * 
 */
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

/**
 * @author Suresh Das
 *
 */
public class ScreenShortsPass {

	public ScreenShortsPass(WebDriver driver) {
		TestBase.driver=driver;
		// TODO Auto-generated constructor stub
	}
	private static final DateFormat dateformat=new SimpleDateFormat("yyyy_MM_dd");
	
	public static String captureScreenpass(WebDriver driver,String screenName) {
		try {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			File src1=((TakesScreenshot)TestBase.driver).getScreenshotAs(OutputType.FILE);
			
			String dest1=System.getProperty("user.dir")+"//Test-Screenshort-Pass//"+screenName+".png";
			File target1=new File(dest1);
			FileUtils.copyFile(src1, target1);
			return dest1;
			
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
