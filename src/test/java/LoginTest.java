import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {
	static WebDriver driver;
	static Reimbursementhomepage reimbursementHome;
	
	@BeforeAll
	public static void setUp() {
		File file = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		driver = new ChromeDriver();
		reimbursementHome = new Reimbursementhomepage(driver);
	}
	
	@AfterAll
	public static void closeDriver() {
		driver.quit();
	}
	
	@Given("the user is on the homepage")
	public void the_user_is_on_the_homepage() {
		reimbursementHome.navigateTo();
	}

	@When("the user enters the correct username")
	public void the_user_enters_the_correct_username() {
		reimbursementHome.inputUsername("ghost");
	}

	@When("the user enters the correct password")
	public void the_user_enters_the_correct_password() {
		reimbursementHome.inputPassword("ghost");
	}

	@When("the user clicks the login button")
	public void the_user_clicks_the_login_button() {
		reimbursementHome.submitLogin();
	}

	@Then("the nav will show the user's first name")
	public void the_nav_will_show_the_user_s_first_name() {
		assertTrue(reimbursementHome.getNavText().contains("ghost"));
		reimbursementHome.logOut();
	}

	@When("the user enters an incorrect username")
	public void the_user_enters_an_incorrect_username() {
		reimbursementHome.inputUsername("asdfghjkl");
	}

	@Then("an incorrect credentials message will be displayed")
	public void an_incorrect_credentials_message_will_be_displayed() {
		String message = reimbursementHome.getMessageBoxText().toLowerCase();
	    assertTrue(message.contains("incorrect credentials"));
	}

	@When("the user enters the incorrect password")
	public void the_user_enters_the_incorrect_password() {
		reimbursementHome.inputPassword("12345678987654321");
	}
	
	@When("the user enters the username {string}")
	public void the_user_enters_the_username(String username) {
		reimbursementHome.inputUsername(username);
	}

	@When("the user enters the password {string}")
	public void the_user_enters_the_password(String password) {
		reimbursementHome.inputPassword(password);
	}

	@Then("an invalid input message will be displayed")
	public void an_invalid_input_message_will_be_displayed() {
		String message = reimbursementHome.getMessageBoxText().toLowerCase();
	    assertTrue(message.contains("invalid input"));
	}
}



