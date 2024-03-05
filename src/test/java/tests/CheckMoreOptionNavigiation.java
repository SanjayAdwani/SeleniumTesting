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
import pages.CheckLoginOptionsPage;
import pages.CheckMoreOptionNavigiationPage;

public class CheckMoreOptionNavigiation {
	//Declare variables
	WebDriver driver=null;
	Read data;
	ExtentTest MoreOptTest;
	ExtentReports MoreOptExtent;
	String projectpath;
	String MoreOptValue;
	String CurrentTitle = "Push Store Online - Buy Push Online at Best Price in India | Flipkart.com";
	//Passing class for logger
	Logger logger= LogManager.getLogger(CheckMoreOptionNavigiation.class);
	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		//Giving dynamic path of project
		projectpath= System.getProperty("user.dir");
		//Reading excel file using sheetname
		XSSFWorkbook Workbook = new XSSFWorkbook(projectpath+"/Excel/TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		//Storing column value in variable
		MoreOptValue=sheet.getRow(8).getCell(1).getStringCellValue();
		data= new Read();
		MoreOptExtent = data.SetUp();
		//Extent Report cases
		MoreOptTest = MoreOptExtent.createTest("Notification option Test", "Checking the Notification prefernce option is working or not");
		MoreOptTest.log(Status.INFO, "Starting step of Notification option Test");
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
	@Test(groups = {"smoke","regression"},enabled = true)
	public void OpenSection() throws Exception {
		if(MoreOptValue.toLowerCase().contains("yes")) {

			logger.info("Checking the more option and click the notification prefrences");
			CheckMoreOptionNavigiationPage more = new CheckMoreOptionNavigiationPage(driver);
			driver.manage().window().maximize();
			//Using implicit wait
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			more.MoreOption();
			more.NotiFicationPrefence();
			Thread.sleep(2000);
			//Checking webpage title
			if(CurrentTitle.contains(driver.getTitle()))
			{
				MoreOptTest.pass("Notification option test is completed");
				logger.info("More option process is completed");

			}
			else {
				MoreOptTest.fail("Notification option test is not completed");
				File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				Files.copy(f, new File(projectpath+"/Screenshots/NotificationTest.jpg"));
				logger.error("More option process is not completed because of some error");
			}

			Assert.assertEquals(driver.getTitle(),CurrentTitle);


			MoreOptTest.info("Test completed successfully");
		}
		else {
			logger.info("Please change execution value in excel");
			MoreOptTest.fail("Test case is not working because of excel execution value");
		}
	}

	//Closing browser
	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close() {
		MoreOptExtent.flush();
		driver.quit();
	}

}
