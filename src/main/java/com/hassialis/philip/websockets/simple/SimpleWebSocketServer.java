package com.hassialis.philip.websockets.simple;

import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerWebSocket("/ws/simple/prices")
public class SimpleWebSocketServer {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSocketServer.class);

	@OnOpen
	public Publisher<String> onOpen(WebSocketSession session) {
		return session.send("Connected!");
	}

	@OnMessage
	public Publisher<String> onMessage(String message, WebSocketSession session) {
		LOG.info("Received message: {} from session {}", message,session.getId());
		if (message.contentEquals("disconnect me")) {
			LOG.info("Disconnecting session {}", session.getId());
			session.close(CloseReason.NORMAL);
			return Flowable.empty();
		}
		return session.send("Not supported => ("+message+")");
	}

	@OnClose
	public void onClose(WebSocketSession session) {
		LOG.info("Session closed {}", session.getId());
	}
}
