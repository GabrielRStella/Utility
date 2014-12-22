package com.ralitski.util.render.display;

import java.awt.event.WindowListener;

public interface DisplayUser extends WindowListener {

	public String title();
	//setup camera, load textures, etc
	public void setup();
	//updates logic and rendering; return false to shutdown program
	public boolean update(boolean tick, float partial);
	//called on program shutdown in order to save / cleanup / whatever
	public void close();
	public int getWidth();
	public int getHeight();
	public int getMinWidth();
	public int getMinHeight();
	public void setWidth(int i);
	public void setHeight(int i);
	public void resize();
}
