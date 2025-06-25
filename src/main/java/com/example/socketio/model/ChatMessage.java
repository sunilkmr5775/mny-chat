/*
package com.example.socketio.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages") // Define the table name for the entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    private Long id;

    @Column(nullable = false) // Sender's ID, cannot be null
    private String senderId;

    @Column(nullable = false) // Receiver's ID, cannot be null
    private String receiverId;

    @Lob // For potentially long text content
    @Column(nullable = false) // Message content, cannot be null
    private String content;

    @Column(nullable = false) // Timestamp of message creation, cannot be null
    private LocalDateTime timestamp;

    // Default constructor for JPA
    public ChatMessage() {
        this.timestamp = LocalDateTime.now(); // Set timestamp on creation
    }

    public ChatMessage(String senderId, String receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}*/
