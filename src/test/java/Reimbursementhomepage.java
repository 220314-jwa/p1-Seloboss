import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;




public class Reimbursementhomepage {
WebDriver driver;
	
	@FindBy(id="usernameLogin")
	WebElement usernameInput;
	@FindBy(id="passwordLogin")
	WebElement passwordInput;
	@FindBy(id="loginbutton")
	WebElement logInBtn;
	WebElement messageBox;
	
	public Reimbursementhomepage(WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	
	public void navigateTo() {
		driver.get("C:\\Users\\SierraNicholes\\Documents\\2203-jwa\\2203-mar14-res\\pet-apps\\pet-app-front\\index.html");
	}
	
	public void inputUsername(String username) {
		usernameInput.sendKeys(username);
	}
	
	public void inputPassword(String password) {
		passwordInput.sendKeys(password);
	}
	
	public void submitLogin() {
		logInBtn.click();
	}
	
	public void logOut() {
		if (logInBtn.getText().equals("Log Out")) {
			logInBtn.click();
		}
	}
	
	public String getMessageBoxText() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions
				.textToBePresentInElement(messageBox, "i"));
		
		return messageBox.getText();
	}
	
	public String getNavText() {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(500));
		fluentWait.until(ExpectedConditions
				.numberOfElementsToBe(By.id("nameDisplay"), 1));
		
		return driver.findElement(By.id("nameDisplay")).getText();
	}
}


