package com.ralitski.util.gui;

import com.ralitski.util.gui.render.GuiRenderManager;

//TODO: input
//also rendering optimization (to allow "render list" type things)
public interface Component {
	Gui getGui();
	Container getParent();
	void setParent(Container container);
	
	int getId();
	void setId(int id);
	
	boolean isSelectable();
	void setSelected(boolean selected);
	boolean isSelected();
	
	//this should reflect an editable (movable) boundary for the component.
	Box getBounds();
	
	//render manager included for ease
	void render(GuiRenderManager manager);
	String getHoverText();
}
