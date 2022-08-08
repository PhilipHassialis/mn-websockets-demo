package com.hassialis.philip.websockets.simple;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.websocket.WebSocketClient;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.inject.Inject;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest

public class SimpleWebSocketServerTest {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleWebSocketServerTest.class);
	SimpleWebSocketClient simpleWebSocketClient;

	@Inject
	@Client("http://localhost:8180")
	WebSocketClient client;

	@BeforeEach
	void connect() {
		simpleWebSocketClient = Flowable.fromPublisher(client.connect(SimpleWebSocketClient.class, "/ws/simple/prices"))
			.blockingFirst();
		LOG.info("Client session {}", simpleWebSocketClient.getSession());
	}

	@Test
	void canReceiveMessagesWithClient() {
		simpleWebSocketClient.send("Hello");
		Awaitility.await().untilAsserted(() -> {
			final Object[] messages = simpleWebSocketClient.getObservedMessages().toArray();
			LOG.info("Observed messages {} - {}", simpleWebSocketClient.getObservedMessages().size(), simpleWebSocketClient.getObservedMessages());
			assertEquals("Connected!", messages[0]);
			assertEquals("Not supported => (Hello)", messages[1]);
		});
	}

//	@Test
//	void canReceiveMessagesWithClientReactively() {
//		var a = simpleWebSocketClient.sendAsync("Hello").blockingGet();
//		LOG.info("Sent {}", );
//		Awaitility.await().untilAsserted(() -> {
//			final Object[] messages = simpleWebSocketClient.getObservedMessages().toArray();
//			LOG.info("Observed messages {} - {}", simpleWebSocketClient.getObservedMessages().size(), simpleWebSocketClient.getObservedMessages());
//			assertEquals("Connected!", messages[0]);
//			assertEquals("Not supported => (Hello)", messages[1]);
//		});
//	}

}
