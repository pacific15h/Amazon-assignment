package com.amazon.ts;

/****************** WebConnector class, it initializes browser and contains selenium methods ******/

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class WebConnector {
	
	WebDriver driver = null;
	WebDriver mozilla=null;
	WebDriver chrome=null;
	static WebConnector w;
	
	public Properties OR = null;
	public Properties config = null;
	
/****** Private constructor for Singleton WebConnector ******************/	
private WebConnector(){
		
		if(OR==null){
			try{
				// initialize OR
				OR = new Properties();
				FileInputStream fs  = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\com\\amazon\\ts\\config\\OR.properties");
				OR.load(fs);
				
				// initialize CONFIG to corresponding env
				config= new Properties();
				fs  = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\com\\amazon\\ts\\config\\config.properties");
				config.load(fs);
				
			}catch(Exception e){
				System.out.println("Error on intializing properties files");
			}
			
		}
}

/************* Initialize WebConnector instance ********************/
		public static WebConnector getInstance(){
			if(w==null)
				w= new WebConnector();
			
			return w;
		}

		
		public void openBrowser(String browser) {
			String browserType = config.getProperty(browser);
			if(browserType.equals("Mozilla") && mozilla==null){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//driver//geckodriver.exe");
				driver = new FirefoxDriver();
				mozilla=driver;
			}else if(browserType.equals("Mozilla") && mozilla!=null){
				driver=mozilla;
			}else if(browserType.equals("Chrome") && chrome==null){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
				driver=new ChromeDriver();
				chrome=driver;
			}else if(browserType.equals("Chrome") && chrome==null){
				driver=chrome;
			}
			
			driver.manage().window().maximize();
			// implicit wait
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		
		

		public void navigate(String URL){
			driver.get(config.getProperty(URL));
			System.out.println(OR.getProperty("StayButton"));
			
			
			if (isElementPresent("StayButton")) {
				driver.findElement(By.xpath(OR.getProperty("StayButton"))).click();
			}
		}

	
		public boolean isElementPresent(String element){
			return (driver.findElements(By.xpath(OR.getProperty(element))).size() > 0);
		}
		
		public void doLogin(String userName, String password) {
			
			driver.findElement(By.xpath(OR.getProperty("SignInButton"))).click();
			driver.findElement(By.xpath(OR.getProperty("EmailIdField"))).sendKeys(userName);
			driver.findElement(By.xpath(OR.getProperty("PasswordField"))).sendKeys(password);
			driver.findElement(By.xpath(OR.getProperty("SignInSubmit"))).click();
			
			}
		
		public void mouseMoveTo(String element) {
			
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath(OR.getProperty(element)))).perform();
			
		}
		
		public void Click(String element) {
			driver.findElement(By.xpath(OR.getProperty(element))).click();
		}
		
		
/*********** selects desired item from dropdown list if item is available otherwise
 *********** it selects first available item***************************************/		
		public void dropdownSelect(String sizeDropdown, String element) {
			Select dropdown = new Select(driver.findElement(By.xpath(sizeDropdown)));
			List <WebElement> alloptions = dropdown.getOptions();
			List <WebElement> availableOptions = new ArrayList <WebElement> ();
			
			String size = OR.getProperty(element);
			
			for (WebElement option : alloptions) {
				if (option.getAttribute("class").contains("Available")) {
					availableOptions.add(option);
				}
				
			}
			
			for (WebElement option : availableOptions) {
				if (option.getText().equalsIgnoreCase(size)) {
					dropdown.selectByVisibleText(size);
				}
				
				else {
					dropdown.selectByVisibleText(availableOptions.get(0).getText());
				}
			}
		}
		
		public boolean verifyPageTitle(String element) {
			System.out.println(OR.getProperty(element));
			System.out.println(driver.getTitle());
			return driver.getTitle().equals(OR.getProperty(element));
		}
		
		public void quit() {
			driver.quit();
		}
}


