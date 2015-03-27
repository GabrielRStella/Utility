package com.ralitski.util.input.event;

public class KeyTimedEvent extends KeyEvent {
	
	private int ticks;

	public KeyTimedEvent(long time, int key, char keyChar, KeyEventType type, int ticks) {
		super(time, key, keyChar, type);
		this.ticks = ticks;
	}

	public int getTicks() {
		return ticks;
	}

}
