package demoblaze.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Then;
import demoblaze.pages.Login;
import demoblaze.util.DriverFactory;

public class SignUpStep extends DriverFactory {
    WebDriver driver;
    Login signUpPage;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver(false); // Set to true for headless mode
        signUpPage = new Login(driver);
        System.out.println("Setting up the test environment");
    }

    @After
    public void tearDown() {
        // Close the WebDriver and clean up after tests
        if (driver != null) {
            driver.quit();
            System.out.println("Test environment cleaned up");
        }
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

    @When("user click pada tombol login")
    public void userClickPadaTombolLogin() {
        signUpPage.clickLoginButton();
    }

    @Then("user melihat nama pengguna {string}")
    public void userBerhasilLoginDenganPesan(String expectedMessage) {
        String actualMessage = signUpPage.getSuccessLoginText();
        Assert.assertEquals(expectedMessage, actualMessage);
    }
}
