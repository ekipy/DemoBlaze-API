Feature: Sign In Functionality

  Scenario: SignIn Account Creation
    Given user berada pada halaman homepage
    When user click pada tab login
    When user mengisi field username dengan "fimo" dan field password dengan "fimo"
    And user click pada tombol login
    Then user melihat nama pengguna "Welcome fimo"

  Scenario: Login Tanpa Mengisi Username dan Password
    Given user berada pada halaman homepage
    When user click pada tab login
    When user mengisi field username dengan "" dan field password dengan ""
    And user click pada tombol login
    Then user melihat alert message