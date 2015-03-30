package com.ralitski.util.input.event;

import org.lwjgl.input.Controller;


public class ControllerEvent extends InputEvent {
	
	private Controller source;
	private int index;
	
	public ControllerEvent(Controller source, int index, long nano) {
		super(nano);
		this.source = source;
		this.index = index;
	}
	
	public Controller getController() {
		return source;
	}
	
	public int getControllerIndex() {
		return index;
	}
}
