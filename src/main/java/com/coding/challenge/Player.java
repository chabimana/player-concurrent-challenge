package com.coding.challenge;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: chabiman
 * @FileName: Player.java
 * @Date: Nov 26, 2020
 * @Package: com.coding.challenge.domain
 * @ProjectName: 360-coding-challenge
 * 
 */
/*
 * This class is aimed at creating a runnable Player object. A BlockingQueue Data structure is used due to the following
 * reasons: <br> 1. It is a thread safe (many threads can write and read from it without any problem) <br> 2. it has the
 * capability to block a thread that's taking an non existing value from it.<br> Source:
 * http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html
 */
public class Player implements Runnable {

	private BlockingQueue<String> messageQueue;

	/** The second player. */
	private Player secondPlayer;

	/** The is initiator. */
	public boolean isInitiator;

	/** The message counter. */
	private int messageCounter = 0;

	/** The received message counter. */
	private int receivedMessageCounter = 0;

	/** The sent message counter. */
	private int sentMessageCounter = 0;

	/** The Constant FIRST_MESSAGE. */
	private static final String FIRST_MESSAGE = "hi";

	/** The name. */
	private String name;

	/**
	 * Instantiates a new player.
	 *
	 * @param messageQueue the message queue
	 * @param isInitiator  the is initiator
	 * @param name         the name
	 */
	public Player(BlockingQueue<String> messageQueue, boolean isInitiator, Player secondPlayer, String name) {
		// player's queue(where the oponent will read messages from
		this.messageQueue = messageQueue;
		// Used to decide which player to send the initial message(otherwise it would stuck at the begining)
		this.isInitiator = isInitiator;
		// this object helps while reading the other player's queue
		this.secondPlayer = secondPlayer;
		// For output purpose
		this.name = name;
	}

	/*
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (isInitiator && messageCounter == 0)
			// then send the first message to start the conversation
			sendInitMessage();

		while (sentMessageCounter < 10 && receivedMessageCounter < 10) {
			String receivedMessage = readMessage();
			sendMessage(receivedMessage);
		}
		System.out.println("The player: " + this.getName() + " sent " + sentMessageCounter + " messages  and received "
				+ receivedMessageCounter + " messages");
	}

	/**
	 * Send message.
	 *
	 * @param message the message
	 */
	private void sendMessage(String message) {
		String reply = message + " " + messageCounter;
		try {
			// put the message on the queue
			this.getMessageQueue().put(reply);
			System.out.println("Player: " + this.getName() + " sent message " + reply);
			messageCounter = messageCounter + 1;
			sentMessageCounter++;
			Thread.sleep(500);
		} catch (InterruptedException interrupted) {
			System.out.println("Player: " + this.getName() + " failed to send the message");
			throw new IllegalStateException(this.getName() + " " + messageCounter);
		}

	}

	/**
	 * Read message.
	 *
	 * @return the string
	 */

	private String readMessage() {
		String receivedMessage;
		try {
			// Take message from the queue or wait if the queue is empty
			if (secondPlayer.getMessageQueue().isEmpty()) {
				System.out.println("Waiting........");
			}
			receivedMessage = secondPlayer.getMessageQueue().take();
			receivedMessageCounter++;
			System.out.println("Player: " + this.getName() + " received  message " + receivedMessage);
		} catch (InterruptedException interrupted) {
			System.out.println("Player: " + this.getName() + " failed to read the message");
			throw new IllegalStateException(this.getName() + " " + messageCounter);
		} catch (NullPointerException ex) {
			throw new IllegalStateException("Queue cannot be null");
		}
		return receivedMessage;
	}

	private void sendInitMessage() {
		try {
			this.getMessageQueue().put(FIRST_MESSAGE);
			System.out.println("Player: " + this.getName() + " sent init  message " + FIRST_MESSAGE);
		} catch (InterruptedException interrupted) {
			System.out.println("Player: " + this.getName() + " failed to read the message");
			throw new IllegalStateException(this.getName() + " " + messageCounter);
		} catch (NullPointerException ex) {
			throw new IllegalStateException("Queue can't be null");
		}
	}

	/**
	 * @return the messageCounter
	 */
	public int getMessageCounter() {
		return messageCounter;
	}

	/**
	 * @return the messageQueue
	 */
	public BlockingQueue<String> getMessageQueue() {
		return messageQueue;
	}

	/**
	 * @param messageQueue the messageQueue to set
	 */
	public void setMessageQueue(BlockingQueue<String> messageQueue) {
		this.messageQueue = messageQueue;
	}

	public void setMessageCounter(int messageCounter) {
		this.messageCounter = messageCounter;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the receivedMessageCounter
	 */
	public int getReceivedMessageCounter() {
		return receivedMessageCounter;
	}

	/**
	 * @param receivedMessageCounter the receivedMessageCounter to set
	 */
	public void setReceivedMessageCounter(int receivedMessageCounter) {
		this.receivedMessageCounter = receivedMessageCounter;
	}

	/**
	 * @return the sentMessageCounter
	 */
	public int getSentMessageCounter() {
		return sentMessageCounter;
	}

	/**
	 * @param sentMessageCounter the sentMessageCounter to set
	 */
	public void setSentMessageCounter(int sentMessageCounter) {
		this.sentMessageCounter = sentMessageCounter;
	}

	/**
	 * @return the secondPlayer
	 */
	public Player getSecondPlayer() {
		return secondPlayer;
	}

	/**
	 * @param secondPlayer the secondPlayer to set
	 */
	public void setSecondPlayer(Player secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

}
