package com.ralitski.util.gui;

import com.ralitski.util.gui.render.GuiRenderManager;

public interface GuiOwner {
	int getWidth();
	int getHeight();
	GuiRenderManager getRenderManager();
	void onTopLevelGuiClose();
}
