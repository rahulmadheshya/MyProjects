package automate.oms;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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

public class DocumentValidation {
	WebDriver driver;
	JavascriptExecutor js;
	int z = 0;

	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "//src//main//resources//chromedriver.exe");
		driver = new ChromeDriver();

		js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.get("http://testfrontend.gscmaven.in/#/wmslogin");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test
	public void openAddNewSKUPage() throws Exception {

		input("//input[@id='loginEmail']", "rahulm");

		input("//input[@id='loginPassword']", "abcd");

		click_on_Element("//button[@id='loginSubmit']");

		select_From_Dropdown("//select[@id='whlist']", "Real Company Warehouse");

		select_From_Dropdown("//select[@name='client']", "Real Company");

		click_on_Element("//input[@value='NEXT']");

		explicitWait_Clickable("//*[contains(text(),'Inbound')]", 10);

		Thread.sleep(2000);
		click_on_Element("//a[@href='#/validation/']");

		explicitWait_Clickable(
				"//table[@id='validationListTable']/tbody/tr/td/following-sibling::td[contains(text(),'GATEP0000000057')]/following-sibling::td/i[@title='Start Validation']",
				10);

		Thread.sleep(2000);

		input("//input[@id='vendors_value']", "Shahid13");
		Thread.sleep(1000);
		click_on_Element("//div[@class='angucomplete-title ng-binding ng-scope' and text()='Shahid13']");

		select_From_Dropdown("//select[@id='DIBillingAddress']", "111111, Delhi - 110048");

		//select_InvoiceDate("01-10-2019");

		for (int j = 0; j < 10; j++) {

			for (int a = 0; a < 10; a++) {
				int i = a;
				if (j != 0) {
					i = 9;
				}
				z = z + 1;
				input("//input[@name='sku" + i + "']", readExcelInput(z));
				Thread.sleep(1000);
				click_on_Element("//div[@class='angucomplete-title ng-binding ng-scope' and text()='"
						+ readExcelInput(z) + "']");

				input("//td[@id='POInvSQty" + i + "']/ng-form/input", "10");
				input("//td[@id='POInvSGross" + i + "']/descendant::input[@placeholder='Gross']", "100.00");
//				if ((j == 2) && (a == 9)) {
//					break;
//				}

				click_on_Element("//a[@title='Add invoice line item']");

			}
		}

		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//span[@id='POInv8TG0DIC']")));
		click_on_Element("//span[@id='POInv8TG0DIC']");

	}

	@AfterTest
	public void tearDown() {
//					driver.quit();
	}

	void input(String xpath, String input) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.sendKeys(input);
	}

	void click_on_Element(String xpath) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		ele.click();
	}

	void select_From_Dropdown(String xpath, String text) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		Select select = new Select(ele);
		select.selectByVisibleText(text);
		;
		;
	}

	void explicitWait_Clickable(String xpath, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		click_on_Element(xpath);
	}

	void select_InvoiceDate(String date) throws Exception {
		LocalDate today = LocalDate.now();
		String formattedDate = today.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		String s[] = formattedDate.split(",");
		String currentYear = s[1].replaceAll(" ", "");
		String currentMonth = (s[0].split(" "))[1];

		//

		String splitDate[] = date.split("-");
		String day = splitDate[0];
		if ((!day.equalsIgnoreCase("10"))) {
			day = day.replaceAll("0", "");
		}
		String month = splitDate[1];
		String year = splitDate[2];

		if ((month.equalsIgnoreCase("01")) || (month.equalsIgnoreCase("1")))
			month = "Jan";
		else if ((month.equalsIgnoreCase("02")) || (month.equalsIgnoreCase("2")))
			month = "Feb";
		else if ((month.equalsIgnoreCase("03")) || (month.equalsIgnoreCase("3")))
			month = "Mar";
		else if ((month.equalsIgnoreCase("04")) || (month.equalsIgnoreCase("4")))
			month = "Apr";
		else if ((month.equalsIgnoreCase("05")) || (month.equalsIgnoreCase("5")))
			month = "May";
		else if ((month.equalsIgnoreCase("06")) || (month.equalsIgnoreCase("6")))
			month = "Jun";
		else if ((month.equalsIgnoreCase("07")) || (month.equalsIgnoreCase("7")))
			month = "Jul";
		else if ((month.equalsIgnoreCase("08")) || (month.equalsIgnoreCase("8")))
			month = "Aug";
		else if ((month.equalsIgnoreCase("09")) || (month.equalsIgnoreCase("9")))
			month = "Sep";
		else if ((month.equalsIgnoreCase("10")))
			month = "Oct";
		else if ((month.equalsIgnoreCase("11")))
			month = "Nov";
		else
			month = "Dec";

		click_on_Element("//div/input[@placeholder='Invoice Date']/following-sibling::button");

		if ((month.equalsIgnoreCase(currentMonth)) && (year.equalsIgnoreCase(currentYear))) {

			explicitWait_Clickable(
					"//div[@id='md-date-pane-2']/descendant::td/span[text()='" + month + " " + year
							+ "']/parent::td/parent::tr/following-sibling::tr/descendant::span[text()='" + day + "']",
					40);

		} else {
			explicitWait_Clickable("//div[@id='md-date-pane-2']/descendant::td/span[text()='" + currentMonth + " "
					+ currentYear + "']", 40);

			explicitWait_Clickable("//tbody[@class='md-calendar-year ng-scope ng-isolate-scope']/tr/td[text()='" + year
					+ "']/parent::tr/following-sibling::tr/descendant::td/span[text()='" + month + "']", 40);

			explicitWait_Clickable(
					"//div[@id='md-date-pane-2']/descendant::td/span[text()='" + month + " " + year
							+ "']/parent::td/parent::tr/following-sibling::tr/descendant::span[text()='" + day + "']",
					40);
		}
	}

	public String readExcelInput(int l) throws Exception {

		File src = new File("E:\\eclipse\\Automation\\src\\test\\resources\\SKU.xls");

		FileInputStream fis = new FileInputStream(src);

		HSSFWorkbook wb = new HSSFWorkbook(fis);

		HSSFSheet sheet = wb.getSheetAt(0);

		DataFormatter formatter = new DataFormatter();

		Cell cell;

		String s = null;

		cell = sheet.getRow(l).getCell(0);
		s = formatter.formatCellValue(cell);

		wb.close();
		return s;

	}

}
