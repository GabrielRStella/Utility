package com.ralitski.util.render.img;

public class Texture {

	public int id, width, height;
	
	Texture(int id, int w, int h)
	{
		this.id = id;
		this.width = w;
		this.height = h;
	}
	
	public void setData(Image newImage)
	{
		this.id = newImage.id();
		this.width = newImage.width();
		this.height = newImage.height();
	}

	public int id()
	{
		return this.id;
	}
	
	public int width()
	{
		return this.width;
	}
	
	public int height()
	{
		return this.height;
	}
	
	public void bind() {
		ImageManager.bindTexture(this.id);
	}
	
	public static Texture getData(Image i)
	{
		return new Texture(i.id(), i.width(), i.height());
	}
}
