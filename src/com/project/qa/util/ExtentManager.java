package com.project.qa.util;

import java.util.Date;
import java.util.Properties;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {
	private static ExtentReports extent;
	public static String reportfileName;
	private static String fileSeparator=System.getProperty("file.separator");
	private static String reportfilePath=System.getProperty("user.dir")+fileSeparator+"TestReport";
	private static String reportFileLocation="";
	public static String reportSuitName;
	
	public static ExtentReports getInstance() {
			if (extent==null) {
				createInstance();
			}
		return extent;
		
	}
	
	public static ExtentReports createInstance() {
		
		try {
			Properties prop=new Properties();
			File file=new File(System.getProperty("user.dir")+"\\src\\com\\project\\qa\\config\\config.properties");
			FileInputStream fileInput=new FileInputStream(file);
			prop.load(fileInput);
			
			String fileName=getReportpath(reportfilePath);
			//Using ExtentHtml Reporter
			ExtentHtmlReporter htmlreporter=new ExtentHtmlReporter(fileName);
			
			DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
			Date date=new Date();
			String sysdateandtime=dateFormat.format(date);
			
			htmlreporter.config().setTheme(Theme.DARK);
			htmlreporter.config().setDocumentTitle(reportSuitName+sysdateandtime);
			htmlreporter.config().setEncoding("utf-8");
			htmlreporter.config().setReportName(reportSuitName);
			htmlreporter.config().setTimeStampFormat("EEEE, MMMM dd,yyyy, hh:mm a'('zzz')'");
			htmlreporter.config().setProtocol(Protocol.HTTPS);
			
			extent=new ExtentReports();
			extent.attachReporter(htmlreporter);
			
			String browser=prop.getProperty("browser");
			extent.setAnalysisStrategy(AnalysisStrategy.SUITE);
			extent.setAnalysisStrategy(AnalysisStrategy.TEST);
			extent.setSystemInfo("OS", "Windows");
			extent.setSystemInfo("Env", "SIT1");
			extent.setSystemInfo("URL", prop.getProperty("url"));
			extent.setSystemInfo("Browser", browser);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return extent;
		
	}
	
	//To Create the Report Path
	private static String getReportpath(String path) {
		reportFileLocation=reportfilePath+fileSeparator+reportSuitName+".html";
		File testDirectory=new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: "+path+"is created");
				return reportFileLocation;
			}
			else {
				System.out.println("Failed to Create Directory.."+path);
				return System.getProperty("user.dir");
			}
		}else {
			System.out.println("Directory Already Exists:"+path);
		}
		
		return path;
		
	}
	
	public static String getsuitename() {
		String suitname=reportSuitName;
		return suitname;
		
	}

}
