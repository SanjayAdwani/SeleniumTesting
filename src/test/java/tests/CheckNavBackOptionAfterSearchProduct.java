package tests;

import java.io.File;
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
import pages.CheckNavBackOptionAfterSearchProductPage;
import pages.ProductSearchpage;

public class CheckNavBackOptionAfterSearchProduct {
	//Declare variables
	WebDriver driver=null;
	Read data;
	String NavBackHomeValue;
	ExtentTest HomeOptTest;
	ExtentReports HomeOptExtent;
	String projectpath;
	String CurrentTitle = "Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!";
	//Passing class for logger
	Logger logger= LogManager.getLogger(CheckNavBackOptionAfterSearchProduct.class);

	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		//Giving dynamic path of project
		projectpath= System.getProperty("user.dir");
		//Reading excel file using sheetname
		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		//Storing column value in variable
		NavBackHomeValue=sheet.getRow(9).getCell(1).getStringCellValue();
		data= new Read();
		HomeOptExtent = data.SetUp();
		//Extent Report cases
		HomeOptTest = HomeOptExtent.createTest("Home option Test", "Checking the Home option button is working or not");
		HomeOptTest.log(Status.INFO, "Starting step of Home option Test");
		String headless = data.headlessvalue();
		logger.info("Choosing the browser");
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
	@Test(groups = {"smoke","regression"},enabled = true)
	public void SearchProduct() throws Exception {
		if(NavBackHomeValue.toLowerCase().contains("yes")) {

			CheckNavBackOptionAfterSearchProductPage backOption= new CheckNavBackOptionAfterSearchProductPage(driver);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			backOption.SearchProduct(data.SearchItem());
			backOption.SearchButton();
			backOption.BackHome();
			Thread.sleep(2000);
			//Checking webpage title
			if(CurrentTitle.contains(driver.getTitle()))
			{
				HomeOptTest.pass("Home option test is completed");
				logger.info("Nav  Back Home test is completed ");
			}
			else {
				HomeOptTest.fail("Home option test is not completed");
				logger.error("Nav Back Home test is not completed because of some error");
				File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				Files.copy(f, new File(projectpath+"/Screenshots/HomeTest.jpg"));
			}

			Assert.assertEquals(driver.getTitle(),CurrentTitle);


			HomeOptTest.info("Test completed successfully");
		}
		else
		{
			logger.info("Please change execution value in excel");
			HomeOptTest.fail("Test case is not working because of excel execution value");
		}


	}
	//Closing browser
	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close()
	{
		HomeOptExtent.flush();
		driver.quit();
	}

}
