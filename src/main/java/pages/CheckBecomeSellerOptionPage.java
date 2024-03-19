package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CheckBecomeSellerOptionPage {
	
	WebDriver driver=null;
	
	private By Open_SellerOption  = By.xpath("//a/span[contains(text(),'Become a Seller')]");
	private By Open_SellerLogin = By.xpath("//button[contains(text(),'Login')]");
	
	public CheckBecomeSellerOptionPage(WebDriver driver) {
		this.driver=driver;
	}
	
	
	public void OpenSellerOption() {
		driver.findElement(Open_SellerOption).click();
	}
	
	public void OpenLoginSeller() {
		driver.findElement(Open_SellerLogin).click();
	}

}
