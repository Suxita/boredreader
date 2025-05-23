package ge.tsu.boredreader.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/static/assets/pdfs")
public class PdfController {
    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);
    private final ResourceLoader resourceLoader;
    public PdfController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String filename) {

        try {
            logger.debug("Attempting to load PDF: {}", filename);
            String resourcePath = "classpath:static/assets/pdfs/" + filename;
            logger.debug("Looking for PDF at resource path: {}", resourcePath);

            Resource resource = resourceLoader.getResource(resourcePath);
            if (!resource.exists()) {
                logger.error("PDF resource not found: {}", resourcePath);
                return ResponseEntity.notFound().build();
            }
            byte[] pdfBytes;
            try (InputStream inputStream = resource.getInputStream()) {
                pdfBytes = inputStream.readAllBytes();
            }
            logger.debug("Successfully loaded PDF, size: {} bytes", pdfBytes.length);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (IOException e) {
            logger.error("Error loading PDF: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}