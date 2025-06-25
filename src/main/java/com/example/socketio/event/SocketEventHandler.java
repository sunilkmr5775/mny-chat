package com.example.socketio.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.socketio.model.Message;
import com.example.socketio.service.MessageService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles Socket.IO events for the chat feature.
 * This class manages client connections, disconnections, and message handling.
 */
@Component
public class SocketEventHandler {

    private final SocketIOServer server;
    private final MessageService messageService;

    // A map to store active clients, mapping userId to SocketIOClient instance.
    // This allows sending messages directly to a specific user.
    private final Map<String, SocketIOClient> connectedClients = new ConcurrentHashMap<>();

    /**
     * Constructor for SocketEventHandler, injecting SocketIOServer and MessageService.
     * @param server The Socket.IO server instance.
     * @param messageService The service for message persistence.
     */
    public SocketEventHandler(SocketIOServer server, MessageService messageService) {
        this.server = server;
        this.messageService = messageService;
    }

    /**
     * Handles client connection events.
     * When a client connects, their userId is extracted from the handshake data (URL parameters)
     * and stored in the connectedClients map.
     *
     * @param client The connected SocketIOClient instance.
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        // Extract userId from URL query parameters.
        // For production, you'd likely pass a JWT token and validate it here to get the userId.
        String userId = client.getHandshakeData().getUrlParams().get("userId") != null ?
                client.getHandshakeData().getUrlParams().get("userId").get(0) :
                "anonymous-" + client.getSessionId().toString();

        connectedClients.put(userId, client); // Store the client associated with their userId
        System.out.println("Client connected: " + client.getSessionId() + " (User: " + userId + ")");
        System.out.println("Currently connected users: " + connectedClients.size());

        // Emit a 'userConnected' event back to the connecting client
        client.sendEvent("userConnected", "Welcome, " + userId + "!");
    }

    /**
     * Handles client disconnection events.
     * When a client disconnects, their entry is removed from the connectedClients map.
     *
     * @param client The disconnected SocketIOClient instance.
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        // Find and remove the disconnected client from the map
        connectedClients.entrySet().removeIf(entry -> entry.getValue().getSessionId().equals(client.getSessionId()));
        System.out.println("Client disconnected: " + client.getSessionId());
        System.out.println("Currently connected users: " + connectedClients.size());
    }

    /**
     * Handles 'sendMessage' events from clients.
     * When a client sends a message:
     * 1. The message content, sender, and receiver IDs are extracted.
     * 2. A timestamp is added.
     * 3. The message is saved to the database.
     * 4. If the receiver is currently connected, the message is emitted to them.
     * 5. The message is also emitted back to the sender as an acknowledgement.
     *
     * @param client The SocketIOClient that sent the message.
     * @param message The Message object containing senderId, receiverId, and content.
     */
    @OnEvent("sendMessage")
    public void onSendMessage(SocketIOClient client, Message message) {
        message.setTimestamp(LocalDateTime.now()); // Set the current timestamp

        // Save the message to the database
        Message savedMessage = messageService.saveMessage(message);
        System.out.println("Message received and saved: " + savedMessage);

        // Try to find the receiver's client instance
        SocketIOClient receiverClient = connectedClients.get(message.getReceiverId());

        // If the receiver is connected, send the message to them
        if (receiverClient != null && receiverClient.isChannelOpen()) {
            // Emit the 'receiveMessage' event to the receiver
            receiverClient.sendEvent("receiveMessage", savedMessage);
            System.out.println("Message sent to receiver " + message.getReceiverId());
        } else {
            System.out.println("Receiver " + message.getReceiverId() + " is not connected or client channel is closed. Message saved but not delivered in real-time.");
            // In a real app, you might queue this message for later delivery or show "offline" status
        }

        // Also, send the message back to the sender as an acknowledgment (optional, but good for UI updates)
        client.sendEvent("messageSentAck", savedMessage);
    }
}