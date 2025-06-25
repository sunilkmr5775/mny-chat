package com.example.socketio.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Configuration class for setting up the Socket.IO server.
 * This class defines the necessary beans to initialize and manage the Socket.IO server.
 */
@Configuration
public class SocketIOConfig {

    @Value("${socket.io.host}")
    private String host; // Host for the Socket.IO server (e.g., localhost)

    @Value("${socket.io.port}")
    private Integer port; // Port for the Socket.IO server (e.g., 9092)

    /**
     * Creates and configures the SocketIOServer bean.
     * The server will be started on the specified host and port.
     *
     * @return Configured SocketIOServer instance.
     */
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host); // Set the host from application.properties
        config.setPort(port);     // Set the port from application.properties

        // Optional: Add more configurations like authorization, namespaces, etc.
        // For a dating app, you might want to add token-based authorization here.
        // config.setAuthorizationListener(data -> {
        //     // Implement your token validation logic here
        //     String token = data.getUrlParams().get("token").get(0);
        //     return isValidToken(token); // Your custom validation method
        // });

        final SocketIOServer server = new SocketIOServer(config);

        // Start the server in a separate thread to not block the main application thread
        // In a production environment, you might want to manage this lifecycle more robustly.
        server.start();

        System.out.println("Socket.IO server started on " + host + ":" + port);

        // Register a shutdown hook to stop the server gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping Socket.IO server...");
            server.stop();
            System.out.println("Socket.IO server stopped.");
        }));

        return server;
    }
}