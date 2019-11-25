package stockMarket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Stock {

	public static void main(String[] args) throws InterruptedException, IOException {

		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.capitalzone.in/fo-data/");
		driver.manage().window().maximize();

		// to perform Scroll on application using Selenium
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,700)", "");
		Thread.sleep(5000);

		String basePath = new File("").getAbsolutePath();
		System.out.println(basePath);
		// Declaring Excel File Path
		String excelFilePath = basePath + "\\StockData.xlsx";
		System.out.println(excelFilePath);
		File src = new File(excelFilePath);
		// load file
		FileInputStream fis = new FileInputStream(src);
		// Load workbook
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		// Load sheet- Here we are loading first sheetonly
		XSSFSheet sh1 = wb.getSheetAt(0);
		// Get Row Count
		int rowCount = sh1.getLastRowNum();
		System.out.println(rowCount);
		FileOutputStream fout;
		for (int i = 1; i <= rowCount; i++) {
			fout = new FileOutputStream(src);
			driver.findElement(By.xpath("//*[@id='stock_symbol_chosen']/a/span")).click();
			Thread.sleep(1000);
			String searchValue = sh1.getRow(i).getCell(1).getStringCellValue();
			driver.findElement(By.xpath("//*[@id='stock_symbol_chosen']/div/div/input")).sendKeys(searchValue);
			driver.findElement(By.xpath("//*[@id='stock_symbol_chosen']/div/div/input")).sendKeys(Keys.ENTER);
			Thread.sleep(10000);
			String a = driver.findElement(By.xpath("//*[@id='post-1690']/div/div/div[2]/div[2]/div[11]/span"))
					.getText();
			String[] maxPain = a.split(" ");
			System.out.println(maxPain[2]);
			sh1.getRow(i).createCell(3).setCellValue(maxPain[2]);
			String d = driver.findElement(By.xpath("//*[@id='post-1690']/div/div/div[2]/div[2]/div[7]/span")).getText();
			String[] spotPrice = d.split(" ");
			System.out.println(spotPrice[2]);
			sh1.getRow(i).createCell(2).setCellValue(spotPrice[2]);
			wb.write(fout);
			fout.close();
		}
		System.out.println("Done");
	}
}
