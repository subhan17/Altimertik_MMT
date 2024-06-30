package org.mmt.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public abstract class AbstractTest {

    protected WebDriver driver;

    @BeforeTest
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        //options.addArguments("user-data-dir=C:\\Users\\Welcome\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("--disable-blink-features=AutomationControlled");
        this.driver = new ChromeDriver(options);
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        // Remove the webdriver property
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }

}
