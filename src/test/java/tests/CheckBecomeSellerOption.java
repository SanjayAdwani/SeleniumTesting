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
import pages.CheckBecomeSellerOptionPage;

public class CheckBecomeSellerOption {
	//Declare variables
	WebDriver driver=null;
	Read data;
	String SellerValue;
	String projectpath;
	String CurrentTitle = "Sell Online - Start Selling Online on Flipkart Seller Hub";
	ExtentTest SellerTest;
	ExtentReports SellerExtent;
	//Passing class for logger
	Logger logger= LogManager.getLogger(CheckBecomeSellerOption.class);
	@BeforeTest(groups = {"smoke"},enabled = true)
	public void setup() throws Exception {
		//Giving dynamic path of project
		projectpath= System.getProperty("user.dir");
		//Reading excel file using sheetname
		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		//Storing column value in variable
		SellerValue=sheet.getRow(3).getCell(1).getStringCellValue();
		data= new Read();
		SellerExtent = data.SetUp();
		//Extent Report cases
		SellerTest = SellerExtent.createTest("Seller option Test", "Checking the Seller option is working or not");
		SellerTest.log(Status.INFO, "Starting step of Seller Test");
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
	@Test(groups = {"smoke"},enabled = true)
	public void Checkbecomeselleroptions() throws Exception {
		if(SellerValue.toLowerCase().contains("yes")) {

			logger.info("Starting the process of checking the seller option");
			driver.manage().window().maximize();
			//Using implicit wait
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			CheckBecomeSellerOptionPage seller= new CheckBecomeSellerOptionPage(driver);
			seller.OpenSellerOption();
			seller.OpenLoginSeller();
			Thread.sleep(2000);
			//Checking webpage title
			if(CurrentTitle.contains(driver.getTitle()))
			{
				SellerTest.pass("Seller option test is completed");
				logger.info("Checking seller option button is completed");
			}
			else {
				SellerTest.fail("Seller option test is not completed");
				File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				Files.copy(f, new File(projectpath+"/Screenshots/SellerOptionTest.jpg"));
				logger.error("Failed the seller option process because of some error");
			}
			//Using assertion
			Assert.assertEquals(driver.getTitle(),CurrentTitle);


			SellerTest.info("Test completed successfully");
		}
		else
		{
			logger.info("Please change execution value in excel");
			SellerTest.fail("Test case is not working because of excel execution value");
		}

	}
	//Closing browser
	@AfterTest(groups = {"smoke"},enabled = true)
	public void close() {
		SellerExtent.flush();
		driver.quit();
	}

}
