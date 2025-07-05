package demoblaze.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cart {

    WebDriver driver;
    WebDriverWait wait;
    List<WebElement> produkList;
    
    By btnPlaceOrder = By.cssSelector(".btn.btn-success");

    public Cart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean produkAdaDalamKeranjang(String namaProduk) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        // Coba polling selama beberapa detik
        int maxTries = 10;
        int intervalMs = 500;

        for (int i = 0; i < maxTries; i++) {
            List<WebElement> produkList = driver.findElements(By.cssSelector("#tbodyid > tr > td:nth-child(2)"));
            System.out.println("[DEBUG] Percobaan " + (i+1) + " - Jumlah produk di keranjang: " + produkList.size());

            for (WebElement produk : produkList) {
                String nama = produk.getText().trim();
                System.out.println("[DEBUG] Produk di keranjang: " + nama);
                if (nama.equalsIgnoreCase(namaProduk.trim())) {
                    return true;
                }
            }

            try {
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void clickBtnPlaceOrder() {
        // Wait for the place order button to be clickable
        wait.until(driver -> driver.findElement(btnPlaceOrder).isDisplayed() && driver.findElement(btnPlaceOrder).isEnabled());
        driver.findElement(btnPlaceOrder).click();
    }

}
