package com.project.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.qa.Base.TestBase;

public class TestUtil extends TestBase{

	public TestUtil() throws FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
	public static String currentDir=System.getProperty("user.dir");
	public static long PAGE_LOAD_TIMEOUT=20;
	public static long IMPLICIT_WAIT=20;
	public static long EXPLICIT_WAIT=20;
	public static WebDriverWait wait=new WebDriverWait(driver, EXPLICIT_WAIT);
	public static JavascriptExecutor js=(JavascriptExecutor) driver;
	
	/*Get Data from particular Cell---Done
	Write Data into Particular Cell---Done
	Write Data into Particular by column Index---Done
	Send Email with and without attachment----Done Both
	How to find Broken links---Done
	How to Check the prices are sorted or not
	Taking ScreenShort---DOne
	Handle IFrames
	Handle multiple windows
	Handle Cookies
	Handle Alert Popup
	Handle SSL Certificate
	*/
	
	@SuppressWarnings("deprecation")
	public static String getCellValue(String filePath,String fileName,String sheetName,String coloumn, int row){
		String cellval=null;
		try(FileInputStream fis=new FileInputStream(new File(filePath+"/"+fileName));XSSFWorkbook wb=new XSSFWorkbook(fis)) {	
			XSSFSheet sheet=wb.getSheet(sheetName);
			DataFormatter fmt=new DataFormatter();
			cellval=fmt.formatCellValue(sheet.getRow(row).getCell(CellReference.convertColStringToIndex(coloumn)));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("File Not Found");
		}
		return cellval;
	}
	
	public static void WriteToCell(String filePath,String fileName,String sheetName,String value,int rownum,String column )throws IOException{
			Cell cell=null;
			//FileInputStream fis=new FileInputStream(new File(filePath+"/"+fileName));
		   XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(new File(filePath+"/"+fileName)));
			XSSFSheet sheet=wb.getSheet(sheetName);
			Row row=sheet.getRow(rownum);
				if (row==null) {
					row=sheet.createRow(rownum);
				}
				cell=row.getCell(CellReference.convertColStringToIndex(column));
				if (cell==null) {
					cell=row.createCell(CellReference.convertColStringToIndex(column));
				}
				cell.setCellValue(value);
			   FileOutputStream fos=new FileOutputStream(filePath+"/"+fileName);
				wb.write(fos);
				wb.close();
				fos.close();
	}
	
	public static void writeToExcelThroughIndex(String filepath,String fileName,String sheetName,String value, int rownum,int colIndex) {
		try(XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(new File(filepath+"/"+fileName)))) {
			Cell cell=null;Row row=null;			
			XSSFSheet sheet=wb.getSheet(sheetName);
			row=sheet.getRow(rownum);
				if (row==null)
					row=sheet.createRow(rownum);				
				cell=row.getCell(colIndex);
				if (cell==null)
					cell=row.createCell(colIndex);
				cell.setCellValue(value);
		FileOutputStream fos=new FileOutputStream(filepath+"/"+fileName);
	    wb.write(fos);
	    fos.close();
			    } catch (IOException e) {
			   e.printStackTrace();
			   System.out.println("File Not Found");			
			}
	}
	
	public static void sendEmailwithoutaattachment(String username, String password,String to,String subject,String message) throws EmailException {
		Email email=new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setSSLOnConnect(true);
		email.setFrom(username);
		email.addTo(to);
		email.setSubject(subject);
		email.setMsg(message);
		email.send();
		System.out.println("Email Sent Successfully");		
	}
	
	public static void sendEmailwithAttachment(String username,String password,String to,String subject,String message,
			String attachmentName,String attachmentDesc) throws EmailException {					
		EmailAttachment emailattach=new EmailAttachment();
		emailattach.setPath(currentDir+"\\test-output\\emailable-report.html");
		emailattach.setDisposition(EmailAttachment.ATTACHMENT);
		emailattach.setDescription(attachmentDesc);
		emailattach.setName(attachmentName);
			MultiPartEmail email=new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setTLS(true);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setSSLOnConnect(true);
		email.setFrom(username);
		email.addTo(to);
		email.setSubject(subject);
		email.setMsg(message);
		email.attach(emailattach);
		email.send();
		System.out.println("Email Sent Succesfully With Attachment");
	}
	
	public static void takeScreenShot(String fileName) throws IOException {
		String currentTime= currentTime();
		TakesScreenshot shot=(TakesScreenshot)driver;
		File src=shot.getScreenshotAs(OutputType.FILE);
		File dest=new File(currentDir+"//screenshort//"+fileName+"_"+currentTime+".png");	
		FileUtils.copyFile(src, dest);
	}
	public static String currentTime() {
		String curentTime=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		return curentTime;
	}
	
	public static List<String> getBrokenLinks() throws MalformedURLException, IOException {
		HttpURLConnection con=null;		
		int responseCode=200;
		List<String>invalidLinks=new ArrayList<String>();
		List<WebElement> links=driver.findElements(By.tagName("a"));
		Iterator<WebElement> itr=links.iterator();
		while (itr.hasNext()) {
			String url=itr.next().getAttribute("href");
			if (url==null||url.isEmpty()) {
				System.out.println("Either not configured for Anchor or empty");
				continue;}			
			con=(HttpURLConnection)(new java.net.URL(url).openConnection());
			con.setRequestMethod("Head");
			con.connect();
			responseCode=con.getResponseCode();
			if (responseCode>=400)
				invalidLinks.add(url);
			else {System.out.println("Valid Link");}
		}
		return invalidLinks;
	}
	
	public static void scrollDownOrUP(int y) {
		js.executeScript("window.scrollBy(0,y)");
	}
	
	public static void scrollHorizontal(int x) {
		js.executeScript("window.scrollBy(x,0)");
	}
	
	public static void highlightElement(String locator, String color) {
		String query=String.format("%1$s.style.background='%2$s'", locator,color);
		js.executeScript(query);
	}
	
	public static void implicitwait(int time) {
		String query=String.format("window.setTimeout(arguments[arguments.length-1],%d);", time);
		js.executeAsyncScript(query);
	}
	
	public static void zoomInOut(int point) {
		String query=String.format("document.body.style.zoom=%d", point);
		js.executeScript(query);
	}
	
	public static void clickbutton(WebElement button) {
		js.executeScript("arguments[0].click();",button );
	}
	
	public static void createalertpopup(String message) {
		js.executeScript("alert('%s')", message);
		/*String query=String.format("alert('%s')", message);
		js.executeScript(query);*/		
	}
	public static void scrollIntoViewElement(WebElement element) {
		String query="element.scrollIntoView(true)";
		js.executeScript(query);
	}
	
	public static void getValue(WebElement element) {
		String query=String.format("function getValue()"+"{var value=%s.innerHTML;}"+"return getValue()", element);
		js.executeScript(query);
	}
	
	public static void refreshPage() {
		js.executeScript("history.go(0)");
		//js.executeScript("location.reload()");
	}
		
	public static void waituntilPageLoad() {/*
		Boolean isPageLoaded() {
			String query="function pageload()"+
							"{var loadingStatus=""}"
		return(Boolean)js.executeScript(query);	
		}
	*/}
}
	
