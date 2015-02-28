package com.ralitski.util.render.display;

public interface DisplayUser {
	
	/**
	 * setup camera, load textures, etc. This is called after OpenGL is instantiated.
	 */
	void setup();
	
	/**
	 * updates logic and rendering; return false to shutdown program
	 * @param tick If at least a full tick has passed
	 * @param partial the part of a tick that has passed since the last call to update(boolean, float)
	 * @return
	 */
	boolean update(boolean tick, float partial);
	
	/**
	 * called on program shutdown in order to save / cleanup / whatever
	 */
	void close();
	
	/**
	 * The preferred width for this window
	 * @return The default window width
	 */
	int getWidth();
	
	/**
	 * The preferred height for this window
	 * @return The default window height
	 */
	int getHeight();
	
	/**
	 * Called on window resize so necessary variables can be updated. Will always be followed by a call to resize().
	 * @param i The new width of the window (in pixels)
	 */
	void setWidth(int i);
	
	/**
	 * Called on window resize so necessary variables can be updated. Will always be followed by a call to resize().
	 * @param i The new height of the window (in pixels)
	 */
	void setHeight(int i);
	
	/**
	 * Notifies the user of a window resize. setWidth() and setHeight() are called beforehand.
	 */
	void resize();
	
	/**
	 * called by the DisplayManager to notify the program of display errors
	 * @param source The source of the error (see integer constants ERROR_* in DisplayManager)
	 * @param e the error
	 */
	void getError(int source, Throwable e);
}
