/*
package com.example.socketio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // For direct WebSocket connection (e.g., from Postman or dedicated WS clients)
        // Temporarily remove .withSockJS() for easier testing with tools that don't implement SockJS.
        // If your Flutter client uses a SockJS wrapper (like stomp_dart_client),
        // you might need to re-add this for Flutter production deployments,
        // as it handles cross-browser compatibility and fallback mechanisms.
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }
}*/
