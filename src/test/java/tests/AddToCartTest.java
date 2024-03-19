package tests;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

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
import pages.AddToCartPage;

public class AddToCartTest {
	//Declare variables
	WebDriver driver=null;
	Read data;
	String execution;
	AddToCartPage product;
	ExtentTest extenttest;
	ExtentReports extent;
	String projectpath;
	String Currenttitle="Shopping Cart | Flipkart.com";
	String Currenturl = "https://www.flipkart.com/";
	//Passing class for logger
	Logger logger= LogManager.getLogger(AddToCartTest.class);
	@BeforeTest(groups = {"sanity"})
    public void RunSetUp() throws Exception {
		data= new Read();
		extent = data.SetUp();
		execution=data.Excelconfig(1, 1);
      driver= data.RunBrowser(logger,execution);
      extenttest = extent.createTest("Add To Cart Test", "Check the Add to cart functionality");
		extenttest.log(Status.INFO, "Starting step of Add to cart test");
	}
	//Performing test cases
	@Test(priority = 1,enabled = true,groups = {"sanity"})
	public void AddToCart() throws Exception {
		if(execution.toLowerCase().contains("yes")) 
		{	
			logger.info("Starting Add to cart process");
			product = new AddToCartPage(driver);
			driver.manage().window().maximize();
			//Using implicit wait
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			product.SearchProduct(data.SearchItem());
			product.SearchButton();
			product.ClickWatch();
		}
		else
		{
			logger.info("Please change execution value in excel");
			extenttest.fail("Test case is not working because of excel execution value");
		}
	}
	@Test(priority = 2,enabled = true,groups = {"sanity"})
	public void switchscreen() throws Exception {

		//Passing code of window handle change
		Set handles =  driver.getWindowHandles();

		Iterator it = handles.iterator();

		String parentID=(String) it.next();
		String childid = (String)it.next();
		driver.switchTo().window(childid);
		product.Click_AddToCart();
		Thread.sleep(2000);

		//Checking webpage title
		if(Currenttitle.contains(driver.getTitle()) && data.link().contains(Currenturl))
		{
			extenttest.pass("Add to cart test is completed");
			logger.info("Add to cart process is  completed");
		}
		else {
			extenttest.fail("Add to cart test is not completed");
			File f= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File(projectpath+"/Screenshots/AddToCart.jpg"));
			logger.error("Failed the process because of error");
		}

		//Using assertion
		Assert.assertEquals(Currenturl, data.link());

		extenttest.info("Test completed successfully");

	}
	//Closing browser
	@AfterTest(groups = {"sanity"},enabled = true)
	public void close() {
		extent.flush();
		driver.close();
		driver.quit();
	}
}
