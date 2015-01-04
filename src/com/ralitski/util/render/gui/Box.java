package com.ralitski.util.render.gui;

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
        Box newBB = new Box(minX - wide, minY - high, maxX + wide, maxY + high);
        return newBB;
    }

    public boolean intersecting(Box other) {
        boolean TOP_BOTTOM = other.minY < maxY;
        boolean BOTTOM_BOTTOM = other.minY > minY;
        boolean TOP_TOP = other.maxY < maxY;
        boolean BOTTOM_TOP = other.maxY > minY;
        boolean intersecting = false;
        if ((TOP_BOTTOM && BOTTOM_BOTTOM) || (TOP_TOP && BOTTOM_TOP)) {
            //sides
            boolean RIGHT_LEFT = other.minX < maxX;
            boolean LEFT_LEFT = other.minX > minX;
            boolean RIGHT_RIGHT = other.maxX < maxX;
            boolean LEFT_RIGHT = other.maxX > minX;
            intersecting = (RIGHT_LEFT && LEFT_LEFT) || (RIGHT_RIGHT && LEFT_RIGHT);
        }
        //todo: will use a separate method to check if boxes are enclosed
        return intersecting || enclosed(other) || other.enclosed(this);
    }

    //broken method
    private boolean enclosed(Box other) {
    	return other.maxX > maxX && other.minX < minX && other.maxY > maxY && other.minY < minY;
    }

    public boolean contains(int x, int y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
    
    public Box clone() {
    	return new Box(minX, minY, maxX, maxY);
    }
}
