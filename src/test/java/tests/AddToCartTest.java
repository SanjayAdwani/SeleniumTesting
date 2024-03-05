package tests;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import Config.Read;
import pages.AddToCartPage;

public class AddToCartTest {
	//Declare variables
	WebDriver driver=null;
	Read data;
	String execution;
	AddToCartPage product;
	ExtentTest extenttest;
	ExtentReports extent;
	String projectpath;
	String Currenttitle="Shopping Cart | Flipkart.com";
	String Currenturl = "https://www.flipkart.com/";
	//Passing class for logger
	Logger logger= LogManager.getLogger(AddToCartTest.class);
	@BeforeTest(groups = {"sanity"})
	public void setup() throws Exception {
		//Giving dynamic path of project
		projectpath= System.getProperty("user.dir");
		//Reading excel file using sheetname
		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
		XSSFSheet Sheet = Workbook.getSheet("TestCases");
		//Storing column value in variable
		execution= Sheet.getRow(1).getCell(1).getStringCellValue();
		data= new Read();
		extent = data.SetUp();
		//Extent Report cases
		extenttest = extent.createTest("Add To Cart Test", "Check the Add to cart functionality");
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
	}
	//Performing test cases
	@Test(priority = 1,enabled = true,groups = {"sanity"})
	public void AddToCart() throws Exception {
		if(execution.toLowerCase().contains("yes")) 
		{	
			logger.info("Starting Add to cart process");
			product = new AddToCartPage(driver);
			driver.manage().window().maximize();
			//Using implicit wait
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			product.SearchProduct(data.SearchItem());
			product.SearchButton();
			product.ClickWatch();
		}
		else
		{
			logger.info("Please change execution value in excel");
			extenttest.fail("Test case is not working because of excel execution value");
		}
	}
	@Test(priority = 2,enabled = true,groups = {"sanity"})
	public void switchscreen() throws Exception {

		//Passing code of window handle change
		Set handles =  driver.getWindowHandles();

		Iterator it = handles.iterator();

		String parentID=(String) it.next();
		String childid = (String)it.next();
		driver.switchTo().window(childid);
		product.Click_AddToCart();
		Thread.sleep(2000);

		//Checking webpage title
		if(Currenttitle.contains(driver.getTitle()) && data.link().contains(Currenturl))
		{
			extenttest.pass("Add to cart test is completed");
			logger.info("Add to cart process is  completed");
		}
		else {
			extenttest.fail("Add to cart test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/AddToCart.jpg"));
			logger.error("Failed the process because of error");
		}

		//Using assertion
		Assert.assertEquals(Currenturl, data.link());

		extenttest.info("Test completed successfully");

	}
	//Closing browser
	@AfterTest(groups = {"sanity"},enabled = true)
	public void close() {
		extent.flush();
		driver.close();
		driver.quit();
	}
}
