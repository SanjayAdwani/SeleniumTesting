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
import pages.CheckAppliancesOptionPage;

public class CheckAppliciacnesOption {
	WebDriver driver=null;
	String projectpath;
	Read data;
	String Applicianes;
	ExtentTest AppTest;
	ExtentReports AppExtent;
	String CurrentTitle="Tvs And Appliances New Clp Store Online - Buy Tvs And Appliances New Clp Online at Best Price in India | Flipkart.com";
	Logger logger= LogManager.getLogger(CheckAppliciacnesOption.class);
	@BeforeTest(groups = {"smoke"})
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		Applicianes= sheet.getRow(2).getCell(1).getStringCellValue();
		data= new Read();
		AppExtent = data.SetUp();
		AppTest = AppExtent.createTest("Appliciances option Test", "Checking the applicianes option is working or not");
		AppTest.log(Status.INFO, "Starting step of Appliciances Test");
		//Get the path of the current project
		projectpath= System.getProperty("user.dir");
		logger.info("Choosing browser");
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
	@Test(enabled = true,groups = {"smoke"})
	public void Application() throws Exception {
		if(Applicianes.toLowerCase().contains("yes")) {
			
		logger.info("Starting the process of checking applicanes options");
		CheckAppliancesOptionPage offer = new CheckAppliancesOptionPage(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		offer.ApplicationOption();
		Thread.sleep(2000);	

		if(CurrentTitle.contains(driver.getTitle()))
		{
			AppTest.pass("Appliciances option test is completed");
			logger.info("Checking applicanes option process is completed");
		}
		else {
			AppTest.fail("Appliciances option test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/AppliciacnesTest.jpg"));
			logger.error("Checking appliances process is failed because of some errors");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		AppTest.info("Test completed successfully");
		}
		else
		{
			System.out.println("Please change execution value in excel");
		}
	}

	@AfterTest(groups = {"smoke"})
	public void close() {
		AppExtent.flush();
		driver.quit();
	}
}