package com.ralitski.util.render.img;

public class Pixel {

	private Image image;
	public int x;
	public int y;
	
	public Pixel(Image i, int x, int y) {
		this.image = i;
		this.x = x;
		this.y = y;
	}
	
	public Color getColor() {
		return image.getColor(x, y);
	}
	
	public void setColor(Color color) {
		image.setColor(x, y, color);
	}
}
