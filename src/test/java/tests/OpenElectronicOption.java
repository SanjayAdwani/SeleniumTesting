package tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import Config.Read;
import pages.OpenElectronicOptionpage;

public class OpenElectronicOption {


	WebDriver driver=null;
	Read data;
	String ElecValue;
	ExtentTest ElecOptTest;
	ExtentReports ElecOptExtent;
	String projectpath;
	String CurrentTitle = "Electronics Best Offers Store Online - Buy Electronics Best Offers Online at Best Price in India | Flipkart.com";
	Logger logger= LogManager.getLogger(OpenElectronicOption.class);

	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		ElecValue=sheet.getRow(13).getCell(1).getStringCellValue();
		data= new Read();
		ElecOptExtent = data.SetUp();
		ElecOptTest = ElecOptExtent.createTest("Open Electronic Option  Test", "Checking the Electronic option from navigiation");
		ElecOptTest.log(Status.INFO, "Starting step of Open Electronic Scenario");
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
	public void OpenElectronicOpt() throws Exception {
		if(ElecValue.toLowerCase().contains("yes")) {
			
		logger.info("Checking the electronic option form nav");
		OpenElectronicOptionpage ElecOption =  new OpenElectronicOptionpage(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		ElecOption.HoverElectric();
		ElecOption.MobileAccessories();

		if(CurrentTitle.contains(driver.getTitle()))
		{
			ElecOptTest.pass("Open Electronic option  Test is completed");
			logger.info("Checking the electronic option form nav is completed");
		}
		else {
			ElecOptTest.fail("Open Electronic option  Test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/ElecOption.jpg"));
			logger.error("Checking the electronic option is not completed");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		ElecOptTest.info("Test completed successfully");
		}
		else
		{
			System.out.println("Please change execution value in excel");
		}
	}

	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close() {
		ElecOptExtent.flush();
		driver.quit();
	}
}