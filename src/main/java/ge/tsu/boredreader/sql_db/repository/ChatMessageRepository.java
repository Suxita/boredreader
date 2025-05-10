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

    List<ChatMessage> findByBookIdOrderByTimestampAsc(Long bookId);


    List<ChatMessage> findByBookIdAndPageNumberOrderByTimestampAsc(Long bookId, Integer pageNumber);


    List<ChatMessage> findByUserIdOrderByTimestampDesc(Long userId);


    List<ChatMessage> findByBookIdAndAiGeneratedTrueOrderByTimestampAsc(Long bookId);


    List<ChatMessage> findByBookIdAndAiGeneratedFalseOrderByTimestampAsc(Long bookId);


    @Query("SELECT cm FROM ChatMessage cm WHERE cm.book.id = :bookId AND cm.timestamp >= :timeThreshold ORDER BY cm.timestamp ASC")
    List<ChatMessage> findRecentMessagesByBookId(
            @Param("bookId") Long bookId,
            @Param("timeThreshold") LocalDateTime timeThreshold);

    // Count messages by book
    long countByBookId(Long bookId);


    void deleteByBookId(Long bookId);


    @Query("SELECT cm FROM ChatMessage cm WHERE cm.book.id = :bookId AND cm.pageNumber = :pageNumber " +
            "ORDER BY cm.timestamp DESC")
    List<ChatMessage> findMostRecentConversationForPage(
            @Param("bookId") Long bookId,
            @Param("pageNumber") Integer pageNumber);

    // Find messages containing specific text (useful for searching conversations)
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.book.id = :bookId " +
            "AND LOWER(cm.content) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "ORDER BY cm.timestamp ASC")
    List<ChatMessage> findMessagesByContentContaining(
            @Param("bookId") Long bookId,
            @Param("searchText") String searchText);
}