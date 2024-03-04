package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CheckAppliancesOptionPage {
	
	WebDriver driver=null;
	
	//private By login_Button=  By.linkText("Login");
	private By Appliciances_Option = By.linkText("Appliances");
	
	public CheckAppliancesOptionPage(WebDriver driver) {
		this.driver=driver;
	}
	
	public void ApplicationOption() {
		driver.findElement(Appliciances_Option).sendKeys(Keys.RETURN);
	}

}
