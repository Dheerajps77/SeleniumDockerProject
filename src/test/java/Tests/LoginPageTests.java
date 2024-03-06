package Tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import BaseClass.BaseTests;

public class LoginPageTests extends BaseTests {

	BaseTests baseTests=new BaseTests();
	By userName = By.name("username");
	By passWord = By.name("password");
	By loginBtn = By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button");
	By errorMessage = By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p");
	By blankUsername = By.xpath("//*[@id='app']/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/span");
	By dashboardPage = By.xpath("//*[@id='app']/div[1]/div[1]/header/div[1]/div[1]/span/h6");

	@Test(priority=0)
	public void invalidCredentials() {
		try {
			getDriver().findElement(userName).sendKeys("1234");
			getDriver().findElement(passWord).sendKeys("12342");
			getDriver().findElement(loginBtn).click();
			String actualErrorMessage = getDriver().findElement(errorMessage).getText();
			System.out.println("Actual ErrorMessage :" + actualErrorMessage);
			assertEquals(actualErrorMessage, "Invalid credentials");

		} catch (Exception e) {
			throw e;
		}
	}

	@Test(priority=1)
	public void blankUsername() {
		try {
			getDriver().findElement(userName).sendKeys("");
			getDriver().findElement(passWord).sendKeys("12342");
			getDriver().findElement(loginBtn).click();
			String actualErrorMessage = getDriver().findElement(blankUsername).getText();
			System.out.println("Actual ErrorMessage :" + actualErrorMessage);
			assertEquals(actualErrorMessage, "Required");
		} catch (Exception e) {
			throw e;
		}
	}

	@Test(priority=2)
	public void successfulLogin() {
		try {
			getDriver().findElement(userName).sendKeys("Admin");
			getDriver().findElement(passWord).sendKeys("admin123");
			getDriver().findElement(loginBtn).click();
			String actualMessage = getDriver().findElement(dashboardPage).getText();
			System.out.println("Message :" + actualMessage);
			assertEquals(actualMessage, "Dashboard");
		} catch (Exception e) {
			throw e;
		}
	}

}
