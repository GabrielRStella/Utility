package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Box;

//TODO: replace strings with RenderString (attributes)
public interface FontRenderer {
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_CENTER = 4;
	
	void renderLine(String line, Box bounds, int align);
}
