package com.ralitski.util.gui;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.gui.layout.BorderLayout;
import com.ralitski.util.gui.layout.Layout;
import com.ralitski.util.gui.render.RenderList;
import com.ralitski.util.gui.render.RenderListState;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseButtonEvent;
import com.ralitski.util.input.event.MouseButtonEvent.MouseEventType;
import com.ralitski.util.input.event.MouseEvent;

public abstract class ContainerAbstract extends ComponentAbstract implements Container {
	
	private int minWidth;
	private int minHeight;
	private boolean resizable;
	
	protected Layout layout;
	protected List<Component> children;
	
	//used to keep track of child components that use their own render lists
	
	/**
	 * The list of components that will be rendered in this Frame's render list.
	 */
	private List<Component> renderWithThis;
	
	/**
	 * The list of components that will not be rendered with this Frame's render list.
	 */
	private List<Component> renderWithSelf;
	
	//render list stuff
	protected RenderList renderList;
	
	private boolean renderSelf;
	
	public ContainerAbstract(Gui gui) {
		super(gui);
		prepare(gui);
	}
	
	public ContainerAbstract(Gui gui, int width, int height) {
		super(gui, width, height);
		prepare(gui);
	}
	
	public ContainerAbstract(Gui gui, Box box) {
		super(gui, box);
		prepare(gui);
	}
	
	private void prepare(Gui gui) {
		layout = new BorderLayout();
		GuiOwner owner = gui.getOwner().getGuiOwner();
		if(owner.supportLists()) {
			renderWithThis = new LinkedList<Component>();
			renderWithSelf = new LinkedList<Component>();
//			getRenderList(owner);
		}
	}
	
	//stuff

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
	public int getRenderStyles() {
		return 1;
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
	public void add(Component c) {
		add(c, null);
	}

	@Override
	public void add(Component c, String layout) {
		children.add(c);
		if(gui.getOwner().getGuiOwner().supportLists()) {
			if(c.useParentRenderList()) this.renderWithThis.add(c);
			else this.renderWithSelf.add(c);
			c.setParentRenderList(renderListState);
		}
		c.setParent(this);
		this.layout.addComponent(c, layout);
	}
	
	public void remove(Component c) {
		if(c.getParent() == this) {
			c.setParent(null);
			children.remove(c);
			layout.removeComponent(c);
			if(gui.getOwner().getGuiOwner().supportLists()) {
				if(c.useParentRenderList()) this.renderWithThis.remove(c);
				else this.renderWithSelf.remove(c);
//				if(!this.renderWithThis.remove(c)) this.renderWithSelf.remove(c);
				c.setParentRenderList(null);
			}
		}
	}
	
	public List<Component> getComponents() {
		return children;
	}

	@Override
	public void setParentRenderList(RenderListState state) {
		if(useParentRenderList()) {
			this.renderListState = state;
			for(Component c : renderWithThis) {
				c.setParentRenderList(state);
			}
		} else {
			throw new UnsupportedOperationException("Containers do not share a RenderList with their parent");
		}
	}
	
	//position components and possibly resize container
	public void refresh() {
		for(Component c : children) {
			if(c instanceof Container) ((Container)c).refresh();
		}
		if(resizable) {
			Dimension d = layout.getMinimumSize();
			if(box.getWidth() < d.getWidth()) box.setWidth(Math.min(Math.max(minWidth, (int)d.getWidth()), gui.getOwner().getGuiOwner().getWidth()));
			if(box.getHeight() < d.getHeight()) box.setHeight(Math.min(Math.max(minHeight, (int)d.getHeight()), gui.getOwner().getGuiOwner().getHeight()));
			BoxPosition.position(null, box, gui.getOwner().getWindow(), BoxPosition.WITHIN_STRICT);
		}
		layout.refresh(box);
	}

	@Override
	public boolean doRenderSelf() {
		return renderSelf;
	}

	@Override
	public void setRenderSelf(boolean renderSelf) {
		this.renderSelf = renderSelf;
	}
	
	public void render(GuiOwner owner) {
		if(owner.supportLists() && !useParentRenderList()) {
			if(renderList == null) getRenderList(owner);
			if(renderListState.isDirty() || !renderList.registered()) {
				renderList.compile();
			}
			//render stuff with this list
			renderList.call();
			//render stuff with own list
			for(Component c : renderWithSelf) {
				c.render(owner);
			}
		} else {
			if(renderSelf) {
				owner.drawBox(box, style);
			}
			for(Component c : children) {
				c.render(owner);
			}
		}
	}
	
	//actually renders the RenderList stuff, but no sense in making another method since this one is already defined
	@Override
	protected void doRender() {
		//render frame background, then components
		GuiOwner owner = gui.getOwner().getGuiOwner();
		if(renderSelf) {
			owner.drawBox(box, style);
		}
		for(Component c : renderWithThis) {
			c.render(owner);
		}
	}
	
	protected void getRenderList(GuiOwner owner) {
		renderList = owner.newList(new RenderRunner());
		renderListState.setDirty(true);
	}
	
	private class RenderRunner implements Runnable {
		@Override
		public void run() {
			doRender();
		}
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
		boolean handled = false;
		for(Component c : children) {
			Box b = c.getBounds();
			if(b.contains(event.getX(), event.getY())) {
				handled = true;
				if(c instanceof Container) {
					//pass event down
					c.onMouseEvent(event);
					break;
				} else {
					if(c.isSelectable() && isSelection(event)) {
						gui.select(c);
					} else {
						//pass event to this component
						c.onMouseEvent(event);
					}
					break;
				}
			}
		}
		if(!handled) {
			mouseNotHandled(event);
		}
	}
	
	protected void mouseNotHandled(MouseEvent event) {
		gui.select(null);
	}
	
	private boolean isSelection(MouseEvent event) {
		if(event instanceof MouseButtonEvent) {
			MouseButtonEvent mEvent = (MouseButtonEvent)event;
			return mEvent.getButton() == 0 && mEvent.getType() == MouseEventType.DOWN;
		}
		return false;
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}

}
