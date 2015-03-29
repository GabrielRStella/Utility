package com.ralitski.util.gui;

import com.ralitski.util.input.event.KeyEvent;

public class Panel extends ContainerAbstract {
	
	public Panel(Gui gui) {
		super(gui);
	}
	
	public Panel(Gui gui, int width, int height) {
		super(gui, width, height);
	}
	
	public Panel(Gui gui, Box box) {
		super(gui, box);
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}

}
