package com.ralitski.util.gui;

import com.ralitski.util.gui.render.RenderList;
import com.ralitski.util.gui.render.RenderListState;
import com.ralitski.util.gui.render.RenderStyle;

public abstract class ComponentAbstract implements Component {
	
	protected Gui gui;
	protected int id = -1;
	protected Box box;
	protected RenderStyle style;
	protected Container parent;
	//render list stuff
	protected RenderList renderList;
	protected RenderListState renderListState;
	
	public ComponentAbstract(Gui gui) {
		prepare(gui);
		GuiOwner owner = gui.getOwner().getGuiOwner();
		box = new Box(0, 0, owner.getWidth(), owner.getHeight());
	}
	
	public ComponentAbstract(Gui gui, int width, int height) {
		prepare(gui);
		GuiOwner owner = gui.getOwner().getGuiOwner();
		box = new Box(0, 0, Math.min(width, owner.getWidth()), Math.min(height, owner.getHeight()));
		BoxPosition.position(null, box, gui.getOwner().getWindow(), BoxPosition.CENTER);
	}
	
	public ComponentAbstract(Gui gui, Box box) {
		prepare(gui);
		BoxPosition.position(null, box, gui.getOwner().getWindow(), BoxPosition.WITHIN_STRICT);
		this.box = box;
	}
	
	private void prepare(Gui gui) {
		this.gui = gui;
		GuiOwner owner = gui.getOwner().getGuiOwner();
		if(owner.supportLists()) {
			renderListState = new RenderListState();
//			getRenderList(owner);
		}
	}
	
	//stuff

	@Override
	public Gui getGui() {
		return gui;
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
		return parent;
	}

	@Override
	public void setParent(Container container) {
		this.parent = container;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public void setSelected(boolean selected) {
		throw new UnsupportedOperationException("This Component can't be selected.");
	}

	@Override
	public boolean isSelected() {
		return false;
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

	@Override
	public boolean useParentRenderList() {
		return false;
	}

	@Override
	public void setParentRenderList(RenderListState state) {
		if(useParentRenderList()) {
			this.renderListState = state;
		} else {
			throw new UnsupportedOperationException("This Component does not share a RenderList with their parent");
		}
	}
	
	public void render(GuiOwner owner) {
		if(owner.supportLists() && !useParentRenderList()) {
			if(renderList == null) getRenderList(owner);
			if(renderListState.isDirty() || !renderList.registered()) {
				renderList.compile();
			}
			renderList.call();
		} else {
			doRender();
		}
	}
	
	protected void getRenderList(GuiOwner owner) {
		renderList = owner.newList(new RenderRunner());
		renderListState.setDirty(true);
	}
	
	protected void doRender() {
		gui.getOwner().getGuiOwner().drawBox(box, style);
	}
	
	private class RenderRunner implements Runnable {
		@Override
		public void run() {
			doRender();
		}
	}

}
