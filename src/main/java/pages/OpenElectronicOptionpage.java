package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class OpenElectronicOptionpage {
	
	WebDriver driver=null;
	
	private By Hover_Electronic = By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[2]/div[1]/div/div[1]/div/div/div/div/div[1]/div[2]/div/div/span/span[1]");
    private By Mobile_Accessories = By.xpath("/html/body/div[4]/div[1]/object/a[9]");
	
	public OpenElectronicOptionpage(WebDriver driver) {
		this.driver=driver;
	}
	public void HoverElectric() {
	WebElement ele=	driver.findElement(Hover_Electronic);
	Actions action =  new Actions(driver);
	action.moveToElement(ele).perform();
	
	}
	
	public void MobileAccessories() {
		driver.findElement(Mobile_Accessories).click();
	}
	
}
