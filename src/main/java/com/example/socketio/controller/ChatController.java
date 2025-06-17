package com.example.socketio.controller;

import com.example.socketio.model.ChatMessage;
import com.example.socketio.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;
import java.util.List;

@Controller // Marks this class as a Spring MVC controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate; // For sending messages via WebSocket
    private final ChatService chatService; // Service for database operations

    @Autowired
    public ChatController(SimpMessageSendingOperations messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    /**
     * Handles incoming chat messages sent to "/app/chat".
     *
     * When a client sends a message to "/app/chat", this method receives it.
     * It then saves the message to the database and broadcasts it to the intended recipient.
     *
     * The `ChatMessage` object in the payload should contain `senderId`, `receiverId`, and `content`.
     * The `timestamp` will be set by the server.
     *
     * @param chatMessage The message payload received from the client.
     */
    @MessageMapping("/chat") // Maps messages sent to '/app/chat'
    public void processMessage(@Payload ChatMessage chatMessage) {
        // Set server-side timestamp for consistency
        chatMessage.setTimestamp(LocalDateTime.now());

        // Save the message to the database
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        System.out.println("Message saved: " + savedMessage); // Log for debugging

        // Send the message back to the sender and the receiver
        // This ensures both parties receive the confirmed message with server-set timestamp.
        // For one-to-one chat, we send to a specific user's queue.
        // The destination is typically `/queue/messages/{userId}` or `/user/{userId}/queue/messages`.
        // Spring's UserDestinationResolver handles the `/user/{userId}` prefix automatically.
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId(), "/queue/messages", savedMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getSenderId(), "/queue/messages", savedMessage);

        System.out.println("Message sent to " + chatMessage.getReceiverId() + " and " + chatMessage.getSenderId());
    }

    /**
     * REST endpoint to retrieve chat history between two users.
     * This can be used by the frontend to load past conversations when a chat is opened.
     *
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @return A list of ChatMessage objects.
     */
    @GetMapping("/api/chat/history/{user1Id}/{user2Id}")
    @ResponseBody // Indicates that the return value should be bound directly to the web response body
    public List<ChatMessage> getChatHistory(@PathVariable String user1Id, @PathVariable String user2Id) {
        System.out.println("Fetching chat history for: " + user1Id + " and " + user2Id);
        List<ChatMessage> history = chatService.getChatHistory(user1Id, user2Id);
        System.out.println("Found " + history.size() + " messages.");
        return history;
    }
}