package demoblaze.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Then;
import demoblaze.pages.Login;
import demoblaze.util.DriverFactory;

public class LoginStep extends DriverFactory {
    WebDriver driver;
    Login signUpPage;

    @Before
    public void setUp() {
        boolean headless = System.getenv("CI") != null; // Check if running in CI environment
        driver = createDriver(headless);
        signUpPage = new Login(driver);
        System.out.println("Setting up the test environment");
    }

    @After
    public void tearDown() {
        // Close the WebDriver and clean up after tests
        DriverFactory.closeDriver();
        System.out.println("Tearing down the test environment");
    }

    @Given("user berada pada halaman homepage")
    public void userBeradaPadaHalamanHomepage() {
        signUpPage.openWebsite();
    }

    @When("user click pada tab login")
    public void userClickPadaTabLogin() {
        signUpPage.clickLoginMenu();
    }

    @When("user mengisi field username dengan {string} dan field password dengan {string}")
    public void userMengisiUsernameDanPassword(String username, String password) {
        signUpPage.signUp(username, password);
    }

    @And("user click pada tombol login")
    public void userClickPadaTombolLogin() {
        signUpPage.clickLoginButton();
    }

    @Then("user melihat alert message")
    public void userMelihatAlertDenganPesan() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());

        // Ambil dan tampilkan teks alert
        String actualAlertText = signUpPage.getAlertText();
        System.out.println("Alert text: " + actualAlertText);

        // Verifikasi isi alert
        Assert.assertEquals("Please fill out Username and Password.", actualAlertText);

        signUpPage.acceptAlert();
    }

    @Then("user melihat nama pengguna {string}")
    public void userBerhasilLoginDenganPesan(String expectedMessage) {
        String actualMessage = signUpPage.getSuccessLoginText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    
}
