package com.ralitski.util.math.geom.d3;

import com.ralitski.util.math.geom.Interval;

public class Segment3d extends Line3d {
	
	private Interval interval;

	public Segment3d(Line3d l) {
		super(l);
		interval = Interval.ALL; //omg it's a real line
	}
	
	public Segment3d(Point3d p) {
		super(p);
		interval = Interval.NONE;
	}
	
	public Segment3d(Point3d p1, Point3d p2) {
		super(p1, p2);
		float firstT = getTFromX(p1.getX());
		if(Float.isNaN(firstT)) firstT = getTFromY(p1.getY());
		if(Float.isNaN(firstT)) firstT = getTFromZ(p1.getZ());
		float secondT = getTFromX(p2.getX());
		if(Float.isNaN(secondT)) secondT = getTFromY(p2.getY());
		if(Float.isNaN(secondT)) secondT = getTFromZ(p2.getZ());
		interval = new Interval(Math.min(firstT, secondT), true, Math.max(firstT, secondT), true);
	}
	
	public void setInterval(Interval i) {
		interval = i;
	}
	
	public Interval getInterval() {
		return interval;
	}
	
	public Point3d getMin() {
		return getPointFromT(interval.getMin());
	}
	
	public Point3d getMax() {
		return getPointFromT(interval.getMax());
	}
	
	public boolean contains(Point3d p) {
		return containsT(getTFromX(p.getX())) && super.contains(p);
	}
	
	private boolean containsX(float x) {
		return containsT(getTFromX(x));
	}
	
	private boolean containsY(float y) {
		return containsT(getTFromY(y));
	}
	
	private boolean containsZ(float z) {
		return containsT(getTFromZ(z));
	}
	
	private boolean containsT(float t) {
		return interval == null || interval.includes(t);
	}
	
	public float getXFromY(float y) {
		return containsY(y) ? super.getXFromY(y) : Float.NaN;
	}
	
	public float getXFromZ(float z) {
		return containsZ(z) ? super.getXFromZ(z) : Float.NaN;
	}
	
	public float getYFromX(float x) {
		return containsX(x) ? super.getYFromX(x) : Float.NaN;
	}
	
	public float getYFromZ(float z) {
		return containsZ(z) ? super.getYFromZ(z) : Float.NaN;
	}
	
	public float getZFromX(float x) {
		return containsX(x) ? super.getZFromX(x) : Float.NaN;
	}
	
	public float getZFromY(float y) {
		return containsY(y) ? super.getZFromY(y) : Float.NaN;
	}
	
	public float getXFromT(float t) {
		return containsT(t) ? super.getXFromT(t) : Float.NaN;
	}
	
	public float getYFromT(float t) {
		return containsT(t) ? super.getYFromT(t) : Float.NaN;
	}
	
	public float getZFromT(float t) {
		return containsT(t) ? super.getZFromT(t) : Float.NaN;
	}
	
	public float getTFromX(float x) {
		float t = super.getTFromX(x);
		return containsT(t) ? t : Float.NaN;
	}
	
	public float getTFromY(float y) {
		float t = super.getTFromY(y);
		return containsT(t) ? t : Float.NaN;
	}
	
	public float getTFromZ(float z) {
		float t = super.getTFromZ(z);
		return containsT(t) ? t : Float.NaN;
	}
	
	public Point3d getPointFromX(float x) {
		return containsX(x) ? super.getPointFromX(x) : null;
	}
	
	public Point3d getPointFromY(float y) {
		return containsY(y) ? super.getPointFromY(y) : null;
	}
	
	public Point3d getPointFromZ(float z) {
		return containsZ(z) ? super.getPointFromZ(z) : null;
	}
	
	public Point3d getPointFromT(float t) {
		return containsT(t) ? super.getPointFromT(t) : null;
	}
	
	public Line3d getIntersection(Line3d other) {
		Line3d line = super.getIntersection(other);
		return line != null ? (line.isPoint() ? line : clone()) : null;
	}
	
	public Line3d getPerpendicularThrough(Point3d thru) {
		//make a new, unbounded line to prevent borks
		return new Line3d(thru, new Vector3d(thru, new Line3d(this).getClosestPointTo(thru)));
	}
	
	public Point3d getClosestPointTo(Point3d other) {
		Point3d p = super.getClosestPointTo(other);
		if(p == null) {
			//if the point is not on a line perpendicular to this
			Point3d min = getMin();
			Point3d max = getMax();
			float dist1 = min.lengthSquared(other);
			float dist2 = max.lengthSquared(other);
			return dist1 < dist2 ? min : max;
		}
		return p;
	}
	
	public float distance(Point3d p) {
		return Math.min(super.distance(p), distanceFromEnds(p));
	}
	
	public float distanceFromEnds(Point3d p) {
		return Math.min(getMin().length(p), getMax().length(p));
	}

	@Override
	public Segment3d project(Plane plane, Vector3d direction) {
		Line3d line = super.project(plane, direction);
		if(direction.isAligned(getSlope())) {
			return (Segment3d)line; //super calls clone()
		}
		Point3d start = getPointFromT(interval.getMin());
		Point3d end = getPointFromT(interval.getMax());
		Line3d startLine = new Line3d(start, direction);
		Line3d endLine = new Line3d(end, direction);
		return new Segment3d(line.getIntersection(startLine).getBase(), line.getIntersection(endLine).getBase());
	}
	
	public Segment3d clone() {
		Segment3d s = new Segment3d(this);
		s.setInterval(interval); //intervals are immutable and can be reused
		return s;
	}
	
	public String toString() {
		return super.toString() + interval;
	}

}
