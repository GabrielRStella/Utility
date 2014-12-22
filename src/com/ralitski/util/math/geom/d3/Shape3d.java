package com.ralitski.util.math.geom.d3;

public interface Shape3d {
	Line3d getIntersection(Line3d line);
	boolean contains(Point3d point);
	Point3d getClosestPointTo(Point3d point);
	
	Surface3d project(Plane plane, Vector3d direction);
	Surface3d crossSection(Plane plane);
}
