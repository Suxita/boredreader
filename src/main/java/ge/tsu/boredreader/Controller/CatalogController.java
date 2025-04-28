package ge.tsu.boredreader.Controller;

import ge.tsu.boredreader.sql_db.entity.Book;
import ge.tsu.boredreader.sql_db.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CatalogController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Value("${books.storage.path:classpath:static/assets}")
    private String booksPath;

    private final ResourceLoader resourceLoader;
    private final BookRepository bookRepository;

    public CatalogController(ResourceLoader resourceLoader, BookRepository bookRepository) {
        this.resourceLoader = resourceLoader;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        try {

            List<Book> booksFromDb = bookRepository.findAll();

            if (booksFromDb.isEmpty()) {

                List<Book> scannedBooks = scanBooksDirectory();
                if (!scannedBooks.isEmpty()) {
                    bookRepository.saveAll(scannedBooks);
                    booksFromDb = scannedBooks;
                }
            }

            model.addAttribute("books", booksFromDb);
            return "catalog";
        } catch (Exception e) {
            logger.error("Error loading catalog", e);
            model.addAttribute("error", "Could not load the book catalog: " + e.getMessage());
            return "error";
        }
    }

    private List<Book> scanBooksDirectory() {
        List<Book> books = new ArrayList<>();

        try {
            Resource resource = resourceLoader.getResource(booksPath);
            Path booksDir;

            if (booksPath.startsWith("classpath:")) {
                booksDir = resource.getFile().toPath();
            } else {
                booksDir = Paths.get(booksPath);
            }

            if (!Files.exists(booksDir)) {
                logger.error("Books directory does not exist: {}", booksDir);
                return books;
            }

            Files.walk(booksDir)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .forEach(pdfPath -> {
                        Book book = new Book();
                        book.setFilename(pdfPath.getFileName().toString());
                        book.setTitle(extractTitle(pdfPath));
                        book.setAuthor("Unknown"); // Default author
                        book.setDescription("No description available"); // Default description
                        book.setCoverImagePath(findCoverImage(pdfPath));
                        books.add(book);
                    });
        } catch (IOException e) {
            logger.error("Error scanning books directory", e);
        }

        return books;
    }

    private String extractTitle(Path path) {
        String filename = path.getFileName().toString();
        return filename.replace(".pdf", "")
                .replaceAll("[_-]", " ");
    }

    private String findCoverImage(Path pdfPath) {
        String filename = pdfPath.getFileName().toString().replace(".pdf", "");
        Path coverPath = pdfPath.getParent().resolve(filename + ".jpg");

        if (Files.exists(coverPath)) {
            return "/assets/" + filename + ".jpg";
        }

        coverPath = pdfPath.getParent().resolve(filename + ".png");
        if (Files.exists(coverPath)) {
            return "/assets/" + filename + ".png";
        }

        return "/assets/default-cover.jpg";
    }
}