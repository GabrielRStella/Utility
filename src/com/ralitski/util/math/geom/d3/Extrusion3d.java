package com.ralitski.util.math.geom.d3;

public class Extrusion3d implements Shape3d {
	
	private Surface3d base;
	private Vector3d direction;
	
	public Extrusion3d(Surface3d s, float length) {
		this.base = s;
		direction = s.getPlane().getOrthogonalVector();
		direction.setMagnitude(length);
	}
	
	public Extrusion3d(Surface3d s, Vector3d direction) {
		this.base = s;
		this.direction = direction;
		if(direction.isOrthogonal(s.getPlane().getOrthogonalVector())) throw new IllegalArgumentException("Can't extrude sideways!");
	}

	@Override
	public Line3d getIntersection(Line3d line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Point3d point) {
		// TODO Auto-generated method stub
		Line3d project = new Line3d(point, direction);
		Line3d line = base.getIntersection(project);
		if(line != null) {
			if(line.isPoint()) {
				Point3d p = line.getBase();
				return p.length(point) <= direction.magnitude();
			} else {
				//um what happened
				//bork
				throw new UnsupportedOperationException("No suitable course of action has been decided for this outcome. Sorry.");
			}
		} else return false;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		Line3d project = new Line3d(point, direction);
		Line3d line = base.getPlane().getIntersection(project);
		if(line != null && base.contains(line.getBase())) {
			return point;
		} else if(line != null) {
			Point3d planeIntersection = line.getBase();
			Point3d closestOnPlane = base.getClosestPointTo(planeIntersection);
			Vector3d distanceFromPlane = new Vector3d(point, planeIntersection);
			if(distanceFromPlane.magnitude() <= direction.magnitude()) {
				closestOnPlane.translate(distanceFromPlane);
				return closestOnPlane;
			} else {
				Vector3d inverseDirection = direction.clone();
				inverseDirection.invert();
				Vector3d distanceExtra = distanceFromPlane.clone();
				distanceExtra.add(inverseDirection);
				planeIntersection.translate(distanceExtra);
				closestOnPlane = base.getClosestPointTo(planeIntersection);
				closestOnPlane.translate(distanceFromPlane);
				return closestOnPlane;
			}
		} else {
			//bork
			throw new UnsupportedOperationException("No suitable course of action has been decided for this outcome. Sorry.");
		}
	}

	@Override
	public Surface3d project(Plane plane, Vector3d direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Surface3d crossSection(Plane plane) {
		// TODO Auto-generated method stub
		return null;
	}

}
