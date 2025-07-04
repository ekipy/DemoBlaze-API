package demoblaze.util;

import java.io.File;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    WebDriver driver;

    @Before
    public void setUp() {
        boolean headless = System.getenv("CI") != null; // Check if running in CI environment
        driver = DriverFactory.createDriver(headless);
        driver.manage().window().maximize(); // Maximize the browser window
        driver.manage().deleteAllCookies(); // Clear cookies before each test
        System.out.println("Setting up the test environment");
    }

    @AfterStep
    public void AfterStep(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        ScreenshotUtil.captureScreenshotAsPdf(driver, scenario); // PNG ke Allure
        ScreenshotFileUtil.captureStepScreenshotAsBytes(driver, scenario, StepNameTracker.get()); // Capture screenshot for each step
    }

    @After
    public void tearDown(Scenario scenario) {
        StepNameTracker.clear(); // Clear step name tracker after scenario

        File pdf = ScreenshotFileUtil.captureScreenshotAsPdf(driver, scenario);
        if (pdf != null) {
            AllureAttachmentUtil.attachPdf(pdf); // 📎 Ini wajib agar PDF muncul
        }
        DriverFactory.closeDriver();
        System.out.println("Tearing down the test environment");
    }
    
}
