package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ProductSearchpage {
  WebDriver driver=null;
  
  private By SeacrhPro = By.name("q");
  private By EnterSearch = By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/button/svg");
  
  public ProductSearchpage(WebDriver driver) {
	  this.driver=driver;
  }
  
  public void SearchProduct(String product) {
	  driver.findElement(SeacrhPro).sendKeys(product);
  }
  public void SearchButton() {
	  driver.findElement(SeacrhPro).sendKeys(Keys.RETURN);
  }
	
}
