package com.project.qa.util;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestmanager {
	public static ExtentTest parent;
	public static ExtentReports extent=ExtentManager.getInstance();
	Timestamp ts=new Timestamp(System.currentTimeMillis());
	public static Timestamp timestamp= new Timestamp(System.currentTimeMillis());
	static Map<Integer, ExtentTest> extentTestMap=new HashMap<Integer,ExtentTest>();
	
	public static synchronized ExtentTest getTest() {
		return (ExtentTest)extentTestMap.get((int)(long)Thread.currentThread().getId());
		
	}
	
	public static synchronized void endTest() {
		extent.flush();
	}
	
	public static synchronized ExtentTest startTest(String testName) {
		ExtentTestmanager.parent=ExtentTestmanager.extent.createTest(ExtentManager.reportfileName+" "+ExtentTestmanager.timestamp);
		extentTestMap.put((int)(long)(Thread.currentThread().getId()), parent);
		return parent;
		
	}
	
}
