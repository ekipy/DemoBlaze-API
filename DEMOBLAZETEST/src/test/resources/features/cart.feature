Feature: Add to Cart Functionality

    Background:
        Given user membuka halaman utama
    
    Scenario: Menambahkan produk ke keranjang
        Given user sudah menambahkan produk "MacBook air" dari kategori "Laptops" ke dalam keranjang
        Then user melihat produk "MacBook air" di dalam keranjang

    Scenario: Melakukan pemesanan
        Given user sudah menambahkan produk "MacBook air" dari kategori "Laptops" ke dalam keranjang
        When user membuka halaman Keranjang dan produk "MacBook air" tampil
        And user klik tombol Place Order
        And sistem menampilkan form pemesanan
        And user mengisi form pemesanan dengan:
            | Name       | John Doe     |
            | Country    | USA          |
            | City       | New York     |
            | Credit Card| 1234-5678-9012-3456 |
            | Month      | 12           |
            | Year       | 2025         |
        And user klik tombol Purchase
        Then sistem menampilkan pesan konfirmasi pembelian