package automate.oms;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Facebook {
	WebDriver driver;

	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test
	public void login() throws InterruptedException {

		driver.findElement(By.id("email")).sendKeys("rahulmadheshya1996@gmail.com");

		driver.findElement(By.id("pass")).sendKeys("");

		driver.findElement(By.name("login")).click();

		driver.findElement(By.xpath("(//*[@viewBox='0 0 28 28'])[8]")).click();

		driver.findElement(By.xpath("(//span[text()='Anjali Gupta'])[3]")).click();

	}

	@AfterTest
	public void closeBrowser() {
		// driver.quit();
	}
}
