package com.ralitski.util.gui;

import com.ralitski.util.gui.BoxEvent.BoxEventType;

public class Box implements Cloneable {
    
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    
    /*
     * Only one BoxEventListener is stored for speed, and since Boxes will often not have a listener.
     */
    private BoxEventListener eventListener;

    public Box() {
    	this(0, 0);
	}

    public Box(int width, int height) {
        this(0, 0, width, height);
    }

    public Box(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

	public BoxEventListener getEventListener() {
		return eventListener;
	}

	public void setEventListener(BoxEventListener eventListener) {
		this.eventListener = eventListener;
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
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_MINX, this.minX, minX));
		this.minX = minX;
	}

	public void setMinY(int minY) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_MINY, this.minY, minY));
		this.minY = minY;
	}

	public void setMaxX(int maxX) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_MAXX, this.maxX, maxX));
		this.maxX = maxX;
	}

	public void setMaxY(int maxY) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_MAXY, this.maxY, maxY));
		this.maxY = maxY;
	}
	
	public void setWidth(int width) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_WIDTH, this.getWidth(), width));
		maxX = minX + width;
	}
	
	public void setHeight(int height) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_HEIGHT, this.getHeight(), height));
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
    	int i = this.getCenterX();
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_CENTERX, i, x));
    	x -= i;
    	minX += x;
    	maxX += x;
    }
    
    public int getCenterY() {
    	return (minY + maxY) / 2;
    }
    
    public void setCenterY(int y) {
    	int i = this.getCenterY();
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_CENTERY, i, y));
    	y -= i;
    	minY += y;
    	maxY += y;
    }
    
    public void setCenter(int x, int y) {
    	int xCenter = getCenterX();
    	int yCenter = getCenterY();
    	translate(x - xCenter, y - yCenter);
    }
    
    public void setX(int x) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_X, getMinX(), x));
    	translateX(x - minX);
    }
    
    public void setY(int y) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.SET_Y, getMinY(), y));
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
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.TRANSLATE_X, 0, x));
    	minX += x;
    	maxX += x;
    }
    
    public void translateY(int y) {
    	if(eventListener != null)
    		eventListener.onBoxEvent(new BoxEvent(this, BoxEventType.TRANSLATE_Y, 0, y));
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
    
    public Dimension getDimensions() {
    	return new Dimension(getWidth(), getHeight());
    }
}
