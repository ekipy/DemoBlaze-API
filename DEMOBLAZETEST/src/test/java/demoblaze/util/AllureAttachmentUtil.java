package demoblaze.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import io.qameta.allure.Attachment;

public class AllureAttachmentUtil {

    @Attachment(value = "Step Screenshot", type = "image/png")
    public static byte[] attachScreenshot(File screenshotFile) {
        try {
            return Files.readAllBytes(screenshotFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Attachment(value = "Scenario PDF", type = "application/pdf")
    public static byte[] attachPdf(File pdfFile) {
        try {
            return Files.readAllBytes(pdfFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
