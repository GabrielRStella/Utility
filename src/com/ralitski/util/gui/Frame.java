package com.ralitski.util.gui;

import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseButtonEvent;
import com.ralitski.util.input.event.MouseButtonEvent.MouseEventType;
import com.ralitski.util.input.event.MouseEvent;
import com.ralitski.util.input.event.MouseMoveEvent;

//todo: the actual frame (borders)
public class Frame extends ContainerAbstract {
	
	private boolean dragging;
	
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
	public void setParent(Container container) {
		if(container instanceof Desktop) {
			super.setParent(container);
		} else {
			throw new UnsupportedOperationException("Frames are top-level containers.");
		}
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
		if(dragging) {
			if(event instanceof MouseMoveEvent) {
				int x = event.getX();
				int y = event.getY();
				Box window = gui.getOwner().getWindow();
				//check that mouse is on frame
				if(box.contains(x, y)) {
					MouseMoveEvent mEvent = (MouseMoveEvent)event;
					int dx = mEvent.getDx();
					int dy = mEvent.getDy();
					dx = -Math.min(-dx, box.getMinX() - window.getMinX());
					dx = Math.min(dx, window.getMaxX() - box.getMaxX());
					dy = -Math.min(-dy, box.getMinY() - window.getMinY());
					dy = Math.min(dy, window.getMaxY() - box.getMaxY());
					box.translate(dx, dy);
					for(Component c : children) {
						c.getBounds().translate(dx, dy);
					}
					renderListState.setDirty(true);
				}
			} else if(event instanceof MouseButtonEvent) {
				MouseButtonEvent mEvent = (MouseButtonEvent)event;
				if(mEvent.getButton() == 0 && mEvent.getType() == MouseEventType.UP) {
					dragging = false;
				}
			}
		} else super.onMouseEvent(event);
	}
	
	protected void mouseNotHandled(MouseEvent event) {
		super.mouseNotHandled(event);
		if(parent != null) {
			if(event instanceof MouseButtonEvent) {
				MouseButtonEvent mEvent = (MouseButtonEvent)event;
				int x = mEvent.getX();
				int y = mEvent.getY();
				if(mEvent.getButton() == 0 && mEvent.getType() == MouseEventType.DOWN && box.contains(x, y)) {
					dragging = true;
				}
			}
		}
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}
	
}
