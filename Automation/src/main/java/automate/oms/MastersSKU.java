package automate.oms;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class MastersSKU {
	WebDriver driver;
	JavascriptExecutor js;

	@BeforeTest
	void openBrowser() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.get("http://realcompany.gscmaven.in/#/login/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test
	void openAddNewSKUPage() throws Exception {

		typeText("username");

		click_on_Element("getClients");

		typeText("password");

		click_on_Element("login");

		explicitWait_Clickable("omsApp", 10);
		click_on_Element("omsApp");

		click_on_Element("masters");

		Thread.sleep(2000);
		click_on_Element("skuPage");

		click_on_Element("addNewSKUPage");

		explicitWait_Clickable("noramlSKU", 10);
		click_on_Element("noramlSKU");

		typeText("skuID");

		typeText("skuName");

		select_From_Dropdown("expiryType", "None");

		select_From_Dropdown("skuCategory", "Card Readers");

		select_From_Dropdown("skuBrand", "Bajaj");

		typeText("skuHSN");

		click_on_Element("skuDescription");
		typeText("skuDescription");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath(readLocatorPropertiesFile("skuProperties"))));

		click_on_Element("skuProperties");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath(readLocatorPropertiesFile("skuPropertyReturnable"))));

		click_on_Element("skuPropertyReturnable");
	}

	@AfterTest
	void tearDown() {
//		driver.quit();
	}

	void click_on_Element(String xpath) {
		xpath = readLocatorPropertiesFile(xpath);
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.click();
	}

	void typeText(String xpath) throws Exception {
		String inputText = readExcelInput(xpath);
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

	String readLocatorPropertiesFile(String propText) {
		Properties prop = null;
		try {
			prop = new Properties();
			InputStream input = new FileInputStream(
					"E:\\eclipse\\Automation\\src\\test\\resources\\SKULocators.properties");
			prop.load(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(propText);
	}

	String readExcelInput(String xpathFor) throws Exception {

		File src = new File("E:\\eclipse\\Automation\\src\\test\\resources\\OMS Input Data.xlsx");

		FileInputStream fis = new FileInputStream(src);

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sheet = wb.getSheet("SKU");

		DataFormatter formatter = new DataFormatter();

		Cell cell;

		String inputValue = null;

		if (xpathFor.equalsIgnoreCase("username")) {

			inputValue = (sheet.getRow(0).getCell(1).getStringCellValue());

		}

		else if (xpathFor.equalsIgnoreCase("password")) {

			inputValue = (sheet.getRow(1).getCell(1).getStringCellValue());

		}

		else if (xpathFor.equalsIgnoreCase("skuID")) {

			inputValue = (sheet.getRow(2).getCell(1).getStringCellValue());

		}

		else if (xpathFor.equalsIgnoreCase("skuName")) {

			inputValue = (sheet.getRow(3).getCell(1).getStringCellValue());

		}

		else if (xpathFor.equalsIgnoreCase("skuHSN")) {

			cell = sheet.getRow(7).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("skuDescription")) {

			inputValue = (sheet.getRow(8).getCell(1).getStringCellValue());

		}
		wb.close();
		return inputValue;

	}

}
