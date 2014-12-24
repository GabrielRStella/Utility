package com.ralitski.util.math.geom.d3;

import com.ralitski.util.math.geom.Interval;

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
	
	public Surface3d getSurface() {
		return base;
	}
	
	public Vector3d getDirection() {
		return direction;
	}

	@Override
	public Line3d getIntersection(Line3d line) {
		Line3d project = line.project(base.getPlane(), direction);
		Line3d surfaceIntersection = base.getIntersection(project);
		if(surfaceIntersection == null) {
			return null;
		} else if(surfaceIntersection.getInterval().equals(Interval.ALL)) {
			//the surface of this extrusion is infinite (probably a line)
			Plane plane = base.getPlane();
			Line3d start = plane.getIntersection(line);
			Point3d base;
			try {
				base = plane.getXIntercept();
			} catch(Exception e) {
				//the plane doesn't intersect through the x-axis
				base = plane.getYIntercept();
			}
			base.translate(direction);
			Plane extrudedPlane = new Plane(base, direction);
			Line3d end = extrudedPlane.getIntersection(line);
			if(start != null) {
				if(start.isPoint()) {
					//both will be points
					return new Segment3d(start.getBase(), end.getBase());
				} else {
					//the line is on the surface's plane
					return start;
				}
			} else if(end != null) {
				//the given line is parallel to the extruded plane
				return end;
			} else {
				//the line is parallel to the plane, but not directly on the plane or extruded plane
				base = line.getBase();
				Point3d closest = plane.getClosestPointTo(base);
				Vector3d v = new Vector3d(closest, base);
				if(v.isAligned(direction) && v.magnitude() <= direction.magnitude()) {
					return line;
				} else {
					//the line is in front of or behind the extrusion
					return null;
				}
			}
		} else {
			//find the bounds of it and whatnot
		}
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
	}

	@Override
	public Surface3d crossSection(Plane plane) {
	}

}
