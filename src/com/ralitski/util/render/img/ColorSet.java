package com.ralitski.util.render.img;

import org.lwjgl.opengl.GL11;

public class ColorSet {

	//array of stores colors
	public Color[] colors;
	
	//transition time (color loops)
	public int time = 0;
	
	//time left until transition (reset to value of 'time' on hitting 0)
	public int timeLeft = 0;
	
	//current index in color loop
	public int index = 0;
	
	//whether or not to do update logic
	public boolean loop = false;

	public ColorSet(Color color)
	{
		this.colors = new Color[]{color};
	}
	
	public ColorSet(Color...colors)
	{
		this.colors = colors;
		this.loop = true;
	}
	
	public Color color(int i)
	{
		return this.colors[i];
	}
	
	public Color color()
	{
		return this.colors[this.index];
	}
	
	public java.awt.Color jColor()
	{
		return this.color().jColor();
	}
	
	public void color(Color c)
	{
		this.colors[this.index] = c; 
	}
	
	public void color(Color c, int i)
	{
		this.colors[i] = c;
	}
	
	public void setTime(int i)
	{
		this.time = i;
	}
	
	public void reColor(Color...colors)
	{
		this.colors = colors;
		this.index = 0;
	}
	
	public void update()
	{
		if(this.loop)
		{
			this.timeLeft--;
			if(this.timeLeft <= 0)
			{
				this.timeLeft = this.time;
				this.index++;
				this.index %= this.colors.length;
			}
		}
	}
	
	public void glColor()
	{
		Color c = this.color();
		GL11.glColor4f((float)c.getRed() / 255F, (float)c.getGreen() / 255F, (float)c.getBlue() / 255F, (float)c.getAlpha() / 255F);
	}
}
