package com.ralitski.util.render;

import com.ralitski.util.render.camera.CameraOwner;
import com.ralitski.util.render.display.DisplayUser;

public interface RenderManagerUser extends DisplayUser, CameraOwner {
	//render stationary stuff in 3d (eg skybox)
	void render3d(float partial);
	//render stuff relative to camera (eg world)
	void render3dRotated(float partial);
	void render3dTranslated(float partial);
	//render 2D stuff on screen
	void render2d(float partial);
}
