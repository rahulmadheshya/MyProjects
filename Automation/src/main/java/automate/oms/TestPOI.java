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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestPOI {
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test
	public void openAddNewSKUPage() throws Exception {

		input("UserName"); // to input UserName

		click_on_Element("GetPasswordInputField"); // to get Password input field after providing the username

		input("Password"); // to input password

		click_on_Element("Login"); // to click on Login button

		explicitWait_Clickable("OmsApp", 20); // wait till OMS App is visible
		click_on_Element("OmsApp"); // click on OMS App

		click_on_Element("Purchases"); // click on Purchases tab

		click_on_Element("PO"); // click on PO page

		waitFor_x_seconds(5); // wait for PO page to load
		click_on_Element("AddNewPO"); // click on Add New PO

		explicitWait_Clickable("Don'tWantToSacnSku(s)", 5); // wait till Add New ask to san SKUs or not
		click_on_Element("Don'tWantToSacnSku(s)"); // click on option that no need to scan SKUs

		explicitWait_Clickable("NewPOPage", 5); // wait till New PO page loads

		select_From_Dropdown("Warehouse"); // to input Warehouse

		select_From_Dropdown("Vendor");

		select_SKU();

		input("SkuQty");
		input("SkuPrice");
		input("SkuMRP");

		click_on_Element("AddSku");

		select_From_Dropdown("ShippingOwner");
		select_From_Dropdown("VendorAddress");

		js.executeScript("arguments[0].scrollIntoView();",
				driver.findElement(By.xpath(readLocatorPropertiesFile("ExpectedDeliveryDate"))));
		explicitWait_Clickable("ExpectedDeliveryDate", 5);
		select_Date("ExpectedDeliveryDate");
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

	void input(String xpath) throws Exception {
		String inputText = readExcelInput(xpath);
		String xpathOfSkuPrice = null;
		WebElement ele = null;
		if (xpath.equalsIgnoreCase("SkuPrice")) {
			xpathOfSkuPrice = readLocatorPropertiesFile(xpath);
		}
		xpath = readLocatorPropertiesFile(xpath);
		if (xpath.equalsIgnoreCase(xpathOfSkuPrice)) {
			ele = driver.findElement(By.xpath(xpathOfSkuPrice));
			ele.click();
			waitFor_x_seconds(2);
			ele.clear();
			ele.sendKeys(Keys.BACK_SPACE + inputText);
		} else {
			ele = driver.findElement(By.xpath(xpath));
			ele.sendKeys(inputText);
		}
	}

	void select_From_Dropdown(String xpath) throws Exception {
		String option = readExcelInput(xpath);
		String xpathOfVendorAdress = null;
		if (xpath.equalsIgnoreCase("VendorAddress")) {
			xpathOfVendorAdress = readLocatorPropertiesFile(xpath);
		}
		xpath = readLocatorPropertiesFile(xpath);
		WebElement ele = driver.findElement(By.xpath(xpath));
		Select select = new Select(ele);
		if (xpath.equalsIgnoreCase(xpathOfVendorAdress)) {
			select.selectByIndex(1);
		} else {
			select.selectByVisibleText(option);
		}
	}

	void explicitWait_Clickable(String xpath, long time) {
		xpath = readLocatorPropertiesFile(xpath);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	public String readLocatorPropertiesFile(String propText) {
		Properties prop = null;
		try {
			prop = new Properties();
			InputStream input = new FileInputStream(
					"E:\\eclipse\\Automation\\src\\test\\resources\\POLocators.properties");
			prop.load(input);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(propText);
	}

	void select_SKU() throws Exception {
		click_on_Element("InputSku");
		input("InputSku");
		explicitWait_Clickable("SelectSku", 5);
		click_on_Element("SelectSku");
	}

	void select_Date(String xpath) throws Exception {

		String date = readExcelInput(xpath);
		String splitDate[] = date.split("/");
		String day = splitDate[1];
		String month = splitDate[0];
		String year = "20" + splitDate[2];
		click_on_Element(xpath);

		if (month.equalsIgnoreCase("1"))
			month = "Jan";
		else if (month.equalsIgnoreCase("2"))
			month = "Feb";
		else if (month.equalsIgnoreCase("3"))
			month = "Mar";
		else if (month.equalsIgnoreCase("4"))
			month = "Apr";
		else if (month.equalsIgnoreCase("5"))
			month = "May";
		else if (month.equalsIgnoreCase("6"))
			month = "Jun";
		else if (month.equalsIgnoreCase("7"))
			month = "Jul";
		else if (month.equalsIgnoreCase("8"))
			month = "Aug";
		else if (month.equalsIgnoreCase("9"))
			month = "Sep";
		else if (month.equalsIgnoreCase("10"))
			month = "Oct";
		else if (month.equalsIgnoreCase("11"))
			month = "Nov";
		else
			month = "Dec";

		waitFor_x_seconds(2);
		explicitWait_Clickable("OpenDatePicker", 5);
		driver.findElement(By.xpath((readLocatorPropertiesFile("selectMonth&Year") + month + " " + year
				+ readLocatorPropertiesFile("selectDay") + day + "']"))).click();

	}

	public void waitFor_x_seconds(long x) throws InterruptedException {
		x *= 1000;
		Thread.sleep(x);
	}

	public String readExcelInput(String xpathFor) throws Exception {

		File src = new File("E:\\eclipse\\Automation\\src\\test\\resources\\OMS Input Data.xlsx");

		FileInputStream fis = new FileInputStream(src);

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sheet = wb.getSheetAt(1);

		DataFormatter formatter = new DataFormatter();

		Cell cell;

		String inputValue = null;

		if (xpathFor.equalsIgnoreCase("UserName")) {

			cell = sheet.getRow(0).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("Password")) {

			cell = sheet.getRow(1).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("Warehouse")) {

			cell = sheet.getRow(2).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("Vendor")) {

			cell = sheet.getRow(3).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("InputSku")) {

			cell = sheet.getRow(4).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("SkuQty")) {

			cell = sheet.getRow(5).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}

		else if (xpathFor.equalsIgnoreCase("SkuPrice")) {

			cell = sheet.getRow(6).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		} else if (xpathFor.equalsIgnoreCase("SkuMRP")) {

			cell = sheet.getRow(7).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		} else if (xpathFor.equalsIgnoreCase("ShippingOwner")) {

			cell = sheet.getRow(8).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		} else if (xpathFor.equalsIgnoreCase("VendorAddress")) {

			cell = sheet.getRow(9).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		} else if (xpathFor.equalsIgnoreCase("PickupDate")) {

			cell = sheet.getRow(10).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		} else if (xpathFor.equalsIgnoreCase("ExpiryDate")) {

			cell = sheet.getRow(11).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		} else if (xpathFor.equalsIgnoreCase("ExpectedDeliveryDate")) {

			cell = sheet.getRow(12).getCell(1);
			inputValue = formatter.formatCellValue(cell);

		}
		wb.close();
		return inputValue;

	}

}
