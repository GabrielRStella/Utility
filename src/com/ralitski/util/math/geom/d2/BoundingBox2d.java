package com.ralitski.util.math.geom.d2;

//TODO: fix collision detection methods to have rotation
public class BoundingBox2d {

    private Point2d origin() {
        return Point2d.origin();
    }
    
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private float angle;
    private Point2d center = origin();

    public BoundingBox2d() {
    	this(0, 0, 0, 0);
	}  

    public BoundingBox2d(float width, float height) {
        this(-width / 2F, -height / 2F, width / 2F, height / 2F);
    }

    public BoundingBox2d(Point2d p) {
    	this(Math.min(p.getX(), 0), Math.min(p.getY(), 0), Math.max(p.getX(), 0), Math.max(p.getY(), 0));
    }

    public BoundingBox2d(Point2d p1, Point2d p2) {
    	this(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()), Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
    }

    public BoundingBox2d(float minX, float minY, float maxX, float maxY) {
    	this(minX, minY, maxX, maxY, 0);
    }

    public BoundingBox2d(float minX, float minY, float maxX, float maxY, float angle) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.angle = angle;
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
    
    public float getAngleDegrees() {
    	return angle;
    }
    
    public void setAngleDegrees(float angle) {
    	this.angle = angle;
    }
    
    public float getAngleRadians() {
    	return (float)Math.toRadians(angle);
    }
    
    public void setAngleRadians(float angle) {
    	this.angle = (float)Math.toDegrees(angle);
    }
    
    public void rotateDegrees(float angle) {
    	this.angle += angle;
    }
    
    public void rotateRadians(float angle) {
    	this.angle += (float)Math.toDegrees(angle);
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
        newBB.setAngleDegrees(angle);
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
    	Point2d p = new Point2d(x, y);
    	p.rotateAround(center, -angle);
    	x = p.getX();
    	y = p.getY();
        return (x >= minX + center.getX() && x <= maxX + center.getX()) && (y >= minY + center.getY() && y <= maxY + center.getY());
    }

    public boolean contains(Point2d p) {
    	p = p.clone();
    	p.rotateAround(center, -angle);
    	float x = p.getX();
    	float y = p.getY();
        return (x >= minX + center.getX() && x <= maxX + center.getX()) && (y >= minY + center.getY() && y <= maxY + center.getY());
    }

	public BoundingBox2d scale(float scale) {
		float w = getWidth();
		float h = getHeight();
		float width = w * scale;
		float height = h * scale;
		w = (width - w) / 2F;
		h = (height - h) / 2F;
		BoundingBox2d b = new BoundingBox2d(minX - w, minY - h, maxX + w, maxY + h, angle);
		b.setCenter(center);
		return b;
	}
    
    public BoundingBox2d clone() {
    	return new BoundingBox2d(getMinX(), getMinY(), getMaxX(), getMaxY());
    }
    
    public String toString() {
    	return "[(" + getMinX() + ", " + getMinY() + "), (" + getMaxX() + ", " + getMaxY() + "), " + angle + "°]";
    }
    
    public Point2d[] getCorners() {
    	Point2d tl, tr, bl, br;
    	tl = new Point2d(getMinX(), getMaxY());
    	tr = new Point2d(getMaxX(), getMaxY());
    	bl = new Point2d(getMaxX(), getMinY());
    	br = new Point2d(getMinX(), getMinY());
    	tl.rotateAround(center, angle);
    	tr.rotateAround(center, angle);
    	bl.rotateAround(center, angle);
    	br.rotateAround(center, angle);
    	return new Point2d[]{tl, tr, br, bl};
    }
}
