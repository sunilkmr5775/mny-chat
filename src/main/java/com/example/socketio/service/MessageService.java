package com.example.socketio.service;

import com.example.socketio.model.Message;
import com.example.socketio.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for handling business logic related to messages.
 * This service interacts with the MessageRepository to perform database operations.
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    /**
     * Constructor for MessageService, injecting MessageRepository.
     * @param messageRepository The repository for message data.
     */
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Saves a new message to the database.
     * @param message The Message object to be saved.
     * @return The saved Message object (with generated ID, etc.).
     */
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Retrieves the conversation history between two users.
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @return A list of messages exchanged between the two users, ordered by timestamp.
     */
    public List<Message> getConversation(String user1Id, String user2Id) {
        return messageRepository.findConversationBetweenUsers(user1Id, user2Id);
    }
}