package com.dice;

import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearch {

	
	public static void main(String[] args) {
		// Set up chrome driver path
		WebDriverManager.chromedriver().setup();
		// invoke selenium webdriver
		WebDriver driver = new ChromeDriver();
		
		//fullscreen
		driver.manage().window().fullscreen();
		//set universal wait time in cse web page is slow
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		String url = "http://www.dice.com";
		driver.get(url);
		
		String expectedTitle = "Job Search for Technology Professionals | Dice.com";
		String currentTitle = driver.getTitle();
		
		if (currentTitle.equals(expectedTitle)) {
			System.out.println("Step PASS. Dice homepage successfully loaded");
		}
		else {
			System.out.println("Step FAIL. Dice homepage DID NOT load successfully.");
			throw new RuntimeException("Step FAIL. Dice homepage DID NOT load successfully.");
		}
		
		String keyword = "Java Developer";
		driver.findElement(By.id("search-field-keyword")).clear();
		driver.findElement(By.id("search-field-keyword")).sendKeys(keyword);

		String location = "73071";
		driver.findElement(By.id("search-field-location")).clear();
		driver.findElement(By.id("search-field-location")).sendKeys(location);
		driver.findElement(By.id("findTechJobs")).click();
		
		String count = driver.findElement(By.id("posiCountId")).getText();
		System.out.println(count);
		int countResult = Integer.parseInt(count.replace(",", ""));
		
		if (countResult > 0) {
			System.out.println("Step PASS: " + keyword + " search returned " + countResult + " results in " + location);
		}
		else {
			System.out.println("Step FAIL: " + keyword + " search returned " + countResult + " results in " + location);
		}
		
		driver.close();
	}
	
	
}
