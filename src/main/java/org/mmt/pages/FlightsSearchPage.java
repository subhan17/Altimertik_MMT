package org.mmt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class FlightsSearchPage extends AbstractPage{
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


    public FlightsSearchPage(WebDriver driver){
        super(driver);
        //PageFactory.initElements(driver, this);
    }

    public void selectCityFromPopularCitiesList(String city){
        //List<WebElement> Cities = this.popularCitiesLst.findElements(By.tagName("li"));
        for (WebElement e : popularCitiesLst) {
            System.out.println(e.getText());
            //String city = e.getText();
            if (e.getText().contains(city)){
                e.click();
                break;
            }
        }
    }
    public void selectFromCity(String FromCity){
        this.selectFromCityInput.sendKeys(FromCity);
        selectCityFromPopularCitiesList(FromCity);
   }
    public void selectToCity(String ToCity){
        this.selectToCityInput.sendKeys(ToCity);
        selectCityFromPopularCitiesList(ToCity);
    }

    public void pressEsc(WebDriver driver){
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    public void clickSearch(){
        this.searchButton.click();
    }

    public void getMMTPage(WebDriver driver){
        driver.get("https://www.makemytrip.com/");
    }
    public static void main(String[] arg){

//        driver = new ChromeDriver();
//        driver.get("https://www.makemytrip.com/");
//        driver.manage().window().maximize();
//        FlightsSearchPage fsp = new FlightsSearchPage();
//        fsp.selectFromCity("Delhi");
//        fsp.selectToCity("Hyderabad");
//        fsp.pressEsc(driver);
//        fsp.clickSearch();

    }

    @Override
    public boolean isAt() {
        return false;
    }
}
