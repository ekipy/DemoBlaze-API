package demoblaze.steps;

import org.openqa.selenium.WebDriver;

import demoblaze.pages.Cart;
import demoblaze.pages.Homepage;
import demoblaze.pages.Login;
import demoblaze.util.DriverFactory;
import demoblaze.util.StepNameTracker;
import io.cucumber.java.en.Given;

public class CommonStep extends DriverFactory {
    WebDriver driver;
    Homepage homepage;
    Login loginPage;
    Cart cartPage;

    @Given("user berada pada halaman homepage")
    public void userBeradaPadaHalamanHomepage() {
        StepNameTracker.set("user berada pada halaman homepage");
        driver = getDriver();
        loginPage = new Login(driver);
        loginPage.openWebsite();   
    }
}
