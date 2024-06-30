package org.mmt.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.mmt.utils.AllureTestListener;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlightsSearchPage extends AbstractPage {
    @FindBy(id = "fromCity")
    private WebElement selectFromCityInput;
    @FindBy(id = "toCity")
    private WebElement selectToCityInput;
    @FindBy(css = ".primaryBtn.font24.latoBold.widgetSearchBtn")
    private WebElement searchButton;
    @FindBy(css = "#react-autowhatever-1 > div > ul > li")
    private List<WebElement> popularCitiesLst;
    @FindBy(className = ".fswTabs.latoRegular.darkGreyText")
    private List<WebElement> tripTypeRadioButton;
    @FindBy(id = "fromAnotherCity0")
    private WebElement fromCity1;
    @FindBy(id = "toAnotherCity0")
    private WebElement toCity1;
    @FindBy(id = "fromAnotherCity1")
    private WebElement fromCity2;
    @FindBy(id = "toAnotherCity1")
    private WebElement toCity2;
    @FindBy(css = "div[class='DayPicker-Day'][aria-disabled='false']")
    private List<WebElement> calendarDates;
    @FindBy(css = ".chNavIcon.appendBottom2.chSprite.chForex")
    private WebElement forexCardImage;


    public FlightsSearchPage(WebDriver driver) {
        super(driver);
    }

    public void selectCityFromPopularCitiesList(String city) {
        //List<WebElement> Cities = this.popularCitiesLst.findElements(By.tagName("li"));
        boolean f = false;
        for (WebElement e : popularCitiesLst) {
            if (e.getText().toLowerCase().contains(city.toLowerCase())) {
                //e.click();
                int attempts = 0;
                while (attempts < 4) {
                    try {
                        e.click();
                        break;
                    } catch (StaleElementReferenceException | NoSuchElementException ex) {
                        System.out.println("Attempt " + (attempts + 1) + ": Retrying finding element...");
                    }
                    attempts++;
                }
                f = true;
                break;
            }
        }
        if (!f)
            Assert.fail("unable to find given city : " + city);
    }

    @Step("Select from city : {0}")
    public void selectFromCity(String FromCity) {
        wait.until(ExpectedConditions.visibilityOf(this.selectFromCityInput));
        this.selectFromCityInput.sendKeys(FromCity);
        selectCityFromPopularCitiesList(FromCity);
    }

    @Step("List all suggested Cities")
    public void listAllSuggestedCities(String FromCity) {
        this.selectFromCityInput.sendKeys(FromCity);
        List<String> suggestedCities = this.popularCitiesLst.stream().map(WebElement::getText).collect(Collectors.toList());
        //System.out.println("All suggested cities list :: "+ Arrays.toString(suggestedCities.toArray()));
        Allure.addAttachment("Suggested cities list : ", Arrays.toString(suggestedCities.toArray()));
    }

    @Step("Select To City : {0}")
    public void selectToCity(String ToCity) {
        this.selectToCityInput.sendKeys(ToCity);
        selectCityFromPopularCitiesList(ToCity);
    }

    public void pressEsc() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();
    }

    @Step("Click Search button")
    public void clickSearch() {
        this.searchButton.click();
    }

    @Step("Launch Make My Trip")
    public void getMMTPage(String url) {
        driver.get(url);
    }

    @Override
    public boolean isAt() {
        wait.until(ExpectedConditions.visibilityOf(this.selectFromCityInput));
        return this.selectFromCityInput.isDisplayed();
    }

    @Step("Select Trip type : {0}")
    public void selectTripType(String tripType) {
        driver.findElement(By.cssSelector("li[data-cy='" + tripType + "']")).click();
    }

    @Step("Select date : {0}")
    public void selectDate(int days) {
        // Calculate the desired date (2 days after today)
        if (days == 0) {
            pressEsc();
            return;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate desiredDate = currentDate.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d"); // Format to match day of month
        String dayString = desiredDate.format(formatter);

        for (WebElement e : this.calendarDates) {
            if (e.getText().equals(dayString)) {
                e.click();
                break;
            }
        }

    }

    @Step("Select first from City in multi way : {0}")
    public void selectFromCity1(String city) {
        this.fromCity1.sendKeys(city);
        selectCityFromPopularCitiesList(city);
    }

    @Step("Select first To City in multi way : {0}")
    public void selectToCity1(String city) {
        this.toCity1.sendKeys(city);
        selectCityFromPopularCitiesList(city);
    }

    @Step("Select Second from City in multi way : {0}")
    public void selectFromCity2(String city) {
        this.fromCity2.sendKeys(city);
        selectCityFromPopularCitiesList(city);
    }

    @Step("Select Second from City in multi way : {0}")
    public void selectToCity2(String city) {
        this.toCity2.sendKeys(city);
        selectCityFromPopularCitiesList(city);
    }

    @Step("Click on Forex card")
    public ForexPage selectForexCard() {
        this.forexCardImage.click();
        return new ForexPage(driver);
    }
}
