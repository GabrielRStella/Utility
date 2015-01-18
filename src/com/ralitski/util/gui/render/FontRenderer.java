package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Box;

//TODO: replace strings with RenderString (attributes)
public interface FontRenderer {
	public static final int ALIGN_LEFT_CUT_LEFT = 1;
	public static final int ALIGN_LEFT_CUT_RIGHT = 2;
	public static final int ALIGN_LEFT = ALIGN_LEFT_CUT_LEFT | ALIGN_LEFT_CUT_RIGHT;
	
	public static final int ALIGN_RIGHT_CUT_LEFT = 4;
	public static final int ALIGN_RIGHT_CUT_RIGHT = 8;
	public static final int ALIGN_RGHT = ALIGN_RIGHT_CUT_LEFT | ALIGN_RIGHT_CUT_RIGHT;
	
	public static final int ALIGN_CENTER = 16;
	
	void renderLine(String line, Box bounds, int align);
}
