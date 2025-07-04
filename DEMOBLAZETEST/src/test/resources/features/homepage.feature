Feature: Homepage Functionality

  Background:
    Given user membuka halaman utama

  Scenario: Menampilkan menu navigasi dan kategori produk di halaman utama
    Then user melihat menu navigasi berisi:
      | Home      |
      | Contact   |
      | About us  |
      | Cart      |
      | Log in    |
      | Sign up   |
    And user melihat kategori produk:
      | Phones    |
      | Laptops   |
      | Monitors  |