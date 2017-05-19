package com.lazerycode.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lazerycode.selenium.DriverBase;


public class ExampleAmazonPriceExtract extends DriverBase {

	public void processAsin(String asin) throws Exception {
		// Create a new WebDriver instance
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		WebDriver driver = getDriver();

		System.out.println("ASIN to Crawl: " + asin);

		// Open Product Page
		driver.get("http://www.amazon.de/dp/" + asin);
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));
		int sizeOfAvailabilityElement = driver.findElements(By.xpath("//*[@id=\"availability\"]")).size();
		if (sizeOfAvailabilityElement == 0) {
			System.out.println("Product Page does not exist - SKIP");
		} else {
			processProduct(driver, element);
		}
	}

	private void processProduct(WebDriver driver, WebElement element) {
		// Enter something to search for
		element.clear();
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());
	}

	public static void main(String[] args) {
		ExampleAmazonPriceExtract example = new ExampleAmazonPriceExtract();
		try {
			DriverBase.instantiateDriverObject();

			String asin = "1784394351";
			example.processAsin( asin );

			DriverBase.clearCookies();

			Thread.sleep( 5_000 );
			DriverBase.closeDriverObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
