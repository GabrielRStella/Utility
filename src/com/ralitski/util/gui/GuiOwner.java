package com.ralitski.util.gui;

import com.ralitski.util.gui.render.FontRenderer;
import com.ralitski.util.gui.render.RenderList;
import com.ralitski.util.gui.render.RenderStyle;
import com.ralitski.util.render.img.Color;
import com.ralitski.util.render.img.Image;

public interface GuiOwner {
	int getWidth();
	int getHeight();
	void onTopLevelGuiClose();
	
	//rendering methods
	
	FontRenderer getFontRenderer();
	//quick draw, used by Gui to render a black tint
	void drawBox(Box box, Color c);
	//use the box's RenderStyle to draw its bounds (and bg)
	void drawBox(Box box, RenderStyle style);
	//draw an image scaled to fit inside the specified box
	void drawImage(Image image, Box box, RenderStyle style);
	//render lists
	boolean supportLists();
	
	/**
	 * 
	 * @param renderer a runnable to be called when the list must be (re)generated
	 * @return a render list which will execute the code supplied in renderer.run(). The RenderList will not have been previously compiled.
	 */
	RenderList newList(Runnable renderer);
}
