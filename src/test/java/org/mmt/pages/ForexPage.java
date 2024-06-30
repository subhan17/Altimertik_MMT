package org.mmt.pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForexPage extends AbstractPage {
    @FindBy(css = "ul[class='Marqueestyle__MarqueeRibbon-sc-1temmac-4 gXzxfS']")
    private List<WebElement> exchangeRatesList;
    @FindBy(css = "div.LandingFaqstyle__TabItem-o5ukui-5.ksAXrz")
    private WebElement multiCurrencyFAQButton;
    @FindBy(css = ".LandingFaqstyle__LandingFaqList-o5ukui-2.gERbGR")
    private WebElement multiCurrencyFAQlist;


    public ForexPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        return false;
    }

    public void printExchangeRate(String[] CountryCodes) {
        //List<String> ForexRates = null;
        List<String> ForexRates1 = this.exchangeRatesList.get(0).findElements(By.tagName("li")).stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> ForexRates2 = this.exchangeRatesList.get(1).findElements(By.tagName("li")).stream().map(WebElement::getText).collect(Collectors.toList());
        //List<String> allForexRates = Stream.concat(ForexRates1.stream(), ForexRates2.stream()).collect(Collectors.toList());

        System.out.println("All Values" + Arrays.toString(ForexRates1.toArray()));
        Map<String, String> forexMap = new HashMap<>();

        for (String entry : ForexRates1) {
            if (!entry.contains("100+ Currencies")) {
                String[] parts = entry.split("₹");
                forexMap.put(parts[0].trim(), parts[1].trim());
            }
        }
        boolean allFound = true;
        for (String country : CountryCodes) {
            if (forexMap.containsKey(country)) {
                System.out.println("Country: " + country + ", Currency: " + forexMap.get(country));
                Allure.addAttachment("Country " + country, "Currency: " + forexMap.get(country));
            } else {
                allFound = false;
                System.out.println("Country: " + country + " is not available in the list.");
                Allure.addAttachment("THis country is not found in the forex list", country);
            }
        }
        if (!allFound) Assert.fail("Forex values of given country codes not found : " + Arrays.toString(CountryCodes));
    }

    public void clickMultiCurrencyCardButton() {
        //this.multiCurrencyFAQButton.click();
        javascriptExecutor.executeScript("arguments[0].click()", this.multiCurrencyFAQButton);
    }

    public String selectAndPrintFaqQuestion(String question) {
        boolean qFound = false;
        String def = "";
        List<WebElement> faqs = this.multiCurrencyFAQlist.findElements(By.tagName("li"));
        for (WebElement e : faqs) {
            String a = e.getText().toLowerCase();
            if (e.getText().toLowerCase().contains(question.toLowerCase())) {
                try {
                    javascriptExecutor.executeScript("arguments[0].click()", e);
                    Thread.sleep(500);
                    def = e.findElement(By.cssSelector(".pointCtr.open_forex")).getText();
                    qFound = true;
                    break;
                } catch (Exception ignored) {
                }
            }
        }
        System.out.println("definition is : " + def);
        Allure.addAttachment("Definition is : ", def);
        return def;
    }

    public void reverseAlternateWords(String paragraph) {
        String[] words = paragraph.split(" ");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i % 2 != 0) {
                words[i] = new StringBuilder(words[i]).reverse().toString();
            }
            result.append(words[i]).append(" ");
        }
        String finalReversedString = result.toString().trim();
        System.out.println("Print reverse : " + finalReversedString);
        Allure.addAttachment("Reversed Definition is : ", finalReversedString);
    }


}
