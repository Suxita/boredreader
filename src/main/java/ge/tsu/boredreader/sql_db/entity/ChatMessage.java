package ge.tsu.boredreader.sql_db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(columnDefinition = "TEXT")
    private String content;

    @Setter
    @Getter
    private LocalDateTime timestamp;

    @Setter
    @Getter
    private Integer pageNumber;

    @Setter
    @Getter
    private boolean aiGenerated;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;


}