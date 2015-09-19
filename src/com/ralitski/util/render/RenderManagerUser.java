package com.ralitski.util.render;

import com.ralitski.util.render.camera.CameraOwner;
import com.ralitski.util.render.display.DisplayUser;

public interface RenderManagerUser extends DisplayUser, CameraOwner {
	//render stationary stuff in 3d (eg skybox)
	void render3dUntransformed(float partial, float partialFromLast);
	//render stuff relative to camera (eg world)
	void render3dRotated(float partial, float partialFromLast);
	void render3dTransformed(float partial, float partialFromLast);
	//render 2D stuff on screen
	void render2d(float partial, float partialFromLast);
}
