package com.ralitski.util.gui.layout;

import java.awt.Dimension;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.BoxPosition;
import com.ralitski.util.gui.Component;

public class BorderLayout implements Layout {
	
	//abbreviations to save a bit of space, since a full string is unnecessary
	public static final String NORTH = "n";
	public static final String SOUTH = "s";
	public static final String WEST = "w";
	public static final String EAST = "e";
	public static final String CENTER = "c";
	
	private Component north;
	private Component south;
	private Component west;
	private Component east;
	private Component center;

	private int spaceW;
	private int spaceH;
	
	public BorderLayout() {
		this(0);
	}
	
	public BorderLayout(int space) {
		this(space, space);
	}
	
	public BorderLayout(int spaceW, int spaceH) {
		this.spaceW = spaceW;
		this.spaceH = spaceH;
	}

	public int getSpaceW() {
		return spaceW;
	}

	public void setSpaceW(int spaceW) {
		this.spaceW = spaceW;
	}

	public int getSpaceH() {
		return spaceH;
	}

	public void setSpaceH(int spaceH) {
		this.spaceH = spaceH;
	}

	@Override
	public void addComponent(Component c, String layout) {
		if(layout == null || layout.isEmpty() || layout.equals(CENTER)) {
			if(center != null) throw new IllegalStateException("Center component is already defined.");
			center = c;
		} else if(layout.equals(NORTH)) {
			if(north != null) throw new IllegalStateException("North component is already defined.");
			north = c;
		} else if(layout.equals(SOUTH)) {
			if(south != null) throw new IllegalStateException("South component is already defined.");
			south = c;
		} else if(layout.equals(WEST)) {
			if(west != null) throw new IllegalStateException("West component is already defined.");
			west = c;
		} else if(layout.equals(EAST)) {
			if(east != null) throw new IllegalStateException("East component is already defined.");
			east = c;
		}
	}

	@Override
	public void removeComponent(Component c) {
		if(c == north) {
			north = null;
		} else if(c == south) {
			south = null;
		} else if(c == west) {
			west = null;
		} else if(c == east) {
			east = null;
		} else if(c == center) {
			center = null;
		}
	}
	
	public void refresh(Box window) {
		if(north != null) BoxPosition.position(null, north.getBounds(), window, BoxPosition.TOP);
		if(south != null) BoxPosition.position(null, south.getBounds(), window, BoxPosition.BOTTOM);
		if(west != null) BoxPosition.position(null, west.getBounds(), window, BoxPosition.LEFT);
		if(east != null) BoxPosition.position(null, east.getBounds(), window, BoxPosition.RIGHT);
		if(center != null) BoxPosition.position(null, center.getBounds(), window, BoxPosition.CENTER);
	}

	@Override
	public Dimension getMinimumSize() {
		int w = 0;
		int countW = -1;
		if(west != null) {
			w += west.getBounds().getWidth();
			countW++;
		}
		if(center != null) {
			w += center.getBounds().getWidth();
			countW++;
		}
		if(east != null) {
			w += east.getBounds().getWidth();
			countW++;
		}
		if(countW > 0) w += countW * spaceW;
		if(north != null) w = Math.max(w, north.getBounds().getWidth());
		if(south != null) w = Math.max(w, south.getBounds().getWidth());
			
		int h = 0;
		int countH = -1;
		if(north != null) {
			h += north.getBounds().getHeight();
			countH++;
		}
		if(center != null) {
			h += center.getBounds().getHeight();
			countH++;
		}
		if(south != null) {
			h += south.getBounds().getHeight();
			countH++;
		}
		if(countH > 0) h += countH * spaceH;
		if(west != null) h = Math.max(w, west.getBounds().getHeight());
		if(east != null) h = Math.max(w, east.getBounds().getHeight());
		
		return new Dimension(w, h);
	}
}
