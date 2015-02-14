package com.ralitski.util.gui;

import com.ralitski.util.gui.render.RenderStyle;

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
	
	//this should reflect a mobile boundary for the component.
	Box getBounds();
	
	//gui owner included for ease
	void render(GuiOwner manager);
	//null or empty to have no hovertext
	String getHoverText();
	
	void setRenderStyle(int index, RenderStyle s);
	RenderStyle getRenderStyle(int index);
	//the number of styles that can be applied to this component
	int getRenderStyles();
}
