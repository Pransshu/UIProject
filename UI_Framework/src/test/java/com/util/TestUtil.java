package com.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.mongodb.MapReduceCommand.OutputType;

public class TestUtil {
	
	public static long PAGE_LOAD_TIMEOUT = 25;
	public static long IMPLICIT_WAIT = 25;

	
	public static String getEnvironment() throws MalformedURLException {
		URL url = new URL(prop.getProperty("url"));
		String urlStr = prop.getProperty("url");
		String hostname = url.getHost();
		String env = null;
		if (hostname.equals("")) {
			env = "PROD";
		} else if (hostname.equals("") || urlStr.contains("")) {
			env = "DEMO";
		} else if (hostname.equals("")) {
			env = "QAS";
		} else {
			System.out.println("Please specify the correct environment");
		}
		return env;

	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");

		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));

	}

	
}
