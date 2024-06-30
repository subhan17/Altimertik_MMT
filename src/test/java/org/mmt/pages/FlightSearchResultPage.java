package org.mmt.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.mmt.utils.AllureTestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FlightSearchResultPage extends AbstractPage {
    @FindBy(xpath = "//button[text()='OKAY, GOT IT!']")
    private WebElement lockPriceOkButton;
    @FindBy(css = ".boldFont.blackText.airlineName")
    private List<WebElement> flightNames;
    @FindBy(css = "div[class='flexOne timeInfoLeft'] p[class='appendBottom2 flightTimeInfo']")
    private List<WebElement> departureTimes;
    @FindBy(xpath = "//p[text()='Other Sort']")
    private WebElement otherSort;
    @FindBy(css = ".sortDropDown li")
    private List<WebElement> otherSortList;
    @FindBy(css = "div[class='filtersOuter']:nth-child(2) p[class='checkboxTitle']")
    private List<WebElement> popularFiltersSection;
    @FindBy(css = ".fliCode")
    private List<WebElement> flightCodes;
    @FindBy(css = "div[class='flexOne timeInfoRight'] p[class='appendBottom2 flightTimeInfo']")
    private List<WebElement> arrivalTimes;

    @FindBy(css = "div[class='blackText fontSize18 blackFont white-space-no-wrap clusterViewPrice'] span")
    private List<WebElement> flightPrices;
    @FindBy(css = "p[class='blackText fontSize18 blackFont white-space-no-wrap']")
    private List<WebElement> flightPricesMultiWay;

    @FindBy(xpath = "//p[text()='Cheapest']")
    private WebElement cheapestFlightPrice;
    @FindBy(css = ".listingCard.appendBottom5")
    private List<WebElement> flightListings;
    @FindBy(xpath = "(//div[@class='listingCard  appendBottom5'])[1]//button")
    private WebElement firstViewPriceButton;

    @FindBy(css = "div[class='fareFamilyCardWrapper cta-wrapper glider-slide active visible left-1'] span[class='fontSize18 blackFont appendRight5']")
    private WebElement saverPriceLabel;

    @FindBy(css = "div[class='fareFamilyCardWrapper cta-wrapper glider-slide active visible left-1'] button[class='lato-black button buttonPrimary buttonBig fontSize14']")
    private WebElement saverBookNowButton;
    @FindBy(css = "img[alt=\"MMT's LOGO\"]")
    private WebElement mmtLogo;
    @FindBy(css = "div.rangeslider__fill")
    private WebElement priceBar;
    @FindBy(css = "div[class='filtersOuter mapSlider appendBottom12 harish'] div[class='rangeslider__handle']")
    private WebElement priceBarSlider;
    @FindBy(css = ".blackFont.fontSize22.textCenter.appendTop65")
    private WebElement holdOnMessage;

    public FlightSearchResultPage(WebDriver driver) {
        super(driver);
    }

    public void handleLockPriceWindow() {
        try {
            if (this.lockPriceOkButton.isDisplayed())
                this.lockPriceOkButton.click();
            takeScreenshotAndAttachToAllure("lockPrice");
        } catch (Exception ignored) {
        }

    }

    @Override
    public boolean isAt() {
        return !this.flightNames.isEmpty();
    }

    @Step("Get all AirLine Names")
    public List<String> getAirlineNames() {
        List<String> airLineNames = this.flightNames.stream().map(WebElement::getText).collect(Collectors.toList());
        //Allure.addAttachment("Printing all Airline Names : ",Arrays.toString(airLineNames.toArray()));
        return airLineNames;
    }

    @Step("Get Flights sorted with departure time")
    public void getFlightsWithSortedDepartureTimes() {

        List<String> dTime = this.departureTimes.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("Printing Departure Times : " + Arrays.toString(dTime.toArray()));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Collections.sort(dTime, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                LocalTime t1 = LocalTime.parse(s1, timeFormatter);
                LocalTime t2 = LocalTime.parse(s2, timeFormatter);
                return t1.compareTo(t2);
            }
        });
        Allure.addAttachment("Printing sorted departure times : ", Arrays.toString(dTime.toArray()));
//        System.out.println("Sorted list based on time:");
//        for (String t : dTime) {
//            System.out.println(t);
//        }
    }

    @Step("Select the type of OTHER sort")
    public void selectOtherSort(String sortBy) {
        this.otherSort.click();
        for (WebElement e : this.otherSortList) {
            if (e.getText().toLowerCase().contains(sortBy.toLowerCase())) {
                e.click();
                break;
            }
        }
    }

    @Step("Select the Popular filter :{0}")
    public void selectPopularFilter(String filterBy) {
        for (WebElement e : this.popularFiltersSection) {
            if (e.getText().toLowerCase().contains(filterBy.toLowerCase())) {
                //e.click();
                javascriptExecutor.executeScript("arguments[0].click()", e);
                break;
            }
        }
    }

    @Step("Get All FLight Numbers")
    public List<String> getAllFlightNumbers() {
        return this.flightCodes.stream().map(WebElement::getText).collect(Collectors.toList());
        //System.out.println("Printing all flight numbers/codes : "+Arrays.toString(filghtNumbers.toArray()));
    }

    @Step("Get All Flights Arrival Time")
    public List<String> getFlightArrivalTime() {
        return this.arrivalTimes.stream().map(WebElement::getText).collect(Collectors.toList());
        //System.out.println("Printing all flights Arrival time : "+Arrays.toString(flightArrTime.toArray()));

    }

    @Step("Get All FLight Prices for the Fly way type : {0}")
    public List<String> getAllFlightPrices(String wayType) {
        //System.out.println("Printing all flights Arrival time : "+Arrays.toString(flightPrices.toArray()));
        List<String> flightPrices = null;
        switch (wayType.toLowerCase()) {
            case "oneway":
                flightPrices = this.flightPrices.stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            case "mulitiCityTrip":
                flightPrices = this.flightPricesMultiWay.stream().map(WebElement::getText).collect(Collectors.toList());
                break;
            default:
                //Assert.assertEquals(actualTitle,expectedTitle, "pCloudy Login Test Failed");
        }
        return flightPrices;
    }

    @Step("Get All FLights Departure time")
    public List<String> getFlightDepartureTime() {
        //System.out.println("Printing all flights Arrival time : "+Arrays.toString(flightPrices.toArray()));
        return this.departureTimes.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    @Step("Click cheapest price widget")
    public void SelectCheapestPrice() {
        this.cheapestFlightPrice.click();
    }

    @Step("Get First flight details")
    public String[] getFirstFlightDetails() {
        String[] fd = new String[2];
        fd[0] = this.flightCodes.get(0).getText();
        fd[1] = this.flightPrices.get(0).getText();
        return fd;
    }

    @Step("Click first flight view price button")
    public void selectViewPrice() {
//       for(WebElement e: this.viewPriceSection){
//           if (e.getText().toLowerCase().contains(price.toLowerCase())){
//               //e.findElement(By.tagName("button")).click();
//               javascriptExecutor.executeScript("arguments[0].click()",e);
//               break;
//           }
//       }
        this.firstViewPriceButton.click();
    }

    @Step("Click on Book Now button of saver option")
    public CompleteYourBookingPage bookSaverOption(String price) {
        wait.until(ExpectedConditions.visibilityOf(this.saverPriceLabel));
        if (this.saverPriceLabel.getText().toLowerCase().contains(price.toLowerCase())) {
            this.saverBookNowButton.click();
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            return new CompleteYourBookingPage(driver);
        } else {
            System.out.println("Price verification check failed");

        }
        return null;
    }

    @Step("Click on MMT logo")
    public void clickMMTLogo() {
        this.mmtLogo.click();
    }

    @Step("Print the message Hold on...")
    public void printHoldOnMessage() {
        //wait.until(ExpectedConditions.visibilityOf(this.holdOnMessage));
        String msg = this.holdOnMessage.getText();
        takeScreenshotAndAttachToAllure("holdOnMessage");
        System.out.println("hold on message : " + msg);
    }

    @Step("Drag the price slider to : {drag}")
    public void dragPriceSlider(String drag) throws InterruptedException {
        int barWidth = this.priceBar.getSize().getWidth();
        Actions actions = new Actions(driver);
//        actions.dragAndDropBy(this.priceBarSlider,0,0).perform();
//        Thread.sleep(1000);
//        actions.dragAndDropBy(this.priceBarSlider,barWidth,0).perform();

        switch (drag.toLowerCase()) {
            case "max":
                actions.dragAndDropBy(this.priceBarSlider, barWidth, 0).perform();
                break;
            case "min":
                actions.dragAndDropBy(this.priceBarSlider, -barWidth, 0).perform();
                break;
            default:
                System.out.println("Please provide correct switch argument");

        }

    }

}
