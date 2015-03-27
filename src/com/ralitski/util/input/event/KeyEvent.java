package com.ralitski.util.input.event;

public class KeyEvent extends InputEvent {

	private int key;
	private char keyChar;
	private KeyEventType type;
	
	public KeyEvent(long time, int key, char keyChar) {
		this(time, key, keyChar, KeyEventType.DOWN);
	}
	
	public KeyEvent(long time, int key, char keyChar, KeyEventType type) {
		super(time);
		this.key = key;
		this.keyChar = keyChar;
		this.type = type;
	}
	
	public int getKey() {
		return key;
	}
	
	public char getKeyChar() {
		return keyChar;
	}
	
	public KeyEventType getType() {
		return type;
	}
	
	public static enum KeyEventType {
		/**
		 * The event fired when a key is clicked
		 */
		DOWN,
		/**
		 * Event fired each tick a key remains down
		 */
		HOLD,
		/**
		 * Event fired when a key is released
		 */
		UP;
	}
}
