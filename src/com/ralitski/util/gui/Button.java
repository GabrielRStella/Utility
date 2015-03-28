package com.ralitski.util.gui;

import com.ralitski.util.gui.render.FontRenderer;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseButtonEvent;
import com.ralitski.util.input.event.MouseButtonEvent.MouseEventType;
import com.ralitski.util.input.event.MouseEvent;

public class Button extends ComponentAbstract {
	
	private String title;
	private int mouseButton = 0;
	
	public Button(Gui gui) {
		super(gui);
	}
	
	public Button(Gui gui, int width, int height) {
		super(gui, width, height);
	}
	
	public Button(Gui gui, Box box) {
		super(gui, box);
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public void setMouseButton(int mouseButton) {
		this.mouseButton = mouseButton;
	}
	
	protected void doRender() {
		super.doRender();
		//love me some getters
		gui.getOwner().getGuiOwner().getFontRenderer().renderLine(title, box, FontRenderer.ALIGN_CENTER);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
		//event can be assumed to be within this button's bounds
		if(event instanceof MouseButtonEvent) {
			MouseButtonEvent mEvent = (MouseButtonEvent)event;
			//TODO: should I alert on release instead? maybe keep track of where it went down,
			//then if it goes down and comes up within button, fire event? hmm...
			if(mEvent.getButton() == mouseButton && mEvent.getType() == MouseEventType.DOWN) {
				for(GuiEventListener listener : eventListeners) {
					listener.onGuiEvent(new GuiEvent(this, null));
				}
			}
		}
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		//dun care
	}

}
