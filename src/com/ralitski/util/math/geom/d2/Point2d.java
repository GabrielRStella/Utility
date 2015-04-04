package com.ralitski.util.math.geom.d2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ralitski.util.io.Streamable;

/**
 *
 * @author ralitski
 */
public class Point2d implements Shape2d, Cloneable, Streamable {

	public static Point2d midpoint(Point2d p1, Point2d p2) {
		return new Point2d((p1.x + p2.x) / 2F, (p1.y + p2.y) / 2F);
	}
	
	public static Point2d fromComplex(float r, float theta) {
		return fromComplexRadians(r, (float)Math.toRadians(theta));
	}
	
	public static Point2d fromComplexRadians(float r, float theta) {
		return new Point2d((float)Math.cos(theta) * r, (float)Math.sin(theta) * r);
	}
	
	public static Point2d origin() {
		return ORIGIN.clone();
	}
	
	public static Point2d ORIGIN = new Point2d() {
	    
	    //add, get, set
	    
	    public void addX(float x) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public void setX(float x) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public float getX() {
	        return 0;
	    }
	    
	    public void addY(float y) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public void setY(float y) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public float getY() {
	        return 0;
	    }
	    
	    //translation
	    
	    public void translate(Point2d p) {
	    }
	    
	    public void translate(float x, float y) {
	    }
	    
	    //length
	    
	    public float length() {
	    	return 0;
	    }
	    
	    public float lengthSquared() {
	    	return  0;
	    }
	    
	    public float length(Point2d center) {
	    	return center.length();
	    }
	    
	    public float lengthSquared(Point2d center) {
	    	return center.lengthSquared();
	    }
	    
	    public float length(float ox, float oy) {
	    	return (float)Math.sqrt(lengthSquared(ox, oy));
	    }
	    
	    public float lengthSquared(float ox, float oy) {
	    	return ox * ox + oy * oy;
	    }
	    
	    //rotation
	    
	    public float rotation() {
	    	return 0;
	    }
	    
	    public float rotationRadians() {
	    	return 0;
	    }
	    
	    public float rotation(Point2d center) {
	    	return 180F + center.rotation();
	    }
	    
	    public float rotationRadians(Point2d center) {
	    	return (float)Math.PI + center.rotationRadians();
	    }
	    
	    //rotate
	    
	    public void rotate(float degrees) {
	    }
	    
	    public void rotateRadians(float radians) {
	    }
	    
	    public void rotateAround(Point2d center, float degrees) {
	    	//prevent unexpected non-rotation
	    	throw new UnsupportedOperationException("Origin can't be rotated");
	    }
	    
	    public void rotateAroundRadians(Point2d center, float radians) {
	    	//prevent unexpected non-rotation
	    	throw new UnsupportedOperationException("Origin can't be rotated");
	    }
	    
	    //misc
	    
	    public Space getQuadrant() {
	    	return Space.ORIGIN;
	    }
	    
	    public Point2d clone() {
	    	//get a mutable object
	    	return new Point2d(0, 0);
	    }
	    
	    public Point2d negative() {
	    	return new Point2d(0, 0);
	    }
	};
    
    private float x;
    private float y;
    
    protected Point2d() {}
    
    public Point2d(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    //add, get, set
    
    public void addX(float x) {
    	this.setX(getX() + x);
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }
    
    public void addY(float y) {
    	this.setY(getY() + y);
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }
    
    //translation, scaling
    
    public void translate(Point2d p) {
    	translate(p.getX(), p.getY());
    }
    
    public void translate(float x, float y) {
    	this.addX(x);
    	this.addY(y);
    }
    
    public void subtract(Point2d p) {
    	subtract(p.getX(), p.getY());
    }
    
    public void subtract(float x, float y) {
    	this.addX(-x);
    	this.addY(-y);
    }
    
    public void multiply(float scale) {
    	setX(getX() * scale);
    	setY(getY() * scale);
    }
    
    //length
    
    public float length() {
    	return (float)Math.sqrt(lengthSquared());
    }
    
    public float lengthSquared() {
    	float x = getX();
    	float y = getY();
    	return x * x + y * y;
    }
    
    public float length(Point2d center) {
    	return (float)Math.sqrt(lengthSquared(center));
    }
    
    public float lengthSquared(Point2d center) {
    	return lengthSquared(center.getX(), center.getY());
    }
    
    public float length(float ox, float oy) {
    	return (float)Math.sqrt(lengthSquared(ox, oy));
    }
    
    public float lengthSquared(float ox, float oy) {
    	float x = this.getX() - ox;
    	float y = this.getY() - oy;
    	return x * x + y * y;
    }
    
    public void setLength(float length) {
    	float l = length();
    	if(l != 0) multiply(length / l);
    }
    
    public void setLength(Point2d center, float length) {
    	subtract(center);
    	float l = length();
    	if(l != 0) multiply(length / l);
    	translate(center);
    }
    
    //rotation
    
    public float rotation() {
    	return (float)Math.toDegrees(Math.atan2(getY(), getX()));
    }
    
    public float rotationRadians() {
    	return (float)Math.atan2(getY(), getX());
    }
    
    public float rotation(Point2d center) {
    	return (float)Math.toDegrees(rotationRadians(center));
    }
    
    public float rotationRadians(Point2d center) {
    	float x = center.getX() - this.getX();
    	float y = center.getY() - this.getY();
    	return (float)Math.atan2(y, x);
    }
    
    //rotate
    
    public void rotate(float degrees) {
    	rotate((float)Math.toRadians(degrees));
    }
    
    public void rotateRadians(float radians) {
    	radians += rotationRadians();
    	float len = length();
    	this.setX((float)Math.cos(radians) * len);
    	this.setY((float)Math.sin(radians) * len);
    }
    
    public void rotateAround(Point2d center, float degrees) {
    	rotateAroundRadians(center, (float)Math.toRadians(degrees));
    }
    
    public void rotateAroundRadians(Point2d center, float radians) {
    	translate(-center.getX(), -center.getY());
    	rotateRadians(radians);
    	translate(center);
    }
    
    //misc
    
    public Space getQuadrant() {
    	return Space.fromRadians(rotationRadians());
    }
    
    public Point2d clone() {
    	return new Point2d(getX(), getY());
    }
    
    public Point2d negative() {
    	return new Point2d(-getX(), -getY());
    }
    
    public String toString() {
    	return "(" + getX() + ", " + getY() + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof Point2d) {
            Point2d p = (Point2d)o;
            return p.getX() == getX() && p.getY() == getY();
        }
        return false;
    }

    @Override
    public int hashCode() {
    	//uses the lowest bits of each part
        return (Float.floatToIntBits(this.getX()) << 16) + (Float.floatToIntBits(this.getY()) & 65535);
    }

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeFloat(getX());
		out.writeFloat(getY());
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		setX(in.readFloat());
		setY(in.readFloat());
	}

	@Override
	public Point2d getPosition() {
		return this;
	}

	@Override
	public void setPosition(Point2d position) {
		this.x = position.x;
		this.y = position.y;
	}

	@Override
	public int getState(Point2d point) {
		return equals(point) ? POINT_OUTSIDE : POINT_ON;
	}

	@Override
	public Point2d getClosestPoint(Point2d point) {
		return this;
	}

	@Override
	public float getArea() {
		return 0;
	}
    
    public Point2d addCopy(Point2d v) {
    	return new Point2d(x + v.x, y + v.y);
    }
    
    public Point2d subtractCopy(Point2d v) {
    	return new Point2d(x - v.x, y - v.y);
    }
    
    public Point2d scaleCopy(float f) {
    	return new Point2d(x * f, y * f);
    }
    
    public Point2d scaleCopy(Point2d v) {
    	return new Point2d(x * v.x, y * v.y);
    }
    
    public Point2d descaleCopy(float f) {
    	return new Point2d(x / f, y / f);
    }
    
    public Point2d descaleCopy(Point2d v) {
    	return new Point2d(x / v.x, y / v.y);
    }
    
    public Point2d negateCopy() {
    	return new Point2d(-x, -y);
    }
    
    public boolean isNaN() {
    	return Float.isNaN(x) || Float.isNaN(y);
    }
    
    public float[] toArray() {
    	return new float[]{x, y};
    }
}
