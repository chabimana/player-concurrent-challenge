package com.coding.challenge;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * This is the main application class with main method. 
 * It initializes 2 Player instances with the queue to be used during the conversation/chat
 *  and starts he threads  that will be concurrently reading and writing from/to the queue.
 *
 *
 **/
public class PlayerMain {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {

		BlockingQueue<String> initiatorQueue = new ArrayBlockingQueue<>(1);

		BlockingQueue<String> oponentQueue = new ArrayBlockingQueue<String>(1);

		// First place instance with a null second player--for now
		Player initiatorPlayer = new Player(initiatorQueue, true, null, "Initiator");

		// Second Player instance with first player as a parameter
		Player receivePlayer = new Player(oponentQueue, false, initiatorPlayer, "Receiver");

		// set the player who will be playing with the initiator
		initiatorPlayer.setSecondPlayer(receivePlayer);

		Thread initiator = new Thread(initiatorPlayer);
		Thread receiver = new Thread(receivePlayer);
		initiator.start();
		receiver.start();

	}

}
