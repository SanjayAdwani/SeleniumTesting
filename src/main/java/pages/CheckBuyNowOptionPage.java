package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class CheckBuyNowOptionPage {
	
	WebDriver driver=null;
	
	 private By SeacrhPro = By.name("q");
	 private By EnterSearch = By.xpath("//*[@id=\"container\"]/div/div[1]/div[1]/div[2]/div[2]/form/div/button/svg");
     private By Check_Watch = By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[2]/div[2]/div/div[1]/div/div/a[1]");
     private By Check_BuyNow = By.xpath("//button[text()='Buy Now']");
     
     public CheckBuyNowOptionPage(WebDriver driver) {
    	 this.driver=driver;
     }
     
     public void SearchProduct(String product) {
		  driver.findElement(SeacrhPro).sendKeys(product);
	  }
	  public void SearchButton() {
		  driver.findElement(SeacrhPro).sendKeys(Keys.RETURN);
	  }
	  
	  public void CheckWatch() {
		  driver.findElement(Check_Watch).click();
	  }
	  
	  public void CheckBuyNowOption() {
		  driver.findElement(Check_BuyNow).click();
	  }
}
