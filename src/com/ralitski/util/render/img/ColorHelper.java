package com.ralitski.util.render.img;

public class ColorHelper {

	public static int WHITE, GRAY, BLACK, RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA;
	
	static
	{
		WHITE = toCompressed(255,255,255);
		GRAY = toCompressed(160, 160, 160);
		BLACK = toCompressed(0,0,0);
		RED = toCompressed(255,0,0);
		GREEN = toCompressed(0,255,0);
		BLUE = toCompressed(0,0,255);
		YELLOW = toCompressed(255,255,0);
		CYAN = toCompressed(0,255,255);
		MAGENTA = toCompressed(255,0,255);
	}

	public static int fromHex(String hex)
	{
		return Integer.parseInt(hex, 16);
	}
	
	// TO HEX use method
	
	public static String toHex(int...colors)
	{
		int compress = toCompressed(colors);
		return toHex(compress);
	}
	
	public static String toHex(int color) {
		return Integer.toHexString(color);
	}
	
	// Compressed use method
	
	public static int[] fromCompressed(int i)
	{
        int a = i >> 24 & 255;
        int r = i >> 16 & 255;
        int g = i >> 8 & 255;
        int b = i & 255;
        return new int[]{a, r, g, b};
	}
	
	public static int[] fromCompressedNoAlpha(int i)
	{
        int r = i >> 16 & 255;
        int g = i >> 8 & 255;
        int b = i & 255;
        return new int[]{r, g, b};
	}
	
	// TO Compressed use method
	
	public static int toCompressed(int...i)
	{
		if(i.length == 4)
		{
			int a = i[0] << 24;
			int r = i[1] << 16;
			int g = i[2] << 8;
			int b = i[3];
			return a + r + g + b;
		} else if(i.length == 3)
		{
			int a = 255 << 24;
			int r = i[0] << 16;
			int g = i[1] << 8;
			int b = i[2];
			return a + r + g + b;
		}
		return 0;
	}
	
	public static int toCompressedNoAlpha(int...i)
	{
		if(i.length != 3) return -1;
		int n = 256;
		int r = i[0] * n * n;
		int g = i[1] * n;
		int b = i[2];
		return r + g + b;
	}
	
	public static int fullAlpha(int i) {
		return i | 0xFF000000;
	}
	
	public static Color blend(Color color1, Color color2, float grad) {
		float grad2 = 1F - grad;
		float r = color1.getRedFloat() * grad + color2.getRedFloat() * grad2;
		float g = color1.getGreenFloat() * grad + color2.getGreenFloat() * grad2;
		float b = color1.getBlueFloat() * grad + color2.getBlueFloat() * grad2;
		float a = color1.getAlphaFloat() * grad + color2.getAlphaFloat() * grad2;
		return new Color(r, g, b, a);
	}
}
