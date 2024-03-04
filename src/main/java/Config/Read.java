package Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Read {
	String link;
	String SearchProduct;
	FileReader reader;
	Properties p;

	//This method is created for extent report
	public ExtentReports SetUp() throws Exception {
		//Declare this section for extent report
		ExtentHtmlReporter	htmlReporter = new ExtentHtmlReporter("extent.html");
		ExtentReports	extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		return extent;
	}
	//This method is created for read the properties file
	public void read() throws Exception {

		reader=new FileReader("config.properties"); 

		p= new Properties();
		p.load(reader);
	}
	//This method is used for passing url
	public String link() throws Exception {
		read();
		String url=p.getProperty("link");
		return url;
	}
	//This method is used for passing key
	public String SearchItem() throws Exception {
		read();
		String	key=p.getProperty("searchproduct");
		return key;
	}
	//This method is used for passing ID
	public String InvalidID() throws Exception {
		read();
		String InvalidID=p.getProperty("InvalidID");
		return InvalidID;
	}
	//This method is used for passing Valid ID
	public String Validid() throws Exception {
		read();
		String validid=p.getProperty("validid");
		return validid;
	}
	//This method is used for passing name of card for Post method
	public String browser() throws Exception {
		read();
		String Browser=p.getProperty("browser");
		return Browser;
	}
	//This method is used for passing updated name of card for put method
	public String headlessvalue() throws Exception {
		read();
		String headless=p.getProperty("head");
		return headless;
	}

}
