package automate.oms;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
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

public class PropCheck {
	WebDriver driver;
	JavascriptExecutor js;
	Properties prop;

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

		typeText("username", "rahul.madheshya@glaucuslogistics.com");

		click_on_Element("getClients");

		typeText("password", "abcd@1234");

		click_on_Element("login");

		click_on_Element("omsApp");

		click_on_Element("masters");

		click_on_Element("skuPage");

		click_on_Element("addNewSKUPage");

		explicitWait_Clickable("noramlSKU", 10);
		click_on_Element("noramlSKU");

		typeText("skuID", "SKU001");

		typeText("skuName", "SKU001");

		select_From_Dropdown("expiryType", "None");

		select_From_Dropdown("skuCategory", "Card Readers");

		select_From_Dropdown("skuBrand", "Bajaj");

		typeText("skuHSN", "12345");

		click_on_Element("skuDescription");
		typeText("skuDescription", "SKU SKU001");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath(readLocatorPropertiesFile("skuProperties"))));

		click_on_Element("skuProperties");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath(readLocatorPropertiesFile("skuPropertyReturnable"))));

		click_on_Element("skuPropertyReturnable");
	}

	@AfterTest
	public void tearDown() {
//		driver.quit();
	}

	void click_on_Element(String xpath) {
		xpath = readLocatorPropertiesFile(xpath);
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.click();
	}

	void typeText(String xpath, String inputText) {
		xpath = readLocatorPropertiesFile(xpath);
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.sendKeys(inputText);
	}

	void select_From_Dropdown(String xpath, String option) {
		xpath = readLocatorPropertiesFile(xpath);
		WebElement ele = driver.findElement(By.xpath(xpath));
		Select select = new Select(ele);
		select.selectByVisibleText(option);
	}

	void explicitWait_Clickable(String xpath, long time) {
		xpath = readLocatorPropertiesFile(xpath);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	public String readLocatorPropertiesFile(String propText) {
		try {
			prop = new Properties();
			InputStream input = new FileInputStream(
					"E:\\eclipse\\Automation\\src\\test\\resources\\OMSLocators.properties");
			prop.load(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(propText);
	}

	
}
