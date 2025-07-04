package demoblaze.pages;
import static org.junit.Assert.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Homepage {
    private WebDriverWait wait;
    private WebDriver driver;

    By navItemsLocator = By.xpath("//div[@id='navbarExample']//a[contains(@class, 'nav-link')]");
    By categoryItemsLocator = By.cssSelector("div.list-group a#itemc");
    By btnCart = By.xpath("//a[@id='cartur']");

    public Homepage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openWebsite() {
        driver.get("https://www.demoblaze.com/index.html");

        By headerLocator = By.xpath("//a[contains(text(), 'PRODUCT STORE')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
        driver.manage().window().maximize();
        System.out.println("Website Demoblaze dibuka: " + driver.getCurrentUrl());
    }

    public void verifyNavbarItems(List<String> expectedItems) {
        List<WebElement> navElements = driver.findElements(navItemsLocator);
        List<String> actualTexts = navElements.stream()
            .map(el -> el.getAttribute("innerText").replace("(current)", "").trim())
            .filter(t -> !t.isEmpty())
            .collect(Collectors.toList());

        System.out.println("NAVBAR TEXTS: " + actualTexts);

        for (String expected : expectedItems) {
            boolean found = actualTexts.stream()
                .anyMatch(text -> text.equalsIgnoreCase(expected.trim()));
            assertTrue("Navbar tidak mengandung: " + expected, found);
        }

        System.out.println("Semua item navbar terverifikasi: " + actualTexts);
    }

    public void verifyCategoryItems(List<String> expectedCategories) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoryItemsLocator));

        List<WebElement> categoryElements = driver.findElements(categoryItemsLocator);
        List<String> actualCategories = categoryElements.stream()
            .map(WebElement::getText)
            .map(String::trim)
            .collect(Collectors.toList());

        for (String expected : expectedCategories) {
            assertTrue("Kategori tidak ditemukan: " + expected, 
                actualCategories.contains(expected));
        }

        System.out.println("Semua kategori terverifikasi: " + actualCategories);
    }

    public void pilihKategori(String namaKategori) {
        WebElement kategori = driver.findElement(By.xpath("//a[@id='itemc' and text()='" + namaKategori + "']"));
        kategori.click();

        if (namaKategori.equalsIgnoreCase("Laptops")) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='hrefch' and normalize-space(text())='MacBook air']")));
        } else if (namaKategori.equalsIgnoreCase("Phones")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='hrefch' and normalize-space(text())='Samsung galaxy s6']")));
        } else if (namaKategori.equalsIgnoreCase("Monitors")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='hrefch' and normalize-space(text())='Apple monitor 24']")));
        }
    }

    public void pilihProduk(String namaProduk) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tbodyid")));
        List<WebElement> produkList = driver.findElements(By.cssSelector("#tbodyid .card-title a"));

        boolean produkDitemukan = false;
        for (WebElement produk : produkList) {
            System.out.println(" - " + produk.getText());
            if (produk.getText().trim().toLowerCase().equalsIgnoreCase(namaProduk)){
                wait.until(ExpectedConditions.elementToBeClickable(produk)).click();
                produkDitemukan = true;
                break;
            }
        }

        if(!produkDitemukan){
            throw new RuntimeException("Produk '" + namaProduk + "' tidak ditemukan di halaman.");
        }
    }

    public void bukaHalaman(String halaman) {
        if (halaman.equalsIgnoreCase("Cart")) {
            wait.until(ExpectedConditions.elementToBeClickable(btnCart)).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
            System.out.println("[DEBUG] Cart page dibuka, tunggu isi keranjang...");
        } 
    }
}
