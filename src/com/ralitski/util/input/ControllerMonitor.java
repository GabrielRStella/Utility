package com.ralitski.util.input;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import com.ralitski.util.input.event.ControllerAxisEvent;
import com.ralitski.util.input.event.ControllerAxisEvent.ControllerAxisType;
import com.ralitski.util.input.event.ControllerButtonEvent;
import com.ralitski.util.input.event.ControllerEvent;

public class ControllerMonitor {
	
	private ControllerUser user;
	
	public ControllerMonitor(ControllerUser user) {
		this.user = user;
	}
	
	public void update() {
		while(Controllers.next()) {
			handleInput();
		}
	}
	
	private void handleInput() {
		int index = Controllers.getEventControlIndex();
		Controller src = Controllers.getEventSource();
		long nano = Controllers.getEventNanoseconds();
		if(Controllers.isEventButton()) {
			boolean state = Controllers.getEventButtonState();
			handleEvent(new ControllerButtonEvent(src, index, nano, state));
		} else if(Controllers.isEventXAxis()) {
			float x = Controllers.getEventXAxisValue();
			handleEvent(new ControllerAxisEvent(src, index, nano, x, ControllerAxisType.AXIS_X));
		} else if(Controllers.isEventYAxis()) {
			float y = Controllers.getEventYAxisValue();
			handleEvent(new ControllerAxisEvent(src, index, nano, y, ControllerAxisType.AXIS_Y));
		} else if(Controllers.isEventPovX()) {
			float x = Controllers.getEventXAxisValue();
			handleEvent(new ControllerAxisEvent(src, index, nano, x, ControllerAxisType.POV_X));
		} else if(Controllers.isEventPovY()) {
			float y = Controllers.getEventYAxisValue();
			handleEvent(new ControllerAxisEvent(src, index, nano, y, ControllerAxisType.POV_Y));
		}
	}
	
	public void handleEvent(ControllerEvent event) {
		user.onControllerEvent(event);
	}
}
