package demoblaze.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    By formPlaceOrder = By.xpath("//div[@id='orderModal']//div[@class='modal-header']");
    By fieldInputName = By.cssSelector("#name");
    By fieldInputCountry = By.cssSelector("#country");
    By fieldInputCity = By.cssSelector("#city");
    By fieldInputCreditCard = By.cssSelector("#card");
    By fieldInputMonth = By.cssSelector("#month");
    By fieldInputYear = By.cssSelector("#year");
    By btnPurchase = By.cssSelector("body > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > button:nth-child(2)");
    By confirmPurchase = By.cssSelector(".confirm.btn.btn-lg.btn-primary");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void displayForm(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(formPlaceOrder));
    }

    public void inputForm(String name, String country, String city, String creditCard, String month, String year) {
        // Wait for the input fields to be visible and enabled
        wait.until(driver -> driver.findElement(fieldInputName).isDisplayed() && driver.findElement(fieldInputName).isEnabled());
        driver.findElement(fieldInputName).sendKeys(name);
        
        wait.until(driver -> driver.findElement(fieldInputCountry).isDisplayed() && driver.findElement(fieldInputCountry).isEnabled());
        driver.findElement(fieldInputCountry).sendKeys(country);
        
        wait.until(driver -> driver.findElement(fieldInputCity).isDisplayed() && driver.findElement(fieldInputCity).isEnabled());
        driver.findElement(fieldInputCity).sendKeys(city);
        
        wait.until(driver -> driver.findElement(fieldInputCreditCard).isDisplayed() && driver.findElement(fieldInputCreditCard).isEnabled());
        driver.findElement(fieldInputCreditCard).sendKeys(creditCard);
        
        wait.until(driver -> driver.findElement(fieldInputMonth).isDisplayed() && driver.findElement(fieldInputMonth).isEnabled());
        driver.findElement(fieldInputMonth).sendKeys(month);
        
        wait.until(driver -> driver.findElement(fieldInputYear).isDisplayed() && driver.findElement(fieldInputYear).isEnabled());
        driver.findElement(fieldInputYear).sendKeys(year);
    }

    public void clickBtnPurchase() {
        // Wait for the purchase button to be clickable
        wait.until(driver -> driver.findElement(btnPurchase).isDisplayed() && driver.findElement(btnPurchase).isEnabled());
        driver.findElement(btnPurchase).click();
    }

    public void confirmPurchase() {
        // Wait for the confirmation button to be clickable
        wait.until(driver -> driver.findElement(confirmPurchase).isDisplayed() && driver.findElement(confirmPurchase).isEnabled());
        driver.findElement(confirmPurchase).click();
    }
    
}
