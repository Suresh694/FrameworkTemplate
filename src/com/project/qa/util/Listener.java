package com.project.qa.util;

import java.io.IOException;

import org.testng.IExecutionListener;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.IMethodInterceptor;
import org.testng.IReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.project.qa.Base.TestBase;

public class Listener implements ITestListener{
	public static String testname,final_outcome;

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		try {
			// TODO Auto-generated method stub
			if (result.getStatus()==ITestResult.SUCCESS) {
				String schreenshot_pass=ScreenShortsPass.captureScreenpass(TestBase.driver, ScreenShortsPass.generateFileName(result));
				try {
					StackTraceElement[]s=result.getThrowable().getStackTrace();
					if (s!=null) {
						ExtentTestmanager.getTest().log(Status.PASS	, result.getThrowable());
					}
				} catch (NullPointerException e) {
					// TODO: handle exception
				}
				ExtentTestmanager.getTest().log(Status.PASS, "Snapshot below:"+ExtentTestmanager.getTest().addScreenCaptureFromPath(schreenshot_pass));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("**Test Execution "+result.getMethod().getMethodName()+"Failed**");
		ExtentTestmanager.getTest().log(Status.FAIL, "Test Failed");
		
		if (result.getStatus()==ITestResult.FAILURE) {
			try {
			String screenshot_Fail=ScreenShortFail.captureScreenFail(TestBase.driver, ScreenShortFail.generateFileName(result));
			
			
				StackTraceElement[]s=result.getThrowable().getStackTrace();
				if (s!=null) {
					ExtentTestmanager.getTest().log(Status.FAIL, result.getThrowable());
				}
			
			ExtentTestmanager.getTest().log(Status.FAIL, "Snapshot below: "+ExtentTestmanager.getTest().addScreenCaptureFromPath(screenshot_Fail));
			} catch (NullPointerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				ExtentTestmanager.getTest().log(Status.FAIL, "Test Failed");
				ExtentTestmanager.endTest();
			}
			
			}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("**Test "+result.getMethod()+" Skipped**");
		ExtentTestmanager.getTest().log(Status.SKIP, "Test Skipped");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("***Test Failed but within Percentage % "+result.getMethod().getMethodName());
	}

	//Added to create the ExtentReport
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ExtentManager.reportfileName=context.getCurrentXmlTest().getName();
		testname=ExtentManager.reportfileName;
		
		ExtentManager.reportSuitName=context.getCurrentXmlTest().getSuite().getName();
		System.out.println("TestSuite -"+ExtentManager.reportfileName+" started");
		System.out.println(ExtentManager.reportfileName);
		
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ExtentManager.reportfileName=context.getCurrentXmlTest().getName();
		System.out.println("Test Suite "+ExtentManager.reportfileName+" ending");
		System.out.println(ExtentManager.reportfileName);
		
		if (final_outcome=="fail") {
			final_outcome="fail";
			
		}
	}

	

}
