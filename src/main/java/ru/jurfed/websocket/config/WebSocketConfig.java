package ru.jurfed.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import ru.jurfed.websocket.handlers.WebSocketHandler;

/**
 * Class for configuration WebSocket connection
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	/**
	 * Connection handler
	 */
	@Autowired
	WebSocketHandler webSocketHandler;

	/**
	 * Method for register WebSocket handlers
	 * @param webSocketHandlerRegistry
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(webSocketHandler, "").setAllowedOrigins("*");
	}

}
