package tests;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
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

public class CheckTermOfUseOption {

	WebDriver driver=null;
	Read data;
	String TUvalue;
	ExtentTest TpOptTest;
	ExtentReports TpOptExtent;
	String projectpath;
	String CurrentTitle = "Terms Store Online - Buy Terms Online at Best Price in India | Flipkart.com";
	Logger logger= LogManager.getLogger(CheckTermOfUseOption.class);

	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		TUvalue=sheet.getRow(10).getCell(1).getStringCellValue();
		data= new Read();
		TpOptExtent = data.SetUp();
		TpOptTest = TpOptExtent.createTest("TU option Test", "Checking the TU option link is working or not");
		TpOptTest.log(Status.INFO, "Starting step of TU option Test");
		//Get the path of the current project
		projectpath= System.getProperty("user.dir");
		logger.info("Choosing the browswer");
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
	public void OpenSection() throws Exception {
		if(TUvalue.toLowerCase().contains("yes")) {
			
		logger.info("Checking the term of use option");
		CheckLoginOptionsPage Tp = new CheckLoginOptionsPage(driver);
		//driver.manage().timeouts().wait(5, );
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Tp.OpenLogin();
		Tp.OpenTermsOfUseOption();
		Thread.sleep(2000);

		if(CurrentTitle.contains(driver.getTitle()))
		{
			TpOptTest.pass("TU option test is completed");
			logger.info("Checking the term of use option is completed");
		}
		else {
			TpOptTest.fail("TU option test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/TU.jpg"));
			logger.error("Term of use process is not completed because of errors");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		TpOptTest.info("Test completed successfully");
		}
		else
		{
			System.out.println("Please change execution value in excel");
		}
	}


	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close() {
		TpOptExtent.flush();
		driver.quit();
	}

}
