package com.ralitski.util.gui;

public class BoxRelative extends Box {

	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_CENTER_X = 4;
	public static final int ALIGN_TOP = 8;
	public static final int ALIGN_BOTTOM = 16;
	public static final int ALIGN_CENTER_Y = 32;
	public static final int ALIGN_WIDE = 0b00000111;
	public static final int ALIGN_HIGH = 0b00111000;
	
	private Box parent;
	private int align;
	private int xOff;
	private int yOff;

	public BoxRelative(Box box, int align, int minX, int minY, int maxX, int maxY, int xOff, int yOff) {
		super(minX, minY, maxX, maxY);
		this.parent = box;
		this.align = align;
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public int getOffsetX() {
		return xOff;
	}

	public void setOffsetX(int outsetX) {
		this.xOff = outsetX;
	}
	
	public int getOffsetY() {
		return yOff;
	}

	public void setOffsetY(int outsetY) {
		this.yOff = outsetY;
	}

    public int getMinX() {
    	int align = this.align & ALIGN_WIDE;
    	return align == ALIGN_LEFT ? (parent.getMinX() + xOff) : (align == ALIGN_CENTER ? (parent.getMinX() + (parent.getWidth() - super.getWidth()) / 2) : (parent.getMaxX() - xOff - super.getWidth()));
    }

    public int getMinY() {
        return parent.getMinY() - yOff;
    }

    public int getMaxX() {
        return parent.getMaxX() + xOff;
    }

    public int getMaxY() {
        return parent.getMaxY() + yOff;
    }

    public void setMinX(int minX) {
    	parent.setMinX(minX + xOff);
	}

	public void setMinY(int minY) {
		parent.setMinY(minY + yOff);
	}

	public void setMaxX(int maxX) {
		parent.setMaxX(maxX - xOff);
	}

	public void setMaxY(int maxY) {
		parent.setMaxY(maxY - yOff);
	}
	
	public void setWidth(int width) {
		parent.setWidth(width - xOff * 2);
	}
	
	public void setHeight(int height) {
		parent.setHeight(height - yOff * 2);
	}

	public int getWidth() {
		return parent.getWidth() + xOff * 2;
    }

    public int getHeight() {
    	return parent.getHeight() + yOff * 2;
    }
    
    public int getCenterX() {
    	//TODO
    }
    
    public void setCenterX(int x) {
    	//TODO
    }
    
    public int getCenterY() {
    	//TODO
    }
    
    public void setCenterY(int y) {
    	//TODO
    }
    
    public void setCenter(int x, int y) {
    	//TODO
    }
    
    public void setX(int x) {
    	translateX(x - getMinX());
    }
    
    public void setY(int y) {
    	translateY(y - getMinY());
    }
    
    public void setPosition(int x, int y) {
    	translate(x - getMinX(), y - getMinY());
    }
    
    public void translate(int x, int y) {
    	translateX(x);
    	translateY(y);
    }
    
    public void translateX(int x) {
    	if((align & ALIGN_LEFT) == ALIGN_LEFT) {
    		xOff += x;
    	} else {
    		xOff -= x;
    	}
    }
    
    public void translateY(int y) {
    	if((align & ALIGN_BOTTOM) == ALIGN_BOTTOM) {
    		yOff += y;
    	} else {
    		yOff -= y;
    	}
    }

}
