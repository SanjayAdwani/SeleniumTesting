package tests;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import pages.AddFilterUsingCheckboxWatchPage;

public class CheckFilterUsingCheckboxWatch {

	WebDriver driver=null;
	Read data;
	String Checkboxvalue;
	ExtentTest CheckboxTest;
	ExtentReports CheckboxExtent;
	String projectpath;
	String CurrentTitle = "Watch For Men- Buy Products Online at Best Price in India - All Categories | Flipkart.com";
	Logger logger= LogManager.getLogger(CheckFilterUsingCheckboxWatch.class);

	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		Checkboxvalue=sheet.getRow(7).getCell(1).getStringCellValue();
		data= new Read();
		CheckboxExtent = data.SetUp();
		CheckboxTest = CheckboxExtent.createTest("Checkbox option Test", "Checking the differnt watch  filter checkbox for product option is working or not");
		CheckboxTest.log(Status.INFO, "Starting step of Checkbox Test");
		//Get the path of the current project
		projectpath= System.getProperty("user.dir");
		logger.info("Choosing the browser");
		String headless = data.headlessvalue();
		if(data.browser().toLowerCase().contains("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", projectpath+"/Drivers/chromedriver.exe");
			//Open the website
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
	@Test(groups = {"smoke","regression"},enabled = true)
	public void Addfilterforwatch() throws Exception {
		if(Checkboxvalue.toLowerCase().contains("yes")) {
			
		logger.info("Changing the brand of watches");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		AddFilterUsingCheckboxWatchPage filter = new AddFilterUsingCheckboxWatchPage(driver);
		filter.SearchProduct(data.SearchItem());
		filter.SearchButton();
		filter.Tick_Checkbox();
		Thread.sleep(2000);

		if(CurrentTitle.contains(driver.getTitle()))
		{
			CheckboxTest.pass("Checkbox option for watch test is completed");
			logger.info("Chaning the brand watch process is completed");
		}
		else {
			CheckboxTest.fail("Checkbox option for watch test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/CheckboxTest.jpg"));
			logger.error("Changing the brand of watch is failed because of some error");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		CheckboxTest.info("Test completed successfully");
		}
		else {
			System.out.println("Please change execution value in excel");
		}

	}

	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close() {
		CheckboxExtent.flush();
		driver.quit();

	}


}
