package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	WebDriver driver;
	
	private By login_textbox = By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div/form/div[1]/input");
	private By Otp_Button = By.xpath("//*[@id=\"container\"]/div/div[3]/div/div[2]/div/form/div[3]/button");
	private By login_Button=  By.linkText("Login");
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}
    
	public void AddUsername(String username) {
		driver.findElement(login_textbox).sendKeys(username);
	}
	
	
	
	public void OtpButton() {
		driver.findElement(Otp_Button).click();
	}
	
	public void loginOpen() {
		driver.findElement(login_Button).click();
	}
	
}
