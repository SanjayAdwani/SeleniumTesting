package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class AddToCartPage {

	  WebDriver driver=null;
	  
	  private By SeacrhPro = By.name("q");
	  private By EnterSearch = By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/button/svg");
	  private By click_watch = By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[2]/div/div[1]/div/div/a[1]");
	  private By clic_addtocart = By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div[2]/div/ul/li[1]/button");
	  private By firefox_watch = By.xpath("/html/body/div/div/div[3]/div[1]/div[2]/div[2]/div/div[3]/div/div/a[1]");
	  
	  public AddToCartPage(WebDriver driver) {
		  this.driver=driver;
	  }
	  
	  public void SearchProduct(String product) {
		  driver.findElement(SeacrhPro).sendKeys(product);
	  }
	  public void SearchButton() {
		  driver.findElement(SeacrhPro).sendKeys(Keys.RETURN);
	  }
	  
	  public void ClickWatch() {
		  driver.findElement(click_watch).click();
	  }
	
	  public void Click_AddToCart() {
		  driver.findElement(clic_addtocart).click();
	  }
	  
	
	
}
