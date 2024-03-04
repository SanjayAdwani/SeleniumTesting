package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OpenAddToCartPage {
	
	WebDriver driver = null;
	
	private By OpenCart= By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[1]/div[1]/header/div[2]/div[3]/div/a[2]");
	private By checktext = By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/div/div/div/div/div[1]");
	
	
	public OpenAddToCartPage(WebDriver driver) {
		this.driver=driver;
	}
	
	public void OpenCart() {
		driver.findElement(OpenCart).click();
	}
	
	public void checktext() {
		driver.findElement(checktext).isDisplayed();
	}

}
