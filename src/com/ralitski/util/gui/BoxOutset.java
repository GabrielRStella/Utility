package com.ralitski.util.gui;

public class BoxOutset extends Box {
	
	private Box parent;
	private int xOff;
	private int yOff;

	public BoxOutset(Box box, int xOff, int yOff) {
		super(0, 0, 0, 0);
		this.parent = box;
		this.xOff = xOff;
		this.yOff = yOff;
	}

    public int getMinX() {
        return parent.getMinX() - xOff;
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
	}
	
	public void setHeight(int height) {
	}

	public int getWidth() {
		return parent.getWidth() + xOff * 2;
    }

    public int getHeight() {
    	return parent.getHeight() + yOff * 2;
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
    	parent.setX(x  + xOff);
    }
    
    public void setY(int y) {
    	parent.setY(y + yOff);
    }
    
    public void setPosition(int x, int y) {
    	parent.setPosition(x + xOff, y + yOff);
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
