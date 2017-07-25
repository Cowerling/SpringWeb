package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import spittr.websocket.MarcoHandler;

@Configuration
@EnableWebSocket
@ComponentScan("spittr.websocket")
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private MarcoHandler marcoHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(marcoHandler, "/marco").withSockJS();
    }
}
