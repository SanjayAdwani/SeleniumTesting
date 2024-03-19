package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class CheckDiscountOptionPage {

WebDriver driver=null;
	
	private By SeacrhPro = By.name("q");
	private By Discount_Price = By.xpath("//div[text()='Discount']");
	private By Tickbox90 = By.xpath("//div[text()='90% or more']");
	//private By tick = By.linkText("90% or more");
	
	public CheckDiscountOptionPage(WebDriver driver) {
		this.driver=driver;
	}
	
	 public void SearchProduct(String product) {
		  driver.findElement(SeacrhPro).sendKeys(product);
	  }
	  public void SearchButton() {
		  driver.findElement(SeacrhPro).sendKeys(Keys.RETURN);
	  }
	  public void discoutopt() {
		  driver.findElement(Discount_Price).click();
	  }
	  public void tick90() {
		  driver.findElement(Tickbox90).click();
	  }
}

