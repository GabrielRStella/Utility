package com.ralitski.util.gui;

public class Box implements Cloneable {
    
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public Box(int width, int height) {
        this(0, 0, width, height);
    }

    public Box(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMinX(int minX) {
		this.minX = minX;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}
	
	public void setWidth(int width) {
		maxX = minX + width;
	}
	
	public void setHeight(int height) {
		maxY = minY + height;
	}

	public int getWidth() {
        return maxX - minX;
    }

    public int getHeight() {
        return maxY - minY;
    }
    
    public int getCenterX() {
    	return (minX + maxX) / 2;
    }
    
    public void setCenterX(int x) {
    	x -= getCenterX();
    	minX += x;
    	maxX += x;
    }
    
    public int getCenterY() {
    	return (minY + maxY) / 2;
    }
    
    public void setCenterY(int y) {
    	y -= getCenterY();
    	minY += y;
    	maxY += y;
    }
    
    public void setCenter(int x, int y) {
    	int xCenter = getCenterX();
    	int yCenter = getCenterY();
    	translate(x - xCenter, y - yCenter);
    }
    
    public void setX(int x) {
    	translateX(x - minX);
    }
    
    public void setY(int y) {
    	translateY(y - minY);
    }
    
    public void setPosition(int x, int y) {
    	translate(x - minX, y - minY);
    }
    
    public void translate(int x, int y) {
    	minX += x;
    	maxX += x;
    	minY += y;
    	maxY += y;
    }
    
    public void translateX(int x) {
    	minX += x;
    	maxX += x;
    }
    
    public void translateY(int y) {
    	minY += y;
    	maxY += y;
    }

    /**
     *
     * @param wide
     * @param high
     * @return a new bounding box
     */
    public Box expand(int wide, int high) {
        Box newBB = new Box(getMinX() - wide, getMinY() - high, getMaxX() + wide, getMaxY() + high);
        return newBB;
    }
    
    public boolean colliding(Box other) {
    	return intersecting(other) || enclosed(other) || other.enclosed(this);
    }
    
    //note: to detect collisions between B1 and B2, (B1.intersecting(B2) || B2.intersecting(B1)) can be used
    public boolean intersecting(Box other) {
        boolean TOP_BOTTOM = other.getMinY() < getMaxY();
        boolean BOTTOM_BOTTOM = other.getMinY() > getMinY();
        boolean TOP_TOP = other.getMaxY() < getMaxY();
        boolean BOTTOM_TOP = other.getMaxY() > getMinY();
        if ((TOP_BOTTOM && BOTTOM_BOTTOM) || (TOP_TOP && BOTTOM_TOP)) {
            //sides
            boolean RIGHT_LEFT = other.getMinX() < getMaxX();
            boolean LEFT_LEFT = other.getMinX() > getMinX();
            boolean RIGHT_RIGHT = other.getMaxX() < getMaxX();
            boolean LEFT_RIGHT = other.getMaxX() > getMinX();
            return (RIGHT_LEFT && LEFT_LEFT) || (RIGHT_RIGHT && LEFT_RIGHT);
        }
        return false;
    }

    private boolean enclosed(Box other) {
    	return other.getMaxX() > getMaxX() && other.getMinX() < getMinX() && other.getMaxY() > getMaxY() && other.getMinY() < getMinY();
    }

    public boolean contains(int x, int y) {
        return x >= getMinX() && x <= getMaxX() && y >= getMinY() && y <= getMaxY();
    }
    
    public Box clone() {
    	return new Box(getMinX(), getMinY(), getMaxX(), getMaxY());
    }
    
    public String toString() {
    	return "[(" + getMinX() + ", " + getMinY() + "), (" + getMaxX() + ", " + getMaxY() + ")]";
    }
}
