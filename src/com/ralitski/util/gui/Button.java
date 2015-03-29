package com.ralitski.util.gui;

import com.ralitski.util.gui.render.FontRenderer;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseButtonEvent;
import com.ralitski.util.input.event.MouseButtonEvent.MouseEventType;
import com.ralitski.util.input.event.MouseEvent;

public class Button extends ComponentAbstract {
	
	private String title;
	private int mouseButton = 0;
	
	public Button(Gui gui, String title) {
		super(gui);
		this.title = title;
	}
	
	public Button(Gui gui, int width, int height, String title) {
		super(gui, width, height);
		this.title = title;
	}
	
	public Button(Gui gui, Box box, String title) {
		super(gui, box);
		this.title = title;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public void setMouseButton(int mouseButton) {
		this.mouseButton = mouseButton;
	}

	@Override
	public boolean useParentRenderList() {
		return true;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(renderListState != null) renderListState.setDirty(true);
	}

	protected void doRender() {
		super.doRender();
		//love me some getters
		gui.getOwner().getGuiOwner().getFontRenderer().renderLine(title, box, this, style, FontRenderer.WIDTH_ALIGN_CENTER | FontRenderer.HEIGHT_ALIGN_CENTER);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
		//event can be assumed to be within this button's bounds
		if(event instanceof MouseButtonEvent && box.contains(event.getX(), event.getY())) {
			MouseButtonEvent mEvent = (MouseButtonEvent)event;
			//TODO: should I alert on release instead? maybe keep track of where it went down,
			//then if it goes down and comes up within button, fire event? hmm...
			if(mEvent.getButton() == mouseButton && mEvent.getType() == MouseEventType.DOWN) {
				for(GuiEventListener listener : eventListeners) {
					listener.onGuiEvent(new GuiEvent(this, title));
				}
			}
		}
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		//dun care
	}

}
