Feature: SignUp Functionality

  Scenario: SignUp Account Creation
    Given user berada pada halaman homepage
    When user click pada tab login
    When user mengisi field username dengan "fimo" dan field password dengan "fimo"
    And user click pada tombol login
    Then user melihat nama pengguna "Welcome fimo"