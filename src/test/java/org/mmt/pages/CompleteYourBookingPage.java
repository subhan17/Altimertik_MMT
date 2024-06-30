package org.mmt.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

public class CompleteYourBookingPage extends AbstractPage {
    @FindBy(xpath = "//button[@type='USERCONSENT']")
    private List<WebElement> continueButton;
    @FindBy(xpath = "//span[text()='Base Fare']/parent::div/following-sibling::span")
    private WebElement baseFarePrice;

    @FindBy(xpath = "//span[text()='Taxes and Surcharges']/parent::div/following-sibling::span")
    private WebElement TaxesAndChargesPrice;
    @FindBy(css = "p[class='fareRow'] span[class='fontSize16 blackFont']:nth-child(2)")
    private WebElement totalAmount;
    @FindBy(xpath = "//p[text()='Fare Summary']")
    private WebElement fareSummarylabel;

    public CompleteYourBookingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        wait.until(ExpectedConditions.visibilityOf(this.fareSummarylabel));
        return this.fareSummarylabel.isDisplayed();
    }

    public void handlePriceRisePopup() {
        if (!this.continueButton.isEmpty()) {
            this.continueButton.get(0).click();
        }

    }

    public String getBasePrice() {
        return this.baseFarePrice.getText();
    }

    public String getTaxesAndSurchargesPrice() {
        return this.TaxesAndChargesPrice.getText();
    }

    public String getTotalAmount() {
        return this.totalAmount.getText();
    }
}
