package com.ralitski.util.gui;

import com.ralitski.util.gui.render.RenderListState;
import com.ralitski.util.gui.render.RenderStyle;
import com.ralitski.util.input.InputUser;
import com.ralitski.util.input.event.MouseEvent;

//TODO: possibly update() or something
//note: all input events passed to a component can be assumed to be within the component (position already checked), unless receiving a selectedMouseEvent (or a KeyEvent...)
public interface Component extends InputUser {
	Gui getGui();
	Container getParent();
	//called by the Container, not by API user
	void setParent(Container container);
	
	int getId();
	void setId(int id);
	
	boolean isSelectable();
	void setSelected(boolean selected);
	boolean isSelected();
	
	//this should reflect an editable boundary for the element; any change to this box should be immediately available when dealing with the component.
	Box getBounds();
	
	//gui owner included for ease
	void render(GuiOwner owner);
	//null or empty to have no hovertext
	String getHoverText();
	
	void setRenderStyle(int index, RenderStyle s);
	RenderStyle getRenderStyle(int index);
	//the number of styles that can be applied to this component
	int getRenderStyles();
	
	/**
	 * If this Component will be put inside its parent's RenderList (if supported), false means it will render itself (and handle RenderLists itself)
	 * @return
	 */
	boolean useParentRenderList();
	
	/**
	 * Adds a RenderListState for this Component to mark the parent's RenderList as dirty, etc.
	 * @param state an object keeping track of the state of the parent's render list, to be updated by this component
	 */
	void setParentRenderList(RenderListState state);
	
	//extra input
	
	/**
	 * sends a MouseEvent to a selected component.
	 * @param event The event in question
	 * @return true to mark that the event has been received and handled, false to allow other Components to handle the event
	 */
	boolean receiveSelectedMouseEvent(MouseEvent event);
	
	//event system

	void addGuiEventListener(GuiEventListener listener);
	void removeGuiEventListener(GuiEventListener listener);
}
