package com.ralitski.util.input.event;

public class MouseButtonEvent extends MouseEvent {
	
	private int button;
	private int ticks;
	private MouseEventType type;

	public MouseButtonEvent(long time, int x, int y, int button) {
		this(time, x, y, button, 0, MouseEventType.DOWN);
	}

	public MouseButtonEvent(long time, int x, int y, int button, int ticks, MouseEventType type) {
		super(time, x, y);
		this.button = button;
		this.ticks = ticks;
		this.type = type;
	}
	
	public int getButton() {
		return button;
	}

	public int getTicks() {
		return ticks;
	}
	
	public MouseEventType getType() {
		return type;
	}
	
	public static enum MouseEventType {
		/**
		 * The event fired when a button is clicked
		 */
		DOWN,
		/**
		 * Event fired each tick a button remains down
		 */
		HOLD,
		/**
		 * Event fired when a button is released
		 */
		UP;
	}

}
