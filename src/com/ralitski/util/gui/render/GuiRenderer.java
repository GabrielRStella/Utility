package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.Component;
import com.ralitski.util.gui.Dimension;
import com.ralitski.util.render.img.Color;
import com.ralitski.util.render.img.Image;

public interface GuiRenderer {
	
	/*
	 * font rendering
	 * TODO: alignments for rendering RenderListBounded objects (to control centering, cropping, scaling, etc)
	 */

	public static final int ALIGN_WIDTH_LEFT = 1;
	public static final int ALIGN_WIDTH_RIGHT = 2;
	public static final int ALIGN_WIDTH_CENTER = 4;
	public static final int ALIGN_HEIGHT_CENTER = 8;
	public static final int ALIGN_HEIGHT_BOTTOM = 16;
	public static final int ALIGN_HEIGHT_TOP = 32;

	/*
	 * image and font rendering
	 */
	
	/**
	 * 
	 * @param line
	 * @param style
	 * @return
	 */
	RenderListBounded getTextRenderList(String line, Component c, RenderStyle style);
	
	Dimension getDimensions(String line, Component c, RenderStyle style);
	
	RenderListBounded getImageRenderList(Image image);
	
	/*
	 * general rendering
	 */
	
	//quick draw, used by Gui to render a black tint
	void drawBox(Box box, Color c);
	//use the box's RenderStyle to draw its bounds (and bg)
	void drawBox(Box box, Component c, RenderStyle style);
	//gets the adjusted bounds of the given RenderList when rendered with the given alignment in the given bounds
	Box getBounds(RenderListBounded list, Box bounds, Component c, RenderStyle style, int align);
	//draw a render list, scaled and formatted to fit inside the given bounds
	void drawBox(RenderListBounded list, Box box, Component c, RenderStyle style, int align);
	//render lists
	boolean supportLists();
	
	/**
	 * 
	 * @param renderer a runnable to be called when the list must be (re)generated
	 * @return a render list which will execute the code supplied in renderer.run(). The RenderList will not have been previously compiled.
	 */
	RenderList newList(Runnable renderer);
}
