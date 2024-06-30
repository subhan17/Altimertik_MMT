package org.mmt.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.mmt.pages.CompleteYourBookingPage;
import org.mmt.pages.FlightSearchResultPage;
import org.mmt.pages.FlightsSearchPage;
import org.mmt.utils.AllureTestListener;
import org.mmt.utils.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Listeners(AllureTestListener.class)
public class FlightSearch extends BaseTest {
    private ConfigLoader config;
    private WebDriver driver;

    @BeforeTest
    public void setParameters() {
        config = ConfigLoader.getInstance();
        initializeDriver();
        this.driver = getDriver();

    }

    @Description("One-Way Flight Search and Filter Analysis")
    @Test
    public void OneWayFlightSearch() throws InterruptedException {
        FlightsSearchPage flightSearchPage = new FlightsSearchPage(driver);
        flightSearchPage.getMMTPage(config.getData("url"));
        flightSearchPage.selectFromCity(config.getData("fromcity1"));//config.getData("fromcity1")
        flightSearchPage.selectToCity(config.getData("tocity1"));
        flightSearchPage.pressEsc();
        flightSearchPage.clickSearch();
        FlightSearchResultPage flightSearchResultPage = new FlightSearchResultPage(driver);
        flightSearchResultPage.printHoldOnMessage();
        flightSearchResultPage.handleLockPriceWindow();

        List<String> AirLineNames = flightSearchResultPage.getAirlineNames();
        //System.out.println("Number of Flights : "+ AirLineNames.size());
        Allure.addAttachment("Number of Flights Showing : ", String.valueOf(AirLineNames.size()));

        //Log all the flight names in ascending order by the departure time.
        flightSearchResultPage.selectOtherSort("Early Departure");
        flightSearchResultPage.handleLockPriceWindow();
        List<String> AirLineNamesAftSort = flightSearchResultPage.getAirlineNames();
        //System.out.println(Arrays.toString(AirLineNamesAftSort.toArray()));
        Allure.addAttachment("Airlines displaying in ascending order by the departure time  : ", String.valueOf(AirLineNamesAftSort));

        //From “Popular Filters”, filter the airlines by “Indigo” and log all the Flight number, Departure
        //time, Arrival time, and Price/Adult respectively
        flightSearchResultPage.selectPopularFilter(config.getData("popular"));
        List<String> flightNames = flightSearchResultPage.getAirlineNames();

        if (flightNames.stream().distinct().count() == 1) {
            List<String> flightNumbers = flightSearchResultPage.getAllFlightNumbers();
            List<String> flightDep = flightSearchResultPage.getFlightDepartureTime();
            List<String> flightArr = flightSearchResultPage.getFlightArrivalTime();
            List<String> flightPrices = flightSearchResultPage.getAllFlightPrices("oneway");

            if (flightNumbers.size() == flightDep.size()
                    && flightDep.size() == flightArr.size()
                    && flightArr.size() == flightPrices.size()) {
                ArrayList<String> Flightdetails = new ArrayList<>();
                for (int i = 0; i < flightNumbers.size(); i++) {
                    Flightdetails.add(flightNumbers.get(i) + " " + flightDep.get(i) + " " + flightArr.get(i) + " " + flightPrices.get(i));
                    //System.out.println(flightNumbers.get(i) +" "+flightDep.get(i)+" "+flightArr.get(i)+" "+flightPrices.get(i));
                }
                Allure.addAttachment("logging all the Flight number, Departure time Arrival time and Price", String.valueOf(Flightdetails));
            } else {
                Assert.fail("There is miss match in size of number of flights, price and timings");
            }
        } else {
            System.out.println("****Filter functionality is not functioning correctly******");
            Assert.fail("Filter functionality is not functioning correctly");
        }

        //Filter the “Non-Stop” and the cheapest Indigo flight. Log its Flight number and its fare
        flightSearchResultPage.SelectCheapestPrice();
        flightSearchResultPage.getFirstFlightDetails();
        String[] flightNo = flightSearchResultPage.getFirstFlightDetails();
        System.out.println("Cheapest flight number and price are : " + flightNo[0] + "::" + flightNo[1]);
        Allure.addAttachment("Cheapest flight number and price are", flightNo[0] + "::" + flightNo[1]);

        //Click on “View Prices” of the cheapest Indigo flight as in the previous point.
        flightSearchResultPage.selectViewPrice();
        CompleteYourBookingPage completebooking = flightSearchResultPage.bookSaverOption(flightNo[1]);
        completebooking.handlePriceRisePopup();

        //log the “Fare Summary” which includes “Base Fare”, “Tax and Surcharges”, and “Total Amount”.
        //completebooking.isAt();
        String basePrice = completebooking.getBasePrice();
        String taxAndSurchargePrice = completebooking.getTaxesAndSurchargesPrice();
        String TotalAmount = completebooking.getTotalAmount();
        completebooking.closeWindow();
        System.out.println("total amt : " + TotalAmount);
//        System.out.println("base price, tax and surcharge price and total amount is : "
//                +basePrice +","+taxAndSurchargePrice+","+TotalAmount );
        Allure.addAttachment("base price, tax and surcharge price and total amount is : ", basePrice + " , " + taxAndSurchargePrice + " , " + TotalAmount);

    }

}
