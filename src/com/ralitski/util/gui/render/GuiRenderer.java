package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.Component;
import com.ralitski.util.gui.Dimension;
import com.ralitski.util.render.img.Color;
import com.ralitski.util.render.img.Image;

public interface GuiRenderer {
	
	/*
	 * font rendering
	 */

	/**
	 * Aligns the object such that its left edge is against the left edge of its bounds.
	 */
	public static final int ALIGN_WIDTH_LEFT = 1;
	/**
	 * Aligns the object such that its right edge is against the right edge of its bounds.
	 */
	public static final int ALIGN_WIDTH_RIGHT = 2;
	/**
	 * Aligns the object such that its center is over the center of its bounds (widthwise). This is the default horizontal alignment.
	 */
	public static final int ALIGN_WIDTH_CENTER = 4;
	/**
	 * Aligns the object such that its center is over the center of its bounds (heightwise).This is the default vertical alignment.
	 */
	public static final int ALIGN_HEIGHT_CENTER = 8;
	/**
	 * Aligns the object such that its bottom edge is against the bottom edge of its bounds.
	 */
	public static final int ALIGN_HEIGHT_BOTTOM = 16;
	/**
	 * Aligns the object such that its top edge is against the top edge of its bounds.
	 */
	public static final int ALIGN_HEIGHT_TOP = 32;
	/**
	 * Scales the object to completely cover the given bounds, cutting off any parts of the object that end up outside of the bounds.
	 */
	public static final int ALIGN_SCALE_MAXIMIZE = 64;
	/**
	 * Scales the object to fit completely within the given bounds, possibly leaving empty space between the bounds and the object in one direction.
	 */
	public static final int ALIGN_SCALE_MINIMIZE = 128;
	/**
	 * Does not scale the object. This is the default scale.
	 */
	public static final int ALIGN_SCALE_NONE = 256;

	/*
	 * image and font rendering
	 */
	
	/**
	 * Creates a RenderList for the given text.
	 * @param line The text to be drawn
	 * @param c The component that is related to the given RenderStyle
	 * @param style The style to render the text with
	 * @return A RenderListBounded representing the rendered text
	 */
	RenderListBounded getTextRenderList(String line, Component c, RenderStyle style);
	
	/**
	 * Calculates the dimensions of the given text as it would be rendered with the given style.
	 * @param line The text to be drawn
	 * @param c The component that is related to the given RenderStyle
	 * @param style The style to render the text with
	 * @return The dimensions of the text when rendered
	 */
	Dimension getDimensions(String line, Component c, RenderStyle style);
	
	/**
	 * Creates a RenderList for the given image.
	 * @param image The image to be prepared
	 * @return A RenderList representing the given image
	 */
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
	 * @return a render list which will execute the code supplied in renderer.run(). The RenderList will not be compiled when it is returned.
	 */
	RenderList newList(Runnable renderer);
}
