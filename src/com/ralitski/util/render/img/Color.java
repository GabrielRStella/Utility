package com.ralitski.util.render.img;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ralitski.util.io.Streamable;

public class Color implements Streamable, Cloneable {

    public static final Color WHITE = new Color(ColorHelper.WHITE);
    public static final Color GRAY = new Color(ColorHelper.GRAY);
    public static final Color BLACK = new Color(ColorHelper.BLACK);
    public static final Color RED = new Color(ColorHelper.RED);
    public static final Color GREEN = new Color(ColorHelper.GREEN);
    public static final Color BLUE = new Color(ColorHelper.BLUE);
    public static final Color YELLOW = new Color(ColorHelper.YELLOW);
    public static final Color CYAN = new Color(ColorHelper.CYAN);
    public static final Color MAGENTA = new Color(ColorHelper.MAGENTA);
    
	private int r, g, b, a;

    public Color(int i) {
        int[] c = ColorHelper.fromCompressed(i);
        this.a = c[0];
        this.r = c[1];
        this.g = c[2];
        this.b = c[3];
    }

    public Color(int i, int j, int k, int l) {
        this.r = i;
        this.g = j;
        this.b = k;
        this.a = l;
    }

    public Color(float i, float j, float k, float l) {
        this((int) (i * 255F), (int) (j * 255F), (int) (k * 255F), (int) (l * 255F));
    }

    public Color(int i, int j, int k) {
        this.r = i;
        this.g = j;
        this.b = k;
        this.a = 255;
    }

    public Color(float i, float j, float k) {
        this((int) (i * 255F), (int) (j * 255F), (int) (k * 255F));
    }

    //DATA
    public int getRed() {
        return this.r;
    }
    
    public float getRedFloat() {
    	return (float)r / 255F;
    }

    public void setRed(int b) {
        this.r = b;
    }

    public int getGreen() {
        return this.g;
    }
    
    public float getGreenFloat() {
    	return (float)g / 255F;
    }

    public void setGreen(int b) {
        this.g = b;
    }

    public int getBlue() {
        return this.b;
    }
    
    public float getBlueFloat() {
    	return (float)b / 255F;
    }

    public void setBlue(int b) {
        this.b = b;
    }

    public int getAlpha() {
        return this.a;
    }
    
    public float getAlphaFloat() {
    	return (float)a / 255F;
    }

    public void setAlpha(int b) {
        this.a = b;
    }

    public int getCompresse() {
        return ColorHelper.toCompressed(a, r, g, b);
    }

    public java.awt.Color jColor() {
        return new java.awt.Color(r, g, b, a);
    }

    public void glColor() {
        GLImageHelper.glColor(this);
    }

    //MANIPULATION
    public void invert() {
        this.r = (255 - this.r);
        this.g = (255 - this.g);
        this.b = (255 - this.b);
    }

    public void equalize() {
        int c = this.r + this.g + this.b;
        c /= 3;
        this.r = this.g = this.b = c;
    }
    
    public Color darker(int dark) {
    	return new Color(r - dark, g - dark, b - dark, a);
    }

    //COMPARISON
    public static boolean compare(Color c, Color c1) {
        boolean r = c.r == c1.r;
        boolean g = c.g == c1.g;
        boolean b = c.b == c1.b;
        boolean a = c.a == c1.a;
        return r && g && b && a;
    }

    public static int difference(Color c, Color c1) {
        int r = c.r - c1.r;
        int g = c.g - c1.g;
        int b = c.b - c1.b;
        int a = c.a - c1.a;
        r = Math.abs(r);
        g = Math.abs(g);
        b = Math.abs(b);
        a = Math.abs(a);
        return r + g + b + a;
    }
    
    public String toString() {
    	return "#" + ColorHelper.toHex(r, g, b, a);
    }
    
    public Color clone() {
    	return new Color(r, g, b);
    }

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeInt(r);
		out.writeInt(g);
		out.writeInt(b);
		out.writeInt(a);
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		r = in.readInt();
		g = in.readInt();
		b = in.readInt();
		a = in.readInt();
	}
	
//	public boolean equals(Object o) {
//		return o == this ? true : (o instanceof Color ? equalsColor((Color)o) : false);
//	}
//
//	private boolean equalsColor(Color o) {
//		return o.r == r && o.g == g && o.b == b && o.a == a;
//	}
}
