package ge.tsu.boredreader.sql_db.repository;

import ge.tsu.boredreader.sql_db.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUserUsernameOrderByTimestampDesc(String username);
    List<ChatMessage> findByBookIdOrderByTimestampAsc(Long bookId);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.book.id = :bookId AND cm.timestamp >= :timeThreshold ORDER BY cm.timestamp ASC")
    List<ChatMessage> findRecentMessagesByBookId(
            @Param("bookId") Long bookId,
            @Param("timeThreshold") LocalDateTime timeThreshold);

    long countByBookId(Long bookId);


    void deleteByBookId(Long bookId);


    @Query("SELECT cm FROM ChatMessage cm WHERE cm.book.id = :bookId AND cm.pageNumber = :pageNumber " +
            "ORDER BY cm.timestamp DESC")
    List<ChatMessage> findMostRecentConversationForPage(
            @Param("bookId") Long bookId,
            @Param("pageNumber") Integer pageNumber);

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.book.id = :bookId " +
            "AND LOWER(cm.content) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "ORDER BY cm.timestamp ASC")
    List<ChatMessage> findMessagesByContentContaining(
            @Param("bookId") Long bookId,
            @Param("searchText") String searchText);
}