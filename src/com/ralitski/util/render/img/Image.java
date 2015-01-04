package com.ralitski.util.render.img;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

public class Image {

	/**
	 * converts image data from java standard (int argb) to opengl standard (byte rgba)
	 * @param dataARGB image data in integer-argb format
	 * @return image data in byte-rgba format
	 */
    public static byte[] getRGBA(int[] dataARGB) {
        byte[] bata = new byte[dataARGB.length * 4];
        for (int c = 0; c < dataARGB.length; ++c) {
            int[] colors = ColorHelper.fromCompressed(dataARGB[c]);
            int a = colors[0];
            int r = colors[1];
            int g = colors[2];
            int b = colors[3];
            /*
             * if(a != 0) { if(r != g && r != b && g != b) {
             * System.out.println(c); System.out.println("r " + r);
             * System.out.println("g " + g); System.out.println("b " + b);
             * System.out.println("a " + a); } }
             */
            bata[c * 4 + 0] = (byte) r;
            bata[c * 4 + 1] = (byte) g;
            bata[c * 4 + 2] = (byte) b;
            bata[c * 4 + 3] = (byte) a;
        }
        return bata;
    }
	
    private int width;
    private int height;
    private int id = -1;
    private byte[] data;
    
    private int minFilter = GL11.GL_LINEAR;
    private int magFilter = GL11.GL_LINEAR;
    
    /**
     * constructs an empty image.
     * @param width
     * @param height
     */
    public Image(int width, int height) {
    	this(width, height, new byte[width * height * 4]);
    }
    
    public Image(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        int[] data = new int[width * height];
        image.getRGB(0, 0, width, height, data, 0, width);
        this.data = getRGBA(data);
    }
    
    public Image(int width, int height, int[] dataARGB) {
    	this(width, height, getRGBA(dataARGB));
    }

    public Image(int width, int height, byte[] dataRGBA) {
        if(width * height * 4 != dataRGBA.length) throw new IllegalArgumentException("Invalid Image data specified");
        this.width = width;
        this.height = height;
        this.data = dataRGBA;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
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

	public byte[] getData() {
        return this.data;
    }
	
	public int getIndex(int x, int y) {
        int z = y * this.width;
        int index = x + z;
        index *= 4;
        return index;
	}
	
	//opengl
    
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
    
    //image editing

    public Color getColor(int x, int y) {
    	int index = getIndex(x, y);
        return new Color(this.data[index], this.data[index + 1], this.data[index + 2], this.data[index + 3]);
    }
    
    public void setColor(int x, int y, Color color) {
    	int index = getIndex(x, y);
    	data[index] = (byte)color.getRed();
    	data[index + 1] = (byte)color.getGreen();
    	data[index + 2] = (byte)color.getBlue();
    	data[index + 3] = (byte)color.getAlpha();
    }
    
    public Pixel getPixel(int x, int y) {
    	return new Pixel(this, x, y);
    }

    public void makeGrayscale() {
        for (int i = 0; i < this.data.length / 4; i++) {
            int n = i * 4;
            byte r = this.data[n];
            byte g = this.data[n + 1];
            byte b = this.data[n + 2];
            int temp = r + g + b;
            temp /= 3;
            this.data[n] = (byte) temp;
            this.data[n + 1] = (byte) temp;
            this.data[n + 2] = (byte) temp;
        }
    }

    public void makeOpaque() {
        for (int i = 3; i < this.data.length; i += 4) {
            this.data[i] = Byte.MAX_VALUE;
        }
    }
    
    public Image[] splitVertical() {
    	if(height > width) {
        	int newHeight = width;
        	int offset = 0;
        	
    		int len = (height - (height % newHeight)) / newHeight;
    		Image[] ret = new Image[len];
    		int index = 0;
        	while(offset + newHeight <= height) {
        		int startIndex = offset * width * 4;
        		byte[] data = new byte[width * newHeight * 4];
        		for(int i = 0; i < data.length; i++) {
        			data[i] = this.data[startIndex + i];
        		}
        		ret[index++] = new Image(width, newHeight, data);
        		offset += newHeight;
        	}
        	return ret;
    	}
    	return null;
    }
    
    public Image[] splitHorizontal() {
    	if(width > height) {
        	int newWidth = height;
        	int offset = 0;
        	
    		int len = (width - (width % newWidth)) / newWidth;
    		Image[] ret = new Image[len];
    		int index = 0;
        	while(offset + newWidth <= width) {
        		int startIndex = offset;
        		int maxWidth = newWidth * 4;
        		byte[] data = new byte[height * maxWidth];
        		for(int i = 0; i < height; i++) {
        			for(int j = 0; j < maxWidth; j++) {
        				data[j + (i * maxWidth)] = this.data[startIndex + j + (i * this.width)];
        			}
        		}
        		ret[index++] = new Image(newWidth, height, data);
        		offset += newWidth;
        	}
        	return ret;
    	}
    	return null;
    }
    
    public boolean equals(Object o) {
    	if(o == this) {
    		return true;
    	} else if(o instanceof Image) {
    		return equalsImg((Image)o);
    	}
    	return false;
    }
    
    private boolean equalsImg(Image i) {
    	if(i.width == width && i.height == height) {
    		return Arrays.equals(data, i.data);
    	}
    	return false;
    }
}
