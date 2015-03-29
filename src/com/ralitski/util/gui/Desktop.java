package com.ralitski.util.gui;

import com.ralitski.util.input.event.KeyEvent;

/**
 * The only Container that can be the parent of a Frame. Desktops can contain only Frames. Frames contained in a Desktop will be given a border and can typically be moved by the user.
 * 
 * @author ralitski
 *
 */
public class Desktop extends ContainerAbstract {

	public Desktop(Gui gui) {
		super(gui);
	}

	@Override
	public void add(Component c, String layout) {
		if(c instanceof Frame) {
			super.add(c, layout);
		} else {
			throw new IllegalArgumentException("Desktop Containers can only store Frames.");
		}
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}

}
