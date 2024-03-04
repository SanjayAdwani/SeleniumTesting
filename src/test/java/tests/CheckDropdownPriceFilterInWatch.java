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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import Config.Read;
import pages.DropdownPriceFilterPage;

public class CheckDropdownPriceFilterInWatch {

	WebDriver driver=null;
	Read data;
	String dropdownvalue;
	DropdownPriceFilterPage FilterPrice;
	ExtentTest DropdownTest;
	ExtentReports DropdownExtent;
	String projectpath;
	String CurrentTitle = "Watch For Men- Buy Products Online at Best Price in India - All Categories | Flipkart.com";
	Logger logger= LogManager.getLogger(CheckDropdownPriceFilterInWatch.class);

	@BeforeTest(groups = {"smoke","regression"},enabled = true)
	public void setup() throws Exception {
		XSSFWorkbook Workbook = new XSSFWorkbook("C:\\Users\\sanjayadwani\\eclipse-workspace\\SeleniumTesting\\Excel\\TestCasesExcel.xlsx");
		XSSFSheet sheet = Workbook.getSheet("TestCases");
		dropdownvalue=sheet.getRow(6).getCell(1).getStringCellValue();
		data= new Read();
		DropdownExtent = data.SetUp();
		DropdownTest = DropdownExtent.createTest("Dropdown option Test", "Checking the price dropdown filter for product option is working or not");
		DropdownTest.log(Status.INFO, "Starting step of Dropdown Test");
		//Get the path of the current project
		projectpath= System.getProperty("user.dir");
		logger.info("Choosing thr browser");
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

	@Test(groups = {"smoke","regression"},enabled = true,priority = 1) 
	public void searchproduct() throws Exception {
		if(dropdownvalue.toLowerCase().contains("yes")) {
			
		logger.info("Checking the price dropdown filter for watch");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		FilterPrice = new DropdownPriceFilterPage(driver);
		FilterPrice.SearchProduct(data.SearchItem());
		FilterPrice.SearchButton();  
		}
		else
		{
			System.out.println("Please change the excel execution value");
		}
	}
	@Test(groups = {"smoke","regression"},enabled = true,priority = 2)
	public void FilterDropdown() throws Exception {
		WebElement dropdown= FilterPrice.DropdownPrice();
		Select obj = new Select(dropdown);
		obj.selectByIndex(0);
		Thread.sleep(2000); 

		if(CurrentTitle.contains(driver.getTitle()))
		{
			DropdownTest.pass("Dropdown price filter option test is completed");
			logger.info("Dropdown price filter option is completed");
		}
		else {
			DropdownTest.fail("Dropdown price filter option test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/DropdownTest.jpg"));
			logger.error("Failed the dropdown price process because of some error");
		}

		Assert.assertEquals(driver.getTitle(),CurrentTitle);


		DropdownTest.info("Test completed successfully");

	}
	@AfterTest(groups = {"smoke","regression"},enabled = true)
	public void close() {
		DropdownExtent.flush();
		driver.quit();
	}
}
