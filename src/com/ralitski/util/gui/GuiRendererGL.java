package com.ralitski.util.gui;

import org.lwjgl.opengl.GL11;

import com.ralitski.util.gui.render.GuiRenderer;
import com.ralitski.util.gui.render.RenderList;
import com.ralitski.util.gui.render.RenderListBounded;
import com.ralitski.util.gui.render.RenderStyle;
import com.ralitski.util.render.img.Color;
import com.ralitski.util.render.img.GLImage;
import com.ralitski.util.render.img.Image;
import com.ralitski.util.render.list.GLListHelper;
import com.ralitski.util.render.list.RunnableListMaker;
import com.ralitski.util.render.list.TexturedUncenteredSquareRenderListCCW;
import com.ralitski.util.render.list.TexturedUncenteredSquareRenderListCW;

public abstract class GuiRendererGL implements GuiRenderer {
	
	private boolean flipTextures = true;
	private boolean isCW = true;

	public boolean isFlipTextures() {
		return flipTextures;
	}

	public void setFlipTextures(boolean flipTextures) {
		this.flipTextures = flipTextures;
	}

	public boolean isCW() {
		return isCW;
	}

	public void setCW(boolean isCW) {
		this.isCW = isCW;
	}

	@Override
	public boolean supportLists() {
		return true;
	}

	@Override
	public RenderList newList(Runnable renderer) {
		return new RunnableListMaker(renderer);
	}

	@Override
	public void drawBox(Box box, Color c) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		c.glColor();
		GL11.glPushMatrix();
		GL11.glTranslatef(box.getMinX(), box.getMinY(), 0);
		GL11.glScalef(box.getWidth(), box.getHeight(), 1);
		if(isCW) GLListHelper.getSquareListUncenteredCW().call();
		else GLListHelper.getSquareListUncenteredCCW().call();
		GL11.glPopMatrix();
	}

	@Override
	public void drawBox(Box box, Component c, RenderStyle style) {
		boolean useImage = false;
		if(style == null) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(1, 1, 1, 1);
		} else {
			Image image = (Image)style.getStyle(c, "image");
			if(image == null) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				Color color = (Color)style.getStyle(c, "color");
				if(color == null) {
					GL11.glColor4f(1, 1, 1, 1);
				} else color.glColor();
			} else {
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GLImage glImage = new GLImage(image);
				glImage.glPrepare();
				glImage.glBind();
				useImage = true;
			}
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(box.getMinX(), box.getMinY(), 0);
		GL11.glScalef(box.getWidth(), box.getHeight(), 1);
		
		if(useImage) {
			if(isCW) TexturedUncenteredSquareRenderListCW.FULL.call();
			else TexturedUncenteredSquareRenderListCCW.FULL.call();
		} else {
			if(isCW) GLListHelper.getSquareListUncenteredCW().call();
			else GLListHelper.getSquareListUncenteredCCW().call();
		}
		
		GL11.glPopMatrix();
	}

	/*
	@Override
	public void drawImage(Image image, Box box, Component c, RenderStyle style) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GLImage glImage = new GLImage(image);
		glImage.glPrepare();
		glImage.glBind();
		if(style == null) {
			GL11.glColor4f(1, 1, 1, 1);
		} else {
			Color color = (Color)style.getStyle(c, "color");
			if(color == null) {
				GL11.glColor4f(1, 1, 1, 1);
			} else color.glColor();
		}
		GL11.glPushMatrix();
		if(flipTextures) {
			GL11.glTranslatef(box.getMinX(), box.getMinY() + box.getHeight(), 0);
			GL11.glScalef(box.getWidth(), -box.getHeight(), 1);
		} else {
			GL11.glTranslatef(box.getMinX(), box.getMinY(), 0);
			GL11.glScalef(box.getWidth(), box.getHeight(), 1);
		}
		if(isCW) TexturedUncenteredSquareRenderListCW.FULL.call();
		else TexturedUncenteredSquareRenderListCCW.FULL.call();
		GL11.glPopMatrix();
	}
	 */

	@Override
	public RenderListBounded getTextRenderList(String line, Component c,
			RenderStyle style) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getDimensions(String line, Component c, RenderStyle style) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RenderListBounded getImageRenderList(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Box getBounds(RenderListBounded list, Box bounds, Component c,
			RenderStyle style, int align) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawBox(RenderListBounded list, Box box, Component c,
			RenderStyle style, int align) {
		// TODO Auto-generated method stub
		
	}

}
