package com.ralitski.util.render.list;

import org.lwjgl.opengl.GL11;

public class CenteredSquareRenderList extends ListMaker {
	
	private static CenteredSquareRenderList instance = new CenteredSquareRenderList();
	
	public static CenteredSquareRenderList getInstance() {
		return instance;
	}
	
	private CenteredSquareRenderList() {
		GLListHelper.getList(this);
	}

	@Override
	public void makeList() {
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2f(-0.5F, -0.5F);
			GL11.glVertex2f(-0.5F, 0.5F);
			GL11.glVertex2f(0.5F, 0.5F);
			GL11.glVertex2f(0.5F, -0.5F);
		}
		GL11.glEnd();
	}
	
}
