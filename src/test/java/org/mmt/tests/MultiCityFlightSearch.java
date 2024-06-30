package org.mmt.tests;

import org.mmt.pages.FlightsSearchPage;
import org.mmt.pages.FlightSearchResultPage;
import org.mmt.utils.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class MultiCityFlightSearch extends BaseTest {
    private ConfigLoader config;
    private WebDriver driver;

    @BeforeTest
    public void setParameters() {
        config = ConfigLoader.getInstance();
        initializeDriver();
        this.driver = getDriver();

    }

    @Test
    public void multiWaySearch() throws InterruptedException {

        FlightsSearchPage flightsSearchPage = new FlightsSearchPage(driver);
        flightsSearchPage.getMMTPage(config.getData("url"));
        flightsSearchPage.listAllSuggestedCities(config.getData("fromcity1").substring(0, 2));
        flightsSearchPage.pressEsc();
        flightsSearchPage.selectTripType(config.getData("triptype"));
        flightsSearchPage.selectFromCity1(config.getData("fromcity1"));
        flightsSearchPage.selectToCity1(config.getData("tocity1"));
        flightsSearchPage.selectDate(0);
        flightsSearchPage.selectFromCity2(config.getData("tocity1"));
        flightsSearchPage.selectToCity2(config.getData("tocity1"));
        flightsSearchPage.selectDate(2);
        flightsSearchPage.clickSearch();
        FlightSearchResultPage flightSearchResultPage = new FlightSearchResultPage(driver);
        //flightSearchResultPage.printHoldOnMessage();
        //flightSearchResultPage.isAt();
        flightSearchResultPage.dragPriceSlider("min");
        Thread.sleep(1000);
        flightSearchResultPage.dragPriceSlider("max");
        //log all the flight numbers and prices in ascending order of departure time
        flightSearchResultPage.handleLockPriceWindow();
        List<String> flightNames = flightSearchResultPage.getAirlineNames();
        List<String> flightPrices = flightSearchResultPage.getAllFlightPrices("mulitiCityTrip");
        if (flightNames.size() == flightPrices.size()) {
            System.out.println("****log all the Flight number and Price******");
            for (int i = 0; i < flightNames.size(); i++) {
                System.out.println(flightNames.get(i) + ":" + flightPrices.get(i));
            }
        } else {
            System.out.println("****Incorrect Filter, miss match in no of flights and no of prices displaying ******");
        }
    }
}
