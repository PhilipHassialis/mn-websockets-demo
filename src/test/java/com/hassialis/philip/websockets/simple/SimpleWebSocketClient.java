package com.hassialis.philip.websockets.simple;

import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.reactivex.rxjava3.core.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

@ClientWebSocket("/ws/simple/prices")
public abstract class SimpleWebSocketClient implements AutoCloseable {

	private final Collection<String> observedMessages = new ConcurrentLinkedDeque<>();
	private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSocketClient.class);
	private WebSocketSession session;

	@OnOpen
	public void onOpen(WebSocketSession session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message) {
		observedMessages.add(message);
	}

	public abstract void send(String mesasge);

	public abstract Single<String> sendAsync(String message);

	public Collection<String> getObservedMessages() {
		return observedMessages;
	}

	public WebSocketSession getSession() {
		return session;
	}
}
