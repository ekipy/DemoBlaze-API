package demoblaze.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;

public class ScreenshotUtil {

    public static void captureScreenshotAsPdf(WebDriver driver, Scenario scenario) {
        try {
            // Tangani alert kalau muncul sebelum ambil screenshot
            if (isAlertPresent(driver)) {
                driver.switchTo().alert().accept();
                System.out.println("⚠ Alert ditemukan dan ditutup sebelum ambil screenshot.");
            }
            
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            byte[] screenshotBytes = Files.readAllBytes(srcFile.toPath());

            // Kirim ke Allure
            attachToAllure(screenshotBytes);

            // Simpan file screenshot ke folder lokal (opsional)
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
            File destFile = new File("build/reports/screenshots/" + scenarioName + "_" + timestamp + ".png");
            FileUtils.copyFile(srcFile, destFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "Step Screenshot", type = "image/png")
    private static byte[] attachToAllure(byte[] bytes) {
        return bytes;
    }

    private static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
