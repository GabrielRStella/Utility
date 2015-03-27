package com.ralitski.util.input.event;

public class MouseMoveEvent extends MouseEvent {
	
	private int dx;
	private int dy;

	public MouseMoveEvent(long time, int x, int y, int dx, int dy) {
		super(time, x, y);
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

}
