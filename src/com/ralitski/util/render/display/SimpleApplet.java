package com.ralitski.util.render.display;

import java.applet.Applet;

//not sure if this works. haven't been able to get past java security dumbs.
public abstract class SimpleApplet extends Applet {

	/**
	 * auto-generated
	 */
	private static final long serialVersionUID = 3898606483147263637L;
	
	//
	protected DisplayManager manager;
	private boolean setup;
	
	public void init() {
		manager = generateManager();
		setup = manager.setup();
	}
	
	public void start() {
		if(setup) manager.start();
	}
	
	public void stop() {
		manager.stop();
	}
	
	protected abstract DisplayManager generateManager();
	
}
