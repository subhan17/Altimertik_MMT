package org.mmt.pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;


public abstract class AbstractPage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected JavascriptExecutor javascriptExecutor;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
        javascriptExecutor = (JavascriptExecutor) driver;
    }


    public void closeWindow() {
        driver.close();
    }

    public void takeScreenshotAndAttachToAllure(String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
            File finalDestination = new File(screenshotPath);
            FileHandler.copy(source, finalDestination);
            byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshotBytes));
        } catch (IOException e) {
            Assert.fail("Unable to take screen shot with name : " + screenshotName);
        }
    }

    public abstract boolean isAt();

}
