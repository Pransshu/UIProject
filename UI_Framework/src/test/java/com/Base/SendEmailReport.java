package com.Base;

import java.io.File;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


public class SendEmailReport {
	public static int cntPass;
	public static int cntFail;
	public static int cntSkip;
	public static String executionTime;
	public static String env;
	public static String oldFileName;
	public static String newFileName;
	public static String version;

	public static void sendEmail() throws EmailException, MalformedURLException {
		env = TestUtil.getEnvironment();
		
		cntPass = MyTestReporter.cntPass;
		cntFail = MyTestReporter.cntFail;
		cntSkip = MyTestReporter.cntSkip;
		executionTime = MyTestReporter.executionTime;

		System.out.println("Old Report File path : " + reportName);
		File f = new File(reportName);
		oldFileName = f.getName();
		System.out.println("Old File Name: " + oldFileName);
		System.out.println("Old File Path: " + f.getAbsolutePath());
		System.out.println(f.getParent());

		newFileName = env + "_" + oldFileName;
		System.out.println("New File Name: " + newFileName);
		String newFilePath = f.getParent() + "/" + newFileName;
		File f1 = new File(newFilePath);
		if (f.renameTo(f1)) {
			System.out.println("File rename success");
			;
		} else {
			System.out.println("File rename failed");
		}
		System.out.println("New File Path: " + f1.getAbsolutePath());

		Date currentDate = new Date();
		String dateToStr = DateFormat.getDateInstance().format(currentDate);
		System.out.println("Sending Email.....");
		EmailAttachment attachment = new EmailAttachment();
		// attachment.setPath(REPORT_PATH);
		attachment.setPath(newFilePath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		// attachment.setDescription("PPS-UI-Automation-Report.html");
		attachment.setDescription(f1.getName());
		// attachment.setName("PPS_UI_AutomationReport.html");
		attachment.setName(f1.getName());

		// Create the email message
		// MultiPartEmail email = new MultiPartEmail();
		HtmlEmail email = new HtmlEmail();
		email.setHostName("");
		email.setSmtpPort(25);
		email.addTo("Pransshu@gmail.com", "Pranshu S Shukla");
	
		email.setFrom("donotreply@sap.com", "DoNotReply");
		email.setSubject("UI test : " + env + " : " + dateToStr +" : UI Test Automation");

		email.setMsg("<font face='verdana' size='3'>"+"Hi All," + System.lineSeparator() + System.lineSeparator()
				+ "Here is the Live Link - Admin Portal UI Test Automation Execution Report on the " + env + " environment - "
				+ prop.getProperty("url") + System.lineSeparator() + System.lineSeparator()
				+ "Below is the test execution summary: " + System.lineSeparator()
				+ "-------------------------------------------" 
				+ "</font>"
				+ "<font face='verdana' size='3' color='green'>"
				+ System.lineSeparator() + "Number of Test Cases Passed : "+ "\t"+cntPass + System.lineSeparator() 
				+ "</font>"
				+ "<font face='verdana' size='3' color='red'>"
				+ "Number of Test Cases Failed : " + "\t"+cntFail + System.lineSeparator()
				+ "</font>"
				+ "<font face='verdana' size='3' color='orange'>"
				+ "Number of Test Cases Skipped : " + "\t"+cntSkip + System.lineSeparator()
				+ "</font>"
				+"<font face='verdana' size='3'>"
				+ "Total execution time for tests : " + "\t"+executionTime + System.lineSeparator()
				+ System.lineSeparator() + System.lineSeparator()
				+ "This email was sent automatically by Live Link UI Test Automation Framework. Please do not reply."
				+ System.lineSeparator() + System.lineSeparator() + "Thanks," + System.lineSeparator()
				+ System.lineSeparator() + "Automation Team"
				+ "</font>");

		// add the attachment
		email.attach(attachment);

		// if tests are failing then send the email otherwise don't send
		email.send();
		System.out.println("Email Sent Successfully....");
		/*
		 * if (cntFail > 0) { email.send();
		 * System.out.println("Email Sent Successfully...."); } else {
		 * System.out.println("All the tests are passing, no need to send email."); }
		 */
	}

	public static String getLatestGeneratedFilePath(String dirPath) {
		File choice = null;
		try {
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				return null;
			}
			// Thread.sleep(60000);
			long lastMod = Long.MIN_VALUE;

			for (File file : files) {
				if (file.lastModified() > lastMod) {
					choice = file;
					lastMod = file.lastModified();
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while getting the last download file");
			e.printStackTrace();
		}
		// test.log(LogStatus.INFO, "The latest generated file is " +
		// choice.getPath());
		System.out.println(choice.getAbsolutePath());
		return choice.getAbsolutePath();
	}
}
