package ge.tsu.boredreader.Service;

import ge.tsu.boredreader.sql_db.entity.Book;
import ge.tsu.boredreader.sql_db.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void initBooks() {

        if (bookRepository.count() == 0) {
            Book book1 = new Book();
            book1.setTitle("Evangelion");
            book1.setAuthor("Hideaki Anno");
            book1.setDescription("Apocalyptic Mecha Psychological drama");
            book1.setCoverImagePath("/static/assets/covers/eva.png");
            book1.setFilename("/static/assets/pdfs/Eva.pdf");
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setTitle("Sherlock Holmes");
            book2.setAuthor("A. Conan Doyle");
            book2.setDescription("Classic detective stories");
            book2.setCoverImagePath("/static/assets/covers/sherlock.jpg");
            book2.setFilename("/static/assets/pdfs/Sherlock.pdf");
            bookRepository.save(book2);


            Book book3 = new Book();
            book3.setTitle("Harry Potter");
            book3.setAuthor("J.K. Rowling");
            book3.setDescription("a boy who survived");
            book3.setCoverImagePath("/static/assets/covers/hpp.jpg");
            book3.setFilename("/static/assets/pdfs/hpp.pdf");
            bookRepository.save(book3);

            Book book4 = new Book();
            book4.setTitle("Jojo");
            book4.setAuthor("Hirohiko Araki");
            book4.setDescription("to be continued...");
            book4.setCoverImagePath("/static/assets/covers/jojo.png");
            book4.setFilename("/static/assets/pdfs/jojo.pdf");
            bookRepository.save(book4);
            log.info("Added sample books to database");
        }
    }
}
//
