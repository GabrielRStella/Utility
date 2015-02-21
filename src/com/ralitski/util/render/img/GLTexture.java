package com.ralitski.util.render.img;

import org.lwjgl.opengl.GL11;

/**
 * references an Image in opengl without storing the image's data separately.
 * 
 * @author ralitski
 *
 */
public class GLTexture {

	private int id;
	private int width;
	private int height;
	private int minFilter;
	private int magFilter;
	
	public GLTexture(GLImage i)
	{
		this(i.getId(), i.getWidth(), i.getHeight(), i.getMinFilter(), i.getMagFilter());
	}
	
	public GLTexture(int id, int width, int height, int minFilter, int magFilter) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.minFilter = minFilter;
		this.magFilter = magFilter;
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getMinFilter() {
		return minFilter;
	}

	public void setMinFilter(int minFilter) {
		this.minFilter = minFilter;
		GLImageHelper.resetImageFilters(id, minFilter, magFilter);
	}

	public int getMagFilter() {
		return magFilter;
	}

	public void setMagFilter(int magFilter) {
		this.magFilter = magFilter;
		GLImageHelper.resetImageFilters(id, minFilter, magFilter);
	}

    public void blend() {
        minFilter = GL11.GL_LINEAR;
        magFilter = GL11.GL_LINEAR;
    }

    public void noBlend() {
        minFilter = GL11.GL_NEAREST;
        magFilter = GL11.GL_NEAREST;
    }
	
	public void glBind() {
		GLImageHelper.bindTexture(id);
	}
	
	public void glDelete() {
		GLImageHelper.deleteImage(id);
		id = -1;
	}
}
