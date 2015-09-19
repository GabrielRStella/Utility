package com.ralitski.util.gui;

import com.ralitski.util.gui.render.RenderStyle;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseButtonEvent;
import com.ralitski.util.input.event.MouseButtonEvent.MouseEventType;
import com.ralitski.util.input.event.MouseEvent;

public class Button extends ComponentAbstract {
	
	private String title;
	private int mouseButton = 0;
	
	private BoxOutset textOffset;
	private int textOutsetX;
	private int textOutsetY;
	private RenderStyle text;
	
	public Button(Gui gui, String title) {
		super(gui);
		prepare(title);
	}
	
	public Button(Gui gui, int width, int height, String title) {
		super(gui, width, height);
		prepare(title);
	}
	
	public Button(Gui gui, Box box, String title) {
		super(gui, box);
		prepare(title);
	}
	
	private void prepare(String title) {
		setTitle(title);
		textOffset = new BoxOutset(box, 0, 0);
	}

	@Override
	public void setRenderStyle(int index, RenderStyle s) {
		if(index == 0) style = s;
		else text = s;
	}

	@Override
	public RenderStyle getRenderStyle(int index) {
		return index == 0 ? style : text;
	}

	@Override
	public int getRenderStyles() {
		return 2;
	}
	
	public RenderStyle getTextStyle() {
		return text != null ? text : style;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = Math.max(minWidth, gui.getOwner().getGuiOwner().getFontRenderer().getDimensions(title, this, getTextStyle()).getWidth() - textOffset.getWidth() * 2);
	}

	public void setMinHeight(int minHeight) {
		this.minWidth = Math.max(minWidth, gui.getOwner().getGuiOwner().getFontRenderer().getDimensions(title, this, getTextStyle()).getHeight() - textOffset.getHeight() * 2);
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public void setMouseButton(int mouseButton) {
		this.mouseButton = mouseButton;
	}
	
	//use negative because outset -> inset
	
	public int getTextInsetX() {
		return -textOffset.getOutsetX();
	}
	
	public void setTextInsetX(int textInset) {
		textOffset.setOutsetX(textOutsetX = -textInset);
	}
	
	public int getTextInsetY() {
		return -textOffset.getOutsetY();
	}
	
	public void setTextInsetY(int textInset) {
		textOffset.setOutsetY(textOutsetY = -textInset);
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
		gui.getOwner().getGuiOwner().getFontRenderer().renderLine(title, textOffset, this, getTextStyle(), FontRenderer.WIDTH_ALIGN_CENTER | FontRenderer.HEIGHT_ALIGN_CENTER);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
		//event can be assumed to be within this button's bounds
		if(event instanceof MouseButtonEvent && box.contains(event.getX(), event.getY())) {
			MouseButtonEvent mEvent = (MouseButtonEvent)event;
			//TODO: should I alert on release instead? maybe keep track of where it went down,
			//then if it goes down and comes up within button, fire event? hmm...
			if(mEvent.getButton() == mouseButton && mEvent.getType() == MouseEventType.DOWN) {
				for(ComponentEventListener listener : eventListeners) {
					listener.onComponentEvent(new ComponentEvent(this, title));
				}
			}
		}
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		//dun care
	}
	
	protected void setBox(Box box) {
		super.setBox(box);
		textOffset = new BoxOutset(box, textOutsetX, textOutsetY);
	}

}
