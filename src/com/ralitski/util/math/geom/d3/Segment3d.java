package com.ralitski.util.math.geom.d3;

import com.ralitski.util.math.geom.Interval;

public class Segment3d extends Line3d {
	
	private Interval interval;

	public Segment3d(Line3d l) {
		super(l);
	}
	
	public Segment3d(Point3d p) {
		super(p);
	}
	
	public Segment3d(Point3d p1, Point3d p2) {
		super(p1, p2);
		interval = new Interval(getTFromX(p1.getX()), true, getTFromX(p2.getX()), true);
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
		return interval.includes(getTFromX(p.getX())) && super.contains(p);
	}
	
	private boolean containsX(float x) {
		return interval.includes(getTFromX(x));
	}
	
	private boolean containsY(float y) {
		return interval.includes(getTFromY(y));
	}
	
	private boolean containsZ(float z) {
		return interval.includes(getTFromZ(z));
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
		return interval.includes(t) ? super.getXFromT(t) : Float.NaN;
	}
	
	public float getYFromT(float t) {
		return interval.includes(t) ? super.getYFromT(t) : Float.NaN;
	}
	
	public float getZFromT(float t) {
		return interval.includes(t) ? super.getXFromT(t) : Float.NaN;
	}
	
	public float getTFromX(float x) {
		float t = super.getTFromX(x);
		return interval.includes(t) ? t : Float.NaN;
	}
	
	public float getTFromY(float y) {
		float t = super.getTFromY(y);
		return interval.includes(t) ? t : Float.NaN;
	}
	
	public float getTFromZ(float z) {
		float t = super.getTFromX(z);
		return interval.includes(t) ? t : Float.NaN;
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
		return interval.includes(t) ? super.getPointFromT(t) : null;
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
	
	public Segment3d clone() {
		Segment3d s = new Segment3d(this);
		s.setInterval(interval); //intervals are immutable and can be reused
		return s;
	}

}
