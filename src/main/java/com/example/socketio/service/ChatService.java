/*
package com.example.socketio.service;

import com.example.socketio.model.ChatMessage;
import com.example.socketio.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Marks this class as a Spring Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired // Injects ChatMessageRepository
    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    */
/**
     * Saves a chat message to the database.
     *
     * @param chatMessage The ChatMessage object to save.
     * @return The saved ChatMessage object.
     *//*

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    */
/**
     * Retrieves chat history between two specific users.
     * This query fetches messages where (senderId=user1 and receiverId=user2) OR
     * (senderId=user2 and receiverId=user1), ordered by timestamp.
     *
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @return A list of ChatMessage objects representing the conversation.
     *//*

    public List<ChatMessage> getChatHistory(String user1Id, String user2Id) {
        return chatMessageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            user1Id, user2Id, user1Id, user2Id);
    }
}*/
