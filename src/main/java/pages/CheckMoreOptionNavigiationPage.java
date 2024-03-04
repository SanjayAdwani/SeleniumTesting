package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CheckMoreOptionNavigiationPage {

	WebDriver driver=null;
	
	private By More_Option = By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[1]/div[1]/header/div[2]/div[5]/div/div/div/div/a/img");
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
