package com.ralitski.util.gui;

import com.ralitski.util.gui.render.RenderListState;
import com.ralitski.util.gui.render.RenderStyle;
import com.ralitski.util.input.InputUser;

//TODO: possibly update() or something
//TODO: edit input event hierarchy
//note: input events passed are not guaranteed to be within this component
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

	int getMinWidth();
	void setMinWidth(int width);
	int getMinHeight();
	void setMinHeight(int height);
	//control whether the container can be resized by its parent's Layout
	void setResizable(boolean resizable);
	boolean isResizable();
	
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
	
	//event system

	void addComponentEventListener(ComponentEventListener listener);
	void removeComponentEventListener(ComponentEventListener listener);
	
	/**
	 * Tells the component to release all of its resources.
	 */
	void delete();
}
