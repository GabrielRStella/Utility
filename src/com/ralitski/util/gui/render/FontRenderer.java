package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.Component;
import com.ralitski.util.gui.Dimension;

//TODO: replace strings with RenderString (attributes)
public interface FontRenderer {
	public static final int WIDTH_ALIGN_LEFT = 1;
	public static final int WIDTH_ALIGN_RIGHT = 2;
	public static final int WIDTH_ALIGN_CENTER = 4;
	public static final int HEIGHT_ALIGN_CENTER = 8;
	public static final int HEIGHT_ALIGN_BOTTOM = 16;
	public static final int HEIGHT_ALIGN_TOP = 32;
	
	void renderLine(String line, Box bounds, Component c, RenderStyle style, int align);
	
	Box getBounds(String line, Box bounds, Component c, RenderStyle style, int align);
	
	Dimension getDimensions(String line, Component c, RenderStyle style);
}
