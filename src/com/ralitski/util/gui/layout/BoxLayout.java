package com.ralitski.util.gui.layout;

import java.util.ArrayList;
import java.util.List;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.BoxPosition;
import com.ralitski.util.gui.Component;
import com.ralitski.util.gui.Dimension;

public class BoxLayout implements Layout {

	public static final int DIR_UP = 1;
	public static final int DIR_DOWN = 2;
	public static final int DIR_LEFT = 4;
	public static final int DIR_RIGHT = 8;
	
	public static final int DIR_X = DIR_LEFT | DIR_RIGHT;
	public static final int DIR_Y = DIR_UP | DIR_DOWN;
	public static final int DIR = DIR_X | DIR_Y;
	
	public static final int ALIGN_UP = 16;
	public static final int ALIGN_DOWN = 32;
	public static final int ALIGN_LEFT = 64;
	public static final int ALIGN_RIGHT = 128;
	public static final int ALIGN_CENTER = 256;
	
	public static final int ALIGN_X = ALIGN_UP | ALIGN_DOWN | ALIGN_CENTER; //x because horizontal can be aligned this way
	public static final int ALIGN_Y = ALIGN_LEFT | ALIGN_RIGHT | ALIGN_CENTER;

	
	private int direction;
	private int spaceX;
	private int spaceY;
	private List<Component> components;
	
	public BoxLayout() {
		this(DIR_DOWN | ALIGN_CENTER);
	}

	public BoxLayout(int direction, int align) {
		this(direction | align);
	}

	public BoxLayout(int direction) {
		this.direction = direction;
		this.components = new ArrayList<Component>();
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpaceX() {
		return spaceX;
	}

	public void setSpaceX(int spaceX) {
		this.spaceX = spaceX;
	}

	public int getSpaceY() {
		return spaceY;
	}

	public void setSpaceY(int spaceY) {
		this.spaceY = spaceY;
	}

	@Override
	public void addComponent(Component c, String layout) {
		if(layout != null) {
			try {
				int i = Integer.parseInt(layout);
				components.add(i, c);
				return;
			} catch (Exception e) {}
		}
		components.add(c);
	}

	@Override
	public void removeComponent(Component c) {
		components.remove(c);
	}

	@Override
	public void refresh(Box window) {
		if(!components.isEmpty()) {
			int dir = DIR & direction;
			BoxPosition pos = dir == DIR_UP ? BoxPosition.ABOVE_STRICT : (dir == DIR_LEFT ? BoxPosition.LEFT_OF_STRICT : (dir == DIR_RIGHT ? BoxPosition.RIGHT_OF_STRICT : BoxPosition.BELOW_STRICT));
			boolean isX = (dir & DIR_X) != 0;
			int align = direction & (isX ? ALIGN_X : ALIGN_Y);
			BoxPosition al = align == ALIGN_LEFT ? BoxPosition.LEFT : (align == ALIGN_RIGHT ? BoxPosition.RIGHT : (align == ALIGN_UP ? BoxPosition.TOP : (align == ALIGN_DOWN ? BoxPosition.BOTTOM : BoxPosition.CENTER)));
			//align base component
			Component prev = components.get(0);
			BoxPosition base = dir == DIR_UP ? BoxPosition.BOTTOM : (dir == DIR_LEFT ? BoxPosition.RIGHT : (dir == DIR_RIGHT ? BoxPosition.LEFT : BoxPosition.TOP));
			al.position(prev.getBounds(), null, isX ? spaceY : spaceX, window);
			base.position(prev.getBounds(), null, isX ? spaceX : spaceY, window);
			//align other components
			for(int i = 1; i < components.size(); i++) {
				Component c = components.get(i);
				al.position(c.getBounds(), prev.getBounds(), isX ? spaceY : spaceX, window);
				pos.position(c.getBounds(), prev.getBounds(), isX ? spaceX : spaceY, window);
				prev = c;
			}
		}
	}

	@Override
	public Dimension getMinimumSize() {
		if((direction & DIR_Y) != 0) {
			//vertical
			int maxX = 0;
			int height = spaceY;
			for(Component c : components) {
				height += c.getBounds().getHeight() + spaceY;
				maxX = Math.max(maxX, c.getBounds().getWidth());
			}
			return new Dimension(maxX + spaceX + spaceX, height);
		} else {
			//horizontal
			int maxY = 0;
			int width = spaceX;
			for(Component c : components) {
				width += c.getBounds().getWidth() + spaceX;
				maxY = Math.max(maxY, c.getBounds().getWidth());
			}
			return new Dimension(width, maxY + spaceY + spaceY);
		}
	}

}
