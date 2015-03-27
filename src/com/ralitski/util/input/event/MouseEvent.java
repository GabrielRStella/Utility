package com.ralitski.util.input.event;

public class MouseEvent extends InputEvent {
	
	private int x;
	private int y;

	public MouseEvent(long time, int x, int y) {
		super(time);
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
