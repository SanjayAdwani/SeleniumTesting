package Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pages.AddToCartPage;

public class Read {
	String link;
	String SearchProduct;
	FileReader reader;
	Properties p;
	WebDriver driver=null;
	String projectpath;
	Read data;
	String execution;
	AddToCartPage product;
	ExtentTest extenttest;
	ExtentReports extentconfig;

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
	public String Excelconfig(int rownum,int colnum) throws Exception {
		projectpath= System.getProperty("user.dir");
		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
		XSSFSheet Sheet = Workbook.getSheet("TestCases");
		//Storing column value in variable
		execution= Sheet.getRow(rownum).getCell(colnum).getStringCellValue();
		return execution;
	}
	public WebDriver RunBrowser(Logger logger,String Exe) throws Exception {
//		extentconfig=SetUp();
		//Giving dynamic path of project
		projectpath= System.getProperty("user.dir");
		//Reading excel file using sheetname
//		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
//		XSSFSheet Sheet = Workbook.getSheet("TestCases");
//		//Storing column value in variable
//		Exe= Sheet.getRow(1).getCell(1).getStringCellValue();
		data= new Read();
		extentconfig = data.SetUp();
		//Extent Report cases
		extenttest = extentconfig.createTest("Add To Cart Test", "Check the Add to cart functionality");
		extenttest.log(Status.INFO, "Starting step of Add to cart test");
		logger.info("Choosing browser");
		String headless = data.headlessvalue();
		//Comparing browsers
		if(data.browser().toLowerCase().contains("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", projectpath+"/Drivers/chromedriver.exe");
			//Checking headless mode
			if(headless.toLowerCase().contains("true")) {
				ChromeOptions opt= new ChromeOptions();
				opt.addArguments("--headless");
				driver= new ChromeDriver(opt);
			}
			else {
				driver= new ChromeDriver();
			}
		}
		else if(data.browser().toLowerCase().contains("edge"))
		{
			//Passing the path of edge driver
			System.setProperty("webdriver.edge.driver", projectpath+"/Drivers/msedgedriver.exe");
			if(headless.toLowerCase().contains("true")) {
				EdgeOptions edgeopt = new EdgeOptions();
				edgeopt.addArguments("--headless");
				driver= new EdgeDriver(edgeopt);
			}
			else
			{
				driver= new EdgeDriver();
			}
		}
		else {
			//Passing the path of firefox driver
			System.setProperty("webdriver.gecko.driver", projectpath+"/Drivers/geckodriver.exe");
			if(headless.toLowerCase().contains("true")) {
				FirefoxOptions firefoxopt = new FirefoxOptions();
				firefoxopt.addArguments("--headless");
				driver= new FirefoxDriver(firefoxopt);
			}
			else
			{
				driver= new FirefoxDriver();
			}
		}
		driver.navigate().to(data.link());
		return driver;
	}

}
