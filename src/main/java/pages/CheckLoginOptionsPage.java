package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckLoginOptionsPage {

	WebDriver driver = null;
	 
	
    private By login_Button=  By.linkText("Login");
    private By Open_TermOfUse = By.xpath("//a[text()='Terms of Use']");
    private By Privacy_PolicyOption = By.linkText("Privacy Policy");
    public CheckLoginOptionsPage(WebDriver driver) {
    	this.driver=driver;
    }
	
    public void OpenLogin() {
    	driver.findElement(login_Button).click();
    }
    
    public void OpenTermsOfUseOption() {
    	driver.findElement(Open_TermOfUse).click();
    }
    
}
