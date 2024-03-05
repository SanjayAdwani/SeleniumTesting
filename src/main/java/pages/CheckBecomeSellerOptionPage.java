package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CheckBecomeSellerOptionPage {
	
	WebDriver driver=null;
	
	private By Open_SellerOption  = By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[1]/div[1]/header/div[2]/div[4]/div/a[1]");
	private By Open_SellerLogin = By.xpath("//*[@id=\"app\"]/div/div[2]/div/div/div[2]/button[1]");
	
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
