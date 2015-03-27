package com.ralitski.util.input.event;

public class MouseWheelEvent extends MouseEvent {
	
	private int wheel;

	public MouseWheelEvent(long time, int x, int y, int wheel) {
		super(time, x, y);
		this.wheel = wheel;
	}
	
	public int getWheel() {
		return wheel;
	}

}
