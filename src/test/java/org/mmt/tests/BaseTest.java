package org.mmt.tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;

import java.time.Duration;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver() {
        if (driver.get() == null) {
            //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-blink-features=AutomationControlled");

            WebDriver chromeDriver = new ChromeDriver(options);
            chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            ((JavascriptExecutor) chromeDriver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
            driver.set(chromeDriver);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    @AfterTest
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

}
