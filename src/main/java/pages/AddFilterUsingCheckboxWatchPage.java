package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class AddFilterUsingCheckboxWatchPage {

	WebDriver driver= null;
	
	private By SeacrhPro = By.name("q");
	private By Apply_Filter = By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div/div[1]/div/section[4]/div[2]/div[1]/div[2]/div/label/div[1]");
	
    public AddFilterUsingCheckboxWatchPage(WebDriver driver) {
    	this.driver = driver;
    }
    
    public void SearchProduct(String product) {
		  driver.findElement(SeacrhPro).sendKeys(product);
	  }
	  public void SearchButton() {
		  driver.findElement(SeacrhPro).sendKeys(Keys.RETURN);
	  }
	  public void Tick_Checkbox() {
		  driver.findElement(Apply_Filter).click();
	  }
}
