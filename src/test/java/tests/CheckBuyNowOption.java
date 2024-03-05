package tests;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import pages.CheckBuyNowOptionPage;

public class CheckBuyNowOption {
	//Declare variables
	WebDriver driver=null;
	Read data;
	ExtentTest BuyTest;
	ExtentReports BuyExtent;
	String projectpath;
	String BuyNowvalue;
	String CurrentTitle = "Flipkart.com: Secure Payment: Login > Select Shipping Address > Review Order > Place Order";
	//Passing class for logger
	Logger logger= LogManager.getLogger(CheckBuyNowOption.class);
	@BeforeTest(groups = {"sanity"},enabled = true)
	public void setup() throws Exception {
		//Giving dynamic path of project
		projectpath= System.getProperty("user.dir");
		//Reading excel file using sheetname
		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		//Storing column value in variable
		BuyNowvalue=sheet.getRow(5).getCell(1).getStringCellValue();
		data= new Read();
		BuyExtent = data.SetUp();
		//Extent Report cases
		BuyTest = BuyExtent.createTest("BuyNow option Test", "Checking the BuyNow option is working or not");
		BuyTest.log(Status.INFO, "Starting step of BuyNow Test");
		logger.info("Choosing the browser");
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
	@Test(groups = {"sanity"},enabled = true)
	public void CheckBuyNowOpt() throws Exception {
		if(BuyNowvalue.toLowerCase().contains("yes")) {

			logger.info("Starting the process of checking the option buy now");
			CheckBuyNowOptionPage BuyNow =  new CheckBuyNowOptionPage(driver);
			driver.manage().window().maximize();
			//Using implicit wait
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			BuyNow.SearchProduct(data.SearchItem());
			BuyNow.SearchButton();
			BuyNow.CheckWatch();
			//Passing code of window handle change
			Set handles =  driver.getWindowHandles();

			Iterator it = handles.iterator();

			String parentID=(String) it.next();
			String childid = (String)it.next();
			driver.switchTo().window(childid);
			BuyNow.CheckBuyNowOption();
			Thread.sleep(2000);
			//Checking webpage title
			if(CurrentTitle.contains(driver.getTitle()))
			{
				BuyTest.pass("BuyNow option test is completed");
				logger.info("Checking buy now process is completed");
			}
			else {
				BuyTest.fail("BuyNow option test is not completed");
				File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				Files.copy(f, new File(projectpath+"/Screenshots/BuyNowTest.jpg"));
				logger.error("Checking the buy now option is failed because of some error");
			}

			Assert.assertEquals(driver.getTitle(),CurrentTitle);


			BuyTest.info("Test completed successfully");
		}
		else
		{
			logger.info("Please change execution value in excel");
			BuyTest.fail("Test case is not working because of excel execution value");
		}
	}

	@AfterTest(groups = {"sanity"},enabled = true)
	//Closing browser
	public void close() {
		BuyExtent.flush();
		driver.quit();
	}

}
