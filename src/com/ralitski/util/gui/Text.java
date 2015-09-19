package com.ralitski.util.gui;

import com.ralitski.util.gui.render.GuiRenderer;
import com.ralitski.util.gui.render.RenderListBounded;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseEvent;

public class Text extends ComponentAbstract {
	
	private String title;
	private RenderListBounded text;
	
	private boolean minDirty = true;
	private int minWidth = 0;
	private int minHeight = 0;
	
	public Text(Gui gui, String title) {
		super(gui);
		prepare(title);
	}
	
	public Text(Gui gui, int width, int height, String title) {
		super(gui, width, height);
		prepare(title);
	}
	
	public Text(Gui gui, Box box, String title) {
		super(gui, box);
		prepare(title);
	}
	
	private void prepare(String title) {
		setTitle(title);
	}

	public int getMinWidth() {
		if(minDirty) {
			recalcMin();
		}
		return minWidth;
	}

	public int getMinHeight() {
		if(minDirty) {
			recalcMin();
		}
		return minHeight;
	}
	
	private void recalcMin() {
		Dimension d = gui.getOwner().getGuiOwner().getRenderer().getDimensions(title, this, style);
		minWidth = d.getWidth();
		minHeight = d.getHeight();
		box.setWidth(minWidth);
		box.setHeight(minHeight);
		minDirty = false;
	}
	
	public boolean isResizable() {
		return true;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(renderListState != null) renderListState.setDirty(true);
		if(text != null) text.delete();
		text = null;
	}

	protected void doRender() {
		//love me some getters
		GuiRenderer renderer = gui.getOwner().getGuiOwner().getRenderer();
		if(text == null) {
			text = renderer.getTextRenderList(title, this, style);
		}
		renderer.drawBox(text, box, null, null, GuiRenderer.ALIGN_WIDTH_CENTER | GuiRenderer.ALIGN_HEIGHT_CENTER);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}
	
	public void delete() {
		if(text != null) text.delete();
	}

}
