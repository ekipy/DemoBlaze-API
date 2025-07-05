package demoblaze.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;

    By btnAddtoCart = By.cssSelector(".btn.btn-success.btn-lg");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickBtnAddtoCart() {
        WebElement btnAdd = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']")));
        btnAdd.click();

        // Tunggu hingga alert muncul, lalu accept
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        System.out.println("[DEBUG] Alert text: " + alert.getText());
        alert.accept();

        try {
            Thread.sleep(2000); // delay 2 detik untuk stabilisasi AJAX
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void bukaCartDariHalamanProduk() {
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur")));
        cartButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
        System.out.println("[DEBUG] Cart page dibuka dari halaman produk.");
    }
}
