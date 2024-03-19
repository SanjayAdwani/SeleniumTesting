package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DropdownPriceFilterPage {
	
	WebDriver driver=null;
	
	private By SeacrhPro = By.name("q");
	private By Dropdown_Price = By.xpath("//option[@value=500 and @class='_3AsjWR']");
	
	public DropdownPriceFilterPage(WebDriver driver) {
		this.driver=driver;
	}
	
	 public void SearchProduct(String product) {
		  driver.findElement(SeacrhPro).sendKeys(product);
	  }
	  public void SearchButton() {
		  driver.findElement(SeacrhPro).sendKeys(Keys.RETURN);
	  }
      
	  public WebElement DropdownPrice () {
	WebElement element = driver.findElement(Dropdown_Price);
	return element;
	  }
}
