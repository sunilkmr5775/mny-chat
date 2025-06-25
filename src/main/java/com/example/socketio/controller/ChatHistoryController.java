package com.example.socketio.controller;

import com.example.socketio.model.Message;
import com.example.socketio.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for retrieving chat history.
 * This controller exposes an endpoint to fetch messages between two users from the database.
 */
@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    private final MessageService messageService;

    /**
     * Constructor for ChatHistoryController, injecting MessageService.
     * @param messageService The service for retrieving message data.
     */
    public ChatHistoryController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint to get the conversation history between two users.
     *
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @return A ResponseEntity containing a list of Message objects.
     */
    @GetMapping("/history/{user1Id}/{user2Id}")
    public ResponseEntity<List<Message>> getChatHistory(
            @PathVariable String user1Id,
            @PathVariable String user2Id) {
        List<Message> conversation = messageService.getConversation(user1Id, user2Id);
        return ResponseEntity.ok(conversation);
    }
}