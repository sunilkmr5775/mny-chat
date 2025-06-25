package com.example.socketio.repository;

import com.example.socketio.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Message entities.
 * Provides methods for CRUD operations and custom queries on the 'messages' table.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds all messages exchanged between two specific users.
     * The query is designed to retrieve messages where user1 is the sender and user2 is the receiver,
     * OR user2 is the sender and user1 is the receiver, effectively getting the full conversation history.
     * Messages are ordered by timestamp in ascending order.
     *
     * @param user1Id The ID of the first user.
     * @param user2Id The ID of the second user.
     * @return A list of Message objects representing the conversation history.
     */
    @Query("SELECT m FROM Message m WHERE " +
           "(m.senderId = :user1Id AND m.receiverId = :user2Id) OR " +
           "(m.senderId = :user2Id AND m.receiverId = :user1Id) " +
           "ORDER BY m.timestamp ASC")
    List<Message> findConversationBetweenUsers(@Param("user1Id") String user1Id, @Param("user2Id") String user2Id);
}