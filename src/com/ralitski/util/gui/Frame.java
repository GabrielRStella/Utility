package com.ralitski.util.gui;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.gui.layout.BorderLayout;
import com.ralitski.util.gui.layout.Layout;
import com.ralitski.util.gui.render.RenderStyle;

//todo: the actual frame (borders, close button)
public class Frame implements Container {
	
	private Gui gui;
	private int id = -1;
	private Box box;
	private RenderStyle style;
	
	private int minWidth;
	private int minHeight;
	private boolean resizable;
	
	private Layout layout;
	private List<Component> children;
	
	private boolean renderSelf;
	
	public Frame(Gui gui) {
		prepare(gui);
		GuiOwner owner = gui.getOwner().getGuiOwner();
		box = new Box(0, 0, owner.getWidth(), owner.getHeight());
	}
	
	public Frame(Gui gui, int width, int height) {
		prepare(gui);
		GuiOwner owner = gui.getOwner().getGuiOwner();
		box = new Box(0, 0, Math.min(width, owner.getWidth()), Math.min(height, owner.getHeight()));
		BoxPosition.position(null, box, gui.getOwner().getWindow(), BoxPosition.CENTER);
	}
	
	public Frame(Gui gui, Box box) {
		prepare(gui);
		BoxPosition.position(null, box, gui.getOwner().getWindow(), BoxPosition.WITHIN_STRICT);
		this.box = box;
	}
	
	private void prepare(Gui gui) {
		this.gui = gui;
		children = new LinkedList<Component>();
		layout = new BorderLayout();
	}
	
	//stuff

	@Override
	public Gui getGui() {
		return gui;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	@Override
	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	@Override
	public boolean isResizable() {
		return resizable;
	}

	@Override
	public void setRenderStyle(int index, RenderStyle s) {
		style = s;
	}

	@Override
	public RenderStyle getRenderStyle(int index) {
		return style;
	}

	@Override
	public int getRenderStyles() {
		return 1;
	}

	@Override
	public Container getParent() {
		return null;
	}

	@Override
	public void setParent(Container container) {
		throw new UnsupportedOperationException("Frames are top-level containers.");
	}
	
	public Layout getLayout() {
		return layout;
	}
	
	public void setLayout(Layout layout) {
		for(Component c : children) {
			layout.addComponent(c, null);
		}
		this.layout = layout;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public void setSelected(boolean selected) {
		throw new UnsupportedOperationException("Frames can't be selected.");
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void add(Component c) {
		add(c, null);
	}

	@Override
	public void add(Component c, String layout) {
		children.add(c);
		c.setParent(this);
		this.layout.addComponent(c, layout);
	}
	
	public void remove(Component c) {
		if(c.getParent() == this) {
			c.setParent(null);
			children.remove(c);
			layout.removeComponent(c);
		}
	}
	
	public List<Component> getComponents() {
		return children;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Box getBounds() {
		return box;
	}

	@Override
	public String getHoverText() {
		return null;
	}
	
	//position components and resize frame
	public void refresh() {
		if(resizable) {
			Dimension d = layout.getMinimumSize();
			if(box.getWidth() < d.getWidth()) box.setWidth(Math.min(Math.max(minWidth, (int)d.getWidth()), gui.getOwner().getGuiOwner().getWidth()));
			if(box.getHeight() < d.getHeight()) box.setHeight(Math.min(Math.max(minHeight, (int)d.getHeight()), gui.getOwner().getGuiOwner().getHeight()));
			BoxPosition.position(null, box, gui.getOwner().getWindow(), BoxPosition.WITHIN_STRICT);
		}
		layout.refresh(box);
	}
	
	//TODO: render lists
	public void render(GuiOwner owner) {
		//render frame background, then components
		if(renderSelf) {
			owner.drawBox(box, style);
		}
		for(Component c : children) {
			c.render(owner);
		}
	}

	@Override
	public boolean doRenderSelf() {
		return renderSelf;
	}

	@Override
	public void setRenderSelf(boolean renderSelf) {
		this.renderSelf = renderSelf;
	}
	
}
