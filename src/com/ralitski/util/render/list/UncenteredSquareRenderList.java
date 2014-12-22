package com.ralitski.util.render.list;

import org.lwjgl.opengl.GL11;

public class UncenteredSquareRenderList extends ListMaker {
	
	private static UncenteredSquareRenderList instance = new UncenteredSquareRenderList();
	
	public static UncenteredSquareRenderList getInstance() {
		return instance;
	}
	
	private UncenteredSquareRenderList() {
		GLListHelper.getList(this);
	}

	@Override
	public void makeList() {
		GL11.glBegin(GL11.GL_QUADS);
		{
			//uncentered! gasp! whatev
			GL11.glVertex2f(0F, 0F);
			GL11.glVertex2f(0F, 1F);
			GL11.glVertex2f(1F, 1F);
			GL11.glVertex2f(1F, 0F);
		}
		GL11.glEnd();
	}
	
}
