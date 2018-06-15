package com.dice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DiceJobSearch {

	
	public static void main(String[] args) throws IOException {
		// Set up chrome driver path
		WebDriverManager.chromedriver().setup();
		// invoke selenium webdriver
		WebDriver driver = new ChromeDriver();
		//fullscreen
		driver.manage().window().fullscreen();
		//set universal wait time in cse web page is slow
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String url = "http://www.dice.com";
		
		List<String> sb = new ArrayList();
		try(BufferedReader br = new BufferedReader(new FileReader("JobSearches.txt"))) {
		    
		    String line = br.readLine();
		    
		    while (line != null) {
		        sb.add(line);
		        line = br.readLine();
		        
		    }
		    
		}
		
		
		
		
			
		for (int i = 0; i < sb.size() ; i++) {
			driver.get(url);
			String expectedTitle = "Job Search for Technology Professionals | Dice.com";
			String currentTitle = driver.getTitle();
			if (currentTitle.equals(expectedTitle)) {
				System.out.println("Step PASS. Dice homepage successfully loaded");
			} else {
				System.out.println("Step FAIL. Dice homepage DID NOT load successfully.");
				throw new RuntimeException("Step FAIL. Dice homepage DID NOT load successfully.");
			}
			String keyword = sb.get(i);
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
				System.out.println(
						"Step PASS: " + keyword + " search returned " + countResult + " results in " + location);
						sb.set(i, sb.get(i) + " - " + countResult);
			} else {
				System.out.println(
						"Step FAIL: " + keyword + " search returned " + countResult + " results in " + location);
			} 
		}
		driver.close();
		System.out.println("TEST COMPLETED - " + LocalDateTime.now());
		//System.out.println(sb);
		
		Path file = Paths.get("JobSearchResults.txt");
		Files.write(file, sb, Charset.forName("UTF-8"));
	}
	
	
}
