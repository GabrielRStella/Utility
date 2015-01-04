package com.ralitski.util.render.gui;

public interface GuiOwner {
	int getWidth();
	int getHeight();
	GuiRenderManager getRenderManager();
	void onTopLevelGuiClose();
}
