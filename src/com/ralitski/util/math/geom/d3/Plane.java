package com.ralitski.util.math.geom.d3;


public class Plane {
	
	private float x;
	private float y;
	private float z;
	private float c;
	
	public Plane(Triangle3d triangle) {
		this(triangle.getA(), triangle.getB(), triangle.getC());
	}
	
	public Plane(Point3d a, Point3d b, Point3d c) {
		//screw the "constructor call must be first" thing :u
		this(a, new Vector3d(a, b).crossProduct(new Vector3d(a, c)));
	}
	
	public Plane(Point3d base, Point3d other) {
		this(base, new Vector3d(other, base));
	}
	
	public Plane(Point3d passThrough, Vector3d orthogonal) {
		x = orthogonal.getX();
		y = orthogonal.getY();
		z = orthogonal.getZ();
		c = -(x * passThrough.getX() + y * passThrough.getY() + z * passThrough.getZ());
	}
	
	public Plane(float x, float y, float z, float c) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.c = c;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getC() {
		return c;
	}

	public void setC(float c) {
		this.c = c;
	}
	
	public Point3d getXIntercept() {
		return new Point3d(-(c / x), 0, 0);
	}
	
	public Point3d getYIntercept() {
		return new Point3d(0, -(c / y), 0);
	}
	
	public Point3d getZIntercept() {
		return new Point3d(0, 0, -(c / z));
	}
	
	public boolean contains(Point3d p) {
		float total = c;
		total += x * p.getX();
		total += y * p.getY();
		total += z * p.getZ();
		return total == 0;
	}
	
	public boolean contains(Vector3d vector) {
		return vector.isOrthogonal(getOrthogonalVector());
	}
	
	public Vector3d getOrthogonalVector() {
		return new Vector3d(x, y, z);
	}
	
	public float getAngleTo(Plane other) {
		return getOrthogonalVector().getAngleTo(other.getOrthogonalVector());
	}
	
	public Line3d getIntersection(Line3d line) {
		Point3d p = line.getBase();
		Vector3d v = line.getSlope();
		float f1 = -c - x * p.getX() - y * p.getY() - z * p.getZ();
		float f2 = x * v.getX() + y * v.getY() + z * v.getZ();
		if(f2 == 0F) return line.clone();
		float t = f1 / f2;
		return new Line3d(line.getPointFromT(t));
	}
	
	public Line3d getIntersection(Plane plane) {
		if(plane.getOrthogonalVector().equals(this.getOrthogonalVector())) {
			if(plane.contains(getXIntercept())) {
				//same plane
			} else {
				return null;
			}
		}
	}
	
	public Point3d getClosestPointTo(Point3d point) {
		Line3d line = getIntersection(new Line3d(point, getOrthogonalVector()));
		return line.isPoint() ? line.getBase() : point;
	}
}
