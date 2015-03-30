package com.ralitski.util.input.event;

import org.lwjgl.input.Controller;

public class ControllerButtonEvent extends ControllerEvent {
	
	private boolean state;

	public ControllerButtonEvent(Controller source, int index, long nano, boolean state) {
		super(source, index, nano);
		this.state = state;
	}
	
	/**
	 * 
	 * @return true for button down, false for button release
	 */
	public boolean getState() {
		return state;
	}

}
