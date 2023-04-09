

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SmartBuyTesting {

	public WebDriver driver;
	public int numberOfTry = 14;

	SoftAssert softassertProsses = new SoftAssert();

	@BeforeTest

	public void this_is_before_tist() {
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);

		driver.get("https://smartbuy-me.com/smartbuystore/");
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();
		driver.manage().window().maximize();

	}

	@Test(groups = "A")
	public void Test_Adding_Itdm_SAMSUNG_50_inch_SMART_4K() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		for (int i = 0; i < numberOfTry; i++) {

			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[1]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();

			String mas = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]")).getText();

			if (mas.contains("Sorry")) {

				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();
				numberOfTry = i;

			} else {

				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();
			}

		}
		driver.navigate().back();
	}

	@Test(groups = "A")

	public void Chack_The_Correct_Price() {

		String the_single_price = driver
				.findElement(
						By.xpath("//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[1]/div/div[2]/div[2]/div/p"))
				.getText();

		String[] the_updated_single_price = the_single_price.split("JOD");
		String the_final_single_price = the_updated_single_price[0].trim();

		String updated = the_final_single_price.replace(",", ".");

		Double final_price = Double.parseDouble(updated);

		System.out.println(final_price);

		double totalprice = final_price * numberOfTry;

		System.out.println(totalprice);

		softassertProsses.assertEquals(totalprice, 1536.0);


	}

	@Test(groups = "B")
	public void Chack_The_Discount_Price() {

		String the_orginal_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();

		String[] the_updated1_orginal_price = the_orginal_price.split("JOD");
		String the_updated2_orginal_price = the_updated1_orginal_price[0].trim();

		Double the_final_orginal_price = Double.parseDouble(the_updated2_orginal_price);
		System.out.println("The price Before discount:  " + the_final_orginal_price);

		String DiscountOnItem = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[4]/div/div[2]/div[2]/div/div/span[1]"))
				.getText();

		String[] updated_discount_on_item1 = DiscountOnItem.split("%");
		String updated_discount_on_item2 = updated_discount_on_item1[0].trim();

		Double the_final_discount_on_item = Double.parseDouble(updated_discount_on_item2);

		Double final_discount = the_final_discount_on_item / 100;

		System.out.println("The discount:" + final_discount);

		Double ThePriceAfterDiscount = (the_final_orginal_price - (final_discount * the_final_orginal_price));

		System.out.println("ThePriceAfterDiscount: " + ThePriceAfterDiscount);

		softassertProsses.assertEquals(ThePriceAfterDiscount, 221.0);

		softassertProsses.assertAll();

	}

}

