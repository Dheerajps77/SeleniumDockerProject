package BaseClass;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTests {
	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
	public static String remote_url = "http://localhost:4444";
	public final static int TIMEOUT = 5;

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) throws Exception {
		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--headless=new");
			options.addArguments("--remote-allow-origins=*");
			driver.set(new RemoteWebDriver(new URL(remote_url), options));
			System.out.println("Browser Started :" + browser);

		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--start-maximized");
			options.addArguments("-headless");
			driver.set(new RemoteWebDriver(new URL(remote_url), options));
			System.out.println("Browser Started :" + browser);

		} else if (browser.equalsIgnoreCase("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("--headless=new");
			driver.set(new RemoteWebDriver(new URL(remote_url), options));
			System.out.println("Browser Started :" + browser);
		} else {
			throw new Exception("Browser is not correct");
		}
		driver.get().get("https://opensource-demo.orangehrmlive.com/");
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}
	
    public WebDriver getDriver() {
    	return driver.get();
    }

	@AfterMethod
	public void closeBrowser() {
		driver.get().quit();
		//driver.remove();
	}

}
