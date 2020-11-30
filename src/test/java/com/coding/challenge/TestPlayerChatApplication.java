
package com.coding.challenge;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;

public class TestPlayerChatApplication {

	/**
	 * Positive test
	 */
	@Test
	public void testPositiveChat() {

		BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(1);
		BlockingQueue<String> queue2 = new ArrayBlockingQueue<String>(1);

		Player initiatorPlayer = new Player(queue1, true, null, "Initiator");

		Player receivePlayer = new Player(queue2, false, initiatorPlayer, "Receiver");
		initiatorPlayer.setSecondPlayer(receivePlayer);

		Thread initiator = new Thread(initiatorPlayer);
		Thread receiver = new Thread(receivePlayer);

		initiator.start();
		receiver.start();

	}

	/**
	 * Test when the first player isn't initialized with the opponent
	 */
	@Test
	public void testChatWithoutQueue() {

		BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(1);
		BlockingQueue<String> queue2 = new ArrayBlockingQueue<String>(1);

		Player initiatorPlayer = new Player(queue1, true, null, "Initiator");

		Player receivePlayer = new Player(queue2, false, initiatorPlayer, "Receiver");

		Thread initiator = new Thread(initiatorPlayer);
		Thread receiver = new Thread(receivePlayer);

		initiator.start();
		receiver.start();

	}
}
