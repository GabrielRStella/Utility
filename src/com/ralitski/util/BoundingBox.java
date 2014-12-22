package com.ralitski.util;

import com.ralitski.util.doc.TODO;
import com.ralitski.util.math.geom.d2.Point2d;

public class BoundingBox {

    private Point2d origin() {
        return Point2d.origin();
    }
    
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private Point2d center = origin();

    public BoundingBox(float width, float height) {
        this(-width / 2F, -height / 2F, width / 2F, height / 2F);
    }

    public BoundingBox(float minX, float minY, float maxX, float maxY) {
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
            BoundingBox b = getAbsolute();
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

    public BoundingBox getAbsolute() {
        return new BoundingBox(minX + center.getX(), minY + center.getY(), maxX + center.getX(), maxY + center.getY());
    }

    /**
     *
     * @param wide
     * @param high
     * @return a new bounding box
     */
    public BoundingBox expand(float wide, float high) {
        BoundingBox newBB = new BoundingBox(minX - wide, minY - high, maxX + wide, maxY + high);
        newBB.setCenter(center);
        return newBB;
    }

    public boolean intersecting(BoundingBox other) {
        boolean TOP_BOTTOM = (other.center.getY() + other.minY) < center.getY() + maxY;
        boolean BOTTOM_BOTTOM = (other.center.getY() + other.minY) > center.getY() + minY;
        boolean TOP_TOP = (other.center.getY() + other.maxY) < center.getY() + maxY;
        boolean BOTTOM_TOP = (other.center.getY() + other.maxY) > center.getY() + minY;
        boolean intersecting = false;
        if ((TOP_BOTTOM && BOTTOM_BOTTOM) || (TOP_TOP && BOTTOM_TOP)) {
            //sides
            boolean RIGHT_LEFT = (other.center.getX() + other.minX) < center.getX() + maxX;
            boolean LEFT_LEFT = (other.center.getX() + other.minX) > center.getX() + minX;
            boolean RIGHT_RIGHT = (other.center.getX() + other.maxX) < center.getX() + maxX;
            boolean LEFT_RIGHT = (other.center.getX() + other.maxX) > center.getX() + minX;
            intersecting = (RIGHT_LEFT && LEFT_LEFT) || (RIGHT_RIGHT && LEFT_RIGHT);
        }
        //todo: will use a separate method to check if boxes are enclosed
        return intersecting;
    }

    //broken method
    @TODO("diagonal contact while not touching")
    private boolean enclosed(BoundingBox other) {
        float xDist = Math.abs((center.getX() + minX + maxX) - (other.center.getX() + other.minX + other.maxX));
        float yDist = Math.abs((center.getY() + minY + maxY) - (other.center.getY() + other.minY + other.maxY));
        boolean flag = (xDist * 2F < getWidth() + other.getWidth()) && (yDist * 2F < getHeight() + other.getHeight());
        return flag;
    }

    public boolean contains(float x, float y) {
        return (x >= minX + center.getX() && x <= maxX + center.getX()) && (y >= minY + center.getY() && y <= maxY + center.getY());
    }
}
