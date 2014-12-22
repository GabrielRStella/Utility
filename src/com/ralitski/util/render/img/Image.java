package com.ralitski.util.render.img;

import java.util.Arrays;

public class Image {

    private int width, height;
    private int ID = -1;
    private byte[] data;

    public Image(int width, int height, byte[] data) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int width() {
        return this.width;
    }

    public void width(int i) {
        this.width = i;
    }

    public int height() {
        return this.height;
    }

    public void height(int i) {
        this.height = i;
    }

    public int id() {
        return this.ID;
    }

    public void id(int i) {
        this.ID = i;
    }

    public byte[] data() {
        return this.data;
    }

    public Color color(int x, int y) {
        int z = y * this.width;
        int index = x + z;
        index *= 4;
        return new Color(this.data[index], this.data[index + 1], this.data[index + 2], this.data[index + 3]);
    }

    public void gray() {
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

    public void opaque() {
        for (int i = 0; i < this.data.length / 4; i++) {
            int n = i * 4;
            this.data[n + 3] = Byte.MAX_VALUE;
        }
    }
    
//    public enum Split {
//    	VERTICAL,
//    	HORIZONTAL
//    }
//    
//    public Image[] split(Split s) {
//    	return s == Split.VERTICAL ? splitVertical() : splitHorizontal();
//    }
    
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
