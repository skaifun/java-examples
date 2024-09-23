package org.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyTextWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(MyTextWebSocketHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("message received: {}({} bytes)", message.getPayload().stripIndent(), message.getPayloadLength());
        session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
        session.close(CloseStatus.GOING_AWAY);
    }
}
