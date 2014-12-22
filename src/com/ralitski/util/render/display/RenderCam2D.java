package com.ralitski.util.render.display;

import org.lwjgl.opengl.GL11;

public class RenderCam2D {
	
	private DisplayUser user;
	private boolean textured;
	
	public RenderCam2D(DisplayUser user)
	{
		this.user = user;
	}
	
	public void setTextured(boolean flag)
	{
		this.textured = flag;
	}
	
	public void setup()
	{
		this.setup(false);
	}

	public void setup(boolean deep)
	{
		if(this.textured) GL11.glEnable(GL11.GL_TEXTURE_2D);
		else GL11.glDisable(GL11.GL_TEXTURE_2D);
		if(!deep) GL11.glDisable(GL11.GL_DEPTH_TEST);
		else GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

        rescale();
	}
	
	public void rescale()
	{
		rescale(user.getWidth(), user.getHeight());
	}
	
	public void rescale(int width, int height)
	{
		rescale(0, 0, width, height);
	}
	
	public void rescale(int x, int y, int width, int height)
	{
		int mode = GL11.glGetInteger(GL11.GL_MATRIX_MODE);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//left, right, bottom, top, zNear, zFar
		GL11.glOrtho(0, width, 0, height, 0, 1);
		GL11.glViewport(x, y, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(mode);
	}
	
	public void update() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
}
