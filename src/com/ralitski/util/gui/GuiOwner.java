package com.ralitski.util.gui;

import com.ralitski.util.gui.render.GuiRenderer;

public interface GuiOwner {
	int getWidth();
	int getHeight();
	void onTopLevelGuiClose();
	GuiRenderer getRenderer();
}
