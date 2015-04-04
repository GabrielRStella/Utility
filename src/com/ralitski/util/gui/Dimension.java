package com.ralitski.util.gui;

import java.awt.geom.Rectangle2D;

public class Dimension {
	
	private int width;
	private int height;
	
	public Dimension() {
		this(0);
	}
	
	public Dimension(int size) {
		this(size, size);
	}
	
	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Dimension(Rectangle2D rect) {
		this((int)Math.ceil(rect.getWidth()), (int)Math.ceil(rect.getHeight()));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Box createBox(int x, int y) {
		return new Box(x, y, x + width, y + width);
	}
}
