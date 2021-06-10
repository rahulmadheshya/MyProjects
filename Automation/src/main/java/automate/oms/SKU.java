package automate.oms;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SKU {
	WebDriver driver;
	JavascriptExecutor js;

	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.get("http://realcompany.gscmaven.in/#/login/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test
	public void openAddNewSKUPage() {

		typeText("//*[@id='loginEmail']\r\n", "rahul.madheshya@glaucuslogistics.com");

		click_on_Element("//*[@id='getClients']");

		typeText("//*[@id='loginPassword']", "abcd@1234");

		click_on_Element("//*[@id='loginSubmit']");

		click_on_Element("//*[text()='OMS']");

		click_on_Element("//div[@id='menuSection']/nav/ul/li[6]/div/div[1]/a");

		click_on_Element("//*[text()='SKU']");

		click_on_Element("//*[text()='Add New SKU']");

		explicitWait_Clickable("//div[@class='normalSku']", 10);
		click_on_Element("//div[@class='normalSku']");

		typeText("//input[@id='newSkuId' and @placeholder='Enter Seller SKU ID']", "SKU001");

		typeText("//input[@id='newSkuName' and @placeholder='Enter Name']", "SKU001");

		select_From_Dropdown("//select[@id='newSkuSLT' and @name='tableSkuShelfLifeType']", "None");

		select_From_Dropdown("//select[@id='newSkuCategory' and @name='category']", "Card Readers");

		select_From_Dropdown("(//select[@id='newSkuBrand' and @name='brand'])[3]", "Bajaj");

		typeText("//input[@id='newSkuHsn' and @placeholder='Enter HSN number']", "12345");

		click_on_Element("//textarea[@id='newSkuDesc' and @placeholder='Enter Description']");
		typeText("//textarea[@id='newSkuDesc' and @placeholder='Enter Description']", "SKU SKU001");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("(//*[contains(text(),'Properties')])[3]")));

		click_on_Element("(//*[contains(text(),'Properties')])[3]");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath("(//*[contains(text(),'Returnable')])[4]")));

		click_on_Element("(//*[contains(text(),'Returnable')])[4]");
	}

	@AfterTest
	public void tearDown() {
//		driver.quit();
	}

	void click_on_Element(String xpath) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.click();
	}

	void typeText(String xpath, String inputText) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.sendKeys(inputText);
	}

	void select_From_Dropdown(String xpath, String option) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		Select select = new Select(ele);
		select.selectByVisibleText(option);
	}

	void explicitWait_Clickable(String xpath, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
}