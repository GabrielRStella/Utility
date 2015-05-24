package com.ralitski.util.gui.layout;

import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.BoxPosition;
import com.ralitski.util.gui.Component;
import com.ralitski.util.gui.Dimension;

public class CenterLayout implements Layout {
	
	private List<Component> components = new LinkedList<Component>();
	private Dimension minDimension;

	@Override
	public void addComponent(Component c, String layout) {
		components.add(c);
		recalcMin();
	}

	@Override
	public void removeComponent(Component c) {
		components.remove(c);
		recalcMin();
	}
	
	private void recalcMin() {
		int width = 0;
		int height = 0;
		for(Component c : components) {
			width = Math.max(c.getBounds().getWidth(), width);
			height = Math.max(c.getBounds().getHeight(), height);
		}
		minDimension = new Dimension(width, height);
	}

	@Override
	public void refresh(Box window) {
		for(Component c : components) BoxPosition.CENTER.position(c.getBounds(), null, 0, window);
	}

	@Override
	public Dimension getMinimumSize() {
		return minDimension;
	}

}
