package ge.tsu.boredreader.sql_db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String coverImagePath;
    private String filename;

    public Book() {}

    public Book(String title, String author, String description,
                String coverImagePath, String filename) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverImagePath = coverImagePath;
        this.filename = filename;
    }

}