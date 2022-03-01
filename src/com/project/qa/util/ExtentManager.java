package com.project.qa.util;

import com.aventstack.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports extent;
	private static String reportfileName;
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
		
		return extent;
		
	}

}
