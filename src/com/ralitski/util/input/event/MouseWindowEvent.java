package com.ralitski.util.input.event;

public class MouseWindowEvent extends MouseEvent {

	private MouseWindowState state;

	public MouseWindowEvent(long time, int x, int y, MouseWindowState state) {
		super(time, x, y);
		this.state = state;
	}
	
	public MouseWindowState getState() {
		return state;
	}
	
	public static enum MouseWindowState {
		ENTER, EXIT
	}

}
