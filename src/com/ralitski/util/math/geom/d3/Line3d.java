package com.ralitski.util.math.geom.d3;

import com.ralitski.util.math.geom.Interval;


public class Line3d implements Surface3d, Cloneable {
	
	public static boolean colinear(Point3d a, Point3d b, Point3d c) {
		return new Line3d(a, b).equals(new Line3d(b, c));
	}
	
	private Plane plane;

	private Point3d base;
	private Vector3d slope;
	
	public Line3d(Line3d l) {
		this(l.base.clone(), l.slope.clone());
	}
	
	public Line3d(Point3d p) {
		this(p, new Vector3d());
	}
	
	public Line3d(Vector3d v) {
		this(Point3d.origin(), v);
	}
	
	public Line3d(Point3d a, Point3d b) {
		this(a, new Vector3d(a, b));
	}
	
	public Line3d(Point3d p, Vector3d v) {
		this.base = p;
		this.slope = v;
	}
	
	public Point3d getBase() {
		return base;
	}
	
	public Vector3d getSlope() {
		return slope;
	}
	
	public Plane getPlane() {
		return plane;
	}
	
	public Interval getInterval() {
		return Interval.ALL;
	}
	
	public void setPlane(Plane plane) {
		if(plane.getOrthogonalVector().isOrthogonal(slope)) {
			this.plane = plane;
		} else {
			throw new IllegalArgumentException("Line must pass through its plane");
		}
	}
	
	public boolean isPoint() {
		return slope.isEmpty();
	}
	
	//had to work this out by hand, uuuugh
	public boolean contains(Point3d p) {
		float x = (p.getX() - base.getX()) / slope.getX();
		float y = (p.getY() - base.getY()) / slope.getY();
		if(x == y) {
			//EXTREME OPTIMIZATION VIA IF-STATEMENTS
			float z = (p.getZ() - base.getZ()) / slope.getZ();
			return y == z;
		} else return false;
	}
	
	public float getXFromY(float y) {
		return slope.getX() * (y - base.getY()) / slope.getY() + base.getX();
	}
	
	public float getXFromZ(float z) {
		return slope.getX() * (z - base.getZ()) / slope.getZ() + base.getX();
	}
	
	public float getYFromX(float x) {
		return slope.getY() * (x - base.getX()) / slope.getX() + base.getY();
	}
	
	public float getYFromZ(float z) {
		return slope.getY() * (z - base.getZ()) / slope.getZ() + base.getY();
	}
	
	public float getZFromX(float x) {
		return slope.getZ() * (x - base.getX()) / slope.getX() + base.getZ();
	}
	
	public float getZFromY(float y) {
		return slope.getZ() * (y - base.getY()) / slope.getY() + base.getZ();
	}
	
	public float getXFromT(float t) {
		return t * slope.getX() + base.getX();
	}
	
	public float getYFromT(float t) {
		return t * slope.getY() + base.getY();
	}
	
	public float getZFromT(float t) {
		return t * slope.getZ() + base.getZ();
	}
	
	public float getTFromX(float x) {
		return (x - base.getX()) / slope.getX();
	}
	
	public float getTFromY(float y) {
		return (y - base.getY()) / slope.getY();
	}
	
	public float getTFromZ(float z) {
		return (z - base.getZ()) / slope.getZ();
	}
	
	public Point3d getPointFromX(float x) {
		float y = getYFromX(x);
		float z = getZFromX(x);
		return new Point3d(x, y, z);
	}
	
	public Point3d getPointFromY(float y) {
		float x = getXFromY(y);
		float z = getZFromY(y);
		return new Point3d(x, y, z);
	}
	
	public Point3d getPointFromZ(float z) {
		float x = getXFromZ(z);
		float y = getYFromZ(z);
		return new Point3d(x, y, z);
	}
	
	public Point3d getPointFromT(float t) {
		float x = getXFromT(t);
		float y = getYFromT(t);
		float z = getZFromT(t);
		return new Point3d(x, y, z);
	}
	
	//THE ALGEBRA
	public Line3d getIntersection(Line3d other) {
		if(slope.isParallel(other.slope)) return contains(other.getBase()) ? clone() : null;
		float f1 = (base.getX() * other.slope.getY() - other.base.getX() * slope.getY()) / base.getY() / other.base.getY();
		float f2 = other.base.getX() * slope.getX() / other.slope.getY();
		float f3 = base.getX() * other.slope.getX() / base.getY();
		float f4 = (other.base.getX() / other.base.getY()) - (base.getX() / base.getY());
		float x = f1 + f2 - f3;
		x /= f4;
		Point3d p = getPointFromX(x);
		if(other.contains(p)) return new Line3d(p);
		else return null;
	}
	
	public Line3d getParallelThrough(Point3d thru) {
		return new Line3d(thru, slope.clone());
	}
	
	public Line3d getPerpendicularThrough(Point3d thru) {
		return new Line3d(thru, new Vector3d(thru, getClosestPointTo(thru)));
	}
	
	public Point3d getClosestPointTo(Point3d other) {
		if(contains(other)) {
			return other;
		} else {
			Plane p = new Plane(other, slope);
			//this will never be parallel
			return p.getIntersection(this).base;
		}
	}
	
	public float distance(Point3d p) {
		return getClosestPointTo(p).length(p);
	}
	
	public Line3d clone() {
		return new Line3d(base.clone(), slope.clone());
	}
//    
//    public Segment2d getBetweenX(float start, float end) {
//        return Segment2d.betweenX(this, start, end);
//    }
//    
//    public Segment2d getBetweenY(float start, float end) {
//        return Segment2d.betweenY(this, start, end);
//    }
//    
//    /**
//     * 
//     * @param start
//     * @param direction true for ray extending in positive direction
//     * @return 
//     */
//    public Ray2d getfromX(float start, boolean direction) {
//        return Ray2d.fromX(this, start, direction);
//    }
//    
//    /**
//     * 
//     * @param start
//     * @param direction true for ray extending in positive direction
//     * @return 
//     */
//    public Ray2d getfromY(float start, boolean direction) {
//        return Ray2d.fromY(this, start, direction);
//    }

	@Override
	public Line3d project(Plane plane, Vector3d direction) {
		Line3d intersect = new Line3d(base, direction);
		Line3d line = plane.getIntersection(intersect);
		if(line == null) {
			return null; //no intersection
		} else if(line.isPoint()) {
			//make a new line
			Point3d newBase = line.getBase();
			Vector3d newSlope = slope.project(plane.getOrthogonalVector());
			line = new Line3d(newBase, newSlope);
		} else {
			line = clone();
		}
		line.plane = plane;
		return line;
	}

	@Override
	public Surface3d crossSection(Plane plane) {
		Line3d line = plane.getIntersection(this);
		if(line != null) {
			if(line.isPoint()) {
				return line.getBase().getAsSurface(plane.getOrthogonalVector());
			} else {
				Line3d newLine = clone();
				newLine.plane = plane;
				return newLine;
			}
		} else {
			return null;
		}
	}
	
	public String toString() {
		return "[t" + slope + " + " + base + "]";
	}
	
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if(o instanceof Line3d) {
			Line3d line = (Line3d)o;
			return line.getInterval().equals(this.getInterval()) && line.getSlope().isParallel(getSlope()) && line.contains(getBase());
		} else return false;
	}
}
