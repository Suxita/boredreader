package ge.tsu.boredreader.Controller;

import ge.tsu.boredreader.entity.Book;
import ge.tsu.boredreader.entity.ChatMessage;
import ge.tsu.boredreader.repository.BookRepository;
import ge.tsu.boredreader.repository.ChatMessageRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;
    private final BookRepository bookRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatController(ChatClient chatClient, BookRepository bookRepository, ChatMessageRepository chatMessageRepository) {
        this.chatClient = chatClient;
        this.bookRepository = bookRepository;
        this.chatMessageRepository = chatMessageRepository;
    }
    private String getRecentConversationContext(Long bookId, Integer currentPage) {
        try {

            List<ChatMessage> recentMessages = chatMessageRepository
                    .findMostRecentConversationForPage(bookId, currentPage)
                    .stream()
                    .limit(5)
                    .toList();

            if (recentMessages.isEmpty()) {
                return "";
            }

            StringBuilder context = new StringBuilder("Recent conversation:\n");
            for (ChatMessage msg : recentMessages) {
                String role = msg.isAiGenerated() ? "Assistant" : "User";
                context.append(role).append(": ").append(msg.getContent()).append("\n");
            }

            return context.toString();
        } catch (Exception e) {
            logger.error("Error getting conversation context", e);
            return "";
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, Object> request) {
        logger.info("Received chat request: {}", request);

        String message = (String) request.get("message");
        Integer currentPage = (Integer) request.get("currentPage");
        String pdfContext = (String) request.get("pdfContext");
        Long bookId = null;

        try {
            bookId = Long.parseLong(request.get("bookId").toString());
        } catch (Exception e) {
            logger.error("Error parsing bookId", e);
        }

        String promptText = String.format(
                "You are an AI assistant helping with a book. Here is some content from page %d: \"%s\". User question: %s",
                currentPage,
                pdfContext.length() > 1500 ? pdfContext.substring(0, 1500) + "..." : pdfContext,
                message
        );

        String aiResponse;
        try {
            aiResponse = this.chatClient.prompt()
                    .user(promptText)
                    .call()
                    .content();

            logger.info("Got AI response successfully");
        } catch (Exception e) {
            logger.error("Error calling AI model", e);
            aiResponse = "Sorry, I encountered an error processing your request. Please try again later.";
        }

        if (bookId != null) {
            saveUserMessage(message, currentPage, bookId);
            saveAiMessage(aiResponse, currentPage, bookId);
        }

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("content", aiResponse);
        resultMap.put("result", Map.of("output", outputMap));

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<ChatMessage>> getBookChatHistory(@PathVariable Long bookId) {
        List<ChatMessage> chatHistory = chatMessageRepository.findByBookIdOrderByTimestampAsc(bookId);
        return ResponseEntity.ok(chatHistory);
    }

    private void saveUserMessage(String content, Integer pageNumber, Long bookId) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                ChatMessage message = new ChatMessage();
                message.setContent(content);
                message.setTimestamp(LocalDateTime.now());
                message.setPageNumber(pageNumber);
                message.setAiGenerated(false);
                message.setBook(bookOptional.get());
                chatMessageRepository.save(message);
            }
        } catch (Exception e) {
            logger.error("Error saving user message", e);
        }
    }

    private void saveAiMessage(String content, Integer pageNumber, Long bookId) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if (bookOptional.isPresent()) {
                ChatMessage message = new ChatMessage();
                message.setContent(content);
                message.setTimestamp(LocalDateTime.now());
                message.setPageNumber(pageNumber);
                message.setAiGenerated(true);
                message.setBook(bookOptional.get());
                chatMessageRepository.save(message);
            }
        } catch (Exception e) {
            logger.error("Error saving AI message", e);
        }
    }
}