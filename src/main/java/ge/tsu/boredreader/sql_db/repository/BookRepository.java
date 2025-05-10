package ge.tsu.boredreader.sql_db.repository;

import ge.tsu.boredreader.sql_db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}