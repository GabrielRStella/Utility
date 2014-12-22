package com.ralitski.util.render;

import com.ralitski.util.render.display.DisplayUser;

public interface RenderManagerUser extends DisplayUser {
	
	void render3d(float partial);
	void render2d(float partial);
}
