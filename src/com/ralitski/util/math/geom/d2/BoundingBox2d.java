package com.ralitski.util.math.geom.d2;

public class BoundingBox2d {

    private Point2d origin() {
        return Point2d.origin();
    }
    
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private Point2d center = origin();

    public BoundingBox2d() {
    	this(0, 0, 0, 0);
	}

    public BoundingBox2d(float width, float height) {
        this(-width / 2F, -height / 2F, width / 2F, height / 2F);
    }

    public BoundingBox2d(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

	public void setCenter(Point2d center) {
        this.center = center != null ? center : origin();
    }

    public void setCenterWithoutChange(Point2d center) {
        if (center != null) {
            this.minX -= center.getX();
            this.minY -= center.getY();
            this.maxX -= center.getX();
            this.maxY -= center.getY();
            this.center = center;
        } else {
            BoundingBox2d b = getAbsolute();
            this.minX = b.minX;
            this.minY = b.minY;
            this.maxX = b.maxX;
            this.maxY = b.maxY;
            this.center = origin();
        }
    }

    public Point2d getCenter() {
        return center;
    }

    public float getMinX() {
        return center.getX() + minX;
    }

    public float getMinY() {
        return center.getY() + minY;
    }

    public float getMaxX() {
        return center.getX() + maxX;
    }

    public float getMaxY() {
        return center.getY() + maxY;
    }

    public float getWidth() {
        return maxX - minX;
    }

    public float getHeight() {
        return maxY - minY;
    }

    public BoundingBox2d getAbsolute() {
        return new BoundingBox2d(minX + center.getX(), minY + center.getY(), maxX + center.getX(), maxY + center.getY());
    }

    /**
     *
     * @param wide
     * @param high
     * @return a new bounding box
     */
    public BoundingBox2d expand(float wide, float high) {
        BoundingBox2d newBB = new BoundingBox2d(minX - wide, minY - high, maxX + wide, maxY + high);
        newBB.setCenter(center);
        return newBB;
    }
    
    public boolean colliding(BoundingBox2d other) {
    	return intersecting(other) || enclosed(other) || other.enclosed(this);
    }

    public boolean intersecting(BoundingBox2d other) {
        boolean TOP_BOTTOM = (other.center.getY() + other.minY) < center.getY() + maxY;
        boolean BOTTOM_BOTTOM = (other.center.getY() + other.minY) > center.getY() + minY;
        boolean TOP_TOP = (other.center.getY() + other.maxY) < center.getY() + maxY;
        boolean BOTTOM_TOP = (other.center.getY() + other.maxY) > center.getY() + minY;
        if ((TOP_BOTTOM && BOTTOM_BOTTOM) || (TOP_TOP && BOTTOM_TOP)) {
            //sides
            boolean RIGHT_LEFT = (other.center.getX() + other.minX) < center.getX() + maxX;
            boolean LEFT_LEFT = (other.center.getX() + other.minX) > center.getX() + minX;
            boolean RIGHT_RIGHT = (other.center.getX() + other.maxX) < center.getX() + maxX;
            boolean LEFT_RIGHT = (other.center.getX() + other.maxX) > center.getX() + minX;
            return (RIGHT_LEFT && LEFT_LEFT) || (RIGHT_RIGHT && LEFT_RIGHT);
        }
        return false;
    }
    
    public boolean enclosed(BoundingBox2d other) {
    	return minX > other.minX && maxX < other.maxX && minY > other.minY && maxY < other.maxY;
    }

    public boolean contains(float x, float y) {
        return (x >= minX + center.getX() && x <= maxX + center.getX()) && (y >= minY + center.getY() && y <= maxY + center.getY());
    }

	public BoundingBox2d scale(float scale) {
		float w = getWidth();
		float h = getHeight();
		float width = w * scale;
		float height = h * scale;
		w = (width - w) / 2F;
		h = (height - h) / 2F;
		return new BoundingBox2d(minX - w, minY - h, maxX + w, maxY + h);
	}
}
