package com.ralitski.util.math.geom.d3;

public class Circle3d implements Surface3d {

	private Point3d center;
	private Plane plane;
	private float radius;
	
	public Circle3d(Plane plane, Point3d center) {
		this(plane, center, plane.getOrthogonalVector().magnitude());
	}

	public Circle3d(Plane plane, Point3d center, float radius) {
		this.plane = plane;
		this.center = center;
		this.radius = radius;
	}

	@Override
	public Line3d getIntersection(Line3d line) {
		line = plane.getIntersection(line);
		if(line == null) {
			return null;
		} else if(line.isPoint()) {
			Point3d p = line.getBase();
			return contains(p) ? line : null;
		} else {
			//TODO
			//get the intersecting segment
		}
	}

	@Override
	public boolean contains(Point3d point) {
		return plane.contains(point) && point.length(center) <= radius;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		if(contains(point)) {
			return point;
		} else {
			Point3d p = plane.getClosestPointTo(point);
			if(contains(p)) {
				return p;
			} else {
				p.setLength(center, radius);
				return p;
			}
		}
	}

	//UUUGH this is gonna SUUUUUUUUUUUCK to work out
	//will sometimes return ellipses and stuff
	// ;-;
	@Override
	public Surface3d project(Plane plane, Vector3d direction) {
	}

	@Override
	public Surface3d crossSection(Plane plane) {
		Object o = this.plane.getIntersection(plane);
		return o != null ? (o instanceof Plane ? this : getIntersection((Line3d)o)) : null;
	}

	@Override
	public Plane getPlane() {
		return plane;
	}

}
