package com.ralitski.util.render.img;

import org.lwjgl.opengl.GL11;

public class GLImage {
	
	private Image src;
	private int id = -1;
    
    private int minFilter = GL11.GL_LINEAR;
    private int magFilter = GL11.GL_LINEAR;
    
    public GLImage(Image src) {
    	this.src = src;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }
    
    public Image getSource() {
    	return src;
    }
    
    public int getWidth() {
    	return src.getWidth();
    }
    
    public int getHeight() {
    	return src.getHeight();
    }
	
	//opengl

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
    
    public void glBind() {
    	GLImageHelper.bindTexture(id);
    }
    
    public void glPrepare() {
    	GLImageHelper.setup(this);
    }
    
    public void glDelete() {
    	GLImageHelper.deleteImage(id);
    	id = -1;
    }
}
