package demoblaze.steps;

import java.util.List;
import org.openqa.selenium.WebDriver;
import demoblaze.pages.Homepage;
import demoblaze.util.DriverFactory;
import demoblaze.util.StepNameTracker;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HomepageStep extends DriverFactory {
    WebDriver driver;
    Homepage homepage;

    public HomepageStep() {
        this.driver = getDriver();
        this.homepage = new Homepage(driver);
    }

    @Given("user membuka halaman utama")
    public void userMembukaHalamanUtama() {
        StepNameTracker.set("user membuka halaman utama");
        homepage.openWebsite();
    }

    // Scenario menampilkan list navbar pada homepage

    @Then("user melihat menu navigasi berisi:")
    public void userMelihatMenuNavigasiBerisi(List<String> items) {
        StepNameTracker.set("user melihat menu navigasi pada header");
        homepage.verifyNavbarItems(items);
    }

    @Then("user melihat kategori produk:")
    public void userMelihatKategoriProduk(List<String> kategori) {
        StepNameTracker.set("user melihat kategori produk pada sidebar");
        homepage.verifyCategoryItems(kategori);
    }
}
