package ru.jurfed.websocket.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.jurfed.websocket.ServerMainClass;
import ru.jurfed.websocket.repositories.HeroRepository;
import ru.jurfed.websocket.services.SendHeroesImpl;

/**
 * WebSocket event handler
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = LogManager.getLogger(WebSocketHandler.class);
    @Autowired
    HeroRepository heroRepository;

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        logger.error("error occured at sender " + session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.warn("Session close: " + session.getId());

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection with the client is established. session: "+session.getId());
        new SendHeroesImpl(session, heroRepository);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage jsonTextMessage) throws Exception {
        logger.info("message received: " + jsonTextMessage.getPayload());
    }
}