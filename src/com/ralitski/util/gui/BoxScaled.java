package com.ralitski.util.gui;

public class BoxScaled extends Box {
	
	private Box parent;
	private float xScale;
	private float yScale;
	
	public BoxScaled(Box box, float scale) {
		this(box, scale, scale);
	}

	public BoxScaled(Box box, float xScale, float yScale) {
		super(0, 0, 0, 0);
		this.parent = box;
		this.xScale = xScale;
		this.yScale = yScale;
	}

	public float getOutsetX() {
		return xScale;
	}

	public void setOutsetX(float outsetX) {
		this.xScale = outsetX;
	}
	
	public float getOutsetY() {
		return yScale;
	}

	public void setOutsetY(float outsetY) {
		this.yScale = outsetY;
	}

    public int getMinX() {
    	int width = getWidth();
    	int extra = parent.getWidth() - width;
        return parent.getMinX() + (extra / 2);
    }

    public int getMinY() {
    	int height = getHeight();
    	int extra = parent.getHeight() - height;
        return parent.getMinY() + (extra / 2);
    }

    public int getMaxX() {
    	return getMinX() + getWidth();
    }

    public int getMaxY() {
        return getMinY() + getHeight();
    }

    public void setMinX(int minX) {
    	parent.translateX(minX - getMinX());
	}

	public void setMinY(int minY) {
    	parent.translateY(minY - getMinY());
	}

	public void setMaxX(int maxX) {
		setMinX(maxX - getWidth());
	}

	public void setMaxY(int maxY) {
		setMinY(maxY - getHeight());
	}
	
	public void setWidth(int width) {
		parent.setWidth((int)(width / xScale));
	}
	
	public void setHeight(int height) {
		parent.setHeight((int)(height / yScale));
	}

	public int getWidth() {
		return (int)(parent.getWidth() * xScale);
    }

    public int getHeight() {
    	return (int)(parent.getHeight() * yScale);
    }
    
    public int getCenterX() {
    	return parent.getCenterX();
    }
    
    public void setCenterX(int x) {
    	parent.setCenterX(x);
    }
    
    public int getCenterY() {
    	return parent.getCenterY();
    }
    
    public void setCenterY(int y) {
    	parent.setCenterY(y);
    }
    
    public void setCenter(int x, int y) {
    	parent.setCenter(x, y);
    }
    
    public void setX(int x) {
    	translateX(x - getMinX());
    }
    
    public void setY(int y) {
    	translateY(y - getMinY());
    }
    
    public void setPosition(int x, int y) {
    	translateX(x - getMinX());
    	translateY(y - getMinY());
    }
    
    public void translate(int x, int y) {
    	parent.translate(x, y);
    }
    
    public void translateX(int x) {
    	parent.translateX(x);
    }
    
    public void translateY(int y) {
    	parent.translateY(y);
    }

}
