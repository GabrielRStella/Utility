package com.ralitski.util.input.event;

import org.lwjgl.input.Controller;

public class ControllerAxisEvent extends ControllerEvent {
	
	private float value;
	private ControllerAxisType type;

	public ControllerAxisEvent(Controller source, int index, long nano, float value, ControllerAxisType type) {
		super(source, index, nano);
		this.value = value;
		this.type = type;
	}
	
	public float getValue() {
		return value;
	}
	
	public ControllerAxisType getType() {
		return type;
	}
	
	public static enum ControllerAxisType {
		AXIS_X,
		AXIS_Y,
		POV_X,
		POV_Y
	}

}
