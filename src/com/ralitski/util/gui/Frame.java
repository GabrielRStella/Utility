package com.ralitski.util.gui;

import com.ralitski.util.input.event.MouseEvent;

//todo: the actual frame (borders, close button)
//ie, if a Frame has a parent, it becomes an actual moveable window thing
//only accepts Desktop as parent
public class Frame extends ContainerAbstract {
	
	public Frame(Gui gui) {
		super(gui);
	}
	
	public Frame(Gui gui, int width, int height) {
		super(gui, width, height);
	}
	
	public Frame(Gui gui, Box box) {
		super(gui, box);
	}

	@Override
	public Container getParent() {
		return null;
	}

	@Override
	public void setParent(Container container) {
		throw new UnsupportedOperationException("Frames are top-level containers.");
	}
	
	protected void mouseNotHandled(MouseEvent event) {
		super.mouseNotHandled(event);
		//TODO: move window if child of Desktop
	}
	
}
