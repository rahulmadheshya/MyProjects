package automate.oms;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

public class PurchaseOrder {
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

		input("UserName");

		click_on_Element("GetPasswordInputField");

		input("Password");

		click_on_Element("Login");

		waitFor_x_seconds(2);

		explicitWait_Clickable("OmsApp", 20);
		click_on_Element("OmsApp");

		click_on_Element("Purchases");

		click_on_Element("PO");

		waitFor_x_seconds(5);
		click_on_Element("AddNewPO");

		explicitWait_Clickable("Don'tWantToSacnSku(s)", 5);
		click_on_Element("Don'tWantToSacnSku(s)");

		explicitWait_Clickable("NewPOPage", 5);
		click_on_Element("NewPOPage");

		select_From_Dropdown("Warehouse");

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
//			ele.sendKeys(inputText);
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

	void explicitWait_CheckVisibility(String xpath, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	void select_SKU() throws Exception {
		click_on_Element("InputSku");
		input("InputSku");
		explicitWait_Clickable("SelectSku", 5);
		click_on_Element("SelectSku");
	}

	void select_Date(String xpath) throws Exception {
		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		String currentDate[] = formattedDate.split(",");
		String currentYear = currentDate[1].replaceAll(" ", "");
		String currentMonth = (currentDate[0].split(" "))[1];
		String currentDay = (currentDate[0].split(" "))[0];
		if ((!currentDay.equalsIgnoreCase("10")) || (!currentDay.equalsIgnoreCase("20"))
				|| (!currentDay.equalsIgnoreCase("30"))) {
			currentDay = currentDay.replaceAll("0", "");
		}

		//

		String storeExpectedDeliverDate = readExcelInput("ExpectedDeliveryDate");
		System.out.println(storeExpectedDeliverDate);
		String expectedDeliverDate[] = storeExpectedDeliverDate.split("/");
		String expectedDay = expectedDeliverDate[1];
		if ((!expectedDay.equalsIgnoreCase("10")) || (!expectedDay.equalsIgnoreCase("20"))
				|| (!expectedDay.equalsIgnoreCase("30"))) {
			expectedDay = expectedDay.replaceAll("0", "");
		}
		String expectedMonth = expectedDeliverDate[0];
		int selectMonth = Integer.parseInt(expectedMonth);
		String expectedYear = "20" + expectedDeliverDate[2];

		if (expectedMonth.equalsIgnoreCase("1"))
			expectedMonth = "Jan";
		else if (expectedMonth.equalsIgnoreCase("2"))
			expectedMonth = "Feb";
		else if (expectedMonth.equalsIgnoreCase("3"))
			expectedMonth = "Mar";
		else if (expectedMonth.equalsIgnoreCase("4"))
			expectedMonth = "Apr";
		else if (expectedMonth.equalsIgnoreCase("5"))
			expectedMonth = "May";
		else if (expectedMonth.equalsIgnoreCase("6"))
			expectedMonth = "Jun";
		else if (expectedMonth.equalsIgnoreCase("7"))
			expectedMonth = "Jul";
		else if (expectedMonth.equalsIgnoreCase("8"))
			expectedMonth = "Aug";
		else if (expectedMonth.equalsIgnoreCase("9"))
			expectedMonth = "Sep";
		else if (expectedMonth.equalsIgnoreCase("10"))
			expectedMonth = "Oct";
		else if (expectedMonth.equalsIgnoreCase("11"))
			expectedMonth = "Nov";
		else
			expectedMonth = "Dec";

		System.out.println(expectedDay + " " + expectedMonth + " " + expectedYear);

		click_on_Element(xpath);

		if ((expectedDay.equalsIgnoreCase(currentDay)) && (expectedMonth.equalsIgnoreCase(currentMonth))
				&& (expectedYear.equalsIgnoreCase(currentYear))) {
			driver.findElement(By.xpath("//tr/td/span[text()='" + expectedMonth + " " + expectedYear
					+ "']/parent::td/parent::tr/following-sibling::tr/descendant::span[text()='" + expectedDay + "']"))
					.click();
		} else {
			explicitWait_CheckVisibility("//div[@id='md-date-pane-14']/descendant::tr/td/span[text()='Sep 2020']", 5);
			driver.findElement(By.xpath("//div[@id='md-date-pane-14']/descendant::tr/td/span[text()='Sep 2020']"))
					.click();
			waitFor_x_seconds(1);

			if (selectMonth <= 6) {
				driver.findElement(By.xpath("//div[@id='md-date-pane-14']/descendant::tr/td[text()='" + expectedYear
						+ "']/parent::tr/descendant::td/span[text()='" + expectedMonth + "']")).click();
			} else {
				driver.findElement(By.xpath("//div[@id='md-date-pane-14']/descendant::tr/td[text()='" + expectedYear
						+ "']/parent::tr/following-sibling::tr/descendant::td/span[text()='" + expectedMonth + "']"))
						.click();

			}
			driver.findElement(By.xpath("//tr/td/span[text()='" + expectedMonth + " " + expectedYear
					+ "']/parent::td/parent::tr/following-sibling::tr/descendant::span[text()='" + expectedDay + "']"))
					.click();
		}
	}

	public void waitFor_x_seconds(long x) throws InterruptedException {
		x *= 1000;
		Thread.sleep(x);
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
