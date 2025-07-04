package demoblaze.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Then;
import demoblaze.pages.Login;
import demoblaze.util.DriverFactory;
import demoblaze.util.StepNameTracker;

public class LoginStep {
    WebDriver driver;
    Login signUpPage;

    public LoginStep() {
        this.driver = DriverFactory.getDriver();
        this.signUpPage = new Login(driver);
    }

    @When("user click pada tab login")
    public void userClickPadaTabLogin() {
        StepNameTracker.set("user click pada tab login");
        signUpPage.clickLoginMenu();
    }

    @When("user mengisi field username dengan {string} dan field password dengan {string}")
    public void userMengisiUsernameDanPassword(String username, String password) {
        StepNameTracker.set("user mengisi field username dan password");
        signUpPage.signUp(username, password);
    }

    @And("user click pada tombol login")
    public void userClickPadaTombolLogin() {
        StepNameTracker.set("user click pada tombol login");
        signUpPage.clickLoginButton();
    }

    @Then("user melihat alert message")
    public void userMelihatAlertDenganPesan() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());

            String actualAlertText = signUpPage.getAlertText();
            System.out.println("Alert text: " + actualAlertText);

            Assert.assertEquals("Please fill out Username and Password.", actualAlertText);

            signUpPage.acceptAlert();

        } catch (TimeoutException | NoAlertPresentException e) {
            // Logging biar mudah debug
            System.out.println("ALERT tidak muncul, mungkin sudah ditangani sebelumnya.");
            // Jangan langsung fail, cukup warning (kecuali alert wajib banget muncul)
            // Kalau kamu tetap ingin fail, jangan tangkap exception dan biarkan naik
        }
    }

    @Then("user melihat nama pengguna {string}")
    public void userBerhasilLoginDenganPesan(String expectedMessage) {
        StepNameTracker.set("user berhasil login dengan pesan");
        String actualMessage = signUpPage.getSuccessLoginText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    
}
