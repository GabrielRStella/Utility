package com.ralitski.util.render.gui;

import com.ralitski.util.BoundingBox;
import com.ralitski.util.render.img.Color;

public interface GuiRenderManager {
	
	FontRenderer getFontRenderer();
	//allows the render manager to override the manner in which certain components are rendered.
	<T extends Component> Renderer<T> getSpecialRenderer(T t);
	
	//filtering methods
	
	//foreground, eg button
	void drawForegroundBox(Box box);
	//background, eg frame
	void drawBackgroundBox(Box box);
	//inset, eg unclickable button or background or scrollbar
	void drawInsetBox(Box box);
	
	//direct methods
	void drawRectangle(Box box, Color color);
	void drawTexturedRectangle(Box box, BoundingBox uv, Color color);
	
	//draws the edges of a box
	void drawBounds(Box box, Color color);
}
