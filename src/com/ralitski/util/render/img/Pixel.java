package com.ralitski.util.render.img;

public class Pixel {

	public int x;
	public int y;
	public int color;
	
	public Pixel(int i, int j, byte[] data)
	{
		this.x = i;
		this.y = j;
		byte r = data[0];
		byte g = data[1];
		byte b = data[2];
		byte a = data[3];
		this.color = ColorHelper.toCompressed(r, g, b, a);
	}
	
	public static boolean compare(Pixel p1, Pixel p2)
	{
		return Color.compare(new Color(p1.color), new Color(p2.color));
	}
	
	public static int difference(Pixel p1, Pixel p2)
	{
		return Color.difference(new Color(p1.color), new Color(p2.color));
	}
}
