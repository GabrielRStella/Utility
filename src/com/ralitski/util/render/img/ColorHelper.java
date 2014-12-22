package com.ralitski.util.render.img;

public class ColorHelper {
	
	/*
	TODO rework logic to store colors as (argb), which allows decoding of compressed colors w/ alpha by
	a regular decompression method, ie:
	int color = ColorHelper.toCompressedAlpha(255 a, 110 r, 100 g, 90 b);
	int[] colors = ColorHelper.fromCompressed(color);
	System.out.println(colors[0]);
	System.out.println(colors[1]);
	System.out.println(colors[2]);
	*/
	
	// HEX use method

	public static int WHITE, RED, GREEN, BLUE, BLACK, YELLOW, CYAN, MAGENTA;
	
	static
	{
		WHITE = toCompressed(255,255,255);
		RED = toCompressed(255,0,0);
		GREEN = toCompressed(0,255,0);
		BLUE = toCompressed(0,0,255);
		BLACK = toCompressed(0,0,0);
		YELLOW = toCompressed(255,255,0);
		CYAN = toCompressed(0,255,255);
		MAGENTA = toCompressed(255,0,255);
	}

	public static int[] fromHex(String hex)
	{
		if(hex.length() != 6) return null;
		char[] c = hex.toCharArray();
		char[] cr = new char[]{c[0], c[1]};
		char[] cg = new char[]{c[2], c[3]};
		char[] cb = new char[]{c[4], c[5]};
		int r = getColorFromHex(cr);
		int g = getColorFromHex(cg);
		int b = getColorFromHex(cb);
		return new int[]{r, g, b};
	}
	
	// TO HEX use method
	
	public static String toHex(int...colors)
	{
		if(colors == null) return null;
		char[] chars = new char[colors.length * 2];
		for(int i = 0; i < colors.length; i++) {
			String s = getHex(colors[i]);
			while(s.length() < 2) {
				s = "0" + s;
			}
			s.getChars(0, 2, chars, i * 2);
		}
		return new String(chars);
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
	
	// HEX-COMPRESSED use method
	
	public static int hexCompress(String hex)
	{
		int[] i = fromHex(hex);
		int r = i[0];
		int g = i[1];
		int b = i[2];
		int n = toCompressed(r, g, b);
		return n;
	}
	
	public static String hexCompress(int i)
	{
		int[] is = fromCompressed(i);
		int r = is[0];
		int g = is[1];
		int b = is[2];
		String s = toHex(r, g, b);
		return s;
	}
	
	// HEX worker methods
	
	public static int getColorFromHex(char[] c)
	{
		if(c.length != 2) return -1;
		char one = c[0];
		char two = c[1];
		int num1 = toNum(one);
		int num2 = toNum(two);
		num1 *= 16;
		return num1 + num2;
	}
	
	public static int toNum(char c)
	{
		String s = Character.toString(c);
                return Integer.parseInt(s, 16);
//		if(s.contains("0")) return 0;
//		if(s.contains("1")) return 1;
//		if(s.contains("2")) return 2;
//		if(s.contains("3")) return 3;
//		if(s.contains("4")) return 4;
//		if(s.contains("5")) return 5;
//		if(s.contains("6")) return 6;
//		if(s.contains("7")) return 7;
//		if(s.contains("8")) return 8;
//		if(s.contains("9")) return 9;
//		if(s.contains("a")) return 10;
//		if(s.contains("b")) return 11;
//		if(s.contains("c")) return 12;
//		if(s.contains("d")) return 13;
//		if(s.contains("e")) return 14;
//		if(s.contains("f")) return 15;
//		return -1;
	}
	
	// TO HEX worker methods
	
	public static String getHex(int color)
	{
		return Integer.toHexString(color);
	}
}
