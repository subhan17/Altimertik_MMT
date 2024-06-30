package org.mmt.tests;

import org.mmt.pages.FlightSearchResultPage;
import org.mmt.pages.FlightsSearchPage;
import org.mmt.pages.ForexPage;
import org.mmt.utils.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class ForexValidations extends BaseTest {
    private ConfigLoader config;
    private WebDriver driver;

    @BeforeTest
    public void setParameters() {
        config = ConfigLoader.getInstance();
        initializeDriver();
        this.driver = getDriver();

    }

    @Test
    public void forexValidations() throws InterruptedException {

        FlightsSearchPage flightsSearchPage = new FlightsSearchPage(driver);
        flightsSearchPage.getMMTPage(config.getData("url"));
        ForexPage forexPage = flightsSearchPage.selectForexCard();
        forexPage.printExchangeRate(new String[]{"EUR", "USD", "NZD"});
        forexPage.clickMultiCurrencyCardButton();
        String def = forexPage.selectAndPrintFaqQuestion("What is a Multicurrency Card?");
        forexPage.reverseAlternateWords(def);

    }
}
