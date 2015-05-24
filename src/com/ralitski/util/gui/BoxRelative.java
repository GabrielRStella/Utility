package com.ralitski.util.gui;

/**
 * relative to parent's (minX, minY) corner
 * @author ralitski
 *
 */
public class BoxRelative extends Box {
	
	public static BoxRelative makeRelative(Box original, Box parent) {
		if(original instanceof BoxRelative) {
			BoxRelative box = (BoxRelative)original;
			return new BoxRelative(parent, box.getMinX(), box.getMinY(), box.getMaxX(), box.getMaxY(), box.getOffsetX() + parent.getMinX() - original.getMinX(), box.getOffsetY() + parent.getMinY() - original.getMinY());
		} else return parent != null ? new BoxRelative(parent, original.getMinX() - parent.getMinX(), original.getMinY() - parent.getMinY(), original.getMaxX() - parent.getMinX(), original.getMaxY() - parent.getMinY(), parent.getMinX(), parent.getMinY())
		: new BoxRelative(null, original.getMinX(), original.getMinY(), original.getMaxX(), original.getMaxY(), 0, 0);
	}
	
	private Box parent;
	private int xOff;
	private int yOff;

	public BoxRelative(Box box, int minX, int minY, int maxX, int maxY, int xOff, int yOff) {
		super(minX, minY, maxX, maxY);
		this.parent = box;
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public Box getParent() {
		return parent;
	}

	public void setParent(Box parent) {
		this.parent = parent;
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
    	int minX = xOff + super.getMinX();
    	return parent != null ? parent.getMinX() + minX : minX;
    }

    public int getMinY() {
    	int minY = yOff + super.getMinY();
    	return parent != null ? parent.getMinY() + minY : minY;
    }

    public int getMaxX() {
    	int maxX = xOff + super.getMaxX();
    	return parent != null ? parent.getMinX() + maxX : maxX;
    }

    public int getMaxY() {
    	int maxY = yOff + super.getMaxY();
    	return parent != null ? parent.getMinY() + maxY : maxY;
    }

    public void setMinX(int minX) {
    	xOff += (minX - getMinX());
	}

	public void setMinY(int minY) {
		yOff += (minY - getMinY());
	}

	public void setMaxX(int maxX) {
		xOff += (maxX - getMaxX());
	}

	public void setMaxY(int maxY) {
		yOff += (maxY - getMaxY());
	}
    
    public int getCenterX() {
    	return (getMinX() + getMaxX()) / 2;
    }
    
    public void setCenterX(int x) {
    	translateX(x - getCenterX());
    }
    
    public int getCenterY() {
    	return (getMinY() + getMaxY()) / 2;
    }
    
    public void setCenterY(int y) {
    	translateY(y - getCenterY());
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
    	xOff += x;
    }
    
    public void translateY(int y) {
    	yOff += y;
    }

}
