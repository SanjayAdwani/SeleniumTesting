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
import pages.LoginPage;


public class ValidLoginCreditional {
	WebDriver driver=null;
	Read data;
	ExtentTest ValidLoginTest;
	ExtentReports ValidLoginExtent;
	String projectpath;
	String ValidloginValue;
	String CurrentTitle = "Online Shopping India | Buy Mobiles, Electronics, Appliances, Clothing and More Online at Flipkart.com";
	Logger logger= LogManager.getLogger(ValidLoginCreditional.class);
	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		ValidloginValue=sheet.getRow(15).getCell(1).getStringCellValue();
		data= new Read();
		ValidLoginExtent = data.SetUp();
		ValidLoginTest = ValidLoginExtent.createTest("Valid  Creditionals Login  Test", "Checking the login using valid creditionals");
		ValidLoginTest.log(Status.INFO, "Starting step of Valid Credtional login Scenario");
		//Get the path of the current project
		projectpath= System.getProperty("user.dir");
		logger.info("Choosing the broswer");
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
	public void login() throws Exception {
		if(ValidloginValue.toLowerCase().contains("yes")) {
			
		logger.info("Staring the proces of login using valid credtional");
		LoginPage login= new LoginPage(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		login.loginOpen();
		login.AddUsername(data.Validid());
		login.OtpButton();
		Thread.sleep(2000);

		if(CurrentTitle.contains(driver.getTitle()))
		{
			ValidLoginTest.pass("Valid creditional login  Test is completed");
			logger.info("Valid credtional login is completed");
		}
		else {
			ValidLoginTest.fail("Valid Credtional login Test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/ValidLogin.jpg"));
			logger.error("Valid creditional login is not completed because of some error");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		ValidLoginTest.info("Test completed successfully");
		}
		else
		{
			System.out.println("Please change execution value in excel");
		}
	}
	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close() {
		ValidLoginExtent.flush();
		driver.quit();
	}
}
