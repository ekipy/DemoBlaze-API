package demoblaze.steps;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import demoblaze.pages.Cart;
import demoblaze.pages.CheckoutPage;
import demoblaze.pages.Homepage;
import demoblaze.pages.ProductPage;
import demoblaze.util.DriverFactory;
import demoblaze.util.StepNameTracker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartStep {

    WebDriver driver = DriverFactory.getDriver();

    Cart cartStep = new Cart(driver);
    Homepage homepageStep = new Homepage(driver);
    ProductPage productPageStep = new ProductPage(driver);
    CheckoutPage checkoutPageStep = new CheckoutPage(driver);

    @Given("user sudah menambahkan produk {string} dari kategori {string} ke dalam keranjang")
    public void userSudahMenambahkanProdukKeKeranjang(String produk, String kategori) {
        StepNameTracker.set("Tambahkan Produk kedalam Keranjang");
        homepageStep.pilihKategori(kategori);
        homepageStep.pilihProduk(produk);
        productPageStep.clickBtnAddtoCart();

        productPageStep.bukaCartDariHalamanProduk();
    }

    @Then("user melihat produk {string} di dalam keranjang")
    public void validasiKeranjang(String namaProduk) {
        StepNameTracker.set("Produk Berhasil Ditambahkan kedalam Keranjang");
        assertTrue("Produk tidak ditemukan pada keranjang: "+ namaProduk, cartStep.produkAdaDalamKeranjang(namaProduk));
    }

    @When("user membuka halaman Keranjang dan produk {string} tampil")
    public void checkKeranjang(String produkNama){
        StepNameTracker.set("Produk Berhasil Ditambahkan kedalam Keranjang");
        assertTrue("Produk tidak ditemukan pada keranjang :" + produkNama, cartStep.produkAdaDalamKeranjang(produkNama));
    }

    @When("user klik tombol Place Order")
    public void checkOutOrder(){
        StepNameTracker.set("User click Tombol Place Order");
        cartStep.clickBtnPlaceOrder();
    }

    @And("sistem menampilkan form pemesanan")
    public void tampilForm(){
        StepNameTracker.set("User melihat form pemesanan");
        checkoutPageStep.displayForm();
    }

    @And("user mengisi form pemesanan dengan:")
    public void userMengisiFormPemesananDengan(DataTable data) {
        StepNameTracker.set("User mengisi form pemesanan");
        Map<String, String> form = data.asMap();
        checkoutPageStep.inputForm(
            form.get("Name"), 
            form.get("Country"), 
            form.get("City"), 
            form.get("Credit Card"), 
            form.get("Month"), 
            form.get("Year")
        ); 
    }

    @And("user klik tombol Purchase")
    public void klikPurchase(){
        StepNameTracker.set("User submit Pemesanan");
        checkoutPageStep.clickBtnPurchase();
    }

    @Then("sistem menampilkan pesan konfirmasi pembelian")
    public void confirmPurchase(){
        StepNameTracker.set("User mengkonfirmasi pembelian");
        checkoutPageStep.confirmPurchase();
    }

}
