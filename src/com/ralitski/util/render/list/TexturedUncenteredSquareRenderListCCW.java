package com.ralitski.util.render.list;

import org.lwjgl.opengl.GL11;

public class TexturedUncenteredSquareRenderListCCW extends ListMaker {
	
	public static TexturedUncenteredSquareRenderListCCW FULL = new TexturedUncenteredSquareRenderListCCW();
	
	private float minU;
	private float minV;
	private float maxU;
	private float maxV;

	public TexturedUncenteredSquareRenderListCCW() {
		this(0, 0, 1, 1);
	}
	
	public TexturedUncenteredSquareRenderListCCW(float maxU, float maxV) {
		this(0, 0, maxU, maxV);
	}
	
	public TexturedUncenteredSquareRenderListCCW(float minU, float minV, float maxU, float maxV) {
		this.minU = minU;
		this.minV = minV;
		this.maxU = maxU;
		this.maxV = maxV;
	}

    @Override
    public void makeList() {
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(minU, minV);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(minU, maxV);
            GL11.glVertex2f(0, 1);
            GL11.glTexCoord2f(maxU, maxV);
            GL11.glVertex2f(1, 1);
            GL11.glTexCoord2f(maxU, minV);
            GL11.glVertex2f(1, 0);
        }
        GL11.glEnd();
    }

}
