package com.shoponline;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.shoponline.dto.ProductDTO;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShoponlineApplicationTests {

	// When User is register
	@Test
	
	public void register() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anjali\\Downloads\\Driver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		
		driver.get("http://localhost:8080/register");

		driver.findElement(By.xpath("//*[@id='userFirstName']")).sendKeys("pooja");
		driver.findElement(By.xpath("//*[@id='userLastName']")).sendKeys("meena");
		driver.findElement(By.xpath("//*[@id='userEmail']")).sendKeys("pooja123@gmail.com");
		driver.findElement(By.xpath("//*[@id='userPassword']")).sendKeys("pooja");
		driver.findElement(By.xpath("//*[@id='agreement']")).click();
		driver.findElement(By.xpath("/html/body/section/div/div/form/button")).click();

		Thread.sleep(2000);

		String actualUrl = "http://localhost:8080/registerNewUser";
		String expectedUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUrl, expectedUrl);

		if (actualUrl.equals(expectedUrl)) {
			System.out.println("Successfully Registered");

		} else {
			System.out.println("Failed to register");
		}

		driver.close();
	}
	
	
	// When User is login
	
	@Test
	public void login() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anjali\\Downloads\\Driver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		
		driver.get("http://localhost:8080/signin");

		driver.findElement(By.xpath("//*[@id='userName']")).sendKeys("jenanjali5@gmail.com");
		driver.findElement(By.xpath("//*[@id='userPassword']")).sendKeys("anjali");
		driver.findElement(By.xpath("//*[@id='login']")).click();

		Thread.sleep(2000);

		String actualUrl = "http://localhost:8080/home";
		String expectedUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUrl, expectedUrl);

		if (actualUrl.equals(expectedUrl)) {
			System.out.println("Successfully login");

		} else {
			System.out.println("Failed to login");
		}

		driver.close();
	}

	
	// When Product is added to cart
	
	@Test
	public void cart() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Anjali\\Downloads\\Driver\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("http://localhost:8080/home");	
		driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul")).click();
		
		Thread.sleep(2000);
		
		driver.get(driver.getCurrentUrl());
		driver.findElement(By.xpath("/html/body/section/table/tbody/tr")).click();

		Thread.sleep(2000);
		
		driver.get(driver.getCurrentUrl());
		driver.findElement(By.xpath("/html/body/section/table")).click();
		
		Thread.sleep(2000);
		
		driver.get(driver.getCurrentUrl());
		
		String val = driver.findElement(By.xpath("//*[@id='id']")).getAttribute("value");
		
		driver.findElement(By.xpath("//*[@id='detail']/form/div[1]")).click();
		
		String size = null;
		if (driver.findElement(By.xpath("//*[@id='small']")).isSelected()) {
			size = driver.findElement(By.xpath("//*[@id='small']")).getAttribute("value");
			
		} else if (driver.findElement(By.xpath("//*[@id='medium']")).isSelected()) {
			size = driver.findElement(By.xpath("//*[@id='medium']")).getAttribute("value");
			
		} else if (driver.findElement(By.xpath("//*[@id='large']")).isSelected()) {
			size = driver.findElement(By.xpath("//*[@id='large']")).getAttribute("value");
			
		} else if (driver.findElement(By.xpath("//*[@id='x-large']")).isSelected()) {
			size = driver.findElement(By.xpath("//*[@id='x-large']")).getAttribute("value");
			
		} else if (driver.findElement(By.xpath("//*[@id='xx-large']")).isSelected()) {
			size = driver.findElement(By.xpath("//*[@id='xx-large']")).getAttribute("value");
			
		}
		
		driver.findElement(By.xpath("//*[@id='detail']/form/div[2]")).click();
		driver.findElement(By.xpath("//*[@id='qtyDiv']")).click();
		driver.findElement(By.xpath("//*[@id='detail']/form/button")).click();

		Thread.sleep(2000);

		String actualUrl = "http://localhost:8080/product/"+val+"?size="+size+"&color=on";
		String expectedUrl = driver.getCurrentUrl();

		Assert.assertEquals(actualUrl, expectedUrl);

		if (actualUrl.equals(expectedUrl)) {
			System.out.println("Successfully Added to cart");

		} else {
			System.out.println("Failed to add");
		}

		driver.close();

	}

}
