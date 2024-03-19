package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CheckMoreOptionNavigiationPage {

	WebDriver driver=null;
	
	private By More_Option = By.xpath("//img[@alt='Dropdown with more help links']");
	private By Notification_Prefernce = By.linkText("Notification Preferences");
	
	public CheckMoreOptionNavigiationPage(WebDriver driver) {
		this.driver=driver;
	}
	
	public void MoreOption() {
		WebElement ele= driver.findElement(More_Option);
		Actions action = new Actions(driver);
		action.moveToElement(ele).perform();
		
	}
	
	public void NotiFicationPrefence() {
		driver.findElement(Notification_Prefernce).click();
	}
}
