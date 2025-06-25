/*
package com.example.socketio.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
import com.example.socketio.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository // Marks this interface as a Spring Data JPA repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    */
/**
     * Finds messages between two users.
     * Orders messages by timestamp in ascending order.
     *
     * @param user1Id ID of the first user.
     * @param user2Id ID of the second user.
     * @return A list of ChatMessage objects exchanged between user1 and user2.
     *//*

    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
        String user1Id, String user2Id, String user3Id, String user4Id);
}*/
