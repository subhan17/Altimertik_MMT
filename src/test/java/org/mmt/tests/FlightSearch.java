package org.mmt.tests;

import org.mmt.pages.FlightsSearchPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FlightSearch extends AbstractTest {


    //private FlightReservationTestData testData;

//    @BeforeTest
//    @Parameters("testDataPath")
//    public void setParameters(String testDataPath){
//        //this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
//    }

    @Test
    public void OneWayFlightSearch() throws InterruptedException {
        FlightsSearchPage fsp = new FlightsSearchPage(driver);
        fsp.getMMTPage(driver);
        Thread.sleep(2000);
        fsp.selectFromCity("Delhi");
        fsp.selectToCity("Hyderabad");
        fsp.pressEsc(driver);
        fsp.clickSearch();

    }


}
