package com.ralitski.util.math.geom.d3;

public class Triangle3d implements Surface3d, Cloneable {
	
	private Point3d a;
	private Point3d b;
	private Point3d c;
	
	public Triangle3d(Point3d a, Point3d b, Point3d c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Point3d getA() {
		return a;
	}

	public void setA(Point3d a) {
		this.a = a;
	}

	public Point3d getB() {
		return b;
	}

	public void setB(Point3d b) {
		this.b = b;
	}

	public Point3d getC() {
		return c;
	}

	public void setC(Point3d c) {
		this.c = c;
	}
	
	public Segment3d getAB() {
		return new Segment3d(a, b);
	}
	
	public Segment3d getBC() {
		return new Segment3d(b, c);
	}
	
	public Segment3d getCA() {
		return new Segment3d(c, a);
	}
	
	public Line3d getIntersection(Line3d line) {
		Line3d intersection = getPlane().getIntersection(line);
		return intersection.isPoint() ? (contains(line.getBase()) ? intersection : null) : getIntersectionInternal(intersection);
	}
	
	//called if a line is on the triangle's plane
	private Line3d getIntersectionInternal(Line3d line) {
		Line3d ab = line.getIntersection(new Line3d(a, b));
		Line3d bc = line.getIntersection(new Line3d(b, c));
		Line3d ca = line.getIntersection(new Line3d(c, a));
		Point3d p1 = null;
		Point3d p2 = null;
		if(ab != null) {
			if(ab.isPoint()) {
				p1 = ab.getBase();
			} else {
				return new Segment3d(a, b);
			}
		}
		if(bc != null) {
			if(bc.isPoint()) {
				if(p1 == null) p1 = bc.getBase();
				else {
					p2 = bc.getBase();
					return new Segment3d(p1, p2);
				}
			} else {
				return new Segment3d(b, c);
			}
		}
		if(ca != null) {
			if(ca.isPoint()) {
				if(p1 == null) p1 = ca.getBase();
				else {
					p2 = ca.getBase();
					return new Segment3d(p1, p2);
				}
			} else {
				return new Segment3d(c, a);
			}
		}
		//no intersection
		return null;
	}
	
	public Plane getPlane() {
		return new Plane(a, b, c);
	}
	
	public boolean contains(Point3d p) {
		//check distances to opposite sides and whatever
		Line3d bc = new Line3d(b, c);
		Line3d ap = new Line3d(a, p);
		
		Line3d ca = new Line3d(c, a);
		Line3d bp = new Line3d(b, p);

		Line3d ab = new Line3d(a, b);
		Line3d cp = new Line3d(c, p);

		Line3d abcL = ap.getIntersection(bc);
		Line3d cabL = cp.getIntersection(ab);
		Line3d bcaL = bp.getIntersection(ca);
		
		if(abcL != null && abcL.isPoint() && cabL != null &&  cabL.isPoint() && bcaL != null &&  bcaL.isPoint()) {
			Point3d abc = abcL.getBase();
			Point3d cab = cabL.getBase();
			Point3d bca = bcaL.getBase();
			
			return a.length(p) <= a.length(abc)
					&& b.length(p) <= b.length(bca)
					&& c.length(p) <= c.length(cab);
		}
		return false;
	}
	
	public Triangle3d project(Plane p, Vector3d v) {
		//Vector3d v = p.getOrthogonalVector();
		Line3d lineA = new Line3d(a, v);
		Line3d lineB = new Line3d(b, v);
		Line3d lineC = new Line3d(c, v);
		//none will be parallel because the line is using the orthogonal vector of the plane
		Point3d a = p.getIntersection(lineA).getBase();
		Point3d b = p.getIntersection(lineB).getBase();
		Point3d c = p.getIntersection(lineC).getBase();
		return new Triangle3d(a, b, c);
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		Point3d p = getPlane().getClosestPointTo(point);
		if(contains(p)) {
			return p;
		} else {
			Point3d AB = getAB().getClosestPointTo(point);
			Point3d BC = getBC().getClosestPointTo(point);
			Point3d CA = getCA().getClosestPointTo(point);
			float fAB = AB.lengthSquared(point);
			float fBC = BC.lengthSquared(point);
			float fCA = CA.lengthSquared(point);
			return fAB < fBC ? (fAB < fCA ? AB : CA) : (fBC < fCA ? BC : CA);
		}
	}

	@Override
	public Surface3d crossSection(Plane plane) {
		Plane thisPlane = getPlane();
		if(plane == thisPlane) {
			return clone();
		} else {
			Line3d planeIntersection = (Line3d)plane.getIntersection(thisPlane);
			if(planeIntersection != null) {
				return getIntersection(planeIntersection);
			} else {
				return null;
			}
		}
	}
	
	public Triangle3d clone() {
		return new Triangle3d(a.clone(), b.clone(), c.clone());
	}
	
	public String toString() {
		return "[" + a.toString() + ", " + b.toString() + ", " + c.toString() + "]";
	}
}
