package demoblaze.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {
    WebDriver driver;
    WebDriverWait wait;

    By loginMenu = By.id("login2");
    By loginUsername = By.id("loginusername");
    By loginPassword = By.id("loginpassword");
    By loginButton = By.xpath("//button[contains(text(),'Log in')]");
    By successLogin = By.id("nameofuser");

    public Login(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openWebsite() {
        driver.get("https://www.demoblaze.com/");
    }

    public void clickLoginMenu() {
        driver.findElement(loginMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginUsername));
    }

    public void signUp(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(loginUsername)).sendKeys(username);
        driver.findElement(loginPassword).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public String getSuccessLoginText() {
        WebElement usernameDisplay = wait.until(ExpectedConditions.visibilityOfElementLocated(successLogin));
        return usernameDisplay.getText();
    }
    
}
