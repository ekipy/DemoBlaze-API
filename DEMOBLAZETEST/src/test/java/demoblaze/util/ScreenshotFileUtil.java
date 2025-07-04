package demoblaze.util;

import io.cucumber.java.Scenario;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.image.ImageDataFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScreenshotFileUtil {

    private static final Map<String, List<StepScreenshot>> scenarioScreenshots = new HashMap<>();

    private static class StepScreenshot {
        byte[] image;
        String timestamp;
        String stepName;

        StepScreenshot(byte[] image, String stepName) {
            this.image = image;
            this.stepName = stepName;
            this.timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            }
    }

    public static void captureStepScreenshotAsBytes(WebDriver driver, Scenario scenario, String stepName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String scenarioKey = scenario.getId();

            scenarioScreenshots
                .computeIfAbsent(scenarioKey, k -> new ArrayList<>())
                .add(new StepScreenshot(screenshotBytes, stepName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File captureScreenshotAsPdf(WebDriver driver, Scenario scenario) {
        String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File outputDir = new File("build/reports/pdf/");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File pdfFile = new File(outputDir, scenarioName + "_" + timestamp + ".pdf");

        try {
            PdfWriter writer = new PdfWriter(pdfFile);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            List<StepScreenshot> screenshots = scenarioScreenshots.getOrDefault(scenario.getId(), Collections.emptyList());
            int stepNumber = 1;

            for (StepScreenshot step : screenshots) {
                if (stepNumber > 1) {
                    document.add(new com.itextpdf.layout.element.AreaBreak());
                }

                // Judul step
                Paragraph title = new Paragraph("Step " + stepNumber + " – " + step.stepName + " – " + step.timestamp)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold()
                        .setFontSize(14f);
                document.add(title);

                // Gambar
                Image img = new Image(ImageDataFactory.create(step.image));
                img.setAutoScale(true);
                document.add(img);

                stepNumber++;
            }

            document.close();
            scenarioScreenshots.remove(scenario.getId());

            return pdfFile;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}