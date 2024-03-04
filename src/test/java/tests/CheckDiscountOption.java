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
import pages.CheckDiscountOptionPage;

public class CheckDiscountOption {

	WebDriver driver=null;
	Read data;
	String discountvalue;
	ExtentTest DiscountTest;
	ExtentReports DiscountExtent;
	String projectpath;
	String CurrentTitle = "Watch For Men- Buy Products Online at Best Price in India - All Categories | Flipkart.com";
	CheckDiscountOptionPage discount;
	Logger logger= LogManager.getLogger(CheckDiscountOption.class);
	@BeforeTest(groups = {"regression"},enabled = true)
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		discountvalue=sheet.getRow(4).getCell(1).getStringCellValue();
		data= new Read();
		DiscountExtent = data.SetUp();
		DiscountTest = DiscountExtent.createTest("Discount option Test", "Checking the Discount filter for product option is working or not");
		DiscountTest.log(Status.INFO, "Starting step of Discount Test");
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
	@Test(groups = {"regression"},enabled = true,priority = 1)
	public void Checkdiscount() throws Exception {
		if(discountvalue.toLowerCase().contains("yes")) {
			
		logger.info("Checking the discount option filter for watch");
		discount =  new CheckDiscountOptionPage(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		discount.SearchProduct(data.SearchItem());
		discount.SearchButton();
		}
		else{
		System.out.println("Please change excel execution value");
		}

	}
	@Test(groups = {"regression"},enabled = true,priority = 2)
	public void discount() throws Exception {
		discount.discoutopt();
		discount.tick90();


		if(CurrentTitle.contains(driver.getTitle()))
		{
			DiscountTest.pass("Discount option test is completed");
			logger.info("Discount option process is completed");
		}
		else {
			DiscountTest.fail("Discount option test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/DiscountTest.jpg"));
			logger.error("Discount option process is failed because of some error");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		DiscountTest.info("Test completed successfully");

	}

	@AfterTest(groups = {"regression"},enabled = true)
	public void close() {
		DiscountExtent.flush();
	
		driver.quit();
	}

}
