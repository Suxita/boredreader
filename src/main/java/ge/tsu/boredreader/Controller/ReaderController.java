package ge.tsu.boredreader.Controller;

import ge.tsu.boredreader.entity.Book;
import ge.tsu.boredreader.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/reader")
public class ReaderController {

    private final BookRepository bookRepository;

    public ReaderController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public String reader(@PathVariable Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "reader";
        } else {
            return "redirect:/catalog";
        }
    }

    @GetMapping
    public String readerDefault() {
        return "redirect:/catalog";
    }
}